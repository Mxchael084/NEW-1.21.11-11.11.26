package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class Aim extends CombatModule {
    private final SliderSetting range = add(new SliderSetting("Range", 2.0, 8.0, 5.0, 1));
    private final SliderSetting speed = add(new SliderSetting("Speed", 1, 100, 20, 0));
    private final SliderSetting fov = add(new SliderSetting("FOV", 10, 180, 60, 0));

    public Aim() { super("Aim", "Zielt sanft auf das nächste Ziel (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void tick(Minecraft mc) {
        var p = mc.player;
        var target = nearestTarget(mc, range.get());
        if (target == null) return;
        Vec3 d = target.getEyePosition().subtract(p.getEyePosition());
        float yaw = (float) (Math.toDegrees(Math.atan2(d.z, d.x)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(d.y, Math.sqrt(d.x * d.x + d.z * d.z)));
        float dYaw = Mth.wrapDegrees(yaw - p.getYRot());
        float dPitch = pitch - p.getXRot();
        if (Math.abs(dYaw) > fov.get()) return;
        float s = (float) (speed.get() / 100.0);
        p.setYRot(p.getYRot() + dYaw * s);
        p.setXRot(Mth.clamp(p.getXRot() + dPitch * s, -90f, 90f));
    }
}
