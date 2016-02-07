package com.setycz.AnotherDustsMod.JEI;

import com.setycz.AnotherDustsMod.Crusher.JEI.CrusherRecipeCategory;
import com.setycz.AnotherDustsMod.Crusher.JEI.CrusherRecipeHandler;
import com.setycz.AnotherDustsMod.Crusher.JEI.CrusherRecipeMaker;
import com.setycz.AnotherDustsMod.ModAnotherDusts;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

/**
 * Created by setyc on 07.02.2016.
 */
@JEIPlugin
public class AnotherDustsJEIPlugin implements IModPlugin{
    // inspired by https://github.com/mezz/JustEnoughItems

    private IJeiHelpers jeiHelpers;

    @Override
    public void register(IModRegistry registry) {
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
        registry.addRecipeHandlers(new CrusherRecipeHandler());
        registry.addRecipes(CrusherRecipeMaker.getRecipes(jeiHelpers));

        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModAnotherDusts.crusher_on));
    }

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {

    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
