package org.zainabed.projects.translation.model;

public class MapValue {
    String key;
    String value;

    public MapValue(){

    }
    public MapValue(Translation translation){
        key = translation.getKeys().getName();
        value = translation.getContent();
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return key + " : " + value;
    }
}
