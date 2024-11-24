package org.example;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.event.subscribe.Subscribe;

public class ElytraSwapModule extends ToggleableModule {

    public ElytraSwapModule() {
        super("ElytraSwap", "Swaps out fully repaired Elytra with a less-than-full one", ModuleCategory.MOVEMENT);
    }

    @Subscribe
    private void onUpdate(EventUpdate event) {
        if (mc.player == null || mc.level == null) {
            return;
        }

        checkAndSwapElytra();
    }

    private void checkAndSwapElytra() {
        ItemStack chestItem = mc.player.getItemBySlot(EquipmentSlot.CHEST);

        if (chestItem.getItem() == Items.ELYTRA && chestItem.getDamageValue() == 0) {
            // Elytra is fully repaired; remove it
            removeFullElytra();
        } else if (chestItem.isEmpty()) {
            // Chest slot is empty; try to equip a damaged Elytra
            equipDamagedElytra();
        }
        // Else, do nothing (damaged Elytra is already equipped)
    }

    private void removeFullElytra() {
        // Shift-click the chest slot to move the Elytra to inventory
        int chestSlotIndex = getChestSlotIndex();

        mc.gameMode.handleInventoryMouseClick(
            mc.player.containerMenu.containerId,
            chestSlotIndex,
            0,
            ClickType.QUICK_MOVE,
            mc.player
        );
    }

    private void equipDamagedElytra() {
        int damagedElytraSlot = findDamagedElytraInInventory();

        if (damagedElytraSlot == -1) {
            // No damaged Elytra found
            ChatUtils.print("No damaged Elytra found in inventory.");
            return;
        }

        // Shift-click the damaged Elytra to equip it
        mc.gameMode.handleInventoryMouseClick(
            mc.player.containerMenu.containerId,
            damagedElytraSlot,
            0,
            ClickType.QUICK_MOVE,
            mc.player
        );
    }

    private int findDamagedElytraInInventory() {
        // Inventory slots are from index 9 to 44 in the container menu
        for (int i = 9; i <= 44; i++) {
            ItemStack stack = mc.player.containerMenu.getSlot(i).getItem();
            if (stack.getItem() == Items.ELYTRA && stack.getDamageValue() > 0) {
                return i;
            }
        }
        return -1;
    }

    private int getChestSlotIndex() {
        // The chestplate slot index in the container menu is 6
        return 6;
    }
}