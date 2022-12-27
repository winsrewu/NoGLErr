package xyz.jawbts.noglerr.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.jawbts.noglerr.StaticValueManager;
import xyz.jawbts.noglerr.Util;

@Mixin(ItemEntity.class)
public abstract class ShowIdAboveItem extends Entity {
    public ShowIdAboveItem(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void injectedTick(CallbackInfo ci) {
        //当NGE_SHOW_DEBUG_ON_ITEM_NAME开启时，修改物品实体名称为调试信息
        if(world.isClient() && Util.getBoolFromMap("ShowIdAboveItem", StaticValueManager.Saver)) {
            int via = (getId() + age) % 4;
            String s = "[{\"text\":\"Id: \",\"color\":\"gray\"},{\"text\":\"" + getId() +
                    "\",\"color\":\"white\"},{\"text\":\" Age: \",\"color\":\"gray\"},{\"text\":\"" + age +
                    "\",\"color\":\"white\"},{\"text\":\" Value: \",\"color\":\"gray\"},{\"text\":\"" + via +
                    "\",\"color\":\"white\"}]";
            setCustomName(Text.Serializer.fromJson(s));
            setCustomNameVisible(true);
        } else if (world.isClient()) {
            setCustomNameVisible(false);
        }
    }
}
