package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.concurrent.ThreadLocalRandom;

/** Zufällige Reichweite zwischen Min und Max (nur in lokalen Welten). */
public class Reach extends CombatModule {
    private final SliderSetting min = add(new SliderSetting("Min Reach", 3.0, 10.0, 3.5, 1));
    private final SliderSetting max = add(new SliderSetting("Max Reach", 3.0, 10.0, 4.0, 1));

    public Reach() { super("Reach", "Größere Reichweite für Angriffe (Einzelspieler und erlaubte Server)"); }

    @Override
    protected void tick(Minecraft mc) {
        double lo = Math.min(min.get(), max.get());
        double hi = Math.max(min.get(), max.get());
        apply(mc, lo + ThreadLocalRandom.current().nextDouble() * (hi - lo));
    }

    @Override
    public void onDisable(Minecraft mc) {
        if (mc.player != null) apply(mc, 3.0);
    }

    private static void apply(Minecraft mc, double value) {
        var attr = mc.player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        if (attr != null) attr.setBaseValue(value);

        var server = mc.getSingleplayerServer();
        if (server == null) return;
        var uuid = mc.player.getUUID();
        server.execute(() -> {
            var sp = server.getPlayerList().getPlayer(uuid);
            if (sp == null) return;
            var a = sp.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            if (a != null) a.setBaseValue(value);
        });
    }
}
