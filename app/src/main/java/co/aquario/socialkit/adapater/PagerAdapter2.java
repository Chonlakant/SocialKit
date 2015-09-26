package co.aquario.socialkit.adapater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.aquario.socialkit.fragment.FragmentAddress;
import co.aquario.socialkit.fragment.FragmentAddressAdd;
import co.aquario.socialkit.fragment.FragmentLogin;
import co.aquario.socialkit.fragment.FragmentPayMents;
import co.aquario.socialkit.fragment.FragmentPayMentsDetail;
import co.aquario.socialkit.fragment.FragmentRegister;

/**
 * Created by root1 on 8/16/15.
 */
public class PagerAdapter2 extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "ที่อยู่การจัดส่ง","การชำละเงิน"};
//    PrefManager pref = MainApplication.getPrefManager();
//    String title = pref.zodiac().getOr("");
//    String data = pref.zodiac().getOr("");

    public PagerAdapter2(FragmentManager fm) {
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
                fragment = new FragmentAddressAdd().newInstance(position);
                break;
            case 1:
                fragment = new FragmentPayMentsDetail().newInstance(position);
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