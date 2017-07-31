package org.telegram.ui.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.SimpleTextView;
import org.telegram.ui.database.model.UpdateModel;

import java.util.Calendar;
import java.util.Date;

import chitchat.FontManager;

public class UpdateCell extends FrameLayout {
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImageView;
    private CheckBox checkBox;
    private User currentUser;
    private SimpleTextView dateTextView;
    private ImageView imageView;
    private String lastName;
    private SimpleTextView nameTextView;
    private int newValueColor;
    private SimpleTextView newValueTextView;
    private int oldValueColor;
    private SimpleTextView oldValueTextView;
    private UpdateModel updateModel;

    @SuppressLint({"RtlHardcoded"})
    public UpdateCell(Context context, int padding) {
        super(context);
        int i;
        int i2;
        int i3 = 5;
        currentUser = null;
        this.lastName = null;
        this.oldValueColor = 0xffa8a8a8;
        this.newValueColor = 0xff3b84c0;
        this.avatarDrawable = new AvatarDrawable();
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(24.0f));
        boolean isRTL = LocaleController.isRTL;
        int avLeftMargin= LocaleController.isRTL ? 0 : 7 + padding;
        int avRightMargin= LocaleController.isRTL ? 7 + padding : 0;
        int gravity=(LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT) | Gravity.TOP;
        addView(avatarImageView, LayoutHelper.createFrame(48, 48, gravity, avLeftMargin, 8, avRightMargin, 0));

        this.nameTextView = new SimpleTextView(context);
        this.nameTextView.setTypeface(FontManager.instance().getTypeface());
        this.nameTextView.setTextColor(0xff212121);
        this.nameTextView.setTextSize(17);
        SimpleTextView simpleTextView = this.nameTextView;
        if (isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        simpleTextView.setGravity(i | 48);
        View view = this.nameTextView;
        if (isRTL) {
            i2 = 5;
        } else {
            i2 = 3;
        }
         addView(view, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 20, (LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT) | Gravity.TOP, LocaleController.isRTL ? 28 : (68 + padding), 11.5f, LocaleController.isRTL ? (68 + padding) : 28, 0));

        this.oldValueTextView = new SimpleTextView(context);
        this.oldValueTextView.setTypeface(FontManager.instance().getTypeface());
        this.oldValueTextView.setTextSize(14);
        simpleTextView = this.oldValueTextView;
        if (isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        simpleTextView.setGravity(i | 48);
        view = this.oldValueTextView;
        if (isRTL) {
            i2 = 5;
        } else {
            i2 = 3;
        }
        addView(view, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 20.0f, i2 | 48, isRTL ? 28.0f : (float) (padding + 68), 34.5f, isRTL ? (float) (padding + 68) : 28.0f, 0.0f));
        this.newValueTextView = new SimpleTextView(context);
        this.newValueTextView.setTypeface(FontManager.instance().getTypeface());
        this.newValueTextView.setTextSize(14);
        simpleTextView = this.newValueTextView;
        if (isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        simpleTextView.setGravity(i | 48);
        view = this.newValueTextView;
        if (isRTL) {
            i2 = 5;
        } else {
            i2 = 3;
        }
        addView(view, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 20.0f, i2 | 48, isRTL ? 28.0f : (float) (padding + 68), 57.5f, isRTL ? (float) (padding + 68) : 28.0f, 0.0f));
        this.dateTextView = new SimpleTextView(context);
        this.dateTextView.setTypeface(FontManager.instance().getTypeface());
        this.dateTextView.setTextSize(14);
        simpleTextView = this.dateTextView;
        if (isRTL) {
            i = 3;
        } else {
            i = 5;
        }
        simpleTextView.setGravity(i | 48);
        view = this.dateTextView;
        if (isRTL) {
            i2 = 3;
        } else {
            i2 = 5;
        }
        addView(view, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 20.0f, i2 | 48, isRTL ? (float) (padding + 5) : 28.0f, 80.5f, isRTL ? 28.0f : (float) (padding + 10), 0.0f));
        this.imageView = new ImageView(context);
        this.imageView.setScaleType(ScaleType.CENTER);
        this.imageView.setVisibility(GONE);
        View view2 = this.imageView;
        if (isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        addView(view2, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, -2.0f, i | 16, isRTL ? 0.0f : 16.0f, 0.0f, isRTL ? 16.0f : 0.0f, 0.0f));
        this.checkBox = new CheckBox(context, R.drawable.round_check2);
        this.checkBox.setVisibility(INVISIBLE);
        View view3 = this.checkBox;
        if (!isRTL) {
            i3 = 3;
        }
        addView(view3, LayoutHelper.createFrame(22, 22.0f, i3 | 48, isRTL ? 0.0f : (float) (padding + 37), 38.0f, isRTL ? (float) (padding + 37) : 0.0f, 0.0f));
    }

    public void setData(UpdateModel UpMOD) {
        User user = MessagesController.getInstance().getUser(UpMOD.getUserId());
        if (user == null) {
            this.nameTextView.setText(BuildConfig.FLAVOR);
            this.avatarImageView.setImageDrawable(null);
        }
        currentUser = user;
        updateModel = UpMOD;
        update();
    }

    public void setChecked(boolean checked, boolean animated) {
        if (this.checkBox.getVisibility() != VISIBLE) {
            this.checkBox.setVisibility(VISIBLE);
        }
        this.checkBox.setChecked(checked, animated);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(104.0f), MeasureSpec.EXACTLY));
    }

    public void update() {
        if (currentUser != null) {
            TLObject photo = null;
            if (currentUser.photo != null) {
                photo = currentUser.photo.photo_small;
            }
            this.avatarDrawable.setInfo(currentUser);
            this.avatarImageView.setImage(photo, "50_50", this.avatarDrawable);
            this.lastName = ContactsController.formatName(currentUser.first_name, currentUser.last_name);
            this.nameTextView.setText(this.lastName);
        }else {System.out.println("**** current user = null ****");}
        this.oldValueTextView.setTextColor(this.oldValueColor);
        this.newValueTextView.setTextColor(this.newValueColor);
        if (this.updateModel.getType() == 1) {
            this.oldValueTextView.setText(BuildConfig.FLAVOR);
            if (this.updateModel.getNewValue().equals("1")) {
                this.newValueTextView.setText(getContext().getString(R.string.get_online));
            } else {
                this.newValueTextView.setText(getContext().getString(R.string.get_offline));
            }
        } else if (this.updateModel.getType() == 2) {
            this.oldValueTextView.setText(getContext().getString(R.string.old_name) + " " + this.updateModel.getOldValue().replace(";;;", " - "));
            this.newValueTextView.setText(getContext().getString(R.string.new_name) + " " + this.updateModel.getNewValue().replace(";;;", " - "));
        } else if (this.updateModel.getType() == 3) {
            this.oldValueTextView.setText(BuildConfig.FLAVOR);
            this.newValueTextView.setText(getContext().getString(R.string.changed_photo));
        } else if (this.updateModel.getType() == 4) {
            this.oldValueTextView.setText(getContext().getString(R.string.old_phone) + " " + this.updateModel.getOldValue());
            this.newValueTextView.setText(getContext().getString(R.string.new_phone) + " " + this.updateModel.getNewValue());
        }
        Long changeDate = Long.valueOf(Long.parseLong(this.updateModel.getChangeDate()));
        if (changeDate.longValue() != 0) {
            Date date = new Date(changeDate.longValue());
            String receiveDate = MoboUtils.getShamsiDate(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.dateTextView.setText(receiveDate + " - " + MoboUtils.intToString(cal.get(Calendar.HOUR_OF_DAY), 2) + ":" + MoboUtils.intToString(cal.get(Calendar.MINUTE), 2));
        }
    }

    public BackupImageView getAvatarImageView() {
        return this.avatarImageView;
    }
}
