package com.everlastsino.cate.util.cookingExp;

import net.minecraft.nbt.NbtCompound;

public class CookingExperienceState {
    public float experience;
    public int level;

    public CookingExperienceState(float experience, int level) {
        this.experience = experience;
        this.level = level;
    }

    public static int getMaxExpByLevel(int level) {
        return 60 * level + 20;
    }

    public boolean add(float exp) { //level up or no
        this.experience += exp;
        int maxExp = getMaxExpByLevel(this.level);
        boolean leveledUp = false;
        while (this.experience >= maxExp) {
            this.experience -= maxExp;
            this.level++;
            maxExp = getMaxExpByLevel(this.level);
            leveledUp = true;
        }
        return leveledUp;
    }

    public static CookingExperienceState readFromNbt(NbtCompound nbt) {
        float exp = nbt.getFloat("exp");
        int level = nbt.getInt("level");
        return new CookingExperienceState(exp, level);
    }

    public NbtCompound writeNbt() {
        NbtCompound child = new NbtCompound();
        child.putFloat("exp", this.experience);
        child.putInt("level", this.level);
        return child;
    }
}
