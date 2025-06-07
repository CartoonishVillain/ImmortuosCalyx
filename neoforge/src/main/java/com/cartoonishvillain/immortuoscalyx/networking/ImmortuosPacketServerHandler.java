package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.NeoforgeImmortuos;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class ImmortuosPacketServerHandler implements IPayloadHandler<NeoforgeImmortuos.ImmortuosPayload> {
    private static final ImmortuosPacketServerHandler INSTANCE = new ImmortuosPacketServerHandler();

    public static ImmortuosPacketServerHandler getInstance() {return INSTANCE;}
    @Override
    public void handle(NeoforgeImmortuos.ImmortuosPayload immortuosPayload, IPayloadContext iPayloadContext) {
        handleData(immortuosPayload, iPayloadContext);
    }

    public static void handleData(NeoforgeImmortuos.ImmortuosPayload immortuosPayload, IPayloadContext iPayloadContext) {
        CommonImmortuos.setClientActiveGenes(Constants.decodeCSV(immortuosPayload.genesEnabled()));
        CommonImmortuos.setClientActiveContamination(Constants.decodeCSV(immortuosPayload.contaminationsEnabled()));
    }
}
