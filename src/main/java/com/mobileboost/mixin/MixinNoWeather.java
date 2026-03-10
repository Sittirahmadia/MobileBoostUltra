package com.mobileboost.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NO WEATHER — cancel rain/snow particle render pass.
 * 1.21.11 renderWeather takes Camera, float, LightmapTextureManager.
 */
@Mixin(WorldRenderer.class)
public class MixinNoWeather {

    @Inject(
        method = "renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FLnet/minecraft/client/render/Camera;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void cancelWeather(LightmapTextureManager lightmap, float tickDelta,
                                Camera camera, CallbackInfo ci) {
        ci.cancel();
    }
}
