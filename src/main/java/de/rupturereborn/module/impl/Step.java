package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.lwjgl.glfw.GLFW;

public class Step extends Module {
    private static final double DEFAULT = 0.6;
    private final SliderSetting height = add(new SliderSetting("Höhe", 0.6, 2.5, 1.0, 1));

    public Step() { super("Step", "Höhere Blöcke hochlaufen"); }

    @Override
    public void onTick(Minecraft mc) {
        var attr = mc.player.getAttribute(Attributes.STEP_HEIGHT);
        if (attr != null && attr.getBaseValue() != height.get()) attr.setBaseValue(height.get());
    }

    @Override
    public void onDisable(Minecraft mc) {
        if (mc.player == null) return;
        var attr = mc.player.getAttribute(Attributes.STEP_HEIGHT);
        if (attr != null) attr.setBaseValue(DEFAULT);
    }
}
