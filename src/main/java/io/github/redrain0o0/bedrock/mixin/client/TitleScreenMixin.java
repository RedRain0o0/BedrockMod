package io.github.redrain0o0.bedrock.mixin.client;

import io.github.redrain0o0.bedrock.Bedrockmod;
import io.github.redrain0o0.bedrock.client.gui.components.MarketOreUIButton;
import io.github.redrain0o0.bedrock.client.gui.components.OreUISpriteIconButton;
import io.github.redrain0o0.bedrock.client.gui.components.TitleOreUIButton;
import io.github.redrain0o0.bedrock.client.gui.components.RealmsOreUIButton;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CommonButtons;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow @Nullable private SplashRenderer splash;
    //@Shadow @Nullable private Component getMultiplayerDisabledReason;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "<init>(ZLnet/minecraft/client/gui/components/LogoRenderer;)V", at = @At("RETURN"))
    public void init(boolean bl, LogoRenderer logoRenderer, CallbackInfo ci) {
        Bedrockmod.LOGGER.info("{}", Minecraft.getInstance().getUser().getType());

    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    protected void init(CallbackInfo ci) {
        ci.cancel();
        super.init();;
        int l = this.height / 4 + 65;
        this.addRenderableWidget(TitleOreUIButton.builder(Component.translatable("menu.singleplayer").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 74, l, 148, 30).build());  /*.texture("default").withColor(5000268)*/
        this.addRenderableWidget(TitleOreUIButton.builder(Component.translatable("menu.options").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(this.width / 2 - 74, l += 32, 148, 30).build());
        ((RealmsOreUIButton)this.addRenderableWidget(RealmsOreUIButton.builder(Component.translatable("menu.online").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
        //    this.minecraft.setScreen(new RealmsMainScreen(this));
            Bedrockmod.LOGGER.info("Pressed realms button");
        }).bounds(this.width / 2 - 74, l += 32, 148, 30).build())).active = true;
        this.addRenderableWidget(MarketOreUIButton.builder(Component.translatable("bedrock.menu.marketplace").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.stop();
        }).bounds(this.width / 2 - 74, l += 32, 148, 30).build());
        this.addRenderableWidget(TitleOreUIButton.builder(Component.translatable("bedrock.menu.dressing").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            //this.minecraft.setScreen(new SelectWorldScreen(this));
            Bedrockmod.LOGGER.info("Pressed dressing room button");
        }).bounds(((this.width / 4) * 3), this.height - 59, 82, 25).build());

        OreUISpriteIconButton inbox = (OreUISpriteIconButton)this.addRenderableWidget(OreUISpriteIconButton.builder(Component.translatable("bedrock.menu.inbox").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            Bedrockmod.LOGGER.info("Pressed notification button");
        }, true).width(24).size(24, 24).sprite(Bedrockmod.createId("icon/inbox"), 16, 16).build()); // .bounds(5, ((this.height / 4) * 3) + 16, 24, 24)
        inbox.setPosition(5, this.height - 59);
        this.addRenderableWidget(TitleOreUIButton.builder(Component.translatable("bedrock.menu.profile").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            //this.minecraft.setScreen(new SelectWorldScreen(this));
            Bedrockmod.LOGGER.info("Pressed profile button");
        }).bounds(5 + 24 + 8, this.height - 59, 64, 24).build());
        if (Minecraft.getInstance().getUser().getType() != User.Type.MSA) {
            this.addRenderableWidget(TitleOreUIButton.builder(Component.translatable("bedrock.menu.sign_in").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
                //this.minecraft.setScreen(new SelectWorldScreen(this));
                Bedrockmod.LOGGER.info("Pressed sign-in button");
            }).bounds(5, this.height - 59 - 35, 44, 25).build());
        }
        //if (this.minecraft.isDemo()) {
        ////    l = TitleScreen.createDemoMenuOptions(l, 24);
        //} else {
        ////    l = TitleScreen.createNormalMenuOptions(l, 24);
        //}
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I", ordinal = 0))
    private String onRender(String string) {
        return "";
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void addVersion(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        super.render(guiGraphics, i, j, f);
        String version = "v" + SharedConstants.getCurrentVersion().getName();
        guiGraphics.fill(this.width - this.font.width(version) - 2, this.height - 12, this.width, this.height, 0xAA000000);
        guiGraphics.drawString(this.font, Component.literal(version).withStyle(Style.EMPTY.withShadowColor(0)), this.width - this.font.width(version) - 1, this.height - 10, 16777215);
        String copyright = "©Mojang AB";
        guiGraphics.fill(0, this.height - 12, this.font.width(copyright) + 3, this.height, 0xAA000000);
        guiGraphics.drawString(this.font, Component.literal(copyright).withStyle(Style.EMPTY.withShadowColor(0)), 1, this.height - 10, 16777215);
        String username = Minecraft.getInstance().getUser().getName();
        guiGraphics.fill((((this.width / 4) * 3) - (this.font.width(username) / 2)) + 40, this.height / 2 - 2, (((this.width / 4) * 3) + (this.font.width(username) / 2)) + 42, this.height / 2 + 9, 0x44000000);
        guiGraphics.drawString(this.font, Component.literal(username).withStyle(Style.EMPTY.withShadowColor(0)), (((this.width / 4) * 3) - (this.font.width(username) / 2)) + 41, this.height / 2, 16777215);
    }
}
