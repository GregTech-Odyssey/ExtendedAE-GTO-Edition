package com.glodblock.github.extendedae.client.button;

import com.glodblock.github.extendedae.util.MessageUtil;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.world.entity.player.Player;

import appeng.client.gui.widgets.IconButton;

import com.gto.ae.api.util.DirectionalGlobalPos;
import com.gto.ae.core.localization.ExtendedLangs;

public final class FacilityHighlightButton {

    private FacilityHighlightButton() {}

    public static IconButton create(DirectionalGlobalPos position, Player player) {
        var button = new HighlightButton();
        button.setMultiplier(position.blockPos().distSqr(player.getOnPos()));
        button.setTarget(position.blockPos(), position.side(), position.dimension());
        button.setSuccessJob(() -> player.displayClientMessage(MessageUtil.createEnhancedHighlightMessage(player,
                position.blockPos(), position.dimension(), ExtendedLangs.HighlightFacilityPosAt.getTranslationKey()), false));
        button.setTooltip(Tooltip.create(ExtendedLangs.HighlightFacilityPos.text()));
        return button;
    }
}
