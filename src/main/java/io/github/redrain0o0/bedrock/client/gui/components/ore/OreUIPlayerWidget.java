package io.github.redrain0o0.bedrock.client.gui.components.ore;

import io.github.redrain0o0.bedrock.Bedrockmod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class OreUIPlayerWidget extends AbstractButton {
    private static final ResourceLocation SOLID_SPRITE = Bedrockmod.createId("widget/player_button_solid");
    private static final WidgetSprites PLAYER_SPRITES = new WidgetSprites(Bedrockmod.createId("widget/player_button"), Bedrockmod.createId("widget/player_button_disabled"), Bedrockmod.createId("widget/player_button_hover"));
    private static final WidgetSprites PERMISSION_SPRITES = new WidgetSprites(Bedrockmod.createId("widget/permission_button"), ResourceLocation.withDefaultNamespace("widget/permission_button_disabled"), ResourceLocation.withDefaultNamespace("widget/permission_button_hover"));
    protected static final CreateNarration DEFAULT_NARRATION = (supplier) -> {
        return (MutableComponent)supplier.get();
    };
    private PlayerInfo playerInfo;

    public static Builder builder() {
        return new Builder();
    }

    public OreUIPlayerWidget(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Override
    public void onPress() {

    }

    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.level.getPlayerByUUID(this.getPlayerInfo().getProfile().getId());
        boolean bl2 = player != null && LivingEntityRenderer.isEntityUpsideDown(player);
        guiGraphics.blitSprite(RenderType::guiTextured, SOLID_SPRITE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        guiGraphics.blitSprite(RenderType::guiTextured, PLAYER_SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        guiGraphics.blitSprite(RenderType::guiTextured, PERMISSION_SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        //int k = this.active ? 16777215 : 10526880;
        //this.renderString(guiGraphics, minecraft.font, k | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Component message = Component.empty();
        private final OnPress onPress = (button) -> {};
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private PlayerInfo playerInfo;
        private CreateNarration createNarration;

        public Builder() {
            this.createNarration = DEFAULT_NARRATION;
        }

        public Builder pos(int i, int j) {
            this.x = i;
            this.y = j;
            return this;
        }

        public Builder playerInfo(PlayerInfo playerInfo) {
            this.playerInfo = playerInfo;
            return this;
        }

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder createNarration(CreateNarration createNarration) {
            this.createNarration = createNarration;
            return this;
        }

        public OreUIPlayerWidget build() {
            OreUIPlayerWidget oreUIButton = new OreUIPlayerWidget(this.x, this.y, 141, 28, this.message);
            oreUIButton.setTooltip(this.tooltip);
            return oreUIButton;
        }
    }

    @Environment(EnvType.CLIENT)
    public interface OnPress {
        void onPress(OreUIPlayerWidget oreUIButton);
    }

    @Environment(EnvType.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> supplier);
    }

    public PlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }
}
