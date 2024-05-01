package org.jawbts.noglerr.tweak.var;

import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.VarEntityHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<SavedData> sd = getData(name);
        sd.ifPresentOrElse(savedData -> {
            if (hard) {
                targetDataHandlerList.remove(new TargetDataHandler(savedData.name, savedData.value));
                targetDataHandlerList.add(new TargetDataHandler(name, value));
                onChanged();
            }
        }, () -> {
            targetDataHandlerList.add(new TargetDataHandler(name, value));
            onChanged();
        });

        return hard || sd.isEmpty();
    }

    public boolean delData(String name) {
        Optional<SavedData> sd = getData(name);
        sd.ifPresent(savedData -> {
            targetDataHandlerList.remove(new TargetDataHandler(savedData.name, savedData.value));
            onChanged();
        });
        return sd.isPresent();
    }

    public Optional<SavedData> getData(String name) {
        for (TargetDataHandler data : targetDataHandlerList) {
            if (data.getName().equals(name)) {
                return Optional.of(data.getSavedData());
            }
        }
        return Optional.empty();
    }

    public void setData(List<SavedData> savedDataList) {
        targetDataHandlerList.clear();
        for (SavedData sd : savedDataList) {
            targetDataHandlerList.add(new TargetDataHandler(sd.name, sd.value));
        }
        onChanged();
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
