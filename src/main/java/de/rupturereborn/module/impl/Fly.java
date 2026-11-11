package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {
    private final SliderSetting speed = add(new SliderSetting("Speed", 0.02, 0.5, 0.05, 2));

    public Fly() { super("Fly", "Fliegen wie im Kreativmodus"); }

    @Override
    public void onTick(Minecraft mc) {
        var a = mc.player.getAbilities();
        a.mayfly = true;
        a.setFlyingSpeed((float) speed.get());
    }

    @Override
    public void onDisable(Minecraft mc) {
        var p = mc.player;
        if (p == null || p.isCreative() || p.isSpectator()) return;
        p.getAbilities().mayfly = false;
        p.getAbilities().flying = false;
        p.getAbilities().setFlyingSpeed(0.05f);
    }
}
