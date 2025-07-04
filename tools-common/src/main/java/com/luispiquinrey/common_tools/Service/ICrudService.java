package com.luispiquinrey.common_tools.Service;

public interface ICrudService<T,V> {
    void deleteTargetById(V id_obj) throws RuntimeException;
    void updateTarget (T obj) throws RuntimeException;
    void createTarget(T obj) throws RuntimeException;
}
