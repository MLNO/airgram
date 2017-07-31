package TextTypeface;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class PTextView extends android.support.v7.widget.AppCompatTextView {
    public PTextView(Context context) {
        super(context);
        if (!isInEditMode())
            setTypeface(FontHelper.getInstance(context).getPersianTextTypeface());
    }
 
    public PTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            setTypeface(FontHelper.getInstance(context).getPersianTextTypeface());
    }
 
    public PTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            setTypeface(FontHelper.getInstance(context).getPersianTextTypeface());
    }
 
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null)
            text = FormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}