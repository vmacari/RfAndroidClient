package demo.sensors.md.shouse_sensors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import demo.sensors.md.shouse_sensors.models.Node;
import demo.sensors.md.shouse_sensors.ws.DataHolder;

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
        Node node = DataHolder.getNodes().get(position);

        return SensorsFragment.newInstance(node.getId());
    }

    @Override
    public int getCount() {
        return DataHolder.getNodes().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Node node = DataHolder.getNodes().get(position);

        return node.getName();
    }
}
