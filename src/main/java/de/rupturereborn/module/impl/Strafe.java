package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;
import de.rupturereborn.util.Keys;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/** Volle Kontrolle in der Luft: Bewegungsrichtung folgt sofort den Tasten. */
public class Strafe extends Module {
    private final SliderSetting speed = add(new SliderSetting("Speed", 0.1, 0.6, 0.28, 2));

    public Strafe() { super("Strafe", "Luftkontrolle: sofortige Richtungswechsel in der Luft"); }

    @Override
    public void onTick(Minecraft mc) {
        var p = mc.player;
        if (p.onGround() || p.isInWater() || p.isInLava() || p.getAbilities().flying || p.isFallFlying()) return;
        float fwd = Keys.forward(mc);
        float str = Keys.strafe(mc);
        if (fwd == 0 && str == 0) return;

        float yaw = p.getYRot() * 0.017453292f;
        double sin = Mth.sin(yaw), cos = Mth.cos(yaw);
        double vx = str * cos - fwd * sin;
        double vz = fwd * cos + str * sin;
        double len = Math.sqrt(vx * vx + vz * vz);
        if (len < 1.0E-4) return;
        vx /= len;
        vz /= len;

        Vec3 dm = p.getDeltaMovement();
        double current = Math.sqrt(dm.x * dm.x + dm.z * dm.z);
        double s = Math.max(current, speed.get());
        p.setDeltaMovement(vx * s, dm.y, vz * s);
    }
}
