package com.setycz.AnotherDustsMod;

import com.setycz.AnotherDustsMod.Crusher.BlockCrusher;
import com.setycz.AnotherDustsMod.Crusher.CrusherRegistry;
import com.setycz.AnotherDustsMod.Crusher.TileEntityCrusher;
import com.setycz.AnotherDustsMod.Dust.ItemDust;
import com.setycz.AnotherDustsMod.InventoryBlock.TileEntityGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by setyc on 29.01.2016.
 */
@Mod(
        modid = ModAnotherDusts.MODID,
        name = ModAnotherDusts.MODNAME,
        version = ModAnotherDusts.VERSION,
        acceptedMinecraftVersions = "1.8.9"
)
public class ModAnotherDusts {
    public static final String MODID = "anotherdusts";
    public static final String VERSION = "1.0";
    public static final String MODNAME = "Another Dusts";

    @Mod.Instance(MODID)
    public static ModAnotherDusts instance;

    public final static AnotherDustsTab tab = new AnotherDustsTab();

    public final static Block crusher = new BlockCrusher().setUnlocalizedName("crusher").setCreativeTab(tab);
    public final static Block crusher_on = new BlockCrusher().setUnlocalizedName("crusher_on").setLightLevel(0.875F);

    public static TileEntityGuiHandler guiHandler = new TileEntityGuiHandler();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        registerDust(event, "iron_dust", 14794665, Blocks.iron_ore, Items.iron_ingot);
        registerDust(event, "gold_dust", 16444747, Blocks.gold_ore, Items.gold_ingot);

        registerBlock(event, crusher);
        registerBlock(event, crusher_on);

        GameRegistry.registerTileEntity(TileEntityCrusher.class, "anotherDustsCrusher");
    }

    private void registerDust(FMLInitializationEvent event, String name, int color, Block ore, Item ingot) {
        Item dust = new ItemDust().setColor(color).setUnlocalizedName(name).setCreativeTab(tab);

        registerItem(event, dust);

        OreDictionary.registerOre(dust.getUnlocalizedName().substring(5), dust);
        GameRegistry.addSmelting(dust, new ItemStack(ingot), 1f);

        CrusherRegistry.registerRecipe(ore, dust);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void registerBlock(FMLInitializationEvent event, Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        if (event.getSide() == Side.CLIENT) {
            Item crusherItem = Item.getItemFromBlock(block);
            registerItemModel(crusherItem);
        }
    }

    private void registerItem(FMLInitializationEvent event, Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        if (event.getSide() == Side.CLIENT) {
            registerItemModel(item);
        }
    }

    private void registerItemModel(Item item) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(MODID + ":" + item.getUnlocalizedName().substring(5), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, resourceLocation);
    }
}
