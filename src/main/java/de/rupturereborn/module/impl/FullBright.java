package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.lwjgl.glfw.GLFW;

/** Rein visuell: Nachtsicht-Effekt nur auf dem Client. */
public class FullBright extends Module {
    public FullBright() { super("FullBright", "Volle Helligkeit (Client-Nachtsicht)"); }

    @Override
    public void onTick(Minecraft mc) {
        mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
    }

    @Override
    public void onDisable(Minecraft mc) {
        if (mc.player != null) mc.player.removeEffect(MobEffects.NIGHT_VISION);
    }
}
