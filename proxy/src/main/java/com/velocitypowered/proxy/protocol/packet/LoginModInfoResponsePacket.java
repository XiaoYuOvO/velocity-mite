package com.velocitypowered.proxy.protocol.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginModInfoResponsePacket implements MinecraftPacket {
    private Map<String, List<String>> modInfo;

    public LoginModInfoResponsePacket() {
        super();
    }

    public LoginModInfoResponsePacket(@NotNull Map<String, List<String>> modInfo) {
        this.modInfo = modInfo;
    }

    @Override
    public void decode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
        this.modInfo = ProtocolUtils.readMap(buf, Maps::newHashMapWithExpectedSize, ProtocolUtils::readString, packetByteBuf -> {
            ArrayList<String> objects = Lists.newArrayList();
            int i1 = ProtocolUtils.readVarInt(packetByteBuf);
            for (int i = 0; i < i1; i++) {
                objects.add(ProtocolUtils.readString(packetByteBuf));
            }
            return objects;
        });
    }

    @Override
    public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
        ProtocolUtils.writeMap(buf, this.modInfo, ProtocolUtils::writeString, (packetByteBuf, strings) -> {
            ProtocolUtils.writeVarInt(packetByteBuf, strings.size());
            strings.forEach(s -> ProtocolUtils.writeString(packetByteBuf, s));
        });
    }

    public Map<String, List<String>> getModInfo() {
        return modInfo;
    }

    @Override
    public boolean handle(MinecraftSessionHandler handler) {
        return handler.handle(this);
    }
}
