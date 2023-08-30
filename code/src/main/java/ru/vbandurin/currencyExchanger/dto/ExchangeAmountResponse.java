package ru.vbandurin.currencyExchanger.dto;

public record ExchangeAmountResponse(Currency baseCurrency, Currency targetCurrency,
                                     double rate, double amount, double convertedAmount) {
}
