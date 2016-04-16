package com.setycz.AnotherDustsMod;

import com.setycz.AnotherDustsMod.Crusher.BlockCrusher;
import com.setycz.AnotherDustsMod.Crusher.BlockCrusherOn;
import com.setycz.AnotherDustsMod.Crusher.CrusherRegistry;
import com.setycz.AnotherDustsMod.Crusher.TileEntityCrusher;
import com.setycz.AnotherDustsMod.Dust.ItemDust;
import com.setycz.AnotherDustsMod.InventoryBlock.TileEntityGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created by setyc on 29.01.2016.
 */
@Mod(
        modid = ModAnotherDusts.MODID,
        name = ModAnotherDusts.MODNAME,
        version = ModAnotherDusts.VERSION,
        acceptedMinecraftVersions = "1.9",
        dependencies = "required-after:Forge@[12.16.0.1865,);"
)
public class ModAnotherDusts {
    public static final String MODID = "anotherdusts";
    public static final String VERSION = "2.0.1";
    public static final String MODNAME = "Another Dusts";
    public static final String TCONSTRUCT = "tconstruct";

    @Mod.Instance(MODID)
    public static ModAnotherDusts instance;

    public static final Logger log = LogManager.getLogger(MODID);

    public final static AnotherDustsTab tab = new AnotherDustsTab();

    public final static Item iron_dust = new ItemDust().setColor(14794665).setUnlocalizedName("iron_dust").setCreativeTab(tab);
    public final static Item gold_dust = new ItemDust().setColor(16444747).setUnlocalizedName("gold_dust").setCreativeTab(tab);

    public final static Block crusher = new BlockCrusher().setUnlocalizedName("crusher").setCreativeTab(tab);
    public final static Block crusher_on = new BlockCrusherOn().setUnlocalizedName("crusher_on").setLightLevel(0.875F);

    public static TileEntityGuiHandler guiHandler = new TileEntityGuiHandler();

    @SidedProxy(clientSide = "com.setycz.AnotherDustsMod.ClientProxy", serverSide = "com.setycz.AnotherDustsMod.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        log.info("Crushing vanila resources.");
        proxy.registerDust(event, iron_dust, Items.iron_ingot, 0, 0.7F, 256);
        proxy.registerDust(event, gold_dust, Items.gold_ingot, 0, 1.0F, 2048);

        proxy.registerBlock(event, crusher);
        proxy.registerBlock(event, crusher_on);
        GameRegistry.registerTileEntity(TileEntityCrusher.class, "anotherDustsCrusher");
        GameRegistry.addRecipe(
                new ItemStack(crusher),
                "FFF", "FFF", "PRP",
                'F', Items.flint, 'P', Blocks.piston, 'R', Blocks.furnace);

        CrusherRegistry.registerRecipe(Blocks.iron_ore, 0, iron_dust, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.gold_ore, 0, gold_dust, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.coal_ore, 0, Items.coal, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.lapis_ore, 0, Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 16);
        CrusherRegistry.registerRecipe(Blocks.diamond_ore, 0, Items.diamond, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.emerald_ore, 0, Items.emerald, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.quartz_ore, 0, Items.quartz, 0, 2);
        CrusherRegistry.registerRecipe(Blocks.redstone_ore, 0, Items.redstone, 0, 5);

        registerTConstruct(event);
    }

    private void registerTConstruct(FMLInitializationEvent event) {
        if(!Loader.isModLoaded(TCONSTRUCT)) {
            return;
        }
        log.info("Tinkers Construct mod detected, crushing even more dusts.");
        Item tIngots = GameRegistry.findItem(TCONSTRUCT, "ingots");
        Block tOre = GameRegistry.findBlock(TCONSTRUCT, "ore");

        Item cobalt_dust = new ItemDust().setColor(2306186).setUnlocalizedName("cobalt_dust").setCreativeTab(tab);
        proxy.registerDust(event, cobalt_dust, tIngots, 0, 1.0F, 1024);
        CrusherRegistry.registerRecipe(tOre, 0, cobalt_dust, 0, 2);

        Item ardite_dust = new ItemDust().setColor(11019543).setUnlocalizedName("ardite_dust").setCreativeTab(tab);
        proxy.registerDust(event, ardite_dust, tIngots, 1, 1.0F, 1024);
        CrusherRegistry.registerRecipe(tOre, 1, ardite_dust, 0, 2);

        try {
            Class tinkerRegistryClass = Class.forName("slimeknights.tconstruct.library.TinkerRegistry");
            Method m = tinkerRegistryClass.getDeclaredMethod("registerMelting", String.class, Fluid.class, int.class);
            m.invoke(null, getItemName(iron_dust), FluidRegistry.getFluid("iron"), 144);
            m.invoke(null, getItemName(gold_dust), FluidRegistry.getFluid("gold"), 144);
            m.invoke(null, getItemName(cobalt_dust), FluidRegistry.getFluid("cobalt"), 144);
            m.invoke(null, getItemName(ardite_dust), FluidRegistry.getFluid("ardite"), 144);
        }
        catch (Exception e) {
            log.error("Cannot register melting recipes for crushed resources.", e);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    private String getItemName(Item dust) {
        return dust.getUnlocalizedName().substring(5);
    }
}
