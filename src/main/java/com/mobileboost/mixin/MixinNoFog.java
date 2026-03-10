package com.mobileboost.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NO FOG — cancel all fog rendering.
 * Fog wastes GPU fill-rate on mobile for minimal visual gain.
 */
@Mixin(BackgroundRenderer.class)
public class MixinNoFog {

    @Inject(
        method = "applyFog",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void disableFog(Camera camera, BackgroundRenderer.FogType fogType,
                                    float viewDistance, boolean thickFog,
                                    float tickDelta, CallbackInfo ci) {
        ci.cancel();
    }
}
