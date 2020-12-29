package mods.gregtechmod.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ic2.core.item.ItemFluidCell;
import mods.gregtechmod.api.GregTechAPI;
import mods.gregtechmod.api.recipe.GtRecipes;
import mods.gregtechmod.api.recipe.IGtMachineRecipe;
import mods.gregtechmod.api.recipe.ingredient.IRecipeIngredient;
import mods.gregtechmod.api.util.Reference;
import mods.gregtechmod.core.GregTechMod;
import mods.gregtechmod.recipe.RecipeAssembler;
import mods.gregtechmod.recipe.RecipeCentrifuge;
import mods.gregtechmod.recipe.RecipeFactory;
import mods.gregtechmod.recipe.RecipeIngredientFactory;
import mods.gregtechmod.recipe.ingredient.RecipeIngredientOre;
import mods.gregtechmod.recipe.manager.RecipeManagerAssembler;
import mods.gregtechmod.recipe.manager.RecipeManagerCentrifuge;
import mods.gregtechmod.recipe.manager.RecipeManagerPulverizer;
import mods.gregtechmod.util.ItemStackDeserializer;
import mods.gregtechmod.util.RecipeFilter;
import mods.gregtechmod.util.RecipeIngredientDeserializer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RecipeLoader {
    private static Path configPath = null;
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .registerModule(new SimpleModule()
                    .addDeserializer(ItemStack.class, ItemStackDeserializer.INSTANCE)
                    .addDeserializer(IRecipeIngredient.class, RecipeIngredientDeserializer.INSTANCE));

    public static void load() {
        GregTechAPI.logger.info("Loading machine recipes");

        GregTechAPI.recipeFactory = new RecipeFactory();
        GregTechAPI.ingredientFactory = new RecipeIngredientFactory();

        try {
            File modFile = Loader.instance().activeModContainer().getSource();
            FileSystem fs = FileSystems.newFileSystem(modFile.toPath(), null);

            Path recipesPath = fs.getPath("assets", Reference.MODID, "machine_recipes");
            Path gtConfig = relocateRecipeConfig(recipesPath);
            if (gtConfig == null) {
                GregTechAPI.logger.error("Couldn't find the recipes config directory. Loading default recipes...");
                configPath = recipesPath;
            } else configPath = gtConfig;

            GtRecipes.industrial_centrifuge = new RecipeManagerCentrifuge();
            RecipeLoader.parseRecipe("industrial_centrifuge", RecipeCentrifuge.class, RecipeFilter.Default.class)
                    .ifPresent(recipes -> recipes.stream().filter(recipe -> !recipe.isInvalid()).forEach(GtRecipes.industrial_centrifuge::addRecipe));
            GtRecipes.assembler = new RecipeManagerAssembler();
            RecipeLoader.parseRecipe("assembler", RecipeAssembler.class, null)
                    .ifPresent(recipes -> recipes.stream().filter(recipe -> !recipe.isInvalid()).forEach(GtRecipes.assembler::addRecipe));

            GtRecipes.pulverizer = new RecipeManagerPulverizer();

        } catch (IOException e) {
            e.printStackTrace();
        }

        GtRecipes.industrial_centrifuge.getRecipes().forEach(recipe -> {
            System.out.println("Input:");
            List<ItemStack> matchingInputs = recipe.getInput().getMatchingInputs();
            if (matchingInputs.isEmpty() && recipe.getInput() instanceof RecipeIngredientOre) System.out.println(" " + ((RecipeIngredientOre) recipe.getInput()).getOre() + " " + recipe.getInput().getCount());
            recipe.getInput().getMatchingInputs().forEach(stack -> System.out.println(" " + stack.getItem().getRegistryName() + " x " + recipe.getInput().getCount() + " @ " + stack.getMetadata()));
            System.out.println("Output:");
            recipe.getOutput().forEach(stack -> {
                String name;
                if (stack.getItem() instanceof ItemFluidCell) name = ((ItemFluidCell)stack.getItem()).getVariant(stack);
                else name = stack.getItem().getRegistryName().toString();
                System.out.println(" "+name+" x "+stack.getCount()+" @ "+stack.getMetadata());
            });
            System.out.println("cells: "+recipe.getCells());
            System.out.println("duration: "+recipe.getDuration());
            System.out.println("energyCost: "+recipe.getEnergyCost());
            System.out.println();
        });

        /*GtRecipes.assembler.getRecipes().forEach(recipe -> {
            System.out.println("Input:");
            recipe.getInput().forEach(input -> {
                if (input.getMatchingInputs().isEmpty() && input instanceof RecipeIngredientOre) System.out.println(" "+((RecipeIngredientOre) input).getOre() + "x" + input.getCount());
                else input.getMatchingInputs().forEach(stack -> System.out.println(" " + stack.getItem().getRegistryName() + " x " + input.getCount() + " @ " + stack.getMetadata()));
            });
            System.out.println("Output:");
            ItemStack output = recipe.getOutput();
            String name;
            if (output.getItem() instanceof ItemFluidCell) name = ((ItemFluidCell)output.getItem()).getVariant(output);
            else name = output.getItem().getRegistryName().toString();
            System.out.println(" "+name+" x "+output.getCount()+" @ "+output.getMetadata());
            System.out.println("duration: "+recipe.getDuration());
            System.out.println("energyCost: "+recipe.getEnergyCost());
            System.out.println();
        });*/
    }

    public static <R extends IGtMachineRecipe<?, ?>, T extends RecipeFilter> Optional<Collection<R>> parseRecipe(String name, Class<R> recipeClass, @Nullable Class<T> recipeType) {
        try {
            ObjectMapper recipeMapper = mapper.copy();
            if (recipeType != null) recipeMapper.addMixIn(IGtMachineRecipe.class, recipeType);

            return Optional.ofNullable(recipeMapper.readValue(Files.newBufferedReader(configPath.resolve(name+".yml")), recipeMapper.getTypeFactory().constructCollectionType(List.class, recipeClass)));
        } catch (IOException e) {
            GregTechAPI.logger.error("Failed to parse recipes for "+name+": "+e.getMessage());
            return Optional.empty();
        }
    }

    private static Path relocateRecipeConfig(Path source) {
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(source);
            File configDir = new File(GregTechMod.configDir.toURI().getPath()+"/GregTech/machine recipes");
            configDir.mkdirs();
            for(Path path : stream) {
                GregTechAPI.logger.debug("Copying recipe config: "+path.getFileName());
                File dest = new File(Paths.get(configDir.getPath(), path.getFileName().toString()).toUri());
                if(!dest.exists()) {
                    BufferedReader in = Files.newBufferedReader(path);
                    FileOutputStream out = new FileOutputStream(dest);
                    for (int i; (i = in.read()) != -1; ) {
                        out.write(i);
                    }

                    in.close();
                    out.close();
                }
            }
            return configDir.toPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}