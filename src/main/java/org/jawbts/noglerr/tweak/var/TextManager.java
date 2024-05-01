package org.jawbts.noglerr.tweak.var;

import org.jawbts.noglerr.config.Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<SavedData> sd = getData(name);
        sd.ifPresentOrElse(savedData -> {
            if (hard) {
                textDataHandlerList.remove(new TextDataHandler(savedData.name, savedData.value));
                textDataHandlerList.add(new TextDataHandler(name, value));
                onChanged();
            }
        }, () -> {
            textDataHandlerList.add(new TextDataHandler(name, value));
            onChanged();
        });

        return hard || sd.isEmpty();
    }

    public boolean delData(String name) {
        Optional<SavedData> sd = getData(name);
        sd.ifPresent(savedData -> {
            textDataHandlerList.remove(new TextDataHandler(savedData.name, savedData.value));
            onChanged();
        });
        return sd.isPresent();
    }

    public Optional<SavedData> getData(String name) {
        for (TextDataHandler data : textDataHandlerList) {
            if (data.getName().equals(name)) {
                return Optional.of(data.getSavedData());
            }
        }
        return Optional.empty();
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
