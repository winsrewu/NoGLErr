package xyz.jawbts.noglerr.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.jawbts.noglerr.NoGLErr.NGE_SHOW_DEBUG_ON_ITEM_NAME;


@Mixin(ItemEntity.class)
public abstract class ShowIdOnItem extends Entity {
    public ShowIdOnItem(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("Id", getId());
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void injectedTick(CallbackInfo ci) {
        //当NGE_SHOW_DEBUG_ON_ITEM_NAME开启时，修改物品实体名称为调试信息
        if(!world.isClient() && world.getGameRules().getBoolean(NGE_SHOW_DEBUG_ON_ITEM_NAME)) {
            int via = (getId() + age) % 4;
            String s = "[{\"text\":\"Id: \",\"color\":\"gray\"},{\"text\":\"" + getId() +
                    "\",\"color\":\"white\"},{\"text\":\" Age: \",\"color\":\"gray\"},{\"text\":\"" + age +
                    "\",\"color\":\"white\"},{\"text\":\" Value: \",\"color\":\"gray\"},{\"text\":\"" + via +
                    "\",\"color\":\"white\"}]";
            setCustomName(Text.Serializer.fromJson(s));
        }
    }
}