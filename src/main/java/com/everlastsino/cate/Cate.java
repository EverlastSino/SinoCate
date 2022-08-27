package com.everlastsino.cate;

import com.everlastsino.cate.common.handler.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Cate.MODID)
public class Cate {
    public static final String MODID = "cate";
    public static final Logger LOGGER = LogManager.getLogger();

    public Cate() {
        LOGGER.info("SinoCate registration started!");
        GeckoLib.initialize();
        RegistryHandler.register();
        LOGGER.info("SinoCate registration done!");
        MinecraftForge.EVENT_BUS.register(this);
    }
}
