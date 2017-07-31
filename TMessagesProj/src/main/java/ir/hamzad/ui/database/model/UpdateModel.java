package org.telegram.ui.database.model;


import org.telegram.messenger.R;

public class UpdateModel {
    private String changeDate;
    private Long id;
    private boolean isNew;
    private String newValue;
    private String oldValue;
    private int type;
    private int userId;

    public UpdateModel() {
    }

    public UpdateModel(Long id, int type, String oldValue, String newValue, int userId, boolean isNew, String string) {
        setId(id);
        setType(type);
        setOldValue(oldValue);
        setNewValue(newValue);
        setUserId(userId);
        setNew(isNew);
        setChangeDate(string);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return this.newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getMessage() {
        if (this.type == 1) {
            if (this.newValue.equals("1")) {
                return R.string.get_online;
            }
            return R.string.get_offline;
        } else if (this.type == 2) {
            return R.string.changed_name;
        } else {
            if (this.type == 3) {
                return R.string.changed_photo;
            }
            if (this.type == 4) {
                return R.string.changed_phone;
            }
            return R.string.change_status;
        }
    }
}
