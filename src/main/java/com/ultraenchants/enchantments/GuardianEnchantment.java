package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class GuardianEnchantment extends BaseEnchantment {
    public GuardianEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR, 3,
                EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD);
    }
}
