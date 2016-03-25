package com.setycz.AnotherDustsMod;

import com.setycz.AnotherDustsMod.Dust.ItemColorDust;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Created by setyc on 25.03.2016.
 */
public class ClientProxy extends CommonProxy {
    private final ItemColorDust itemColorDust = new ItemColorDust();

    @Override
    public void registerDust(FMLInitializationEvent event, Item dust, Item ingot, int ingotMeta, float experience, int emc) {
        super.registerDust(event, dust, ingot, ingotMeta, experience, emc);
        registerItemModel(dust);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColorDust, dust);
    }

    @Override
    public void registerBlock(FMLInitializationEvent event, Block block) {
        super.registerBlock(event, block);
        Item crusherItem = Item.getItemFromBlock(block);
        registerItemModel(crusherItem);
    }

    private void registerItemModel(Item item) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(ModAnotherDusts.MODID + ":" + getItemName(item), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, resourceLocation);
    }
}
