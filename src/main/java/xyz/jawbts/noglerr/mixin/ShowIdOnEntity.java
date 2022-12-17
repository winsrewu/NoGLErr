package xyz.jawbts.noglerr.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.jawbts.noglerr.NoGLErr.NGE_SHOW_DEBUG_ON_ENTITY_NAME;
import static xyz.jawbts.noglerr.NoGLErr.NGE_SHOW_DEBUG_ON_ITEM_NAME;

@Mixin(Entity.class)
public abstract class ShowIdOnEntity {
    @Shadow
    World world;
    @Shadow
    public int age;
    @Shadow
    public int id;
    @Shadow
    public void setCustomName(@Nullable Text name) {}

    @Shadow public abstract void setCustomNameVisible(boolean visible);

    @Inject(method = "baseTick()V", at = @At("HEAD"))
    public void baseTick(CallbackInfo ci) {
        if(!world.isClient()) {
            boolean showDebugEntity = world.getGameRules().getBoolean(NGE_SHOW_DEBUG_ON_ENTITY_NAME),
                    showDebugItem = world.getGameRules().getBoolean(NGE_SHOW_DEBUG_ON_ITEM_NAME);

            //当NGE_SHOW_DEBUG_ON_ENTITY_NAME开启时，修改实体实体名称为调试信息
            if(showDebugEntity) {
                String s = "[{\"text\":\"Id: \",\"color\":\"gray\"},{\"text\":\"" + id +
                        "\",\"color\":\"white\"},{\"text\":\" Age: \",\"color\":\"gray\"},{\"text\":\"" + age +
                        "\",\"color\":\"white\"}]";
                setCustomName(Text.Serializer.fromJson(s));
            }

            //物品名称显示控制
            setCustomNameVisible(showDebugEntity || showDebugItem);
        }
    }
}
