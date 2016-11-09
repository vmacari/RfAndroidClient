package demo.sensors.md.shouse_sensors.enums;

import demo.sensors.md.shouse_sensors.enums.PresentationType;
import demo.sensors.md.shouse_sensors.models.Sensor;

import static demo.sensors.md.shouse_sensors.enums.PresentationType.ACTUATOR;
import static demo.sensors.md.shouse_sensors.enums.PresentationType.BOOL;
import static demo.sensors.md.shouse_sensors.enums.PresentationType.CHART;
import static demo.sensors.md.shouse_sensors.enums.PresentationType.UNKNOWN;

/**
 * Created by elisita on 11/9/16.
 */

public enum SensorType {
    S_DOOR(CHART, ""),
    S_MOTION(CHART, ""),
    S_SMOKE(CHART, ""),
    S_LIGHT(CHART, "LUX"),
    S_BINARY(BOOL, ""),
    S_DIMMER(ACTUATOR, ""),
    S_COVER(UNKNOWN, ""),
    S_TEMP(CHART, "°C"),
    S_HUM(CHART, "%"),
    S_BARO(CHART, "PA"),
    S_WIND(UNKNOWN, ""),
    S_RAIN(UNKNOWN, ""),
    S_UV(UNKNOWN, ""),
    S_WEIGHT(UNKNOWN, ""),
    S_POWER(UNKNOWN, ""),
    S_HEATER(UNKNOWN, ""),
    S_DISTANCE(UNKNOWN, ""),
    S_LIGHT_LEVEL(UNKNOWN, ""),
    S_ARDUINO_NODE(UNKNOWN, ""),
    S_ARDUINO_REPEATER_NODE(UNKNOWN, ""),
    S_LOCK(BOOL, ""),
    S_IR(UNKNOWN, ""),
    S_WATER(BOOL, ""),
    S_AIR_QUALITY(UNKNOWN, ""),
    S_CUSTOM(UNKNOWN, ""),
    S_DUST(UNKNOWN, ""),
    S_SCENE_CONTROLLER(UNKNOWN, ""),
    S_RGB_LIGHT(CHART, ""),
    S_RGBW_LIGHT(CHART, ""),
    S_COLOR_SENSOR(CHART, ""),
    S_HVAC(UNKNOWN, ""),
    S_MULTIMETER(UNKNOWN, ""),
    S_SPRINKLER(UNKNOWN, ""),
    S_WATER_LEAK(BOOL, ""),
    S_SOUND(CHART, ""),
    S_VIBRATION(CHART, ""),
    S_MOISTURE(UNKNOWN, ""),
    S_INFO(UNKNOWN, ""),
    S_GAS(CHART, ""),
    S_GPS(UNKNOWN, ""),
    S_WATER_QUALITY(UNKNOWN, "");

    PresentationType presentationType;
    String unit;

    SensorType(PresentationType presentationType, String unit) {
        this.presentationType = presentationType;
        this.unit = unit;
    }

    public PresentationType getPresentationType() {
        return presentationType;
    }

    public void setPresentationType(PresentationType presentationType) {
        this.presentationType = presentationType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static SensorType getByName(String sensorTypeName) {
        for (SensorType sensorType : SensorType.values()) {
            if (sensorTypeName.equals(sensorType.toString())) {
                return sensorType;
            }
        }
        return null;
    }
}
