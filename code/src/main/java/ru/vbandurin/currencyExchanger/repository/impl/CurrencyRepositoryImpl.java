package ru.vbandurin.currencyExchanger.repository.impl;

import ru.vbandurin.currencyExchanger.database.DatabaseUtils;
import ru.vbandurin.currencyExchanger.dto.Currency;
import ru.vbandurin.currencyExchanger.exception.RepositoryException;
import ru.vbandurin.currencyExchanger.repository.CurrencyRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            return getCurrencyFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Can't get connection for finding currency with id=%d.".formatted(id), e);
        }
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM 'Currencies' WHERE code=?");
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getCurrencyFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Can't get connection for finding currency with code=%s.".formatted(code), e);
        }
    }

    private Optional<Currency> getCurrencyFromResultSet(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                Currency currency = new Currency(
                        resultSet.getLong("id"), resultSet.getString("Code"),
                        resultSet.getString("FullName"), resultSet.getString("Sign"));
                return Optional.of(currency);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Unexpected error occurred while accessing to database", e);
        }
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> resultList = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 'Currencies'");
            while (resultSet.next()) {
                resultList.add(new Currency(
                        resultSet.getLong("id"), resultSet.getString("Code"),
                        resultSet.getString("FullName"), resultSet.getString("Sign")));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't get connection for finding all currencies.", e);
        }
        return resultList;
    }

    @Override
    public <S extends Currency> S save(S entity) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO 'Currencies' (Code, FullName, Sign) VALUES (?, ?, ?)");
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setString(2, entity.getFullName());
            preparedStatement.setString(3, entity.getSign());
            if (preparedStatement.executeUpdate() != 1) {
                throw new RepositoryException("Can't save entity " + entity);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't get connection for saving currency.", e);
        }
        return entity;
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
