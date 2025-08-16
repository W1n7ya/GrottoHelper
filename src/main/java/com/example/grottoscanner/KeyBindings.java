package com.yourname.grottoscanner;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    public static KeyBinding openGui;

    public static void init() {
        openGui = new KeyBinding("Open Grotto Settings", Keyboard.KEY_G, "Better Grotto Scanner");
        ClientRegistry.registerKeyBinding(openGui);
    }
}
