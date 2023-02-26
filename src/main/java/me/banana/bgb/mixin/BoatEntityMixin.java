package me.banana.bgb.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
    public BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("TAIL"))
    private void setStepHeight(EntityType entityType, World world, CallbackInfo ci) {
        this.stepHeight = 0.5f;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/vehicle/BoatEntity;checkBlockCollision()V"))
    private void noCollision(BoatEntity instance) {
    }
}
