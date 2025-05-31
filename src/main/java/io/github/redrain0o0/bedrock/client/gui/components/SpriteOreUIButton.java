package io.github.redrain0o0.bedrock.client.gui.components;

import io.github.redrain0o0.bedrock.Bedrockmod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SpriteOreUIButton extends FlatOreUIButton {
    private static final WidgetSprites SPRITES_DEFAULT = new WidgetSprites(Bedrockmod.createId("widget/button"), Bedrockmod.createId("widget/button_disabled"), Bedrockmod.createId("widget/button_highlighted"));
    protected final ResourceLocation sprite;

    SpriteOreUIButton(int i, int j, Component component, ResourceLocation resourceLocation, FlatOreUIButton.OnPress onPress, @Nullable FlatOreUIButton.CreateNarration createNarration) {
        super(0, 0, i, j, component, onPress, createNarration == null ? DEFAULT_NARRATION : createNarration);
        this.sprite = resourceLocation;
    }

    public static Builder builder(Component component, FlatOreUIButton.OnPress onPress, boolean bl) {
        return new Builder(component, onPress, bl);
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Component message;
        private final FlatOreUIButton.OnPress onPress;
        private int width = 150;
        private int height = 20;
        private boolean bool;
        @Nullable
        private ResourceLocation sprite;
        @Nullable
        FlatOreUIButton.CreateNarration narration;

        public Builder(Component component, FlatOreUIButton.OnPress onPress, boolean bl) {
            this.message = component;
            this.onPress = onPress;
            this.bool = bl;
        }

        public Builder width(int i) {
            this.width = i;
            return this;
        }

        public Builder size(int i, int j) {
            this.width = i;
            this.height = j;
            return this;
        }

        public Builder sprite(ResourceLocation resourceLocation) {
            this.sprite = resourceLocation;
            return this;
        }

        public Builder narration(FlatOreUIButton.CreateNarration createNarration) {
            this.narration = createNarration;
            return this;
        }

        public SpriteOreUIButton build() {
            if (this.sprite == null) {
                throw new IllegalStateException("Sprite not set");
            } else {
                return new TextAndIcon(this.width, this.height, this.message, this.sprite, this.onPress, this.narration);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class TextAndIcon extends SpriteOreUIButton {
        protected TextAndIcon(int i, int j, Component component, ResourceLocation resourceLocation, FlatOreUIButton.OnPress onPress, @Nullable FlatOreUIButton.CreateNarration createNarration) {
            super(i, j, component, resourceLocation, onPress, createNarration);
        }

        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            super.renderWidget(guiGraphics, i, j, f);
            int k = this.getX() + (this.getHeight() / 2 - 8);
            int l = this.getY() + this.getHeight() / 2 - 8;
            guiGraphics.fill(k - 1, l - 1, k + 17, l + 17, 0xFF030303);
            guiGraphics.blitSprite(RenderType::guiTextured, this.sprite, k, l, 16, 16);
        }

        public void renderString(GuiGraphics guiGraphics, Font font, int i) {
            int j = this.getX() + (this.getHeight() / 2 + 13);
            int k = this.getX() + this.getWidth() - 4;
            int l = this.getX() + (this.getHeight() / 2 + 13);
            renderScrollingString(guiGraphics, font, this.getMessage(), l, j, this.getY(), k, this.getY() + this.getHeight(), i);
        }
    }
}
