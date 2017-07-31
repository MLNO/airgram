package org.telegram.ui.database.biz;

import android.annotation.SuppressLint;

import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.database.DataBaseAccess;
import org.telegram.ui.database.model.UpdateModel;

import org.telegram.messenger.BuildConfig;

public class UpdateBiz {
    private DataBaseAccess dba;

    public UpdateBiz() {
        this.dba = new DataBaseAccess();
    }

    public boolean insertUpdate(TLRPC.User currentUser, TLRPC.Update update) {
        if (update.user_id == UserConfig.getClientUserId() || currentUser == null) {
            return false;
        }
        UpdateModel updateModel = new UpdateModel();
        updateModel.setUserId(currentUser.id);
        updateModel.setNew(true);
        if (update instanceof TLRPC.TL_updateUserName) {
            updateModel.setOldValue(formatUserSearchName(currentUser.username, currentUser.first_name, currentUser.last_name));
            updateModel.setNewValue(formatUserSearchName(update.username, update.first_name, update.last_name));
            updateModel.setType(2);
        }
        if (!(update instanceof TLRPC.TL_updateUserPhoto)) {
            return false;
        }
        updateModel.setType(3);
        this.dba.insertOrUpdateUpdate(updateModel);
        return true;
    }

    @SuppressLint({"DefaultLocale"})
    private String formatUserSearchName(String username, String firstName, String lastName) {
        StringBuilder str = new StringBuilder(BuildConfig.FLAVOR);
        if (firstName != null && firstName.length() > 0) {
            str.append(firstName);
        }
        if (lastName != null && lastName.length() > 0) {
            if (str.length() > 0) {
                str.append(" ");
            }
            str.append(lastName);
        }
        if (username != null && username.length() > 0) {
            str.append(";;;");
            str.append(username);
        }
        return str.toString().toLowerCase();
    }
}
