package com.cartoonishvillain.immortuoscalyx.data.gene;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.ladysnake.cca.api.v3.component.TransientComponent;

import java.util.Optional;

public class GeneItemComponent implements GeneComponent, TransientComponent {
    public static final ItemApiLookup<GeneComponent, Void> LOOKUP = ItemApiLookup.get(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_component"), GeneComponent.class, Void.class
    );

    public GeneItemComponent(DataComponentType<GeneData> componentType, ItemStack stack) {
        this.componentType = componentType;
        this.stack = stack;
    }


    public static Optional<GeneComponent> maybeGet(ItemStack stack) {
        return Optional.ofNullable(LOOKUP.find(stack, null));
    }

    private final DataComponentType<GeneItemComponent.GeneData> componentType;
    private final ItemStack stack;

    @Override
    public String getGene1() {
        return this.stack.getOrDefault(this.componentType, GeneData.EMPTY).geneValue1();
    }

    @Override
    public String getGene2() {
        return this.stack.getOrDefault(this.componentType, GeneData.EMPTY).geneValue2();
    }

    @Override
    public String getContamination() {
        return this.stack.getOrDefault(this.componentType, GeneData.EMPTY).contaminationValue();
    }

    @Override
    public int getGeneQuality() {
        return this.stack.getOrDefault(this.componentType, GeneData.EMPTY).quality();
    }

    @Override
    public void setData(String gene1, String gene2, String contamination, int quality, boolean wasEquipped) {
        this.stack.set(this.componentType, new GeneData(gene1, gene2, contamination, quality, wasEquipped));
    }

    public record GeneData(String geneValue1, String geneValue2, String contaminationValue, int quality, boolean previouslyEquipped) {
        public static final GeneData EMPTY = new GeneData("", "", "",0, false);
        public static final Codec<GeneData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("geneValue1").forGetter(GeneData::geneValue1),
                        Codec.STRING.fieldOf("geneValue2").forGetter(GeneData::geneValue2),
                        Codec.STRING.fieldOf("contagionValue").forGetter(GeneData::contaminationValue),
                        Codec.INT.fieldOf("quality").forGetter(GeneData::quality),
                        Codec.BOOL.fieldOf("equipped").forGetter(GeneData::previouslyEquipped)
                ).apply(instance, GeneData::new));
        public static final StreamCodec<ByteBuf, GeneData> PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, GeneData::geneValue1,
                ByteBufCodecs.STRING_UTF8, GeneData::geneValue2,
                ByteBufCodecs.STRING_UTF8, GeneData::contaminationValue,
                ByteBufCodecs.INT, GeneData::quality,
                ByteBufCodecs.BOOL, GeneData::previouslyEquipped,
                GeneData::new
        );
        public static final DataComponentType<GeneData> COMPONENT_TYPE = DataComponentType.<GeneData>builder()
                .persistent(CODEC)
                .networkSynchronized(PACKET_CODEC)
                .build();
    }
}
