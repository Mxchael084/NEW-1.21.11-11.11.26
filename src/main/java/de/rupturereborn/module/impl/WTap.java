package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.util.Keys;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

/** Lässt nach einem Treffer kurz die Vorwärtstaste los, um den Sprint zurückzusetzen. */
public class WTap extends CombatModule {
    private int ticks;

    public WTap() { super("WTap", "Sprint-Reset nach Treffern (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void attacked(Minecraft mc, Entity target) {
        if (ticks == 0) ticks = 2;
    }

    @Override
    protected void tick(Minecraft mc) {
        if (ticks <= 0) return;
        ticks--;
        if (ticks == 1) {
            mc.options.keyUp.setDown(false);
        } else if (ticks == 0) {
            Keys.restore(mc, mc.options.keyUp);
            if (mc.options.keyUp.isDown()) mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable(Minecraft mc) {
        ticks = 0;
        if (mc.player != null) Keys.restore(mc, mc.options.keyUp);
    }
}
