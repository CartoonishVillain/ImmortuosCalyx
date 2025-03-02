package com.cartoonishvillain.immortuoscalyx.data.gene;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class GeneComponent {
    public record GeneRecord(String geneValue1, String geneValue2, String contaminationValue, int quality, boolean hasBeenEquipped) {}

    public class GeneClass {
        private final String geneValue1;
        private final String geneValue2;
        private final String contagionValue;
        private final int quality;
        private final boolean hasBeenEquipped;

        public GeneClass(String geneValue1, String geneValue2, String contagionValue, int quality, boolean hasBeenEquipped) {
            this.geneValue1 = geneValue1;
            this.geneValue2 = geneValue2;
            this.contagionValue = contagionValue;
            this.quality = quality;
            this.hasBeenEquipped = hasBeenEquipped;
        }

        @Override
        public int hashCode() {
            return Objects.hash(geneValue1, geneValue2, contagionValue, quality);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else {
                return obj instanceof GeneClass gene
                        && Objects.equals(this.geneValue1, gene.geneValue1)
                        && Objects.equals(this.geneValue2, gene.geneValue2)
                        && Objects.equals(this.contagionValue, gene.contagionValue)
                        && Objects.equals(this.hasBeenEquipped, gene.hasBeenEquipped)
                        && this.quality == gene.quality;
            }
        }
    }

    public static final Codec<GeneRecord> BASIC_CODEC = RecordCodecBuilder.create( instance ->
            instance.group(
                    Codec.STRING.fieldOf("geneValue1").forGetter(GeneRecord::geneValue1),
                    Codec.STRING.fieldOf("geneValue2").forGetter(GeneRecord::geneValue2),
                    Codec.STRING.fieldOf("contagionValue").forGetter(GeneRecord::contaminationValue),
                    Codec.INT.fieldOf("quality").forGetter(GeneRecord::quality),
                    Codec.BOOL.fieldOf("hasBeenEquipped").forGetter(GeneRecord::hasBeenEquipped)
            ).apply(instance, GeneRecord::new)
    );

    public static final StreamCodec<ByteBuf, GeneRecord> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, GeneRecord::geneValue1,
            ByteBufCodecs.STRING_UTF8, GeneRecord::geneValue2,
            ByteBufCodecs.STRING_UTF8, GeneRecord::contaminationValue,
            ByteBufCodecs.INT, GeneRecord::quality,
            ByteBufCodecs.BOOL, GeneRecord::hasBeenEquipped,
            GeneRecord::new
    );
}
