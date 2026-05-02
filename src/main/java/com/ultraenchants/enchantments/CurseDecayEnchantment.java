package com.ultraenchants.enchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
public class CurseDecayEnchantment extends BaseEnchantment {
    public CurseDecayEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, 1,
                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET);
    }
    @Override public boolean isCursed() { return true; }
    @Override public boolean isTreasure() { return false; }
}
