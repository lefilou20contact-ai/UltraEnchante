package com.ultraenchants;

import com.ultraenchants.init.ModEnchantments;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UltraEnchantsMain implements ModInitializer {

    public static final String MOD_ID = "ultraenchants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[UltraEnchants] Chargement de 40+ enchantements custom...");
        ModEnchantments.registerAll();
        ModEnchantments.registerEvents();
        LOGGER.info("[UltraEnchants] Enchantements chargés avec succès !");
    }
}
