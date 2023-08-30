package ru.vbandurin.currencyExchanger.repository.impl;

import ru.vbandurin.currencyExchanger.database.DatabaseUtils;
import ru.vbandurin.currencyExchanger.dto.Currency;
import ru.vbandurin.currencyExchanger.exception.RepositoryException;
import ru.vbandurin.currencyExchanger.repository.CurrencyRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public Optional<Currency> findById(long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM 'Currencies' WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                Currency currency = new Currency(
                        resultSet.getLong("id"), resultSet.getString("Code"),
                        resultSet.getString("FullName"), resultSet.getString("Sign"));
                return Optional.of(currency);
            }

        } catch (SQLException e) {
            throw new RepositoryException("Can't find currency.", e);
        }
    }

    @Override
    public Iterable<Currency> findAll() {
        return null;
    }

    @Override
    public <S extends Currency> S save(S entity) {
        return null;
    }

    private int getRowsCount(ResultSet resultSet) {
        int count = 0;
        try {
            resultSet.last();
            count = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (SQLException e) {
            throw new RepositoryException("Can't get access to result set", e);
        }
        return count;
    }
}
