package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.module.setting.BoolSetting;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

/**
 * Maximaler Schaden nach den Kampfregeln von Minecraft 1.21.x:
 *  - Angriff erst bei (fast) voll aufgeladenem Cooldown (Schaden skaliert stark mit der Aufladung)
 *  - kritische Treffer (+50 %): im Fall treffen, ohne zu sprinten, Cooldown über 90 %
 *  - Sprung wird passend zur Waffengeschwindigkeit ausgelöst, damit der Fall und der volle
 *    Cooldown zusammenfallen
 */
public class DamageMax extends CombatModule {
    /** Wird von Sprint beachtet: in der Luft nicht sprinten, sonst gibt es keinen Crit. */
    public static volatile boolean noSprint;

    private final SliderSetting range = add(new SliderSetting("Range", 2.0, 6.0, 3.0, 1));
    private final SliderSetting minCooldown = add(new SliderSetting("Min Cooldown %", 50, 100, 92, 0));
    private final BoolSetting crits = add(new BoolSetting("Crits", true));
    private final BoolSetting aim = add(new BoolSetting("Zielen", true));

    public DamageMax() {
        super("DamageMax", "Maximaler Schaden: Cooldown-Timing und Crits (Einzelspieler und erlaubte Server)");
    }

    @Override
    protected void tick(Minecraft mc) {
        noSprint = false;
        var p = mc.player;
        LivingEntity target = nearestTarget(mc, range.get());
        if (target == null) return;

        if (aim.get()) face(p, target);

        float strength = p.getAttackStrengthScale(0f);
        float needed = (float) (minCooldown.get() / 100.0);
        if (crits.get()) needed = Math.max(needed, 0.91f); // Crits brauchen mehr als 90 %

        boolean critPossible = crits.get()
                && !p.isInWater() && !p.onClimbable() && !p.isPassenger()
                && !p.hasEffect(MobEffects.BLINDNESS);

        if (critPossible) {
            if (p.onGround()) {
                // Sprung so früh auslösen, dass der Fall (ca. Tick 6-12 des Sprungs) auf den vollen Cooldown trifft
                float delay = Math.max(1f, p.getCurrentItemAttackStrengthDelay());
                double jumpAt = Math.max(0.0, 1.0 - 11.0 / delay);
                if (strength >= jumpAt) p.jumpFromGround();
                return;
            }
            // In der Luft: nicht sprinten und im Fall zuschlagen
            noSprint = true;
            p.setSprinting(false);
            boolean falling = p.getDeltaMovement().y < -0.03;
            if (falling && strength >= needed) hit(mc, target);
            return;
        }

        if (strength >= needed) hit(mc, target);
    }

    @Override
    public void onDisable(Minecraft mc) {
        noSprint = false;
    }

    private static void hit(Minecraft mc, LivingEntity target) {
        mc.gameMode.attack(mc.player, target);
        mc.player.swing(InteractionHand.MAIN_HAND);
    }

    private static void face(net.minecraft.world.entity.player.Player p, LivingEntity target) {
        Vec3 d = target.getEyePosition().subtract(p.getEyePosition());
        float yaw = (float) (Math.toDegrees(Math.atan2(d.z, d.x)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(d.y, Math.sqrt(d.x * d.x + d.z * d.z)));
        p.setYRot(yaw);
        p.setXRot(Mth.clamp(pitch, -90f, 90f));
    }
}
