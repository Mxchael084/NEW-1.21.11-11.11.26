package de.rupturereborn.module.impl;

import de.rupturereborn.RuptureReborn;
import de.rupturereborn.module.Module;
import net.minecraft.client.Minecraft;

public class SelfDestruct extends Module {
    public SelfDestruct() { super("SelfDestruct", "Schaltet alles aus und deaktiviert den Client bis zum Neustart"); }

    @Override
    public void onEnable(Minecraft mc) {
        RuptureReborn.destruct(mc);
    }
}
