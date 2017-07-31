/*
 * This is the source code of Telegram for Android v. 3.x.x
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2016.
 */

package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.R;

import chitchat.ChatBaseColors;
import chitchat.ChitSettings;
import chitchat.ColorUtils;

public class ResourceLoader {

    public static Drawable backgroundDrawableIn;
    public static Drawable backgroundDrawableInSelected;
    public static Drawable backgroundDrawableOut;
    public static Drawable backgroundDrawableOutSelected;
    public static Drawable backgroundMediaDrawableIn;
    public static Drawable backgroundMediaDrawableInSelected;
    public static Drawable backgroundMediaDrawableOut;
    public static Drawable backgroundMediaDrawableOutSelected;
    public static Drawable checkDrawable;
    public static Drawable halfCheckDrawable;
    public static Drawable clockDrawable;
    public static Drawable broadcastDrawable;
    public static Drawable checkMediaDrawable;
    public static Drawable halfCheckMediaDrawable;
    public static Drawable clockMediaDrawable;
    public static Drawable broadcastMediaDrawable;
    public static Drawable errorDrawable;
    public static Drawable backgroundBlack;
    public static Drawable backgroundBlue;
    public static Drawable mediaBackgroundDrawable;
    public static Drawable[] clockChannelDrawable = new Drawable[2];

    public static Drawable[][] shareDrawable = new Drawable[2][2];

    public static Drawable viewsCountDrawable;
    public static Drawable viewsOutCountDrawable;
    public static Drawable[] viewsMediaCountDrawable = new Drawable[2];

    public static Drawable geoInDrawable;
    public static Drawable geoOutDrawable;

    public static Drawable[][] audioStatesDrawable = new Drawable[10][3];

    public static Drawable[] placeholderDocDrawable = new Drawable[3];
    public static Drawable videoIconDrawable;
    public static Drawable[] docMenuDrawable = new Drawable[3];
    public static Drawable[] docMenuDrawableF = new Drawable[3];
    public static Drawable[] buttonStatesDrawables = new Drawable[8];
    public static Drawable[][] buttonStatesDrawablesDoc = new Drawable[3][3];


    //:ramin fantasy
    public static Drawable backgroundDrawableInF;
    public static Drawable backgroundDrawableInSelectedF;
    public static Drawable backgroundDrawableOutF;
    public static Drawable backgroundDrawableOutSelectedF;
    public static Drawable checkDrawableF;
    public static Drawable halfCheckDrawableF;
    public static Drawable clockDrawableF;
    public static Drawable viewsCountDrawableF;
    public static Drawable viewsOutCountDrawableF;
    public static Drawable broadcastDrawableF;
    public static Drawable backgroundMediaDrawableInF;
    public static Drawable backgroundMediaDrawableInSelectedF;
    public static Drawable backgroundMediaDrawableOutF;
    public static Drawable backgroundMediaDrawableOutSelectedF;

    public static void loadRecources(Context context) {
        if (backgroundDrawableIn == null) {
            backgroundDrawableIn = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in, null);
            backgroundDrawableInSelected = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_selected, null);
            backgroundDrawableOut = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out, null);
            backgroundDrawableOutSelected = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_selected, null);
            backgroundMediaDrawableIn = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_photo, null);
            backgroundMediaDrawableInSelected = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_photo_selected, null);
            backgroundMediaDrawableOut = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_photo, null);
            backgroundMediaDrawableOutSelected = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_photo_selected, null);
            checkDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_check, null);
            halfCheckDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_halfcheck, null);
            clockDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_clock, null);
            checkMediaDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_check_w, null);
            halfCheckMediaDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_halfcheck_w, null);
            clockMediaDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_clock_photo, null);
            clockChannelDrawable[0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_clock2, null);
            clockChannelDrawable[1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_clock2_s, null);
            errorDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_warning, null);
            mediaBackgroundDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.phototime, null);
            broadcastDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.broadcast3, null);
            broadcastMediaDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.broadcast4, null);
            backgroundBlack = ResourcesCompat.getDrawable(context.getResources(), R.drawable.system_black, null);
            backgroundBlue = ResourcesCompat.getDrawable(context.getResources(), R.drawable.system_blue, null);

            viewsCountDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.post_views, null);
            viewsOutCountDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.post_viewsg, null);
            viewsMediaCountDrawable[0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.post_views_w, null);
            viewsMediaCountDrawable[1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.post_views_s, null);

            audioStatesDrawable[0][2] = audioStatesDrawable[0][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.play_w2, null);
            audioStatesDrawable[0][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.play_w2_pressed, null);

            audioStatesDrawable[1][2] = audioStatesDrawable[1][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_w2, null);
            audioStatesDrawable[1][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_w2_pressed, null);

            audioStatesDrawable[2][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.download_g, null);
            audioStatesDrawable[2][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.download_g_pressed, null);
            audioStatesDrawable[2][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.download_g_s, null);

            audioStatesDrawable[3][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_g, null);
            audioStatesDrawable[3][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_g_pressed, null);
            audioStatesDrawable[3][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_g_s, null);

            audioStatesDrawable[4][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cancel_g, null);
            audioStatesDrawable[4][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cancel_g_pressed, null);
            audioStatesDrawable[4][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cancel_g_s, null);

            audioStatesDrawable[5][2] = audioStatesDrawable[5][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.play_w, null);
            audioStatesDrawable[5][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.play_w_pressed, null);

            audioStatesDrawable[6][2] = audioStatesDrawable[6][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_w, null);
            audioStatesDrawable[6][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_w_pressed, null);

            audioStatesDrawable[7][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.download_b, null);
            audioStatesDrawable[7][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.download_b_pressed, null);
            audioStatesDrawable[7][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.download_b_s, null);

            audioStatesDrawable[8][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_b, null);
            audioStatesDrawable[8][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_b_pressed, null);
            audioStatesDrawable[8][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause_b_s, null);

            audioStatesDrawable[9][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cancel_b, null);
            audioStatesDrawable[9][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cancel_b_pressed, null);
            audioStatesDrawable[9][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cancel_b_s, null);

            placeholderDocDrawable[0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doc_blue, null);
            placeholderDocDrawable[1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doc_green, null);
            placeholderDocDrawable[2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doc_blue_s, null);
            buttonStatesDrawables[0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.photoload, null);
            buttonStatesDrawables[1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.photocancel, null);
            buttonStatesDrawables[2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.photogif, null);
            buttonStatesDrawables[3] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.playvideo, null);
            buttonStatesDrawables[4] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.photopause, null);
            buttonStatesDrawables[5] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.burn, null);
            buttonStatesDrawables[6] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle, null);
            buttonStatesDrawables[7] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.photocheck, null);
            buttonStatesDrawablesDoc[0][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.docload_b, null);
            buttonStatesDrawablesDoc[0][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.docload_g, null);
            buttonStatesDrawablesDoc[0][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.docload_b_s, null);
            buttonStatesDrawablesDoc[1][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doccancel_b, null);
            buttonStatesDrawablesDoc[1][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doccancel_g, null);
            buttonStatesDrawablesDoc[1][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doccancel_b_s, null);
            buttonStatesDrawablesDoc[2][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.docpause_b, null);
            buttonStatesDrawablesDoc[2][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.docpause_g, null);
            buttonStatesDrawablesDoc[2][2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.docpause_b_s, null);
            videoIconDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_video, null);
            docMenuDrawable[0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doc_actions_b, null);
            docMenuDrawable[1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doc_actions_g, null);
            docMenuDrawable[2] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.doc_actions_b_s, null);

            shareDrawable[0][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shareblue, null);
            shareDrawable[0][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shareblue_pressed, null);
            shareDrawable[1][0] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shareblack, null);
            shareDrawable[1][1] = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shareblack_pressed, null);

            geoInDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.location_b, null);
            geoOutDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.location_g, null);

            context.getResources().getDrawable(R.drawable.attach_camera_states);
            context.getResources().getDrawable(R.drawable.attach_gallery_states);
            context.getResources().getDrawable(R.drawable.attach_video_states);
            context.getResources().getDrawable(R.drawable.attach_audio_states);
            context.getResources().getDrawable(R.drawable.attach_file_states);
            context.getResources().getDrawable(R.drawable.attach_contact_states);
            context.getResources().getDrawable(R.drawable.attach_location_states);
            context.getResources().getDrawable(R.drawable.attach_hide_states);


            backgroundDrawableInF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_f, null);
            backgroundDrawableInSelectedF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_selected_f, null);
            backgroundDrawableOutF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_f, null);
            backgroundDrawableOutSelectedF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_selected_f, null);
            backgroundMediaDrawableInF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_photo_f, null);
            backgroundMediaDrawableInSelectedF= ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_in_photo_selected_f, null);
            backgroundMediaDrawableOutF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_photo_f, null);
            backgroundMediaDrawableOutSelectedF = ResourcesCompat.getDrawable(context.getResources(), R.drawable.msg_out_photo_selected_f, null);


            checkDrawableF = checkDrawable;//context.getResources().getDrawable(R.drawable.msg_check);
            halfCheckDrawableF = halfCheckDrawable;//context.getResources().getDrawable(R.drawable.msg_halfcheck);

            broadcastDrawableF = broadcastDrawable;//context.getResources().getDrawable(R.drawable.broadcast3);
            viewsCountDrawableF = viewsCountDrawable;//context.getResources().getDrawable(R.drawable.post_views);
            viewsOutCountDrawableF = viewsOutCountDrawable;//context.getResources().getDrawable(R.drawable.post_viewsg);
            clockDrawableF = clockDrawable;//context.getResources().getDrawable(R.drawable.msg_clock);



            docMenuDrawableF[0] = docMenuDrawable[0];
            docMenuDrawableF[1] = docMenuDrawable[1];
            docMenuDrawableF[2] = docMenuDrawable[2];

            //:ramin
            ColorUtils.init(context);
        }

        if(ChitSettings.showFantasy){
            checkDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            halfCheckDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            clockDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            viewsOutCountDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            viewsCountDrawableF.setColorFilter(0xffb5a564, PorterDuff.Mode.SRC_ATOP);
            broadcastDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);

            docMenuDrawableF[0].setColorFilter(ChatBaseColors.timeInF, PorterDuff.Mode.SRC_ATOP);
            docMenuDrawableF[1].setColorFilter(ChatBaseColors.timeOutF, PorterDuff.Mode.SRC_ATOP);
            docMenuDrawableF[2].setColorFilter(ChatBaseColors.timeInSelectedF, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void fantasyChanged(boolean fantasy){
        Context c= ApplicationLoader.applicationContext;
        loadRecources(c);

        if(fantasy){
            checkDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            halfCheckDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            clockDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            viewsOutCountDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            viewsCountDrawableF.setColorFilter(0xffb5a564, PorterDuff.Mode.SRC_ATOP);
            broadcastDrawableF.setColorFilter(0xff68a7ad, PorterDuff.Mode.SRC_ATOP);
            docMenuDrawableF[0].setColorFilter(ChatBaseColors.timeInF, PorterDuff.Mode.SRC_ATOP);
            docMenuDrawableF[1].setColorFilter(ChatBaseColors.timeOutF, PorterDuff.Mode.SRC_ATOP);
            docMenuDrawableF[2].setColorFilter(ChatBaseColors.timeInSelectedF, PorterDuff.Mode.SRC_ATOP);
        }else {
            checkDrawableF.clearColorFilter();
            halfCheckDrawableF.clearColorFilter();
            clockDrawableF.clearColorFilter();
            viewsOutCountDrawableF.clearColorFilter();
            viewsCountDrawableF.clearColorFilter();
            broadcastDrawableF.clearColorFilter();
            docMenuDrawableF[0].clearColorFilter();
            docMenuDrawableF[1].clearColorFilter();
            docMenuDrawableF[2].clearColorFilter();
        }

    }

}
