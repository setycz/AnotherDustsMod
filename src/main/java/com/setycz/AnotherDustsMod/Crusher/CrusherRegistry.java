package com.setycz.AnotherDustsMod.Crusher;

import gnu.trove.map.hash.THashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Created by setyc on 30.01.2016.
 */
public final class CrusherRegistry {
    private final static Map<Block, Item> recipes = new THashMap<Block, Item>();

    public static void registerRecipe(Block oreBlock, Item dust) {
        recipes.put(oreBlock, dust);
    }

    public static Item getItemForBlock(Block oreBlock) {
        return recipes.getOrDefault(oreBlock, null);
    }

    public static ItemStack createItemsForBlock(Block oreBlock) {
        Item dustItem = getItemForBlock(oreBlock);
        if (dustItem == null) {
            return null;
        }
        return new ItemStack(dustItem, 2);
    }
}
