package com.mobileboost;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileBoostMod implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("MobileBoostUltra");
    public static final String MOD_ID = "mobileboost";

    private static final int GC_TICKS = 600; // nudge GC every 30s
    private int gcCounter = 0;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[MobileBoost ULTRA] Render-level optimizations loaded!");

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            System.gc();
            LOGGER.info("[MobileBoost ULTRA] JVM warmed up.");
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (++gcCounter >= GC_TICKS) {
                System.gc();
                gcCounter = 0;
            }
        });
    }
}
