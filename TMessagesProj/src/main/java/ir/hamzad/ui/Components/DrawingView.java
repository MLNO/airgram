package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawingView extends View {
  private static final float TOUCH_TOLERANCE = 4;
  private Bitmap mBitmap;
  private Canvas mCanvas;
  private Path mPath;
  private Paint mBitmapPaint;
  private Paint mPaint;
  private boolean mDrawMode;
  private float mX, mY;
  private float mPenSize = 10;
  private float mEraserSize = 10;

  public DrawingView(Context c) {
    this(c, null);
  }

  public DrawingView(Context c, AttributeSet attrs) {
    this(c, attrs, 0);
  }

  public DrawingView(Context c, AttributeSet attrs, int defStyle) {
    super(c, attrs, defStyle);
    init();
  }

  private void init() {
    mPath = new Path();
    mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    mPaint.setColor(Color.WHITE);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeJoin(Paint.Join.ROUND);
    mPaint.setStrokeCap(Paint.Cap.ROUND);
    mPaint.setStrokeWidth(mPenSize);
    mDrawMode = true;
    mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    mCanvas = new Canvas(mBitmap);
    mCanvas.drawColor(Color.BLACK);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    canvas.drawPath(mPath, mPaint);
  }

  private void touch_start(float x, float y) {
    mPath.reset();
    mPath.moveTo(x, y);
    mX = x;
    mY = y;
    mCanvas.drawPath(mPath, mPaint);
  }

  private void touch_move(float x, float y) {
    float dx = Math.abs(x - mX);
    float dy = Math.abs(y - mY);
    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
      mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
      mX = x;
      mY = y;
    }
    mCanvas.drawPath(mPath, mPaint);
  }

  private void touch_up() {
    mPath.lineTo(mX, mY);
    mCanvas.drawPath(mPath, mPaint);
    mPath.reset();
    if (mDrawMode) {
      mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
    } else {
      mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        if (!mDrawMode) {
          mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
          mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        }
        touch_start(x, y);
        invalidate();
        break;
      case MotionEvent.ACTION_MOVE:
        touch_move(x, y);
        if (!mDrawMode) {
          mPath.lineTo(mX, mY);
          mPath.reset();
          mPath.moveTo(x, y);
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        touch_up();
        invalidate();
        break;
    }
    return true;
  }

  public void initializePen() {
    mDrawMode = true;
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeJoin(Paint.Join.ROUND);
    mPaint.setStrokeCap(Paint.Cap.ROUND);
    mPaint.setStrokeWidth(mPenSize);
    mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
  }

  public void initializeEraser() {
    mDrawMode = false;
    mPaint.setColor(Color.parseColor("#f4f4f4"));
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(mEraserSize);
    mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
  }

  @Override public void setBackgroundColor(int color) {
    mCanvas.drawColor(color);
    super.setBackgroundColor(color);
  }

  public void setEraserSize(float size) {
    mEraserSize = size;
    initializeEraser();
  }

  public void setPenSize(float size) {
    mPenSize = size;
    initializePen();
  }

  public float getEraserSize() {
    return mEraserSize;
  }

  public float getPenSize() {
    return mPenSize;
  }

  public void setPenColor(@ColorInt int color) {
    mPaint.setColor(color);
  }

  public @ColorInt int getPenColor() {
    return mPaint.getColor();
  }

  public boolean saveImage(String filePath, String filename, Bitmap.CompressFormat format,
      int quality) {
    if (quality > 100) {
      Log.d("saveImage", "quality cannot be greater that 100");
      return false;
    }
    File file;
    FileOutputStream out = null;
    try {
      switch (format) {
        case PNG:
          file = new File(filePath, filename + ".png");
          out = new FileOutputStream(file);
          return mBitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
        case JPEG:
          file = new File(filePath);
          out = new FileOutputStream(file);
          return mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        default:
          file = new File(filePath, filename + ".png");
          out = new FileOutputStream(file);
          return mBitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
