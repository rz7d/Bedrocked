package com.github.stilllogic20.bedrocktools.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public final class Recipes {

    private Recipes() {
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new Recipes());
    }

    @SubscribeEvent
    public void registerRecipes(@Nullable RegistryEvent.Register<IRecipe> event) {
        GameRegistry.addSmelting(Items.BEDROCK_PICKAXE, new ItemStack(Blocks.BEDROCK, 3), 10F);
        GameRegistry.addSmelting(Items.BEDROCK_SWORD, new ItemStack(Blocks.BEDROCK, 2), 10F);
    }

}
