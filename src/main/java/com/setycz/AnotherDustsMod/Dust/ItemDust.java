package com.setycz.AnotherDustsMod.Dust;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by setyc on 31.01.2016.
 */
public class ItemDust extends Item {
    private int color = 16777215;

    public ItemDust setColor(int color) {
        this.color = color;
        return this;
    }

    public int getColor() {
        return color;
    }
}
