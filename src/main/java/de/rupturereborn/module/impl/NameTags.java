package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

/** Zeigt Name und Lebenspunkte über Mobs (rein clientseitig). */
public class NameTags extends Module {
    public NameTags() { super("NameTags", "Zeigt Lebenspunkte über Mobs"); }

    @Override
    public void onTick(Minecraft mc) {
        for (Entity e : mc.level.entitiesForRendering()) {
            if (e instanceof LivingEntity le && !(e instanceof Player) && !(e instanceof ArmorStand)) {
                String name = le.getType().getDescription().getString();
                e.setCustomName(Component.literal(name + " §c" + (int) Math.ceil(le.getHealth()) + " HP"));
                e.setCustomNameVisible(true);
            }
        }
    }

    @Override
    public void onDisable(Minecraft mc) {
        if (mc.level == null) return;
        for (Entity e : mc.level.entitiesForRendering()) {
            if (e instanceof LivingEntity && !(e instanceof Player) && !(e instanceof ArmorStand)) {
                e.setCustomName(null);
                e.setCustomNameVisible(false);
            }
        }
    }
}
