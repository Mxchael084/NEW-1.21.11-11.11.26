package de.rupturereborn.module;

import de.rupturereborn.module.impl.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Panel "a"
        modules.add(new KillAura());
        modules.add(new DamageMax());
        modules.add(new Gui());
        modules.add(new Reach());
        modules.add(new SelfDestruct());
        modules.add(new AutoClick());
        modules.add(new Aim());
        modules.add(new Strafe());
        modules.add(new WTap());
        modules.add(new STap());
        modules.add(new Velocity());
        // Panel "b"
        modules.add(new FastPlace());
        modules.add(new FullBright());
        modules.add(new Hud());
        modules.add(new NameTags());
        modules.add(new AutoArmor());
        modules.add(new ChestStealer());
        modules.add(new Fly());
        modules.add(new Speed());
        // Panel "c"
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Glow());
        modules.add(new Parkour());
        modules.add(new AutoRespawn());
        modules.add(new Triggerbot());
        modules.add(new AutoHeal());

        get(Hud.class).setEnabled(true);
    }

    public List<Module> all() { return Collections.unmodifiableList(modules); }

    public <T extends Module> T get(Class<T> type) {
        for (Module m : modules) if (type.isInstance(m)) return type.cast(m);
        return null;
    }

    public Module byName(String name) {
        for (Module m : modules) if (m.getName().equalsIgnoreCase(name)) return m;
        return null;
    }
}
