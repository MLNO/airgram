package ir.hamzad.telegram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Dialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogbaneer);
        final String s1 = getIntent().getExtras().getString("s1");
        final String s2 = getIntent().getExtras().getString("s2");
        final String s3 = getIntent().getExtras().getString("s3");
        final String s4 = getIntent().getExtras().getString("s4");
        final String s5 = getIntent().getExtras().getString("s5");
        //Initialize ImageView
        ImageView imageView = (ImageView) findViewById(R.id.imageViewc);

//Loading image from below url into imageView

        Picasso.with(this)
                .load(s5)
                .into(imageView);
        //String s5 = getIntent().getExtras().getString("s5");
        //String s6 = getIntent().getExtras().getString("s6");

        findViewById(R.id.buttonc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DirectLaunchIntent = new Intent(Intent.ACTION_VIEW  , Uri.parse(s3));
                if (s4.equals("0")){
                    DirectLaunchIntent.setPackage("com.farsitel.bazaar");
                }
                DirectLaunchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                DirectLaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DirectLaunchIntent);
                finish();
                }
        });
        TextView t = (TextView) findViewById(R.id.textViewc);
        Button t2 = (Button) findViewById(R.id.buttonc);
        Typeface persianTypeface = Typeface.createFromAsset(getAssets(), "fonts/ir.ttf");

        t2.setTypeface(persianTypeface);
        t.setText(s1);
        t2.setText(s2);
        System.out.println(s1);
    }
}
