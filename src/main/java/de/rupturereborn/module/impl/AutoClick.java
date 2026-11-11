package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Klickt automatisch, solange die linke Maustaste gehalten wird und ein Ziel im Fadenkreuz ist.
 * Bei mehr als 20 CPS werden pro Tick mehrere Klicks ausgeführt (Minecraft tickt 20x pro Sekunde).
 */
public class AutoClick extends CombatModule {
    private final SliderSetting minCps = add(new SliderSetting("Min CPS", 1, 30, 8, 0));
    private final SliderSetting maxCps = add(new SliderSetting("Max CPS", 1, 30, 12, 0));
    private double budget;

    public AutoClick() { super("AutoClick", "Automatisches Klicken mit bis zu 30 CPS (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void tick(Minecraft mc) {
        if (mc.screen != null || !mc.options.keyAttack.isDown()
                || !(mc.hitResult instanceof EntityHitResult hit)) {
            budget = 0;
            return;
        }

        double lo = Math.min(minCps.get(), maxCps.get());
        double hi = Math.max(minCps.get(), maxCps.get());
        double cps = lo + ThreadLocalRandom.current().nextDouble() * (hi - lo);

        // Pro Tick sammelt sich cps/20 Klicks an; ganze Klicks werden ausgeführt.
        budget += cps / 20.0;
        int clicks = (int) budget;
        budget -= clicks;

        for (int i = 0; i < clicks; i++) {
            mc.gameMode.attack(mc.player, hit.getEntity());
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
