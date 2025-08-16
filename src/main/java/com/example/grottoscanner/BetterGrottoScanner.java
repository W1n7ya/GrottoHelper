package com.yourname.grottoscanner;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.gui.GuiScreen;

@Mod(
        modid = "bettergrottoscanner",
        name  = "Better Grotto Scanner",
        version = "1.0",
        clientSideOnly = true
)
public class BetterGrottoScanner {
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ConfigHandler.init(e.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        KeyBindings.init();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new ScanningHandler());
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new WaypointRenderer());
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (KeyBindings.openGui.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettings());
        }
    }
}
