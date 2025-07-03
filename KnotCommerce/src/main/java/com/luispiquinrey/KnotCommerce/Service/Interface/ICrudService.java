package com.luispiquinrey.KnotCommerce.Service.Interface;

public interface ICrudService<T,V> {
    void deleteTargetById(V id_obj) throws RuntimeException;
    void updateTarget (T obj) throws RuntimeException;
    void createTarget(T obj) throws RuntimeException;
}
