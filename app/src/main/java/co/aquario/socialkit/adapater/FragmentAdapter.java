package co.aquario.socialkit.adapater;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import co.aquario.socialkit.Sell.SellViewPagerFragment;
import co.aquario.socialkit.Tabs.AccountFragment;
import co.aquario.socialkit.Tabs.BrowseFragment;
import co.aquario.socialkit.Tabs.ExploreFragment;
import co.aquario.socialkit.fragment.ShopFragment;


/**
 * Created by Joseph on 7/3/15.
 */
public class FragmentAdapter extends android.support.v4.app.FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    final String[] Titles = {"Shop", "Sell", "Spark", "Look", "Me"};
    private Context mContext;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ShopFragment();
        } else if (position == 1) {
            return new SellViewPagerFragment();
        } else if (position == 2) {
            return new BrowseFragment();
        } else if (position == 3) {
            return new ExploreFragment();
        } else {
            return new AccountFragment();
        }
    }

    @Override
    public String getPageTitle(int position) {
        return Titles[position];
    }
}
