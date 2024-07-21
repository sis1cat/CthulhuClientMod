package sisicat.cthulhu.payload.payloads;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

public class CthulhuCommandPayload implements CustomPayload {

    public static final CustomPayload.Id<CthulhuCommandPayload> ID = new CustomPayload.Id<>(Identifier.of("cthulhu", "commandexec"));

    private final String command;

    public CthulhuCommandPayload(String command){
        this.command = command;
    }

    public static final PacketCodec<PacketByteBuf, CthulhuCommandPayload> CODEC = CustomPayload.codecOf(
            CthulhuCommandPayload::write,
            packetByteBuf -> new CthulhuCommandPayload("")
    );

    public final void write(final PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBytes(this.command.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public CustomPayload.Id<CthulhuCommandPayload> getId() {
        return ID;
    }

}
