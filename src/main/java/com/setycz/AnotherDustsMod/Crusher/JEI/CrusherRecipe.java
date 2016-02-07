package com.setycz.AnotherDustsMod.Crusher.JEI;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Created by setyc on 07.02.2016.
 */
public class CrusherRecipe extends BlankRecipeWrapper {
    @Nonnull
    private final List<List<ItemStack>> input;
    @Nonnull
    private final List<ItemStack> output;

    public CrusherRecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output) {
        this.input = Collections.singletonList(input);
        this.output = Collections.singletonList(output);
    }

    @Override
    public List getInputs() {
        return input;
    }

    @Override
    public List getOutputs() {
        return output;
    }
}
