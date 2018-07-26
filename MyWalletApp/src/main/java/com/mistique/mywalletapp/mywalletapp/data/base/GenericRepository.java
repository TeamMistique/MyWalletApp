package com.mistique.mywalletapp.mywalletapp.data.base;

import java.util.List;
import java.util.stream.Stream;

public interface GenericRepository<T> {
    List<T> listAll();

    Stream<T> modelStream();

    T findById(int id);

    void create(T post);

    void update(int id, T post);

    void delete(int id);


}
