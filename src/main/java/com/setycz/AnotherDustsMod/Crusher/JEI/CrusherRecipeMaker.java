package com.setycz.AnotherDustsMod.Crusher.JEI;

import com.setycz.AnotherDustsMod.Crusher.CrusherRegistry;
import com.setycz.AnotherDustsMod.Crusher.CrusherRegistryItem;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by setyc on 07.02.2016.
 */
public class CrusherRecipeMaker {
    @Nonnull
    public static List<CrusherRecipe> getRecipes(IJeiHelpers jeiHelpers) {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        List<CrusherRecipe> recipes = new ArrayList<CrusherRecipe>();
        for (CrusherRegistryItem recipe : CrusherRegistry.getAllRecipes()) {
            recipes.add(new CrusherRecipe(
                    stackHelper.getSubtypes(new ItemStack(recipe.getOre())),
                    recipe.createStack()
            ));
        }
        return recipes;
    }
}
