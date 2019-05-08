package com.github.stilllogic20.bedrocktools.common.network;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.stilllogic20.bedrocktools.common.item.ItemBedrockPickaxe;
import com.github.stilllogic20.bedrocktools.common.item.ItemBedrockPickaxe.VeinMode;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class VeinModeChangedMessage implements IMessage, IMessageHandler<VeinModeChangedMessage, IMessage> {

    @Nullable
    private VeinMode mode;

    public VeinModeChangedMessage() {}

    public VeinModeChangedMessage(@Nonnull VeinMode mode) {
        this.mode = mode;
    }

    @Nullable
    public VeinMode getMode() {
        return mode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Objects.requireNonNull(buf);
        mode = VeinMode.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        Objects.requireNonNull(buf);
        if (mode != null) {
            buf.writeInt(mode.ordinal());
        }
    }

    @Override
    public IMessage onMessage(VeinModeChangedMessage message, MessageContext ctx) {
        assert ctx.side == Side.SERVER;
        EntityPlayer player = ctx.getServerHandler().player;
        ItemStack stack = player.getHeldItemMainhand();
        if (message != null && stack.getItem() instanceof ItemBedrockPickaxe) {
            VeinMode mode = message.mode;
            if (mode != null) {
                ItemBedrockPickaxe.setVeinMode(stack, mode);
            }
        }
        return null;
    }

}
