package com.mobileboost.mixin;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * FRUSTUM ENTITY CULLING — skip entities outside camera frustum.
 * Entities within 8 blocks always render; beyond that strictly frustum-culled.
 */
@Mixin(EntityRenderDispatcher.class)
public class MixinFrustumCull {

    @Inject(
        method = "shouldRender",
        at = @At("HEAD"),
        cancellable = true
    )
    private <E extends Entity> void aggressiveCull(
            E entity, Frustum frustum,
            double x, double y, double z,
            CallbackInfoReturnable<Boolean> cir) {

        double dx = entity.getX() - x;
        double dy = entity.getY() - y;
        double dz = entity.getZ() - z;
        double distSq = dx * dx + dy * dy + dz * dz;

        if (distSq < 64) return; // within 8 blocks — always render

        if (!frustum.isVisible(entity.getBoundingBox())) {
            cir.setReturnValue(false);
        }
    }
}
