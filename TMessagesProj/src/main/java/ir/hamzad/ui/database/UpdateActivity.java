package org.telegram.ui.database;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

import org.telegram.messenger.R;

import chitchat.FontManager;

public class UpdateActivity extends BaseFragment implements NotificationCenterDelegate, PhotoViewerProvider {
    private static final int delete = 2;
    private static final int filter = 3;
    private int currentFilterType;
    private UpdateCursorAdapter cursorAdapter;
    private DataBaseAccess dataBaseAccess;
    private TextView emptyTextView;
    private ActionBarMenuItem filterItem;
    private ListView listView;
    private boolean paused;
    private User selectedUser;
    protected BackupImageView selectedUserAvatar;

    class C20941 implements OnClickListener {
        C20941() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            System.out.println("delete action ="+i);
            //UpdateActivity.this.dataBaseAccess.deleteUpdate();
            UpdateActivity.this.forceReload();
        }
    }

    class C20952 extends ActionBarMenuOnItemClick {
        C20952() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                UpdateActivity.this.finishFragment();
            } else if (i == UpdateActivity.delete) {
                UpdateActivity.this.showDeleteHistoryConfirmation();
            } else if (i == UpdateActivity.filter) {
                UpdateActivity.this.showFilterDialog();
            }
        }
    }

    /* renamed from: org.telegram.ui.telehgram.UpdateActivity.3 */
    class C20963 implements OnTouchListener {
        C20963() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.telehgram.UpdateActivity.4 */
    class C20974 implements OnItemClickListener {
        C20974() {
        }

        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            UpdateActivity.this.selectedUser = MessagesController.getInstance().getUser(Integer.valueOf(UpdateActivity.this.dataBaseAccess.getUpdateFromCursor((Cursor) UpdateActivity.this.cursorAdapter.getItem(i)).getUserId()));
            if (UpdateActivity.this.selectedUser != null) {
                UpdateActivity.this.selectedUserAvatar = ((UpdateCell) view).getAvatarImageView();
                UpdateActivity.this.showUserActionsDialog();
            }
        }
    }

    /* renamed from: org.telegram.ui.telehgram.UpdateActivity.5 */
    class C20985 implements OnClickListener {
        C20985() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == 0) {
                UpdateActivity.this.filterItem.setIcon(R.drawable.ic_ab_filter);
            } else {
                UpdateActivity.this.filterItem.setIcon(R.drawable.ic_ab_filter_);
            }
            if (i == 0) {
                UpdateActivity.this.currentFilterType = 0;
            } else if (i == 1) {
                UpdateActivity.this.currentFilterType = UpdateActivity.delete;
            } else if (i == UpdateActivity.delete) {
                UpdateActivity.this.currentFilterType = UpdateActivity.filter;
            } else if (i == UpdateActivity.filter) {
                UpdateActivity.this.currentFilterType = 4;
            }
            UpdateActivity.this.forceReload();
            dialogInterface.dismiss();
        }
    }

    public UpdateActivity(Bundle bundle) {
        super(bundle);
        this.currentFilterType = 0;
    }

    private void forceReload() {
         cursorAdapter = new UpdateCursorAdapter(getParentActivity(), dataBaseAccess.getUpdateListCursor(currentFilterType, 500));
            System.out.println("force reload="+cursorAdapter.getCount());
         listView.setAdapter(this.cursorAdapter);
    }

    private void openChatActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", this.selectedUser.id);
        presentFragment(new ChatActivity(bundle), false);
    }

    private void showDeleteHistoryConfirmation() {
        Builder builder = new Builder(getParentActivity());
        builder.setMessage(LocaleController.getString("AreYouSureDeleteChanges", R.string.AreYouSureDeleteChanges));
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C20941());
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    public boolean cancelButtonPressed() {
        return false;
    }

    public View createView(Context context) {
        this.fragmentView = new FrameLayout(context);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ContactsChanges", R.string.ContactsChanges));
        this.actionBar.setActionBarMenuOnItemClick(new C20952());
        ActionBarMenu createMenu = this.actionBar.createMenu();
        createMenu.addItem((int) delete, (int) R.drawable.ic_ab_delete);
        this.filterItem = createMenu.addItem((int) filter, (int) R.drawable.ic_ab_filter);
        this.dataBaseAccess = new DataBaseAccess();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setVisibility(View.INVISIBLE);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ((FrameLayout) this.fragmentView).addView(linearLayout);
        LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        layoutParams.gravity = 48;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOnTouchListener(new C20963());
        this.emptyTextView = new TextView(context);
        this.emptyTextView.setTextColor(ContextCompat.getColor(context,R.color.black));
        this.emptyTextView.setTextSize(1, 20.0f);
        this.emptyTextView.setGravity(17);
        this.emptyTextView.setTypeface(FontManager.instance().getTypeface());
        this.emptyTextView.setText(LocaleController.getString("NoContactChanges", R.string.NoContactChanges));
        linearLayout.addView(this.emptyTextView);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.emptyTextView.getLayoutParams();
        layoutParams2.width = LayoutParams.MATCH_PARENT;
        layoutParams2.height = LayoutParams.MATCH_PARENT;
        layoutParams2.weight = 0.5f;
        this.emptyTextView.setLayoutParams(layoutParams2);
        View frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout);
        layoutParams2 = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        layoutParams2.weight = 0.5f;
        frameLayout.setLayoutParams(layoutParams2);
        this.cursorAdapter = new UpdateCursorAdapter( context, dataBaseAccess.getUpdateListCursor(this.currentFilterType, 500));
        System.out.println("my cursor adapter size= "+cursorAdapter.getCount());
        this.listView = new ListView(context);
        this.listView.setEmptyView(linearLayout);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setDivider(null);
        this.listView.setDividerHeight(0);
        this.listView.setFastScrollEnabled(true);
        this.listView.setCacheColorHint(0);
        this.listView.setScrollingCacheEnabled(false);
        this.listView.setAdapter(this.cursorAdapter);
        ((FrameLayout) this.fragmentView).addView(this.listView);
        layoutParams = (LayoutParams) this.listView.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        this.listView.setLayoutParams(layoutParams);
        this.listView.setOnItemClickListener(new C20974());
        FontManager.instance().setTypefaceImmediate(fragmentView);
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (!this.paused) {
            UpdateNotificationUtil.dismissNotification();
            this.dataBaseAccess.markAllUpdatesAsRead();
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
        }
        forceReload();
    }

    public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
        if (fileLocation == null) {
            return null;
        }
        if (!(this.selectedUser == null || this.selectedUser.id == 0)) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.selectedUser.id));
            if (!(user == null || user.photo == null || user.photo.photo_big == null)) {
                FileLocation fileLocation2 = user.photo.photo_big;
                if (fileLocation2 != null || fileLocation2.local_id != fileLocation.local_id || fileLocation2.volume_id != fileLocation.volume_id || fileLocation2.dc_id != fileLocation.dc_id) {
                    return null;
                }
                int[] iArr = new int[delete];
                this.selectedUserAvatar.getLocationInWindow(iArr);
                PlaceProviderObject placeProviderObject = new PlaceProviderObject();
                placeProviderObject.viewX = iArr[0];
                placeProviderObject.viewY = iArr[1] - AndroidUtilities.statusBarHeight;
                placeProviderObject.parentView = this.selectedUserAvatar;
                placeProviderObject.imageReceiver = this.selectedUserAvatar.getImageReceiver();
                placeProviderObject.dialogId = this.selectedUser.id;
                placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
                placeProviderObject.size = -1;
                placeProviderObject.radius = this.selectedUserAvatar.getImageReceiver().getRoundRadius();
                return placeProviderObject;
            }
        }
        return null != null ? null : null;
    }

    public int getSelectedCount() {
        return 0;
    }

    public Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
        return null;
    }

    public boolean isPhotoChecked(int i) {
        return false;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onPause() {
        super.onPause();
        this.paused = true;
    }

    public void onResume() {
        super.onResume();
        this.paused = false;
        UpdateNotificationUtil.dismissNotification();
        this.dataBaseAccess.markAllUpdatesAsRead();
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
    }

    public void sendButtonPressed(int i) {
    }

    public void setPhotoChecked(int i) {
    }

    protected void showFilterDialog() {
        int i = 0;
        Builder builder = new Builder(getParentActivity());
        builder.setTitle(R.string.filter_title);
        CharSequence[] charSequenceArr = new CharSequence[]{getParentActivity().getString(R.string.AllChanges), getParentActivity().getString(R.string.change_name), getParentActivity().getString(R.string.change_photo), getParentActivity().getString(R.string.change_phone)};
        if (this.currentFilterType != 0) {
            i = this.currentFilterType - 1;
        }
        builder.setSingleChoiceItems(charSequenceArr, i, new C20985());
        showDialog(builder.create());
    }

    protected void showUserActionsDialog() {
        openChatActivity();
    }

    public void updatePhotoAtIndex(int i) {
    }

    public void willHidePhotoViewer() {
        if (this.selectedUserAvatar != null) {
            this.selectedUserAvatar.getImageReceiver().setVisible(true, true);
        }
    }

    public void willSwitchFromPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
    }
}
