package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.lwjgl.glfw.GLFW;

public class Triggerbot extends CombatModule {
    public Triggerbot() { super("Triggerbot", "Schlägt zu, sobald das Fadenkreuz ein Lebewesen trifft"); }

    @Override
    protected void tick(Minecraft mc) {
        var p = mc.player;
        if (p.getAttackStrengthScale(0f) < 1f) return;
        if (mc.hitResult instanceof EntityHitResult hit && hit.getEntity() instanceof LivingEntity le && le.isAlive()) {
            mc.gameMode.attack(p, le);
            p.swing(InteractionHand.MAIN_HAND);
        }
    }
}
