package demo.sensors.md.shouse_sensors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import demo.sensors.md.shouse_sensors.enums.HomeTab;

/**
 * Created by elisita on 11/5/16.
 */

public class HomePagerFragmentAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public HomePagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        HomeTab tab = HomeTab.values()[position];
        Fragment fragment = null;
        switch (tab) {
            case DATA:
                fragment = new DataFragment();
                break;
            case SENSORS:
                fragment = new SensorsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return HomeTab.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        HomeTab tab = HomeTab.values()[position];

        return context.getString(tab.getTitleRes());
    }
}
