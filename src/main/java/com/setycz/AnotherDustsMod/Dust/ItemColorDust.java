package com.setycz.AnotherDustsMod.Dust;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by setyc on 24.03.2016.
 */
@SideOnly(Side.CLIENT)
public class ItemColorDust implements IItemColor {
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return ((ItemDust)stack.getItem()).getColor();
    }
}
