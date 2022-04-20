package com.everlastsino.cate.api.enums;

public enum SaucepanSlots {
    INGREDIENT_1,
    INGREDIENT_2,
    INGREDIENT_3,
    INGREDIENT_4,
    INGREDIENT_5,
    INGREDIENT_6,
    RESULT_1,
    RESULT_2,
    RESULT_3,
    CONTAINER,
    WATER_CONTAINER;

    public static int[] UP_SLOTS = new int[]{0, 1, 2, 3, 4, 5};
    public static int[] SIDE_SLOTS = new int[]{9};
    public static int[] DOWN_SLOTS = new int[]{6, 7, 8};
}
