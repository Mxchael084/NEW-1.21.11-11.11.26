package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.module.setting.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;

public class ChestStealer extends Module {
    private final SliderSetting delay = add(new SliderSetting("Delay (Ticks)", 0, 10, 1, 0));
    private int timer;

    public ChestStealer() { super("ChestStealer", "Leert geöffnete Kisten automatisch"); }

    @Override
    public void onTick(Minecraft mc) {
        if (!(mc.screen instanceof ContainerScreen) || !(mc.player.containerMenu instanceof ChestMenu menu)) {
            timer = 0;
            return;
        }
        if (timer > 0) { timer--; return; }
        int slots = menu.getRowCount() * 9;
        for (int i = 0; i < slots; i++) {
            if (menu.getSlot(i).hasItem()) {
                mc.gameMode.handleInventoryMouseClick(menu.containerId, i, 0, ClickType.QUICK_MOVE, mc.player);
                timer = (int) delay.get();
                return;
            }
        }
    }
}
