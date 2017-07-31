package TextTypeface;

import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {
    private static FontHelper instance;
    private static Typeface persianTypeface;
 
    private FontHelper(Context context) {
        persianTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/rmedium.ttf");
    }
 
    public static synchronized FontHelper getInstance(Context context) {
        if (instance == null)
            instance = new FontHelper(context);
        return instance;
    }
 
    public Typeface getPersianTextTypeface() {
        return persianTypeface;
    }
}