package demo.sensors.md.shouse_sensors.enums;

import demo.sensors.md.shouse_sensors.R;

/**
 * Created by elisita on 11/5/16.
 */

public enum HomeTab {
    DATA(R.string.tab_data_title), SENSORS(R.string.tab_sensors_title);

    int titleRes;
    int iconRes;

    HomeTab(int titleRes) {
        this.titleRes = titleRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getIconRes() {
        return iconRes;
    }
}
