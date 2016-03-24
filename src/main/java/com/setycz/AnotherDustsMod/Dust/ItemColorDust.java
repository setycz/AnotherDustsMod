package com.setycz.AnotherDustsMod.Dust;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

/**
 * Created by setyc on 24.03.2016.
 */
public class ItemColorDust implements IItemColor {
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return ((ItemDust)stack.getItem()).getColor();
    }
}
