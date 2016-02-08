package com.setycz.AnotherDustsMod.Crusher;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by setyc on 07.02.2016.
 */
public class CrusherRegistryItem {
    private final Block ore;
    private final int oreMeta;
    private final Item dust;
    private final int amount;
    private final int meta;

    public CrusherRegistryItem(Block ore, int oreMeta, Item item, int amount, int meta) {
        this.ore = ore;
        this.oreMeta = oreMeta;
        this.dust = item;
        this.amount = amount;
        this.meta = meta;
    }

    public ItemStack createStack() {
        return new ItemStack(dust, amount, meta);
    }

    public Block getOre() {
        return ore;
    }

    public int getOreMeta() {
        return oreMeta;
    }
}
