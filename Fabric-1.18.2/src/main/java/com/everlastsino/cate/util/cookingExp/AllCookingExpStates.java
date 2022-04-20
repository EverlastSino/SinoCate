package com.everlastsino.cate.util.cookingExp;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AllCookingExpStates extends PersistentState {
    public static AllCookingExpStates INSTANCE = new AllCookingExpStates();

    public Map<UUID, CookingExperienceState> expMap = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        this.expMap.forEach(((uuid, expState) -> nbt.put(uuid.toString(), expState.writeNbt())));
        return nbt;
    }

    public static AllCookingExpStates readFromNbt(NbtCompound nbt) {
        AllCookingExpStates states = new AllCookingExpStates();
        for (String uuid : nbt.getKeys()) {
            states.set(UUID.fromString(uuid), CookingExperienceState.readFromNbt(nbt.getCompound(uuid)));
        }
        return states;
    }

    public void set(PlayerEntity player, CookingExperienceState state) {
        this.expMap.put(player.getUuid(), state);
        this.markDirty();
    }

    public void set(UUID uuid, CookingExperienceState state) {
        this.expMap.put(uuid, state);
        this.markDirty();
    }

    public void clear(PlayerEntity player) {
        this.expMap.put(player.getUuid(), new CookingExperienceState(0, 0));
        this.markDirty();
    }

    public void add(PlayerEntity player, float exp) {
        CookingExperienceState state = this.get(player);
        if (state.add(exp)) {
            player.sendMessage(Text.of("Successfully upgraded to level " + state.level), false);
        }
        player.sendMessage(Text.of("You are now on level " + state.level + " with exp " + state.experience), false);
        this.markDirty();
    }

    public CookingExperienceState get(PlayerEntity player) {
        UUID uuid = player.getUuid();
        if (!this.expMap.containsKey(uuid)) this.expMap.put(uuid, new CookingExperienceState(0, 0));
        return this.expMap.get(uuid);
    }
}
