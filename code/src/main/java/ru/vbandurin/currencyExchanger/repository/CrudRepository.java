package ru.vbandurin.currencyExchanger.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(long id);
    List<T> findAll();
    <S extends T> S save(S entity);
}
