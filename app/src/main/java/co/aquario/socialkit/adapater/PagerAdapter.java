package co.aquario.socialkit.adapater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.aquario.socialkit.fragment.FragmentLogin;
import co.aquario.socialkit.fragment.FragmentRegister;

/**
 * Created by root1 on 8/16/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "สมัคร", "เข้าสู่ระบบ"};
//    PrefManager pref = MainApplication.getPrefManager();
//    String title = pref.zodiac().getOr("");
//    String data = pref.zodiac().getOr("");

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new FragmentRegister().newInstance(position);
                break;
            case 1:
                fragment = new FragmentLogin().newInstance(position);
                break;

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}