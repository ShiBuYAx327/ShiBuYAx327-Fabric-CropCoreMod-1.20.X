package net.shibuya.cropcoremod.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.shibuya.cropcoremod.Cropcoremod;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final  ComponentType<BlockPos> COORDINATES =
            register("coordinates", builder -> builder.codec(BlockPos.CODEC));



    public  static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Cropcoremod.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {
        Cropcoremod.LOGGER.info("Registering Data Component Types for " + Cropcoremod.MOD_ID);
    }
}
