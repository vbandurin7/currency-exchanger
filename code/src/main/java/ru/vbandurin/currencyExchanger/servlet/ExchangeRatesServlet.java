package ru.vbandurin.currencyExchanger.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = {"/exchangeRates", "/exchangeRates/*"}
)
public class ExchangeRatesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // url == /exchangeRates => list of all exchange rates
        // url == /exchangeRates/USDRUB => get special rate for USD and RUB
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // add new exchange rates for 2 currencies
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) {
        // update exchange rate between two currencies in database
    }
}
