package org.jawbts.noglerr.tweak.var;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.entity.Entity;
import org.jawbts.noglerr.commands.ClientEntitySelector;
import org.jawbts.noglerr.event.ClientTickHandler;

import java.util.List;

public class TargetDataHandler extends DataHandlerBase {
    public TargetDataHandler() {
        data.name = "unnamed";
        data.value = "";
    }

    public TargetDataHandler(String name, String target) {
        data.name = name;
        data.value = target;
    }

    public List<? extends Entity> getEntities() throws CommandSyntaxException {
        EntitySelectorReader reader = new EntitySelectorReader(getReader());
        ClientEntitySelector clientEntitySelector = new ClientEntitySelector(reader.read());
        return clientEntitySelector.getEntities(ClientTickHandler.mc);
    }

    public StringReader getReader() {
        return new StringReader(data.value);
    }
}
