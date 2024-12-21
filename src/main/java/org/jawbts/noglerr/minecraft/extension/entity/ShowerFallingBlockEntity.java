package org.jawbts.noglerr.minecraft.extension.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.jawbts.noglerr.event.EntityHandler;

public class ShowerFallingBlockEntity extends FallingBlockEntity {
    ClientPlayerEntity player;

    public ShowerFallingBlockEntity(World world, double x, double y, double z, BlockState block, ClientPlayerEntity player) {
        super(world, x, y, z, block);
        this.player = player;
        // setInvisible(true);
        setNoGravity(true);
        EntityHandler.getInstance().addEntity(this);
    }

    @Override
    public void tick() {
        // pass
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public int getTeamColorValue() {
        return 0xFF0000;
    }

    @Override
    public void onRemoved() {
        EntityHandler.getInstance().removeEntity(this);
    }
}
