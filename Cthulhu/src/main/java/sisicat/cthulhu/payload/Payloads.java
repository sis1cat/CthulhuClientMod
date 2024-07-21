package sisicat.cthulhu.payload;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import sisicat.cthulhu.payload.payloads.CthulhuCheckInstalledPayload;
import sisicat.cthulhu.payload.payloads.CthulhuCommandPayload;

public class Payloads {

    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(CthulhuCheckInstalledPayload.ID, CthulhuCheckInstalledPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CthulhuCheckInstalledPayload.ID, CthulhuCheckInstalledPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(CthulhuCommandPayload.ID, CthulhuCommandPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CthulhuCommandPayload.ID, CthulhuCommandPayload.CODEC);
    }
}
