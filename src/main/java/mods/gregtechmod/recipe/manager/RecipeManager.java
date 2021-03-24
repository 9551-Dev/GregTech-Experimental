package mods.gregtechmod.recipe.manager;

import mods.gregtechmod.api.recipe.IMachineRecipe;
import mods.gregtechmod.api.recipe.manager.IGtRecipeManager;

import java.util.ArrayList;
import java.util.List;

public abstract class RecipeManager<RI, I, R extends IMachineRecipe<RI, ?>> implements IGtRecipeManager<RI, I, R> {
    protected final List<R> recipes;

    public RecipeManager() {
        this.recipes = new ArrayList<>();
    }

    @Override
    public boolean addRecipe(R recipe, boolean overwrite) {
        if (recipe.isInvalid()) return false;

        R existing = getRecipeForExact(recipe);
        if (existing != null) {
            if (overwrite) this.recipes.remove(existing);
            else return false;
        }

        return this.recipes.add(recipe);
    }

    @Override
    public void removeRecipe(R recipe) {
        R existing = getRecipeForExact(recipe);
        if (existing != null) this.recipes.remove(existing);
    }

    @Override
    public List<R> getRecipes() {
        return this.recipes;
    }

    protected abstract R getRecipeForExact(R recipe);
}