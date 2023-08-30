package ru.vbandurin.currencyExchanger.database;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {

    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;

        static {

            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:D:/github/currency-exchanger/config/CurrencyExchanger");

            try (Connection connection = dataSource.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't create testing db connection");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't create testing db connection", e);
            }

            INSTANCE = dataSource;
        }
    }
}
