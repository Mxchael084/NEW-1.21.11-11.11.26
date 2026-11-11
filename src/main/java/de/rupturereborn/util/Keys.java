package de.rupturereborn.util;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class Keys {
    private Keys() {}

    /** GLFW-Fenster-Handle: erst über den aktuellen Kontext, sonst über Minecrafts Window-Objekt. */
    public static long window() {
        long ctx = GLFW.glfwGetCurrentContext();
        if (ctx != 0L) return ctx;
        try {
            Object w = Minecraft.getInstance().getWindow();
            for (String name : new String[]{"handle", "getWindow"}) {
                try {
                    Object r = w.getClass().getMethod(name).invoke(w);
                    if (r instanceof Long l && l != 0L) return l;
                } catch (ReflectiveOperationException ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        return 0L;
    }

    public static boolean physical(Minecraft mc, KeyMapping k) {
        int code = k.getKey().getValue();
        long win = window();
        if (code <= 0 || win == 0L) return false;
        return GLFW.glfwGetKey(win, code) == GLFW.GLFW_PRESS;
    }

    /** Setzt die Taste auf den echten Tastaturzustand zurück. */
    public static void restore(Minecraft mc, KeyMapping k) {
        k.setDown(physical(mc, k));
    }

    /** Vorwärts-/Rückwärts-Eingabe aus den Tasten: 1, 0 oder -1. */
    public static float forward(Minecraft mc) {
        return (mc.options.keyUp.isDown() ? 1f : 0f) - (mc.options.keyDown.isDown() ? 1f : 0f);
    }

    /** Seitliche Eingabe aus den Tasten: links = 1, rechts = -1. */
    public static float strafe(Minecraft mc) {
        return (mc.options.keyLeft.isDown() ? 1f : 0f) - (mc.options.keyRight.isDown() ? 1f : 0f);
    }
}
