package com.example.guitoylanguage.Model.Values;

import com.example.guitoylanguage.Model.Types.Type;
public interface Value {
    Type getType();
    Value deepCopy();

}
