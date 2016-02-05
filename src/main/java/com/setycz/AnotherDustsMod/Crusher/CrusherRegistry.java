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
    static class RegisteredItem {
        private final Item dust;
        private final int amount;
        private final int meta;

        public RegisteredItem(Item item, int amount, int meta) {
            this.dust = item;
            this.amount = amount;
            this.meta = meta;
        }

        public ItemStack createStack() {
            return new ItemStack(dust, amount, meta);
        }
    }

    private final static Map<Block, RegisteredItem> recipes = new THashMap<Block, RegisteredItem>();

    public static void registerRecipe(Block ore, Item resultItem, int resultItemMeta, int resultAmount) {
        recipes.put(ore, new RegisteredItem(resultItem, resultAmount, resultItemMeta));
    }

    public static ItemStack createItemsForBlock(Block oreBlock) {
        RegisteredItem registeredItem = recipes.getOrDefault(oreBlock, null);
        if (registeredItem == null) {
            return null;
        }
        return registeredItem.createStack();
    }
}
