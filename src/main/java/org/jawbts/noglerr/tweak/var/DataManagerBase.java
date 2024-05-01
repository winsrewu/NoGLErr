package org.jawbts.noglerr.tweak.var;

import java.util.List;
import java.util.Optional;

public interface DataManagerBase {
    /**
     * 删除一个数据
     *
     * @param name 数据的名称
     * @return 如果不存在, {@code false}, 反之为 {@code true}.
     */
    boolean delData(String name);

    /**
     * 添加一个数据
     *
     * @param name  数据的名称
     * @param value 数据的值
     * @param hard  是否强制添加
     * @return 在不强制添加的情况下, 如果数据已存在, 返回{@code false}. 强制情况下一定为{@code true}.
     */
    boolean addData(String name, String value, boolean hard);

    /**
     * 获取数据
     *
     * @param name 数据的名称
     * @return 存在就有值, 不存在就没有.
     */
    Optional<SavedData> getData(String name);

    void setData(List<SavedData> savedDataList);

    List<SavedData> getDataList();
}
