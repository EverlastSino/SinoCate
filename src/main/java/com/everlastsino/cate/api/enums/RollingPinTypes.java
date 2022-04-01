package com.everlastsino.cate.api.enums;

import com.everlastsino.cate.item.CateItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;

public enum RollingPinTypes implements StringIdentifiable {
    DOUGH(0, "dough"),
    TANG_YUAN(1, 1,"tang_yuan", 6),
    ;

    public int id;
    public int amount;
    public String name;
    public Text text;
    public int steps;

    public static Map<RollingPinTypes, Item> MAP = new HashMap<>();

    public static void registerRollingPinTypes() {
        MAP.put(RollingPinTypes.DOUGH, CateItems.Dough);
        MAP.put(RollingPinTypes.TANG_YUAN, CateItems.Tang_Yuan);
    }

    RollingPinTypes(int id, int amount, String name, int steps) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.text = new TranslatableText("item.cate.rolling_pin.tooltip." + this.name).formatted(Formatting.AQUA);
        this.steps = steps;
    }

    RollingPinTypes(int id, String name) {
        this.id = id;
        this.amount = 1;
        this.name = name;
        this.text = new TranslatableText("item.cate.rolling_pin.tooltip." + this.name).formatted(Formatting.AQUA);
        this.steps = 5;
    }

    public RollingPinTypes getNext() {
        RollingPinTypes[] types = values();
        for (RollingPinTypes type : types) {
            if (type.id == this.id + 1) {
                return type;
            }
        }
        return RollingPinTypes.DOUGH;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static ItemStack getStackFromType(RollingPinTypes type) {
        return new ItemStack(MAP.get(type), type.amount);
    }

    public static RollingPinTypes valueOf(int id) {
        RollingPinTypes[] types = values();
        for (RollingPinTypes type : types) {
            if (type.id == id) {
                return type;
            }
        }
        return RollingPinTypes.DOUGH;
    }
}