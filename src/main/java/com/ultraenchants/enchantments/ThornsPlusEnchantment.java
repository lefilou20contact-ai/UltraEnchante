package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class ThornsPlusEnchantment extends BaseEnchantment {
    public ThornsPlusEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, 5,
                EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD);
    }
    @Override
    protected boolean canAccept(net.minecraft.enchantment.Enchantment other) {
        if (other instanceof net.minecraft.enchantment.ThornsEnchantment) return false;
        return super.canAccept(other);
    }
}
