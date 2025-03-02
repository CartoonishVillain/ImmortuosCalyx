package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.gene.GeneComponent;
import com.cartoonishvillain.immortuoscalyx.data.player.NeoForgeInfectionPlayerData;
import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import com.cartoonishvillain.immortuoscalyx.items.DefaultGeneMethods;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.Objects;

import static com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionDataAttachment.INFECTION_DATA;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public void setInfectionPercentage(ServerPlayer serverPlayer, int infectionPercentage) {
        NeoForgeInfectionPlayerData playerData = serverPlayer.getData(INFECTION_DATA);
        playerData.setInfectionPercent(infectionPercentage);
        serverPlayer.setData(INFECTION_DATA, playerData);
    }

    @Override
    public int getInfectionPercentage(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getInfectionPercent();
    }

    @Override
    public ArrayList<Symptom> getSymptoms(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getSymptoms();
    }

    @Override
    public void addSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom) {
        symptom.chirpAndAddSymptomEffect(serverPlayer);
        serverPlayer.getData(INFECTION_DATA).addSymptom(symptom.getSymptom());
    }

    @Override
    public void removeSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom) {
        symptom.removeSymptomEffect(serverPlayer);
        serverPlayer.getData(INFECTION_DATA).removeSymptom(symptom.getSymptom());
    }

    @Override
    public void setSymptoms(ServerPlayer player, ArrayList<Symptom> symptoms) {
        player.getData(INFECTION_DATA).setSymptoms(symptoms);
    }

    @Override
    public boolean tickInfection(ServerPlayer serverPlayer) {
        MobEffectInstance instance = null;
        if (serverPlayer.hasEffect(
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IMMORTUOS.get())
        )) {
            instance = serverPlayer.getEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IMMORTUOS.get()));
        }

        return serverPlayer.getData(INFECTION_DATA).tickInfection(instance);
    }

    @Override
    public void setResistance(ServerPlayer serverPlayer, float resistance) {
        serverPlayer.getData(INFECTION_DATA).setResistance(resistance);
    }

    @Override
    public float getResistance(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getResistance();
    }

    @Override
    public void updateGeneAndGiveToPlayer(Player pPlayer, ItemStack identifiedGene, String randomGene, int quality) {
        identifiedGene.set(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord(randomGene, "", "", quality, false));
        ItemEntity itemEntity = new ItemEntity(pPlayer.level(), pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), identifiedGene);
        itemEntity.setPos(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
        pPlayer.level().addFreshEntity(itemEntity);
    }

    @Override
    public void tryGeneCombination(Player pPlayer, ItemStack mainStack, ItemStack offStack) {
        GeneComponent.GeneRecord mainGeneData = mainStack.getComponents().getOrDefault(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord("", "", "", 0, false));
        GeneComponent.GeneRecord offGeneData = offStack.getComponents().getOrDefault(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord("", "", "", 0, false));

        // if either gene was previously equipped, destroy the stack
        if (mainGeneData.hasBeenEquipped()) {
            mainStack.shrink(1);
            pPlayer.displayClientMessage(Component.translatable("gene.immortuoscalyx.destablized.main").withStyle(ChatFormatting.RED), true);
            pPlayer.level().playSound(null, pPlayer.blockPosition().above(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1f, 2f);
        } else if (offGeneData.hasBeenEquipped()) {
            offStack.shrink(1);
            pPlayer.displayClientMessage(Component.translatable("gene.immortuoscalyx.destablized.off").withStyle(ChatFormatting.RED), true);
            pPlayer.level().playSound(null, pPlayer.blockPosition().above(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1f, 2f);
        } else if (
                !mainGeneData.geneValue2().isBlank() || !offGeneData.geneValue2().isBlank() || !mainGeneData.contaminationValue().isBlank() || !offGeneData.contaminationValue().isBlank()
            //any contamination or pre-combined genes are rejected.
        ) {
            pPlayer.displayClientMessage(Component.translatable("gene.immortuoscalyx.invalid").withStyle(ChatFormatting.RED), true);
            pPlayer.level().playSound(null, pPlayer.blockPosition().above(), SoundEvents.NOTE_BLOCK_PLING.value(), SoundSource.PLAYERS, 1f, 0.5f);
        } else if (
                Objects.equals(mainGeneData.geneValue1(), offGeneData.geneValue1()) //when combining two of the same geen
        ) {
            String contamination = "";
            SoundEvent event = NeoSoundEvents.SCANCLEAR.get();

            //5% chance for contamination
            boolean contaminated = pPlayer.getRandom().nextInt(100) < 5;
            if (contaminated) {
                contamination = DefaultGeneMethods.contaminationPicker(pPlayer.getRandom());
                event = NeoSoundEvents.SCANBAD.get();
            }

            int quality = mainGeneData.quality() + offGeneData.quality();
            if (quality > 100) quality = 100;
            mainStack.set(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord(mainGeneData.geneValue1(), "", contamination, quality, false));
            pPlayer.level().playSound(null, pPlayer.blockPosition().above(), event, SoundSource.PLAYERS, 1f, 1f);
            offStack.shrink(1);
        } else {
            //Two different non-combined non-contaminated genes
            String contamination = "";
            SoundEvent event = NeoSoundEvents.SCANCLEAR.get();
            //12% chance for contamination
            boolean contaminated = pPlayer.getRandom().nextInt(100) < 12;
            if (contaminated) {
                contamination = DefaultGeneMethods.contaminationPicker(pPlayer.getRandom());
                event = NeoSoundEvents.SCANBAD.get();
            }

            int quality = (mainGeneData.quality() + offGeneData.quality())/2;
            mainStack.set(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord(mainGeneData.geneValue1(), offGeneData.geneValue1(), contamination, quality, false));
            pPlayer.level().playSound(null, pPlayer.blockPosition().above(), event, SoundSource.PLAYERS, 1f, 1f);
            offStack.shrink(1);
        }
    }

    @Override
    public Holder<MobEffect> INFECTION_BLIND() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_BLIND.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WATER_BREATHING() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_WATER_BREATH.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_STRENGTH_TEMPERATURE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_COAGULATION.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_STRENGTH() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_STRENGTH.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_RESIST() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_RESIST.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WEAKEN() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_WEAKEN.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_VULNERABLE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_VULNERABLE.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SPEED_TEMPERATURE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_STABILITY.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SPEED() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_SPEED.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SLOW() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_SLOW.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CHAT() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_CHAT.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CONTAGION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_CONTAGION.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CONSUMPTION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_CONSUME.get());
    }

    @Override
    public Holder<MobEffect> GENE_IMMORTUOS() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IMMORTUOS.get());
    }

    @Override
    public Holder<MobEffect> GENE_ZOMBIE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_ZOMBIE.get());
    }

    @Override
    public Holder<MobEffect> GENE_OCELOT() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_OCELOT.get());
    }

    @Override
    public Holder<MobEffect> GENE_TURTLE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_TURTLE.get());
    }

    @Override
    public Holder<MobEffect> GENE_IRON_GOLEM() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IRON_GOLEM.get());
    }

    @Override
    public Holder<MobEffect> GENE_IRON_GOLEM_ACTIVE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_TEMP_IRON_GOLEM.get());
    }

    @Override
    public Holder<MobEffect> GENE_FROG() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_FROG.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_HELIOPHOBIA() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_HELIOPHOBIA.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_HYDROPHOBIA() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_HYDROPHOBIA.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_GENETIC_DESTABILIZATION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_GENETIC_DESTABLIZATION.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_STAGGER() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_STAGGER.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_KNEE_PASTAFICATION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_KNEE_PASTAFICATION.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_KNEE_PASTAFICATION_ACTIVE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_KNEE_PASTAFICATION_TEMP.get());
    }

    @Override
    public SoundEvent HUMANOID_AMBIENT() {
        return NeoSoundEvents.HUMANAMBIENT.value();
    }

    @Override
    public SoundEvent HUMANOID_HURT() {
        return NeoSoundEvents.HUMANHURT.value();
    }

    @Override
    public SoundEvent HUMANOID_DEATH() {
        return NeoSoundEvents.HUMANDEATH.value();
    }

    @Override
    public SoundEvent INJECT() {
        return NeoSoundEvents.INJECT.value();
    }

    @Override
    public SoundEvent EXTRACT() {
        return NeoSoundEvents.EXTRACT.value();
    }

    @Override
    public SoundEvent SCAN_BAD() {
        return NeoSoundEvents.SCANBAD.value();
    }

    @Override
    public SoundEvent SCAN_GOOD() {
        return NeoSoundEvents.SCANCLEAR.value();
    }

    @Override
    public EntityType<? extends Monster> getInfectedHuman() {
        return NeoEntity.INFECTEDHUMAN.get();
    }

    @Override
    public Item EMPTY_SYRINGE() {
        return NeoItems.SYRINGE.value();
    }

    @Override
    public Item CALYXANIDE() {
        return NeoItems.CALYXANIDE.value();
    }

    @Override
    public Item IMMORTUOS_SAMPLE() {
        return NeoItems.IMMORTUOS_SAMPLE.value();
    }

    @Override
    public Item IMMORTUOS_EGG() {
        return NeoItems.IMMORTUOS_EGGS.value();
    }

    @Override
    public Item ANTIPARASITIC() {
        return NeoItems.ANTI_PARASITIC.value();
    }

    @Override
    public Item HEALTH_SCANNER() {
        return NeoItems.HEALTH_SCANNER.value();
    }

    @Override
    public Item UNIDENTIFIED_GENE() {
        return NeoItems.UNIDENTIFIED_GENE.get();
    }

    @Override
    public Item IDENTIFIED_GENE() {
        return NeoItems.IDENTIFIED_GENE.get();
    }
}