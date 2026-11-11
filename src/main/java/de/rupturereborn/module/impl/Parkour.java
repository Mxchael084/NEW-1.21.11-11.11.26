package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import net.minecraft.client.Minecraft;

/** Springt automatisch an Blockkanten. */
public class Parkour extends Module {
    public Parkour() { super("Parkour", "Springt automatisch an Kanten"); }

    @Override
    public void onTick(Minecraft mc) {
        var p = mc.player;
        if (!p.onGround() || p.isShiftKeyDown() || p.isInWater()) return;
        var box = p.getBoundingBox().move(0, -0.5, 0).inflate(-0.001, 0, -0.001);
        if (mc.level.noCollision(p, box)) p.jumpFromGround();
    }
}
