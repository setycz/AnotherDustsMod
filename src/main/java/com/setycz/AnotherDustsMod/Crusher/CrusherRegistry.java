package com.setycz.AnotherDustsMod.Crusher;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by setyc on 30.01.2016.
 */
public final class CrusherRegistry {
    private final static List<CrusherRegistryItem> recipes = new ArrayList<CrusherRegistryItem>();

    public static void registerRecipe(Block ore, int blockMeta, Item resultItem, int resultItemMeta, int resultAmount) {
        recipes.add(new CrusherRegistryItem(ore, blockMeta, resultItem, resultAmount, resultItemMeta));
    }

    public static ItemStack createItemsForBlock(Block oreBlock, int oreMeta) {
        for (CrusherRegistryItem registeredItem : recipes) {
            if (registeredItem.getOre().equals(oreBlock) && registeredItem.getOreMeta() == oreMeta) {
                return registeredItem.createStack();
            }
        }
        return null;
    }

    public static List<CrusherRegistryItem> getAllRecipes() {
        return recipes;
    }
}
