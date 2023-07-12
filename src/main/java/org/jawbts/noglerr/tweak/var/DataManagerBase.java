package org.jawbts.noglerr.tweak.var;

import java.util.List;

public interface DataManagerBase {
    //if not exists, return false
    boolean delData(String name);

    //if exists, return false; if hard, modify the data
    boolean addData(String name, String value, boolean hard);

    //if not exists, return null
    SavedData getData(String name);

    void setData(List<SavedData> savedDataList);

    List<SavedData> getDataList();
}
