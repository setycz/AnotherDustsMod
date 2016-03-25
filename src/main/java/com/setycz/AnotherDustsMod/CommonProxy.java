package com.setycz.AnotherDustsMod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by setyc on 25.03.2016.
 */
public class CommonProxy {
    public void registerDust(FMLInitializationEvent event, Item dust, Item ingot, int ingotMeta, float experience, int emc) {
        GameRegistry.registerItem(dust, getItemName(dust));
        GameRegistry.addSmelting(dust, new ItemStack(ingot, 1, ingotMeta), experience);
        OreDictionary.registerOre(getItemName(dust), dust);

        if (Loader.isModLoaded("ProjectE")) {
            // ProjectE not ported to 1.9 yet
            //ProjectEAPI.getEMCProxy().registerCustomEMC(new ItemStack(dust), emc);
        }
    }


    public void registerBlock(FMLInitializationEvent event, Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }

    protected static String getItemName(Item dust) {
        return dust.getUnlocalizedName().substring(5);
    }
}
