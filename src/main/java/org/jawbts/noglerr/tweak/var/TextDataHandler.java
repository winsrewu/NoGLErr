package org.jawbts.noglerr.tweak.var;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.jawbts.noglerr.tweak.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextDataHandler extends DataHandlerBase {
    private static final String compileString = "\\$[^$]+\\$";
    private static final Pattern compile = Pattern.compile(compileString);
    private String treatedText;
    private List<VarDataHandler> argList = new ArrayList<>();
    private String argListFailReason = null;

    public TextDataHandler() {
        data.name = "unnamed";
        data.value = "";
    }

    public TextDataHandler(String name, String text) {
        data.name = name;
        data.value = text;
    }

    public String getTreatedData(Entity entity, ClientWorld world, ClientPlayerEntity player) {
        if (!Utils.TickUpdateChecker.check("TEXTDATA" + hashCode())) {
            argListFailReason = null;
            createArgList();
            Utils.TickUpdateChecker.mark("TEXTDATA" + hashCode());
        }

        if (argListFailReason != null) {
            return argListFailReason;
        }

        List<String> list = new ArrayList<>();
        for (VarDataHandler handler : argList) {
            list.add(handler.getTreatedData(entity, world, player));
        }
        String[] treatedArgList = list.toArray(new String[list.size()]);
        String ans;
        ans = String.format(treatedText, (Object[]) treatedArgList);
        return ans;
    }

    public List<Object> getArgListCopy() {
        return new ArrayList<>(argList);
    }

    public void setArgList(List<VarDataHandler> argList) {
        this.argList = new ArrayList<>(argList);
    }

    public void createArgList() {
        argList.clear();

        Matcher matcher = compile.matcher(data.value);

        while (matcher.find()) {
            String name = matcher.group().replaceAll("\\$", "");
            VarDataHandler handler = VarManager.getInstance().getHandler(name);
            if (handler == null) {
                handler = ScriptVarManager.getInstance().getHandler(name);
            }
            if (handler == null) {
                argListFailReason = Text.Serializer.toJson(Utils.createText("noglerr.command.varNotExists", "red"));
            }
            argList.add(handler);
        }
        treatedText = data.value.replaceAll(compileString, "%s");
    }

    public String getData() {
        return data.value;
    }

    public String toString() {
        return String.format("Name: %s Value: %s", data.name, Utils.escapeString(data.value));
    }
}
