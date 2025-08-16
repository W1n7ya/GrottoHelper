package com.yourname.grottoscanner;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.*;

public class ScanningHandler {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Set<BlockPos> found = new HashSet<>();
    private final Random rand = new Random();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !ConfigHandler.enabled) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;

        int px = mc.thePlayer.getPosition().getX();
        int py = mc.thePlayer.getPosition().getY();
        int pz = mc.thePlayer.getPosition().getZ();

        // throttle: scan 8 random positions per tick
        for (int i = 0; i < 8; i++) {
            int dx = rand.nextInt(ConfigHandler.scanRadius * 2 + 1) - ConfigHandler.scanRadius;
            int dy = rand.nextInt(ConfigHandler.scanRadius * 2 + 1) - ConfigHandler.scanRadius;
            int dz = rand.nextInt(ConfigHandler.scanRadius * 2 + 1) - ConfigHandler.scanRadius;
            BlockPos pos = new BlockPos(px + dx, py + dy, pz + dz);
            Block b = mc.theWorld.getBlockState(pos).getBlock();

            boolean isPinkWool = (b == Blocks.wool &&
                    mc.theWorld.getBlockState(pos).getValue(net.minecraft.block.BlockColored.COLOR)
                            == net.minecraft.init.Blocks.wool.getMetaFromState(
                            Blocks.wool.getDefaultState().withProperty(
                                    net.minecraft.block.BlockColored.COLOR,
                                    net.minecraft.block.BlockColored.EnumDyeColor.PINK)));
            boolean isMagGlass = (b == Blocks.stained_glass &&
                    mc.theWorld.getBlockState(pos).getValue(net.minecraft.block.BlockStainedGlass.COLOR)
                            == net.minecraft.block.BlockStainedGlass.EnumDyeColor.MAGENTA);

            if ((isPinkWool || isMagGlass) && !found.contains(pos)) {
                found.add(pos);
                String msg = String.format("§dFairy Grotto at §b(%d,%d,%d)", pos.getX(),pos.getY(),pos.getZ());
                mc.thePlayer.addChatMessage(new net.minecraft.util.ChatComponentText(msg));

                if (ConfigHandler.showOnScreen) {
                    // one second popup
                    net.minecraft.client.gui.ScaledResolution sr =
                            new net.minecraft.client.gui.ScaledResolution(mc);
                    int w = sr.getScaledWidth()/2;
                    int h = sr.getScaledHeight()/3;
                    mc.ingameGUI.getChatGUI().printChatMessage(
                            new net.minecraft.util.ChatComponentText(
                                    "§a[Grotto] §r" + msg
                            )
                    );
                }

                if (ConfigHandler.showWaypoint) {
                    WaypointRenderer.addWaypoint(pos);
                }

                if (ConfigHandler.debugMode) {
                    System.out.println("[GrottoScanner DEBUG] Found at " + pos);
                }
            }
        }
    }
}
