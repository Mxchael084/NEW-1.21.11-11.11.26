package de.rupturereborn.module.setting;

import java.util.Locale;

public class SliderSetting extends Setting {
    private final double min, max;
    private final int decimals;
    private double value;

    public SliderSetting(String name, double min, double max, double value, int decimals) {
        super(name);
        this.min = min;
        this.max = max;
        this.decimals = decimals;
        set(value);
    }

    public double get() { return value; }
    public double getMin() { return min; }
    public double getMax() { return max; }

    public void set(double v) {
        double f = Math.pow(10, decimals);
        v = Math.max(min, Math.min(max, v));
        this.value = Math.round(v * f) / f;
    }

    public String display() {
        return String.format(Locale.ROOT, "%." + decimals + "f", value);
    }
}
