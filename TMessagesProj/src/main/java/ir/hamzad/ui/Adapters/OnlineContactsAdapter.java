package org.telegram.ui.Adapters;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationCompat.ViewProxy;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController.Contact;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC.TL_contact;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.GreySectionCell;
import org.telegram.ui.Cells.LetterSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.StickersActivity.TouchHelperCallback;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineContactsAdapter extends BaseSectionsAdapter {
    private HashMap<Integer, ?> checkedMap;
    private HashMap<Integer, User> ignoreUsers;
    private boolean isAdmin;
    private Context mContext;
    private boolean needPhonebook;
    private boolean onlyUsers;
    private boolean scrolling;

    public OnlineContactsAdapter(Context context, boolean arg1, boolean arg2, HashMap<Integer, User> arg3, boolean arg4) {
        mContext = context;
        onlyUsers = arg1;
        needPhonebook = arg2;
        ignoreUsers = arg3;
        isAdmin = arg4;
    }

    public void setCheckedMap(HashMap<Integer, ?> map) {
        checkedMap = map;
    }

    public void setIsScrolling(boolean value) {
        scrolling = value;
    }

    public Object getItem(int section, int position) {
        ArrayList<TL_contact> arr;
        if (!onlyUsers || isAdmin) {
            if (section == 0) {
                return null;
            }
            if (section - 1 < ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
                arr = (ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section - 1));
                if (position < arr.size()) {
                    return MessagesController.getInstance().getUser(Integer.valueOf(((TL_contact) arr.get(position)).user_id));
                }
                return null;
            } else if (needPhonebook) {
                return ContactsController.getInstance().phoneBookContacts.get(position);
            } else {
                return null;
            }
        } else if (section >= ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
            return null;
        } else {
            arr = (ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section));
            if (position < arr.size()) {
                return MessagesController.getInstance().getUser(Integer.valueOf(((TL_contact) arr.get(position)).user_id));
            }
            return null;
        }
    }

    public boolean isRowEnabled(int section, int row) {
        if (!onlyUsers || isAdmin) {
            if (section == 0) {
                if (needPhonebook || isAdmin) {
                    if (row == 1) {
                        return false;
                    }
                    return true;
                } else if (row == 3) {
                    return false;
                } else {
                    return true;
                }
            } else if (section - 1 >= ContactsController.getInstance().onlineSortedUsersSectionsArray.size() || row < ((ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section - 1))).size()) {
                return true;
            } else {
                return false;
            }
        } else if (row < ((ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section))).size()) {
            return true;
        } else {
            return false;
        }
    }

    public int getSectionCount() {
        int count = ContactsController.getInstance().onlineSortedUsersSectionsArray.size();
        if (!onlyUsers) {
            count++;
        }
        if (isAdmin) {
            count++;
        }
        if (needPhonebook) {
            return count + 1;
        }
        return count;
    }

    public int getCountForSection(int section) {
        int count;
        if (!onlyUsers || isAdmin) {
            if (section == 0) {
                if (needPhonebook || isAdmin) {
                    return 2;
                }
                return 4;
            } else if (section - 1 < ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
                count = ((ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section - 1))).size();
                if (section - 1 != ContactsController.getInstance().onlineSortedUsersSectionsArray.size() - 1 || needPhonebook) {
                    return count + 1;
                }
                return count;
            }
        } else if (section < ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
            count = ((ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section))).size();
            if (section != ContactsController.getInstance().onlineSortedUsersSectionsArray.size() - 1 || needPhonebook) {
                return count + 1;
            }
            return count;
        }
        if (needPhonebook) {
            return ContactsController.getInstance().phoneBookContacts.size();
        }
        return 0;
    }

    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new LetterSectionCell(mContext);
        }
        if (!onlyUsers || isAdmin) {
            if (section == 0) {
                ((LetterSectionCell) convertView).setLetter(BuildConfig.FLAVOR);
            } else if (section - 1 < ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
                ((LetterSectionCell) convertView).setLetter((String) ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section - 1));
            } else {
                ((LetterSectionCell) convertView).setLetter(BuildConfig.FLAVOR);
            }
        } else if (section < ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
            ((LetterSectionCell) convertView).setLetter((String) ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section));
        } else {
            ((LetterSectionCell) convertView).setLetter(BuildConfig.FLAVOR);
        }
        return convertView;
    }

    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        float f = 72.0f;
        boolean z = true;
        int type = getItemViewType(section, position);
        int dp;
        if (type == 4) {
            if (convertView != null) {
                return convertView;
            }
            float f2;
            convertView = new DividerCell(mContext);
            if (LocaleController.isRTL) {
                f2 = 28.0f;
            } else {
                f2 = 72.0f;
            }
            dp = AndroidUtilities.dp(f2);
            if (!LocaleController.isRTL) {
                f = 28.0f;
            }
            convertView.setPadding(dp, 0, AndroidUtilities.dp(f), 0);
            return convertView;
        } else if (type == 3) {
            if (convertView != null) {
                return convertView;
            }
            convertView = new GreySectionCell(mContext);
            ((GreySectionCell) convertView).setText(LocaleController.getString("Contacts", R.string.Contacts).toUpperCase());
            return convertView;
        } else if (type == 2) {
            if (convertView == null) {
                convertView = new TextCell(mContext);
            }
            TextCell actionCell = (TextCell) convertView;
            if (needPhonebook) {
                actionCell.setTextAndIcon(LocaleController.getString("InviteFriends", R.string.InviteFriends), R.drawable.menu_invite);
            } else if (isAdmin) {
                actionCell.setTextAndIcon(LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink), R.drawable.menu_invite);
            } else {
                if (position == 0) {
                    actionCell.setTextAndIcon(LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup);
                } else if (position == 1) {
                    actionCell.setTextAndIcon(LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret);
                } else if (position == 2) {
                    actionCell.setTextAndIcon(LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast);
                }
            }
        } else if (type == 1) {
            if (convertView == null) {
                convertView = new TextCell(mContext);
            }
            Contact contact = (Contact) ContactsController.getInstance().phoneBookContacts.get(position);
            if (contact.first_name != null && contact.last_name != null) {
                ((TextCell) convertView).setText(contact.first_name + " " + contact.last_name);
                return convertView;
            } else if (contact.first_name == null || contact.last_name != null) {
                ((TextCell) convertView).setText(contact.last_name);
                return convertView;
            } else {
                ((TextCell) convertView).setText(contact.first_name);
                return convertView;
            }
        } else if (type != 0) {
            return convertView;
        } else {
            if (convertView == null) {
                convertView = new UserCell(mContext, 58, 1,true);
                ((UserCell) convertView).setStatusColors(-5723992, -12876608);
            }
            HashMap hashMap = ContactsController.getInstance().onlineUsersSectionsDict;
            ArrayList arrayList = ContactsController.getInstance().onlineSortedUsersSectionsArray;
            if (!onlyUsers || isAdmin) {
                dp = 1;
            } else {
                dp = 0;
            }
            User user = MessagesController.getInstance().getUser(Integer.valueOf(((TL_contact) ((ArrayList) hashMap.get(arrayList.get(section - dp))).get(position)).user_id));
            ((UserCell) convertView).setData(user, null, null, 0);
            if (checkedMap != null) {
                UserCell userCell = (UserCell) convertView;
                boolean containsKey = checkedMap.containsKey(Integer.valueOf(user.id));
                if (scrolling || VERSION.SDK_INT <= 10) {
                    z = false;
                }
                userCell.setChecked(containsKey, z);
            }
            if (ignoreUsers == null) {
                return convertView;
            }
            if (ignoreUsers.containsKey(Integer.valueOf(user.id))) {
                ViewProxy.setAlpha(convertView, 0.5f);
                return convertView;
            }
            ViewProxy.setAlpha(convertView, TouchHelperCallback.ALPHA_FULL);
            return convertView;
        }
        return convertView;
    }
    public int getItemViewType(int section, int position) {
        if (!onlyUsers || isAdmin) {
            if (section == 0) {
                if (needPhonebook || isAdmin) {
                    if (position == 1) {
                        return 3;
                    }
                } else if (position == 3) {
                    return 3;
                }
                return 2;
            } else if (section - 1 >= ContactsController.getInstance().onlineSortedUsersSectionsArray.size()) {
                return 1;
            } else {
                if (position >= ((ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section - 1))).size()) {
                    return 4;
                }
                return 0;
            }
        } else if (position < ((ArrayList) ContactsController.getInstance().onlineUsersSectionsDict.get(ContactsController.getInstance().onlineSortedUsersSectionsArray.get(section))).size()) {
            return 0;
        } else {
            return 4;
        }
    }

    public int getViewTypeCount() {
        return 5;
    }
}
