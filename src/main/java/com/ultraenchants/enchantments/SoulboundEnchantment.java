package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class SoulboundEnchantment extends BaseEnchantment {
    public SoulboundEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, 1,
                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET);
    }
    @Override public boolean isTreasure() { return true; }
    @Override public boolean isAvailableForEnchantedBookOffer() { return false; }
    @Override public boolean isAvailableForRandomSelection() { return false; }
}
