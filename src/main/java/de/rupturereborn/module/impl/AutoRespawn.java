package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import net.minecraft.client.Minecraft;

public class AutoRespawn extends Module {
    public AutoRespawn() { super("AutoRespawn", "Respawnt automatisch nach dem Tod"); }

    @Override
    public void onTick(Minecraft mc) {
        if (mc.player.isDeadOrDying()) mc.player.respawn();
    }
}
