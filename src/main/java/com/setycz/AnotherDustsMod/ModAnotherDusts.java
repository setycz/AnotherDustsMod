package com.setycz.AnotherDustsMod;

import com.setycz.AnotherDustsMod.Crusher.BlockCrusher;
import com.setycz.AnotherDustsMod.Crusher.BlockCrusherOn;
import com.setycz.AnotherDustsMod.Crusher.CrusherRegistry;
import com.setycz.AnotherDustsMod.Crusher.TileEntityCrusher;
import com.setycz.AnotherDustsMod.Dust.ItemDust;
import com.setycz.AnotherDustsMod.InventoryBlock.TileEntityGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
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
    public static final String VERSION = "1.1";
    public static final String MODNAME = "Another Dusts";

    @Mod.Instance(MODID)
    public static ModAnotherDusts instance;

    public final static AnotherDustsTab tab = new AnotherDustsTab();

    public final static Item iron_dust = new ItemDust().setColor(14794665).setUnlocalizedName("iron_dust").setCreativeTab(tab);
    public final static Item gold_dust = new ItemDust().setColor(16444747).setUnlocalizedName("gold_dust").setCreativeTab(tab);

    public final static Block crusher = new BlockCrusher().setUnlocalizedName("crusher").setCreativeTab(tab);
    public final static Block crusher_on = new BlockCrusherOn().setUnlocalizedName("crusher_on").setLightLevel(0.875F);

    public static TileEntityGuiHandler guiHandler = new TileEntityGuiHandler();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        registerDust(event, iron_dust, Items.iron_ingot);
        registerDust(event, gold_dust, Items.gold_ingot);

        registerBlock(event, crusher);
        registerBlock(event, crusher_on);
        GameRegistry.registerTileEntity(TileEntityCrusher.class, "anotherDustsCrusher");
        GameRegistry.addRecipe(
                new ItemStack(crusher),
                "FFF", "FFF", "PRP",
                'F', Items.flint, 'P', Blocks.piston, 'R', Blocks.furnace);

        CrusherRegistry.registerRecipe(Blocks.iron_ore, iron_dust, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.gold_ore, gold_dust, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.coal_ore, Items.coal, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.lapis_ore, Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 9);
        CrusherRegistry.registerRecipe(Blocks.diamond_ore, Items.diamond, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.emerald_ore, Items.emerald, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.quartz_ore, Items.quartz, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.redstone_ore, Items.redstone, 0, 6);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void registerDust(FMLInitializationEvent event, Item dust, Item ingot) {
        registerItem(event, dust);
        GameRegistry.addSmelting(dust, new ItemStack(ingot), 1f);
        OreDictionary.registerOre(getItemName(dust), dust);
    }

    private void registerBlock(FMLInitializationEvent event, Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        if (event.getSide() == Side.CLIENT) {
            Item crusherItem = Item.getItemFromBlock(block);
            registerItemModel(crusherItem);
        }
    }

    private void registerItem(FMLInitializationEvent event, Item item) {
        GameRegistry.registerItem(item, getItemName(item));
        if (event.getSide() == Side.CLIENT) {
            registerItemModel(item);
        }
    }

    private void registerItemModel(Item item) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(MODID + ":" + getItemName(item), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, resourceLocation);
    }

    private String getItemName(Item dust) {
        return dust.getUnlocalizedName().substring(5);
    }
}
