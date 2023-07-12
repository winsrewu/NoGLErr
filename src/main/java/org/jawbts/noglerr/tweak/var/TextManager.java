package org.jawbts.noglerr.tweak.var;

import org.jawbts.noglerr.config.Configs;

import java.util.ArrayList;
import java.util.List;

public class TextManager implements DataManagerBase {
    private static final TextManager INSTANCE = new TextManager();
    private static final DataEditor DATA_EDITOR = new DataEditor(INSTANCE);
    private final List<TextDataHandler> textDataHandlerList = new ArrayList<>();

    public static TextManager getInstance() {
        return INSTANCE;
    }

    public static DataEditor getEditor() {
        return DATA_EDITOR;
    }

    public boolean addData(String name, String value, boolean hard) {
        SavedData sd = getData(name);
        if (sd != null) {
            if (hard) {
                textDataHandlerList.remove(new TextDataHandler(sd.name, sd.value));
                textDataHandlerList.add(new TextDataHandler(name, value));
                onChanged();
                return true;
            }
            return false;
        }
        textDataHandlerList.add(new TextDataHandler(name, value));
        onChanged();
        return true;
    }

    public boolean delData(String name) {
        SavedData sd = getData(name);
        if (sd == null) {
            return false;
        }
        textDataHandlerList.remove(new TextDataHandler(sd.name, sd.value));
        onChanged();
        return true;
    }

    public SavedData getData(String name) {
        for (TextDataHandler data : textDataHandlerList) {
            if (data.getName().equals(name)) {
                return data.getSavedData();
            }
        }
        return null;
    }

    public TextDataHandler getHandler(String name) {
        for (TextDataHandler data : textDataHandlerList) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        return null;
    }

    public void setData(List<SavedData> savedDataList) {
        textDataHandlerList.clear();
        for (SavedData sd : savedDataList) {
            textDataHandlerList.add(new TextDataHandler(sd.name, sd.value));
        }
        onChanged();
    }

    public List<SavedData> getDataList() {
        List<SavedData> sdl = new ArrayList<>();
        for (TextDataHandler tdh : textDataHandlerList) {
            sdl.add(tdh.getSavedData());
        }
        return sdl;
    }

    private void onChanged() {
        Configs.Detailed.SHOW_ENTITY_DETAIL_CONFIG.setValueFromString(DATA_EDITOR.SavedDataListToJson());
    }
}
