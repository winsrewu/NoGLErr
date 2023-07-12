package org.jawbts.noglerr.tweak.var;

import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.VarEntityHandler;

import java.util.ArrayList;
import java.util.List;

public class TargetManager implements DataManagerBase {
    private static final TargetManager INSTANCE = new TargetManager();
    public static final DataEditor DATA_EDITOR = new DataEditor(INSTANCE);
    private final List<TargetDataHandler> targetDataHandlerList = new ArrayList<>();

    public static TargetManager getInstance() {
        return INSTANCE;
    }

    public static DataEditor getEditor() {
        return DATA_EDITOR;
    }

    public boolean addData(String name, String value, boolean hard) {
        SavedData sd = getData(name);
        if (sd != null) {
            if (hard) {
                targetDataHandlerList.remove(new TargetDataHandler(sd.name, sd.value));
                targetDataHandlerList.add(new TargetDataHandler(name, value));
                onChanged();
                return true;
            }
            return false;
        }
        targetDataHandlerList.add(new TargetDataHandler(name, value));
        onChanged();
        return true;
    }

    public boolean delData(String name) {
        onChanged();
        SavedData sd = getData(name);
        if (sd == null) {
            return false;
        }
        targetDataHandlerList.remove(new TargetDataHandler(sd.name, sd.value));
        return true;
    }

    public SavedData getData(String name) {
        for (TargetDataHandler data : targetDataHandlerList) {
            if (data.getName().equals(name)) {
                return data.getSavedData();
            }
        }
        return null;
    }

    public void setData(List<SavedData> savedDataList) {
        onChanged();
        targetDataHandlerList.clear();
        for (SavedData sd : savedDataList) {
            targetDataHandlerList.add(new TargetDataHandler(sd.name, sd.value));
        }
    }

    public List<SavedData> getDataList() {
        List<SavedData> sdl = new ArrayList<>();
        for (TargetDataHandler tdh : targetDataHandlerList) {
            sdl.add(tdh.getSavedData());
        }
        return sdl;
    }

    public List<TargetDataHandler> getHandlerList() {
        return new ArrayList<>(targetDataHandlerList);
    }

    private void onChanged() {
        Configs.Detailed.SHOW_ENTITY_DETAIL_TARGET_CONFIG.setValueFromString(DATA_EDITOR.SavedDataListToJson());
        VarEntityHandler.clearAll();
    }
}
