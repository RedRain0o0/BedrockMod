package io.github.redrain0o0.bedrock.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.redrain0o0.bedrock.Bedrockmod;
import io.github.redrain0o0.bedrock.client.gui.components.FlatOreUIButton;
import io.github.redrain0o0.bedrock.client.gui.components.OreUISpriteIconButton;
import io.github.redrain0o0.bedrock.client.gui.components.SpriteOreUIButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen{
    @Unique private final Minecraft minecraft = Minecraft.getInstance();
    @Shadow private Button disconnectButton;
    @Shadow protected abstract void onDisconnect();
    @Shadow protected abstract Button openScreenButton(Component component, Supplier<Screen> supplier);

    @Unique
    private boolean isPaused = false;

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @WrapMethod(method = "renderBackground")
    public void background(GuiGraphics guiGraphics, int i, int j, float f, Operation<Void> original) {}

    @WrapMethod(method = "createPauseMenu")
    public void pauseMenu(Operation<Void> original) {
        // Bottom Buttons
        OreUISpriteIconButton feedback = (OreUISpriteIconButton)this.addRenderableWidget(OreUISpriteIconButton.builder(Component.translatable("menu.feedback").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed feedback button");
            //return new PauseScreen.FeedbackSubScreen(this);
        }, true).width(24).size(24, 24).sprite(Bedrockmod.createId("icon/feedback"), 16, 16).build()); // .bounds(5, ((this.height / 4) * 3) + 16, 24, 24)
        feedback.setPosition(7, this.height - 33);
        OreUISpriteIconButton screenshot = (OreUISpriteIconButton)this.addRenderableWidget(OreUISpriteIconButton.builder(Component.translatable("bedrock.menu.screenshot").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed screenshot button");
        }, true).width(24).size(24, 24).sprite(Bedrockmod.createId("icon/screenshot"), 16, 16).build()); // .bounds(5, ((this.height / 4) * 3) + 16, 24, 24)
        screenshot.setPosition(38, this.height - 33);
        SpriteOreUIButton profile = this.addRenderableWidget(SpriteOreUIButton.builder(Component.translatable("bedrock.menu.profile").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed profile button");
        }, true).sprite(Bedrockmod.createId("icon/alex")).size(64, 24).build());
        profile.setPosition(71, this.height - 33);

        // Main Buttons
        int l = this.height / 2 - 75;
        int btnFromSide = this.width / 2 - 243 - 25;
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("menu.returnToGame").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed resume button");
            this.minecraft.setScreen((Screen)null);
            this.minecraft.mouseHandler.grabMouse();
        }).bounds(btnFromSide, l, 243, 28).build());
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("menu.options").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed options button");
            //return new OptionsScreen(this, this.minecraft.options);
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(btnFromSide, l += 30, 243, 28).build());
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("bedrock.menu.marketplace").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed marketplace button");
            this.minecraft.setScreen(new ShareToLanScreen(this));
        }).bounds(btnFromSide, l += 30, 243, 28).build());
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("menu.returnToMenu").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed exit button");
            this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::onDisconnect, true);
        }).bounds(btnFromSide, l += 30, 243, 28).build());

        // Dressing Room
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("bedrock.menu.dressing").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed exit button");
        }).bounds(this.width / 2 + 33, l + 1, 82, 26).build());

        // Sidebar
        int sbStart = (int)(this.width  * 0.75F);
        SpriteOreUIButton invite = this.addRenderableWidget(SpriteOreUIButton.builder(Component.translatable("bedrock.menu.invite").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed invite button");
        }, true).sprite(Bedrockmod.createId("icon/invite")).size(this.width - sbStart - 8, 26).build());
        invite.setPosition(sbStart + 4, 8);


        //original.call();
    }

    @WrapMethod(method = "render")
    public void render(GuiGraphics guiGraphics, int i, int j, float f, Operation<Void> original) {
        if (this.minecraft.isPaused()) {
            Component paused = Component.translatable("bedrock.menu.paused").withStyle(Style.EMPTY.withShadowColor(0));
            guiGraphics.fill(147, this.height - 33, 147 + 7 + this.font.width(paused) + 7, this.height - 9, 0xBB000000);
            guiGraphics.drawString(this.font, paused, 154, this.height - 25, 16777215);
        }
        int sbStart = (int)(this.width  * 0.75F);
        String username = Minecraft.getInstance().getUser().getName();
        guiGraphics.fill(this.width / 2 + 74 - (this.font.width(username) / 2) - 1, this.height / 2 - 78, this.width / 2 + 74 + (this.font.width(username) / 2) + 1, this.height / 2 - 67, 0x44000000);
        guiGraphics.drawString(this.font, Component.literal(username).withStyle(Style.EMPTY.withShadowColor(0)), this.width / 2 + 74 - (this.font.width(username) / 2), this.height / 2 - 76, 16777215);
        guiGraphics.fill(sbStart, 0, this.width, this.height, 0xFF6B6B6B);
        guiGraphics.drawString(this.font, Component.translatable("bedrock.menu.playersInWorld").withStyle(Style.EMPTY.withShadowColor(0)), sbStart + 4, 38, 16777215);
        original.call(guiGraphics, i, j, f);
    }
}
