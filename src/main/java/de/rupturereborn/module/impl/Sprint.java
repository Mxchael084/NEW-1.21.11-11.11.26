package de.rupturereborn.module.impl;

import de.rupturereborn.module.Module;
import de.rupturereborn.util.Keys;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Sprint extends Module {
    public Sprint() { super("Sprint", "Sprintet automatisch beim Vorwärtslaufen"); }

    @Override
    public void onTick(Minecraft mc) {
        if (DamageMax.noSprint) return; // DamageMax braucht in der Luft kein Sprinten (Crits)
        var p = mc.player;
        if (Keys.forward(mc) > 0 && !p.horizontalCollision && !p.isUsingItem() && p.getFoodData().getFoodLevel() > 6) {
            p.setSprinting(true);
        }
    }
}
