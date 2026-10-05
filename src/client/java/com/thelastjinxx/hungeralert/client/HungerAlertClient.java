package com.thelastjinxx.hungeralert.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class HungerAlertClient implements ClientModInitializer {
    // Food bar is 0-20 (each drumstick = 2). 6 = 3 drumsticks left.
    private static final int HUNGER_THRESHOLD = 6;

    private boolean warned = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                warned = false;
                return;
            }
            int food = client.player.getFoodData().getFoodLevel();
            if (food <= HUNGER_THRESHOLD) {
                if (!warned) {
                    warned = true;
                    client.getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f));
                    client.gui.setOverlayMessage(
                        Component.literal("Hungry! Food level: " + food + "/20"), false);
                }
            } else {
                warned = false;
            }
        });
    }
}
