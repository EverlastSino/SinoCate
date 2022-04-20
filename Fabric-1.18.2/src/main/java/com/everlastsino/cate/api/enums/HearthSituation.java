package com.everlastsino.cate.api.enums;

import net.minecraft.util.StringIdentifiable;

public enum HearthSituation implements StringIdentifiable {
    NONE("none", Integer.MIN_VALUE, 0, 0),
    SMALL("small", 1, 3600, 1),
    LARGE("large", 3601, Integer.MAX_VALUE, 2)
    ;

    public String name;
    public int minTick, maxTick, damage;

    HearthSituation(String name, int minTick, int maxTick, int damage) {
        this.name = name;
        this.minTick = minTick;
        this.maxTick = maxTick;
        this.damage = damage;
    }

    public boolean inRange(int number) {
        return number >= this.minTick && number <= this.maxTick;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static HearthSituation getSituation(int remainingTicks) {
        for (HearthSituation situation : values()) {
            if (situation.inRange(remainingTicks)) return situation;
        }
        return NONE;
    }
}
