package org.example.app.services;

import java.util.Collection;
import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    void removeItemById(Integer id);

    void removeByRegexp(String regex);

}
