package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.lwjgl.glfw.GLFW;

public class KillAura extends CombatModule {
    private final SliderSetting range = add(new SliderSetting("Range", 2.0, 8.0, 4.0, 1));

    public KillAura() { super("KillAura", "Greift das nächste Ziel in Reichweite an (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void tick(Minecraft mc) {
        var p = mc.player;
        if (p.getAttackStrengthScale(0f) < 1f) return;
        var target = nearestTarget(mc, range.get());
        if (target == null) return;
        mc.gameMode.attack(p, target);
        p.swing(InteractionHand.MAIN_HAND);
    }
}
