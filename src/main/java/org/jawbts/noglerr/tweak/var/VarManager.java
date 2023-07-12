package org.jawbts.noglerr.tweak.var;

import org.jawbts.noglerr.config.Configs;

import java.util.ArrayList;
import java.util.List;

public class VarManager implements DataManagerBase {
    private static final VarManager INSTANCE = new VarManager();
    private static final DataEditor DATA_EDITOR = new DataEditor(INSTANCE);
    private final List<VarDataHandler> varDataHandlerList = new ArrayList<>();

    public static VarManager getInstance() {
        return INSTANCE;
    }

    public static DataEditor getEditor() {
        return DATA_EDITOR;
    }

    public boolean addData(String name, String value, boolean hard) {
        SavedData sd = getData(name);
        if (sd != null) {
            if (hard) {
                varDataHandlerList.remove(new VarDataHandler(sd.name, sd.value));
                varDataHandlerList.add(new VarDataHandler(name, value));
                onChanged();
                return true;
            }
            return false;
        }
        varDataHandlerList.add(new VarDataHandler(name, value));
        onChanged();
        return true;
    }

    public boolean delData(String name) {
        SavedData sd = getData(name);
        if (sd == null) {
            return false;
        }
        varDataHandlerList.remove(new VarDataHandler(sd.name, sd.value));
        onChanged();
        return true;
    }

    public SavedData getData(String name) {
        for (VarDataHandler data : varDataHandlerList) {
            if (data.getName().equals(name)) {
                return data.getSavedData();
            }
        }
        return null;
    }

    public VarDataHandler getHandler(String name) {
        for (VarDataHandler data : varDataHandlerList) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        return null;
    }

    public void setData(List<SavedData> savedDataList) {
        varDataHandlerList.clear();
        for (SavedData sd : savedDataList) {
            varDataHandlerList.add(new VarDataHandler(sd.name, sd.value));
        }
        onChanged();
    }

    public List<SavedData> getDataList() {
        List<SavedData> sdl = new ArrayList<>();
        for (VarDataHandler vdh : varDataHandlerList) {
            sdl.add(vdh.getSavedData());
        }
        return sdl;
    }

    private void onChanged() {
        Configs.Detailed.SHOW_ENTITY_DETAIL_VAR_CONFIG.setValueFromString(DATA_EDITOR.SavedDataListToJson());
    }
}
