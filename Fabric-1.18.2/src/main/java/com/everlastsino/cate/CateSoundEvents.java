package com.everlastsino.cate;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CateSoundEvents {
    public static SoundEvent Stone_Mill_Working;

    private static SoundEvent addSoundEvent(String id) {
        Identifier identifier = new Identifier("cate", id);
        SoundEvent event = new SoundEvent(identifier);
        Registry.register(Registry.SOUND_EVENT, identifier, event);
        return event;
    }

    public static void register() {
        Stone_Mill_Working = addSoundEvent("stone_mill_working");
    }
}
