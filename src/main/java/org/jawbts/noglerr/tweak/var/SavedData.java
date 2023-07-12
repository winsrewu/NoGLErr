package org.jawbts.noglerr.tweak.var;

public class SavedData {
    public String name, value;

    @Override
    public int hashCode() {
        String s = name + "\n" + value;
        return s.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof SavedData) &&
                ((SavedData) o).name.equals(name) &&
                ((SavedData) o).value.equals(value);
    }

    @Override
    public String toString() {
        return String.format("Name: %s Value: %s", name, value);
    }
}
