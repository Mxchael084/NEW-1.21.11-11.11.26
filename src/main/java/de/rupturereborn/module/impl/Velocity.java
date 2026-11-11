package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

/** Reduziert Rückstoß (100 % = normal, 0 % = keiner). */
public class Velocity extends CombatModule {
    private final SliderSetting horizontal = add(new SliderSetting("Horizontal %", 0, 100, 60, 0));
    private final SliderSetting vertical = add(new SliderSetting("Vertikal %", 0, 100, 100, 0));
    private int lastHurt;

    public Velocity() { super("Velocity", "Reduziert den erhaltenen Rückstoß (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void tick(Minecraft mc) {
        var p = mc.player;
        if (p.hurtTime > 0 && lastHurt == 0) {
            Vec3 v = p.getDeltaMovement();
            double h = horizontal.get() / 100.0;
            p.setDeltaMovement(v.x * h, v.y * (vertical.get() / 100.0), v.z * h);
        }
        lastHurt = p.hurtTime;
    }
}
