package com.github.stilllogic20.bedrocktools.common.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NBTAccess {

    private final ItemStack item;
    private final NBTTagCompound tags;

    public NBTAccess(ItemStack item) {
        this(item, item.getTagCompound());
    }

    private NBTAccess(ItemStack item, @Nullable NBTTagCompound tags) {
        this.item = item;
        this.tags = tags;
    }

    public NBTAccess reset() {
        final ItemStack item = this.item;
        final NBTTagCompound nbt = new NBTTagCompound();
        item.setTagCompound(nbt);
        return new NBTAccess(item, nbt);
    }

    public NBTAccess prepare() {
        if (tags == null)
            return reset();
        return this;
    }

    public Optional<? extends NBTAccess> resolve(@Nullable String key) {
        final NBTTagCompound tags = this.tags;
        return tags != null && has(key)
            ? Optional.of(new NBTAccess(item, tags.getCompoundTag(key)))
            : Optional.empty();
    }

    public <T extends Enum<T>> Optional<T> getEnum(@Nullable String key, T[] values) {
        if (!has(key))
            return Optional.empty();
        int index = getInt(key).orElse(0);
        if (index >= values.length)
            index = 0;
        return Optional.of(values[index]);
    }

    public <T extends Enum<T>> boolean setEnum(@Nullable String key, T value) {
        final NBTTagCompound tags = this.tags;
        if (tags == null)
            return false;
        tags.setInteger(key, value.ordinal());
        return true;
    }

    public OptionalInt getInt(@Nullable String key) {
        final NBTTagCompound tags = this.tags;
        if (tags == null)
            return OptionalInt.empty();
        return has(key) ? OptionalInt.of(tags.getInteger(key)) : OptionalInt.empty();
    }

    public boolean has(@Nullable String key) {
        final NBTTagCompound tags = this.tags;
        return tags != null && tags.hasKey(key);
    }

    public <T extends NBTBase> boolean compareAndSet(@Nullable String key, @Nullable T expected,
                                                     @Nullable T value) {
        final NBTTagCompound tags = this.tags;
        if (tags != null && Objects.equals(tags.getTag(key), expected)) {
            tags.setTag(key, value);
            return true;
        }
        return false;
    }

}