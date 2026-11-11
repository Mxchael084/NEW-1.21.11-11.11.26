package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

/** Visuelle Hervorhebung (Umriss) anderer Spieler, nur clientseitig. */
public class Glow extends Module {
    public Glow() { super("Glow", "Umriss um andere Spieler (Client)"); }

    @Override
    public void onTick(Minecraft mc) {
        for (Entity e : mc.level.entitiesForRendering()) {
            if (e instanceof Player && e != mc.player) e.setGlowingTag(true);
        }
    }

    @Override
    public void onDisable(Minecraft mc) {
        if (mc.level == null) return;
        for (Entity e : mc.level.entitiesForRendering()) {
            if (e instanceof LivingEntity) e.setGlowingTag(false);
        }
    }
}
