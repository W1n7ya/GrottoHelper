package com.yourname.grottoscanner;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;

public class GuiSettings extends GuiScreen {
    private int idCounter = 0;
    @Override
    public void initGui() {
        buttonList.clear();
        int x = width/2 - 100, y = height/2 - 80, dy = 24;

        buttonList.add(new GuiButton(idCounter++, x,  y, 200, 20,
                "Scanner: " + (ConfigHandler.enabled ? "ON" : "OFF")));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "Radius: " + ConfigHandler.scanRadius));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "OnScreen: " + (ConfigHandler.showOnScreen ? "ON":"OFF")));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "Waypoint: " + (ConfigHandler.showWaypoint ? "ON":"OFF")));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "Tracer: " + (ConfigHandler.showTracer ? "ON":"OFF")));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "Waypoint Color: #" + Integer.toHexString(ConfigHandler.waypointColor).toUpperCase()));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "Tracer Color: #" + Integer.toHexString(ConfigHandler.tracerColor).toUpperCase()));
        buttonList.add(new GuiButton(idCounter++, x,  y+=dy, 200, 20,
                "Debug Mode: " + (ConfigHandler.debugMode ? "ON":"OFF")));
        buttonList.add(new GuiButton(999, width/2-40, height/2+90, 80, 20, "Done"));
    }

    @Override
    protected void actionPerformed(GuiButton btn) throws IOException {
        switch (btn.id) {
            case 0: ConfigHandler.enabled = !ConfigHandler.enabled; break;
            case 1: ConfigHandler.scanRadius = (ConfigHandler.scanRadius % 32) + 1; break;
            case 2: ConfigHandler.showOnScreen = !ConfigHandler.showOnScreen; break;
            case 3: ConfigHandler.showWaypoint = !ConfigHandler.showWaypoint; break;
            case 4: ConfigHandler.showTracer = !ConfigHandler.showTracer; break;
            case 5:
                ConfigHandler.waypointColor = (ConfigHandler.waypointColor + 0x550000) & 0xFFFFFF;
                break;
            case 6:
                ConfigHandler.tracerColor = (ConfigHandler.tracerColor + 0x005500) & 0xFFFFFF;
                break;
            case 7: ConfigHandler.debugMode = !ConfigHandler.debugMode; break;
            case 999:
                ConfigHandler.syncConfig();
                mc.displayGuiScreen(null);
                return;
        }
        // update button text live
        initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
