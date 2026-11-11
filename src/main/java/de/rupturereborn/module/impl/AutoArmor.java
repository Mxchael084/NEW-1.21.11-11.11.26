package de.rupturereborn.module.impl;

import de.rupturereborn.module.CombatModule;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ClickType;

public class AutoArmor extends CombatModule {
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public AutoArmor() { super("AutoArmor", "Zieht Rüstung aus dem Inventar automatisch an"); }

    @Override
    protected void tick(Minecraft mc) {
        if (mc.screen != null) return;
        var p = mc.player;
        for (EquipmentSlot slot : SLOTS) {
            if (!p.getItemBySlot(slot).isEmpty()) continue;
            for (int i = 9; i <= 44; i++) {
                var stack = p.inventoryMenu.getSlot(i).getItem();
                var equippable = stack.get(DataComponents.EQUIPPABLE);
                if (equippable != null && equippable.slot() == slot) {
                    mc.gameMode.handleInventoryMouseClick(p.inventoryMenu.containerId, i, 0, ClickType.QUICK_MOVE, p);
                    return; // ein Teil pro Tick
                }
            }
        }
    }
}
