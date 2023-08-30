CREATE TABLE Currencies
(
    id       INTEGER PRIMARY KEY,
    Code     varchar UNIQUE,
    FullName varchar,
    Sign     varchar
);

CREATE TABLE ExchangeRates
(
    id               INTEGER PRIMARY KEY,
    BaseCurrencyId   INTEGER,
    TargetCurrencyId INTEGER,
    ExchangeRate     DECIMAL(6),
    FOREIGN KEY (BaseCurrencyId) REFERENCES Currencies (id),
    FOREIGN KEY (TargetCurrencyId) REFERENCES Currencies (id)
);
