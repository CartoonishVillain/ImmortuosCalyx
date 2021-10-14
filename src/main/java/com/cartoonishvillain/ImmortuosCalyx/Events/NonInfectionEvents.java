package com.cartoonishvillain.ImmortuosCalyx.Events;

import com.cartoonishvillain.ImmortuosCalyx.Configs.CommonConfig;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedDiverEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedHumanEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedIGEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedVillagerEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.InternalOrganDamage;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import com.cartoonishvillain.ImmortuosCalyx.commands.SetInfectionRateCommand;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NonInfectionEvents {

    @SubscribeEvent
    public static void commandRegister(RegisterCommandsEvent event){
        SetInfectionRateCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void selfUseCalyxide(PlayerInteractEvent.RightClickItem event){
        if(event.getEntity() instanceof Player && event.getItemStack().getItem().equals(Register.CALYXANIDE.get())){
            Player player = (Player) event.getEntity();
            if(player.isCrouching()) {CalyxideCure(player);
            event.getItemStack().shrink(1);
            if(event.getSide() == LogicalSide.SERVER){event.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), Register.INJECT.get(), SoundSource.PLAYERS, 1f, 1f);}
            }


        }
    }

    @SubscribeEvent
    public static void selfUseAntiParasite(PlayerInteractEvent.RightClickItem event){
        if(event.getEntity() instanceof Player && event.getItemStack().getItem().equals(Register.GENERALANTIPARASITIC.get())){
            Player player = (Player) event.getEntity();
            if(player.isCrouching()) {AntiParasiticCure(player);
            event.getItemStack().shrink(1);
            if(event.getSide() == LogicalSide.SERVER){event.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), Register.INJECT.get(), SoundSource.PLAYERS, 1f, 1f);}
            }
        }
    }

    @SubscribeEvent
    public static void selfUseCEggs(PlayerInteractEvent.RightClickItem event){
        if(event.getEntity() instanceof Player && event.getItemStack().getItem().equals(Register.IMMORTUOSCALYXEGGS.get())){
            Player player = (Player) event.getEntity();
            if(player.isCrouching()) {
                player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                    if(h.getInfectionProgress() == 0){h.addInfectionProgress(ImmortuosCalyx.config.EGGINFECTIONSTART.get());}
                });
                event.getItemStack().shrink(1);
                if(event.getSide() == LogicalSide.SERVER){event.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), Register.INJECT.get(), SoundSource.PLAYERS, 1f, 1f);}
            }

        }
    }

    @SubscribeEvent
    public static void useCalyxide(AttackEntityEvent event){
        Entity target = event.getTarget();
        Player user = (Player) event.getEntity();
        if(user.getMainHandItem().getItem().equals(Register.CALYXANIDE.get())){
            user.getMainHandItem().shrink(1);
            CalyxideCure(target);
            event.setCanceled(true);
            if(!event.getTarget().level.isClientSide()){target.getCommandSenderWorld().playSound(null, target.getX(), target.getY(), target.getZ(), Register.INJECT.get(), SoundSource.PLAYERS, 1f, 1f);}

        }
    }

    @SubscribeEvent
    public static void useAntiParasite(AttackEntityEvent event){
        Entity target = event.getTarget();
        Player user = (Player) event.getEntity();
        if(user.getMainHandItem().getItem().equals(Register.GENERALANTIPARASITIC.get())){
            user.getMainHandItem().shrink(1);
            AntiParasiticCure(target);
            event.setCanceled(true);
            if(!event.getTarget().level.isClientSide()){target.getCommandSenderWorld().playSound(null, target.getX(), target.getY(), target.getZ(), Register.INJECT.get(), SoundSource.PLAYERS, 1f, 1f);}
        }
    }
    @SubscribeEvent
    public static void useCEggs(AttackEntityEvent event){
        Entity target = event.getTarget();
        Player user = (Player) event.getEntity();
        if(user.getMainHandItem().getItem().equals(Register.IMMORTUOSCALYXEGGS.get())){
            user.getMainHandItem().shrink(1);
            target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() == 0){h.addInfectionProgress(ImmortuosCalyx.config.EGGINFECTIONSTART.get());}
            });
            event.setCanceled(true);
            if(!event.getTarget().level.isClientSide()){target.getCommandSenderWorld().playSound(null, target.getX(), target.getY(), target.getZ(), Register.INJECT.get(), SoundSource.PLAYERS, 1f, 1f);}
            if(target instanceof InfectedDiverEntity || target instanceof InfectedHumanEntity || target instanceof InfectedVillagerEntity){((Monster) target).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30*20, 2, true, false)); ((Monster) target).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30*20, 1, true, false));}
        }
    }

    @SubscribeEvent
    public static void playerResistanceDownTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            Player playerEntity = event.player;
            playerEntity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                h.addResistance(-0.001);
                if(h.getResistance() < 1){h.setResistance(1);}
            });
        }
    }

    @SubscribeEvent
    public static void playerExtract(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        if (event.getSide() == LogicalSide.SERVER) {
            if (event.getHand() == InteractionHand.MAIN_HAND) {
                if (event.getTarget() instanceof Slime && event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    if (player.getMainHandItem().getItem().equals(Register.SYRINGE.get())) {
                        ItemStack olditemstack = player.getMainHandItem();
                        olditemstack.shrink(1);
                        ItemStack itemStack = new ItemStack(Register.GENERALANTIPARASITIC.get());
                        player.getInventory().add(itemStack);
                        if (!event.getEntity().level.isClientSide()) {
                            target.getCommandSenderWorld().playSound(null, target.getX(), target.getY(), target.getZ(), Register.EXTRACT.get(), SoundSource.PLAYERS, 1f, 1f);
                        }
                    }
                } else if ((event.getTarget() instanceof InfectedHumanEntity) || (event.getTarget() instanceof InfectedDiverEntity) || (event.getTarget() instanceof InfectedVillagerEntity) || (event.getTarget() instanceof InfectedIGEntity) && event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    if (player.getMainHandItem().getItem().equals(Register.SYRINGE.get())) {
                        ItemStack olditemstack = player.getMainHandItem();
                        olditemstack.shrink(1);
                        ItemStack itemStack = new ItemStack(Register.IMMORTUOSCALYXEGGS.get());
                        player.getInventory().add(itemStack);
                        if (!target.getCommandSenderWorld().isClientSide()) {
                            target.getCommandSenderWorld().playSound(null, target.getX(), target.getY(), target.getZ(), Register.EXTRACT.get(), SoundSource.PLAYERS, 1f, 1f);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void selfScan(PlayerInteractEvent.RightClickItem event){
        if(event.getSide() == LogicalSide.SERVER) {
            if (event.getEntity() instanceof Player && event.getItemStack().getItem().equals(Register.SCANNER.get()) && event.getEntity().isCrouching()) {
                Player p = (Player) event.getEntity();
                event.getEntity().getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                    p.sendMessage(new TextComponent("===(" + p.getScoreboardName() + "'s stats)==="), p.getUUID());
                    p.sendMessage(new TextComponent("Saturation level: " + p.getFoodData().getSaturationLevel()), p.getUUID());
                    p.sendMessage(new TextComponent("Infection Level: " + h.getInfectionProgress() + "%"), p.getUUID());
                    p.sendMessage(new TextComponent("Resistance Multiplier: " + h.getResistance()), p.getUUID());
                });
            }
        }
    }

    @SubscribeEvent
    public static void Scan(AttackEntityEvent event){
        if(!event.getEntity().getCommandSenderWorld().isClientSide() && event.getEntity() instanceof Player) {
            if (((Player) event.getEntity()).getMainHandItem().getItem().equals(Register.SCANNER.get())) {
                event.setCanceled(true);
                if (event.getTarget() instanceof Player) {
                    Player t = (Player) event.getTarget();
                    Player a = (Player) event.getEntity();

                    t.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                        a.sendMessage(new TextComponent("===(" + t.getScoreboardName() + "'s stats)==="), a.getUUID());
                        a.sendMessage(new TextComponent("Health: " + t.getHealth()), a.getUUID());
                        a.sendMessage(new TextComponent("Food: " + t.getFoodData().getFoodLevel()), a.getUUID());
                        a.sendMessage(new TextComponent("Infection Level: " + h.getInfectionProgress() + "%"), a.getUUID());
                        a.sendMessage(new TextComponent("Resistance Multiplier: " + h.getResistance()), a.getUUID());
                    });
                } else if (event.getTarget() instanceof InfectedHumanEntity || event.getTarget() instanceof InfectedDiverEntity || event.getTarget() instanceof InfectedIGEntity || event.getTarget() instanceof InfectedVillagerEntity) {
                    event.getEntity().sendMessage(new TextComponent("===(Target completely infected)==="), event.getEntity().getUUID());
                } else if (event.getTarget() instanceof  LivingEntity){
                    LivingEntity entity = (LivingEntity) event.getTarget();
                    Player player = (Player) event.getEntity();
                    entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                        player.sendMessage(new TextComponent("===(" + entity.getName().getString() + "'s stats)==="), player.getUUID());
                        player.sendMessage(new TextComponent("Health: " + entity.getHealth()), player.getUUID());
                        player.sendMessage(new TextComponent("Infection Rate: " + h.getInfectionProgress() + "%"), player.getUUID());
                        if(player.isCreative() && entity instanceof Villager){
                            player.sendMessage(new TextComponent("Immortuos Follower: " + h.isFollower()), player.getUUID());
                        }
                    });
                } else {
                    event.getEntity().sendMessage(new TextComponent("Invalid Target."), event.getEntity().getUUID());
                }
            }
        }
    }

    private static void AntiParasiticCure(Entity target) {
        target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() <= 40){//is only effective in curing in dormant phase
                h.addInfectionProgress(-10);
            }
            if(h.getInfectionProgress() < 0) h.setInfectionProgress(0);
            h.setResistance(ImmortuosCalyx.config.RESISTGIVENAP.get());
        });
        target.hurt(InternalOrganDamage.causeInternalDamage(target), 2f);  //divide the reduced infection rate by 5, multiply by 4. 100 infection rate -> 25/5 = 5, * 4 = 20. Vanilla instant kill.
    }

    private static void CalyxideCure(Entity target){
        target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() >= 75){// 75 is internal damage threshold.
                int tempinfection = h.getInfectionProgress();;
                tempinfection -= 70; // reduce 75 to 1, 100 to 25, and all the numbers in between
                target.hurt(InternalOrganDamage.causeInternalDamage(target), ((tempinfection/5)*4));  //divide the reduced infection rate by 5, multiply by 4. 100 infection rate -> 25/5 = 5, * 4 = 20. Vanilla instant kill.
            }

            h.addInfectionProgress(-40);
            if(h.getInfectionProgress() < 0) h.setInfectionProgress(0);
        });
    }


    @SubscribeEvent
    public static void BiomeLoad(BiomeLoadingEvent event){
        ImmortuosCalyx.DimensionExclusion = CommonConfig.getDimensions();
    }

}
