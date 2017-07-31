package org.telegram.ui.database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.telegram.ui.Components.BackupImageView;

public class UpdateCursorAdapter extends CursorAdapter {
    private DataBaseAccess dataBaseAccess;

    public class ViewHolder {
        BackupImageView avatarImageView;
        TextView tvNewValue;
        TextView tvOldValue;
    }

    public UpdateCursorAdapter(  Context context, Cursor cursor) {
        super(context, cursor, 0);
        dataBaseAccess = new DataBaseAccess();
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return new UpdateCell(this.mContext, 10);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ((UpdateCell) view).setData(this.dataBaseAccess.getUpdateFromCursor(cursor));
    }
}
