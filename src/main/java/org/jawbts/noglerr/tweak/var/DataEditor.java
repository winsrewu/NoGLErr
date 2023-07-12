package org.jawbts.noglerr.tweak.var;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataEditor {
    private static final Gson gson = new Gson();
    private static final Type SavedDataTypeToken = new TypeToken<ArrayList<SavedData>>() {
    }.getType();
    private final DataManagerBase dataManagerBase;

    DataEditor(DataManagerBase db) {
        dataManagerBase = db;
    }

    public static List<SavedData> JsonToSavedDataList(String json) {
        Gson gson = new Gson();
        List<SavedData> list;

        JsonParser parser = new JsonParser();
        JsonElement element;
        try {
            element = parser.parse(json);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }


        if (element == null || !element.isJsonArray()) {
            return null;
        }
        JsonArray jsonArray = element.getAsJsonArray();

        try {
            list = gson.fromJson(jsonArray, SavedDataTypeToken);
        } catch (Exception ignored) {
            return null;
        }

        for (SavedData sd : list) {
            if (sd == null || sd.name == null || sd.value == null) {
                return null;
            }
        }

        return list;
    }

    //if something wrong with json, return false
    public boolean setListFromJson(String json) {
        List<SavedData> sdl = JsonToSavedDataList(json);
        if (sdl == null) {
            return false;
        }
        assert dataManagerBase != null;
        dataManagerBase.setData(sdl);
        return true;
    }

    public String SavedDataListToJson() {
        return gson.toJson(dataManagerBase.getDataList());
    }

    public String getType() {
        if (dataManagerBase instanceof VarManager) {
            return "Var";
        }
        if (dataManagerBase instanceof TextManager) {
            return "Text";
        }
        if (dataManagerBase instanceof TargetManager) {
            return "Target";
        }
        return "Unknown";
    }

    public void printDataList(PlayerMessageSender pms, int page) {
        pms.add("yellow", "------");
        pms.add(String.format("%s List: ", getType()));

        assert dataManagerBase != null;
        List<SavedData> sd = dataManagerBase.getDataList();

        int maxpage = sd.size() / 10;
        page = Math.min(page - 1, maxpage);
        int subpage = Math.min((page + 1) * 10, sd.size());

        pms.add(sd.subList(page * 10, subpage));

        if (sd.size() > 10) {
            pms.add(String.format("Page %d / %d", page + 1, maxpage + 1));
        } else if (sd.isEmpty()) {
            pms.add("Nothing...");
        }

        pms.add("yellow", "------");
    }
}
