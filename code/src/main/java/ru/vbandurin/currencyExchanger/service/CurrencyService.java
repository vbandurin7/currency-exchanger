package ru.vbandurin.currencyExchanger.service;

import ru.vbandurin.currencyExchanger.dto.Currency;
import ru.vbandurin.currencyExchanger.exception.RepositoryException;
import ru.vbandurin.currencyExchanger.repository.CurrencyRepository;
import ru.vbandurin.currencyExchanger.repository.impl.CurrencyRepositoryImpl;
import ru.vbandurin.currencyExchanger.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class CurrencyService {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]{3}");

    private final CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();

    public Optional<Currency> findById(long id) {
        return currencyRepository.findById(id);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public <S extends Currency> S save(S entity) {
        currencyRepository.save(entity);
        Optional<Currency> insertedEntity = findByCode(entity.getCode());
        if (insertedEntity.isEmpty()) {
            throw new RepositoryException("Can't find entity with code %s after save".formatted(entity.getCode()));
        }
        entity.setId(insertedEntity.get().getId());
        return entity;
    }

    public Optional<Currency> findByCode(String code) {
        if (!CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Currency code must contain 3 capital letters and have length 3");
        }
        Optional<Currency> currency = currencyRepository.findByCode(code);
        return currency;
    }

    public boolean validateCurrencyParams(String name, String code, String sign) {
        return !StringUtils.isEmpty(name)
                && !StringUtils.isEmpty(sign)
                && CODE_PATTERN.matcher(code).matches();
    }
}
