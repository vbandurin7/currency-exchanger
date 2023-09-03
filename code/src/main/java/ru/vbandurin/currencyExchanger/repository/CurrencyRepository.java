package ru.vbandurin.currencyExchanger.repository;

import ru.vbandurin.currencyExchanger.dto.Currency;

import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency> {

    Optional<Currency> findByCode(String code);
}
