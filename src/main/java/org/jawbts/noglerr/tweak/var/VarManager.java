package org.jawbts.noglerr.tweak.var;

import org.jawbts.noglerr.config.Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<SavedData> sd = getData(name);
        sd.ifPresentOrElse(savedData -> {
            if (hard) {
                varDataHandlerList.remove(new VarDataHandler(savedData.name, savedData.value));
                varDataHandlerList.add(new VarDataHandler(name, value));
                onChanged();
            }
        }, () -> {
            varDataHandlerList.add(new VarDataHandler(name, value));
            onChanged();
        });

        return hard || sd.isEmpty();
    }

    public boolean delData(String name) {
        Optional<SavedData> sd = getData(name);
        sd.ifPresent(savedData -> {
            varDataHandlerList.remove(new VarDataHandler(savedData.name, savedData.value));
            onChanged();
        });
        return sd.isPresent();
    }

    public Optional<SavedData> getData(String name) {
        for (VarDataHandler data : varDataHandlerList) {
            if (data.getName().equals(name)) {
                return Optional.ofNullable(data.getSavedData());
            }
        }
        return Optional.empty();
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
