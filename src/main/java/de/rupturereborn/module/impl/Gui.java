package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;

/** Einstellungen der ClickGUI (Rechtsklick auf das Modul). */
public class Gui extends Module {
    public final SliderSetting dim = add(new SliderSetting("Abdunkelung", 0, 100, 40, 0));

    public Gui() { super("Gui", "Einstellungen der ClickGUI"); }
}
