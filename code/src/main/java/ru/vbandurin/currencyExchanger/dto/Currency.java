package ru.vbandurin.currencyExchanger.dto;

public class Currency {

    private long id;
    private final String fullName;
    private final String code;
    private final String sign;

    public Currency(long id, String fullName, String code, String sign) {
        this(fullName, code, sign);
        this.id = id;
    }

    public Currency(String fullName, String code, String sign) {
        this.fullName = fullName;
        this.code = code;
        this.sign = sign;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Currency[code=" + code + ", fullName=" + fullName + ", sign=" + sign + "]";
    }
}
