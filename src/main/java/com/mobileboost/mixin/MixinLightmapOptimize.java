package com.mobileboost.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * LIGHTMAP THROTTLE — update lightmap texture every 3 ticks instead of every tick.
 * Reduces GPU texture upload overhead significantly on mobile.
 */
@Mixin(LightmapTextureManager.class)
public class MixinLightmapOptimize {

    private static int lightmapCounter = 0;
    private static final int INTERVAL = 3;

    @Inject(
        method = "update",
        at = @At("HEAD"),
        cancellable = true
    )
    private void throttleLightmap(float tickDelta, CallbackInfo ci) {
        if (++lightmapCounter % INTERVAL != 0) {
            ci.cancel();
        }
    }
}
