package sisicat.cthulhu.payload.payloads;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class CthulhuCheckInstalledPayload implements CustomPayload {

    public static final Id<CthulhuCheckInstalledPayload> ID = new Id<>(Identifier.of("cthulhu", "isinstalled"));

    public static final PacketCodec<PacketByteBuf, CthulhuCheckInstalledPayload> CODEC = CustomPayload.codecOf(
            (cthulhuCheckInstalledPayload, packetByteBuf) -> {},
            packetByteBuf -> new CthulhuCheckInstalledPayload()
    );

    @Override
    public Id<CthulhuCheckInstalledPayload> getId() {
        return ID;
    }

}
