package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.util.Keys;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

/** Drückt nach einem Treffer kurz die Rückwärtstaste. */
public class STap extends CombatModule {
    private int ticks;

    public STap() { super("STap", "Kurzes Zurücktippen nach Treffern (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void attacked(Minecraft mc, Entity target) {
        if (ticks == 0) ticks = 3;
    }

    @Override
    protected void tick(Minecraft mc) {
        if (ticks <= 0) return;
        ticks--;
        if (ticks == 2) {
            mc.options.keyUp.setDown(false);
            mc.options.keyDown.setDown(true);
        } else if (ticks == 0) {
            Keys.restore(mc, mc.options.keyDown);
            Keys.restore(mc, mc.options.keyUp);
            if (mc.options.keyUp.isDown()) mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable(Minecraft mc) {
        ticks = 0;
        if (mc.player != null) {
            Keys.restore(mc, mc.options.keyDown);
            Keys.restore(mc, mc.options.keyUp);
        }
    }
}
