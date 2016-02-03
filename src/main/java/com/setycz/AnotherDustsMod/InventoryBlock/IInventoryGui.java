package com.setycz.AnotherDustsMod.InventoryBlock;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by setyc on 29.01.2016.
 */
public interface IInventoryGui {
    Container createContainer(InventoryPlayer inventoryplayer, World world, BlockPos pos);

    @SideOnly(Side.CLIENT)
    GuiContainer createGui(InventoryPlayer inventoryplayer, World world, BlockPos pos);
}
