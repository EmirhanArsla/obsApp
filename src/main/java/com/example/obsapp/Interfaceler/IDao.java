package com.example.obsapp.Interfaceler;

import org.bson.Document;
import java.util.List;

public interface IDao<T, ID> {
    boolean add(T entity);
    void deleteById(ID id);

}
