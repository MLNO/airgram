package ir.hamzad.telegram;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellConfiguration;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class videoad extends Activity {

    private static final String appKey = "kiltkoprhfsrijnimrcnhkrkdhdmaapkisecjjahmhkhprollltjedigtdqojfdfkfnibl";


    private static final String myAppMainZoneId = "593038204684652d07b5144a";

    private boolean showCompleteDialog = false;
    private boolean rewarded = false;
    private boolean completed = false;

    Button requestAdBtn, showAddBtn;
    TapsellAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);

        setContentView(R.layout.videoad);


        TapsellConfiguration config = new TapsellConfiguration();
        config.setDebugMode(true);
        config.setAutoHandlePermissions(true);

        Tapsell.initialize(this, config, appKey);

        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {
                Log.e("video","isCompleted? "+completed+ ", ad was rewarded?" + (ad!=null && ad.isRewardedAd()) );
                showCompleteDialog = true;
                completed=completed;
                rewarded=(ad!=null && ad.isRewardedAd());
                // store user reward in local database
            }
        });




        showAddBtn = (Button) findViewById(R.id.btnShowAd);
        findViewById(R.id.fin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showAddBtn.setEnabled(false);
        loadAd(myAppMainZoneId);

    }
    private void showtest(int i) {
        findViewById(R.id.textView3).setVisibility(View.GONE);
        findViewById(R.id.ll).setVisibility(View.VISIBLE);
        if (i==1){
            TextView t = (TextView) findViewById(R.id.textView);
            EditText et = (EditText) findViewById(R.id.editText);

            t.setText("نوع کارت شارژ"+" : ایرانسل ");
            Random rand = new Random();

            String n = ""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+"";
            et.setText(n);
        }else {
            TextView t = (TextView) findViewById(R.id.textView);
            EditText et = (EditText) findViewById(R.id.editText);

            t.setText("نوع کارت شارژ"+" : همراه اول ");
            Random rand = new Random();

            String n = ""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+""+(rand.nextInt(9) + 1)+"";
            et.setText(n);
        }
    }
    private void loadAd(String zoneId) {

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);

        Tapsell.requestAd(videoad.this, zoneId, options, new TapsellAdRequestListener() {
            @Override
            public void onError(String error) {
                finish();
            }

            @Override
            public void onAdAvailable(TapsellAd ad) {

                ad = ad;
                if(ad!=null && ad.isValid())
                {
                    showAddBtn.setEnabled(false);
                    TapsellShowOptions showOptions = new TapsellShowOptions();
                    showOptions.setBackDisabled(false);
                    showOptions.setImmersiveMode(true);
                    showOptions.setRotationMode(TapsellShowOptions.ROTATION_UNLOCKED);
                    ad.show(videoad.this, showOptions);
                }
                else if( ad==null ){
                    finish();
                }
                else {
                    finish();
                }
            }

            @Override
            public void onNoAdAvailable() {
                finish();
            }

            @Override
            public void onNoNetwork() {
                finish();
            }

            @Override
            public void onExpiring(TapsellAd ad) {
                showAddBtn.setEnabled(false);
                loadAd(myAppMainZoneId);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(showCompleteDialog)
        {
            showCompleteDialog=false;
            new AlertDialog.Builder(videoad.this)
                    .setTitle("انتخاب سیم کارت")
                    .setMessage("نوع کارت شارژ درخواستی رو انتخاب کن")
                    .setNeutralButton("ایرانسل", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showtest(1);
                            dialog.dismiss();
                        }

                    })
                    .setPositiveButton("همراه اول", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            showtest(2);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }



    @Override
    public void onBackPressed() {

    }
}

