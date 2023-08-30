package ru.vbandurin.currencyExchanger.repository;

import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(long id);
    Iterable<T> findAll();
    <S extends T> S save(S entity);
}
