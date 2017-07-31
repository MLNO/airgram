package org.telegram.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import org.telegram.messenger.R;
import org.telegram.ui.Components.ColorPickerDialog;
import org.telegram.ui.Components.DrawingView;

public class DrawPaint extends FragmentActivity
    implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
  private Button mSaveButton, mPenButton, mEraserButton, mPenColorButton, mBackgroundColorButton;
  private DrawingView mDrawingView;
  private SeekBar mPenSizeSeekbar, mEraserSeekbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_paint);

    System.out.println(getIntent().getExtras().getString(MediaStore.EXTRA_OUTPUT,"000"));
    initializeUI();
    setListeners();
  }

  private void setListeners() {
    mSaveButton.setOnClickListener(this);
    mPenButton.setOnClickListener(this);
    mEraserButton.setOnClickListener(this);
    mPenColorButton.setOnClickListener(this);
    mBackgroundColorButton.setOnClickListener(this);
    mPenSizeSeekbar.setOnSeekBarChangeListener(this);
    mEraserSeekbar.setOnSeekBarChangeListener(this);
  }

  private void initializeUI() {
    mDrawingView = (DrawingView) findViewById(R.id.scratch_pad);
    mSaveButton = (Button) findViewById(R.id.save_button);
    mPenButton = (Button) findViewById(R.id.pen_button);
    mEraserButton = (Button) findViewById(R.id.eraser_button);
    mPenColorButton = (Button) findViewById(R.id.pen_color_button);
    mBackgroundColorButton = (Button) findViewById(R.id.background_color_button);
    mPenSizeSeekbar = (SeekBar) findViewById(R.id.pen_size_seekbar);
    mEraserSeekbar = (SeekBar) findViewById(R.id.eraser_size_seekbar);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.save_button:
        String url = getIntent().getExtras().getString(MediaStore.EXTRA_OUTPUT,"000");
        if (!url.equals("000")) {
          Boolean rest = mDrawingView.saveImage(url, "test",
                  Bitmap.CompressFormat.JPEG, 100);
          Intent returnIntent = new Intent();
          returnIntent.putExtra("result",rest);
          returnIntent.putExtra("url",url);
          setResult(61,returnIntent);
          finish();
        }else {
          mDrawingView.saveImage(url, "test",
                  Bitmap.CompressFormat.PNG, 100);
        }
        break;
      case R.id.pen_button:
        mDrawingView.initializePen();
        break;
      case R.id.eraser_button:
        mDrawingView.initializeEraser();
        break;
      case R.id.pen_color_button:
        int initialColor = Color.WHITE;
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {
          @Override
          public void onColorSelected(int color) {
            System.out.println(color);
            mDrawingView.setPenColor(color);
          }
        });
        colorPickerDialog.show();
        break;
      case R.id.background_color_button:
        int initialColor2 = Color.BLACK;
        ColorPickerDialog colorPickerDialog2 = new ColorPickerDialog(this, initialColor2, new ColorPickerDialog.OnColorSelectedListener() {
          @Override
          public void onColorSelected(int color) {
            System.out.println(color);
            mDrawingView.setBackgroundColor(color);
          }
        });
        colorPickerDialog2.show();
        break;
    }
  }

  @Override public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    switch (seekBar.getId()) {
      case R.id.pen_size_seekbar:
        mDrawingView.setPenSize(i);
        break;
      case R.id.eraser_size_seekbar:
        mDrawingView.setEraserSize(i);
        break;
    }
  }

  @Override public void onStartTrackingTouch(SeekBar seekBar) {
  }

  @Override public void onStopTrackingTouch(SeekBar seekBar) {
  }
}
