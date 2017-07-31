package org.telegram.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;

import chitchat.FontManager;

/**
 * Created by Root on 6/10/2016.
 */
public class about extends BaseFragment implements View.OnClickListener {
    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle(LocaleController.getString("AboutUs", R.string.AboutUs));
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                    finishFragment();
            }
        });
        fragmentView = getParentActivity().getLayoutInflater().inflate(R.layout.about, null, false);
        TextView tv1 = (TextView) fragmentView.findViewById(R.id.text_);
        TextView tv2 = (TextView) fragmentView.findViewById(R.id.name_);
        TextView tv4 = (TextView) fragmentView.findViewById(R.id.nazar_);
        TextView tv5 = (TextView) fragmentView.findViewById(R.id.apps_);
        CardView c1 = (CardView) fragmentView.findViewById(R.id.about_1);
        CardView c3 = (CardView) fragmentView.findViewById(R.id.about_3);
        CardView c4 = (CardView) fragmentView.findViewById(R.id.about_4);
        c1.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        FontManager.instance().setTypefaceImmediate(fragmentView);
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.about_3:
            Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("bazaar://collection?slug=by_author&aid=008646696783"));
            intent.setPackage("com.farsitel.bazaar");
            getParentActivity().startActivity(intent);
            break;
        case R.id.about_4:
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"redfox323@outlook.com"});
            i.putExtra(Intent.EXTRA_SUBJECT,"");
            i.putExtra(Intent.EXTRA_TEXT   ," ");
            try {
                getParentActivity().startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
            }
            break;
    }
    }
}
