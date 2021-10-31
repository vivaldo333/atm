package com.fuib.convertors;

public interface Convertor<S, T> {
    T convert(S source);
}
