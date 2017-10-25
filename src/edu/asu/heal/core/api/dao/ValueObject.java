package edu.asu.heal.core.api.dao;

import java.util.HashMap;
import java.util.Map;

public final class ValueObject {
    private Map<String, Object> _attributes = new HashMap<>();

    public ValueObject() {}

    public boolean equals(Object obj) {
        return (obj instanceof ValueObject) && this._attributes.equals(((ValueObject)obj)._attributes);
    }

    public int hashCode() {
        return _attributes.hashCode();
    }

    public Object getAttribute(String key) {
        return _attributes.get(key);
    }
    public Object putAttribute(String key, Object value) {
        return _attributes.put(key, value);
    }

    public Map<String,Object> getValueObject()
    {
        return _attributes;
    }
}
