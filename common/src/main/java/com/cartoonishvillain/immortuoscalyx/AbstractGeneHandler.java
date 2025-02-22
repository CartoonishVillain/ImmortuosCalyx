package com.cartoonishvillain.immortuoscalyx;


public class AbstractGeneHandler {
    public static float turtleDamageHandler(float damageIncoming, int amplitude) {
        return damageIncoming * (0.95f - (0.05f * (float) amplitude));
    }

    public static float frogGeneJumpBoost(float jumpStrength, int amplitude) {
        return jumpStrength + (0.10f + (0.05F * (float) amplitude));
    }
}
