package com.setycz.AnotherDustsMod.Crusher;

import gnu.trove.map.hash.THashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by setyc on 30.01.2016.
 */
public final class CrusherRegistry {

    private final static Map<Block, CrusherRegistryItem> recipes = new THashMap<Block, CrusherRegistryItem>();

    public static void registerRecipe(Block ore, Item resultItem, int resultItemMeta, int resultAmount) {
        recipes.put(ore, new CrusherRegistryItem(ore, resultItem, resultAmount, resultItemMeta));
    }

    public static ItemStack createItemsForBlock(Block oreBlock) {
        CrusherRegistryItem registeredItem = recipes.getOrDefault(oreBlock, null);
        if (registeredItem == null) {
            return null;
        }
        return registeredItem.createStack();
    }

    public static List<CrusherRegistryItem> getAllRecipes() {
        return new ArrayList<CrusherRegistryItem>(recipes.values());
    }
}
