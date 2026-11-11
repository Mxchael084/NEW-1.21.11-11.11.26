package de.rupturereborn.module.setting;

public class BoolSetting extends Setting {
    private boolean value;
    public BoolSetting(String name, boolean value) { super(name); this.value = value; }
    public boolean get() { return value; }
    public void set(boolean v) { this.value = v; }
    public void toggle() { value = !value; }
}
