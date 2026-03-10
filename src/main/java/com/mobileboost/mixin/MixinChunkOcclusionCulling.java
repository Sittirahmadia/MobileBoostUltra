package com.mobileboost.mixin;

import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * OCCLUSION CULLING — force cave culling always ON.
 * Skips chunks hidden behind solid terrain — same as Sodium's chunk graph culling.
 */
@Mixin(WorldRenderer.class)
public class MixinChunkOcclusionCulling {

    @Inject(
        method = "isCaveCullingEnabled",
        at = @At("HEAD"),
        cancellable = true
    )
    private void forceCaveCulling(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
