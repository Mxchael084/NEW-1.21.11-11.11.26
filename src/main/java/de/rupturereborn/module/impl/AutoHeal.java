package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;

public class AutoHeal extends CombatModule {
    public AutoHeal() { super("AutoHeal", "Isst goldene Äpfel aus der Hotbar bei niedriger Gesundheit"); }

    @Override
    protected void tick(Minecraft mc) {
        var p = mc.player;
        if (p.getHealth() > 8f || p.isUsingItem()) return;
        var inv = p.getInventory();
        for (int i = 0; i < 9; i++) {
            var stack = inv.getItem(i);
            if (stack.is(Items.GOLDEN_APPLE) || stack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
                inv.setSelectedSlot(i);
                mc.gameMode.useItem(p, InteractionHand.MAIN_HAND);
                return;
            }
        }
    }
}
