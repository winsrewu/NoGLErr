package org.jawbts.noglerr.tweak.var;

public class DataHandlerBase {
    final SavedData data = new SavedData();

    public String getName() {
        return data.name;
    }

    public void setName(String name) {
        data.name = name;
    }

    public String getData() {
        return data.value;
    }

    public void setData(String data) {
        this.data.value = data;
    }

    public SavedData getSavedData() {
        return data;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DataHandlerBase) &&
                ((DataHandlerBase) o).getSavedData().equals(getSavedData());
    }
}