package ru.vbandurin.currencyExchanger.dto;

public class ExchangeRate {

    private final long id;
    private final Currency baseCurrency;
    private final Currency targetCurrenct;
    private double rate;

    public ExchangeRate(long id, Currency baseCurrency, Currency targetCurrenct, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrenct = targetCurrenct;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrenct() {
        return targetCurrenct;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
