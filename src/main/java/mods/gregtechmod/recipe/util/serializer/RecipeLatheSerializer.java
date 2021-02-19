package mods.gregtechmod.recipe.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import mods.gregtechmod.api.recipe.IGtMachineRecipe;
import mods.gregtechmod.api.recipe.ingredient.IRecipeIngredient;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.List;

public class RecipeLatheSerializer extends RecipeSerializer<IGtMachineRecipe<IRecipeIngredient, List<ItemStack>>, IRecipeIngredient, List<ItemStack>> {
    public static final RecipeLatheSerializer INSTANCE = new RecipeLatheSerializer();

    @Override
    public void serializeInput(IRecipeIngredient input, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObjectField("input", input);
    }

    @Override
    public void serializeOutput(List<ItemStack> output, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeArrayFieldStart("output");
        output.forEach(stack -> {
            try {
                gen.writeObject(stack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        gen.writeEndArray();
    }

    @Override
    public void serializeExtraFields(IGtMachineRecipe<IRecipeIngredient, List<ItemStack>> recipe, JsonGenerator gen, SerializerProvider serializers) {}
}
