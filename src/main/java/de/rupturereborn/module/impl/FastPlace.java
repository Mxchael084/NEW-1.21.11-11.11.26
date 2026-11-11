package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/** Setzt Blöcke ohne Verzögerung, mehrfach pro Tick, solange die rechte Maustaste gehalten wird. */
public class FastPlace extends Module {
    private static final double REACH = 4.5;
    private final SliderSetting perTick = add(new SliderSetting("Blöcke pro Tick", 1, 5, 2, 0));

    public FastPlace() { super("FastPlace", "Blöcke ohne Verzögerung und mehrfach pro Tick setzen"); }

    @Override
    public void onTick(Minecraft mc) {
        var p = mc.player;
        if (mc.screen != null || !mc.options.keyUse.isDown()) return;

        int count = (int) perTick.get();
        for (int i = 0; i < count; i++) {
            if (!(p.getMainHandItem().getItem() instanceof BlockItem)) return;

            // Nach jeder Platzierung neu zielen, damit der nächste Block an der neuen Stelle landet
            HitResult hr = p.pick(REACH, 1.0f, false);
            if (!(hr instanceof BlockHitResult hit) || hit.getType() != HitResult.Type.BLOCK) return;

            InteractionResult result = mc.gameMode.useItemOn(p, InteractionHand.MAIN_HAND, hit);
            if (result instanceof InteractionResult.Success) {
                p.swing(InteractionHand.MAIN_HAND);
            } else {
                return;
            }
        }
    }
}
