package xyz.jawbts.noglerr.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;


@Mixin(ItemEntity.class)
public abstract class ShowId extends Entity {
    public ShowId(EntityType<?> type, World world) {
        super(type, world);
    }
    @Shadow
    private int health;
    @Shadow
    private int itemAge;
    @Shadow
    private int pickupDelay;

    @Shadow
    public abstract UUID getOwner();
    @Shadow
    public abstract UUID getThrower();
    @Shadow
    public abstract ItemStack getStack();
    /**
     * @author Mojang
     * @reason A New nbt!
     */
    @Overwrite
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Health", (short)this.health);
        nbt.putShort("Age", (short)this.itemAge);
        nbt.putShort("PickupDelay", (short)this.pickupDelay);
        if (this.getThrower() != null) {
            nbt.putUuid("Thrower", this.getThrower());
        }

        if (this.getOwner() != null) {
            nbt.putUuid("Owner", this.getOwner());
        }

        if (!this.getStack().isEmpty()) {
            nbt.put("Item", this.getStack().writeNbt(new NbtCompound()));
        }
        nbt.putInt("Id", getId());
    }
}