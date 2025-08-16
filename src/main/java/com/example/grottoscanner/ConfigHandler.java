package com.yourname.grottoscanner;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class ConfigHandler {
    public static boolean enabled;
    public static int scanRadius;
    public static boolean showOnScreen;
    public static boolean showWaypoint;
    public static boolean showTracer;
    public static int waypointColor; // 0xRRGGBB
    public static int tracerColor;   // 0xRRGGBB
    public static boolean debugMode;
    private static Configuration cfg;

    public static void init(File configFile) {
        cfg = new Configuration(configFile);
        syncConfig();
    }

    public static void syncConfig() {
        enabled      = cfg.getBoolean("enabled", "general", true,  "Enable scanning");
        scanRadius   = cfg.getInt("scanRadius", "general", 10, 1, 32, "Scan radius around player");
        showOnScreen = cfg.getBoolean("showOnScreen", "display", true,  "Popup on-screen");
        showWaypoint = cfg.getBoolean("showWaypoint", "display", true,  "Mark with waypoint beacon");
        showTracer   = cfg.getBoolean("showTracer",   "display", false, "Draw tracer line");
        waypointColor= cfg.getInt("waypointColor","display", 0xFF00FF, "RGB color for waypoint");
        tracerColor  = cfg.getInt("tracerColor",  "display",  0xFFFFFF, "RGB color for tracer");
        debugMode    = cfg.getBoolean("debugMode","general", false, "Debug logs");
        if (cfg.hasChanged()) cfg.save();
    }
}
