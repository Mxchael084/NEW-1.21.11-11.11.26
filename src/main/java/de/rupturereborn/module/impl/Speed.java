package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;
import de.rupturereborn.util.Keys;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class Speed extends Module {
    private final SliderSetting multiplier = add(new SliderSetting("Multiplikator", 1.0, 2.0, 1.1, 2));

    public Speed() { super("Speed", "Erhöhte Bodengeschwindigkeit"); }

    @Override
    public void onTick(Minecraft mc) {
        var p = mc.player;
        if (!p.onGround() || p.isInWater() || (Keys.forward(mc) == 0 && Keys.strafe(mc) == 0)) return;
        Vec3 v = p.getDeltaMovement();
        double m = multiplier.get();
        p.setDeltaMovement(v.x * m, v.y, v.z * m);
    }
}
