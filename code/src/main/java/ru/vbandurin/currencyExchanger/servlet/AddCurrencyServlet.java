package ru.vbandurin.currencyExchanger.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.vbandurin.currencyExchanger.dto.Currency;
import ru.vbandurin.currencyExchanger.service.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/currencies")
public class AddCurrencyServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";

    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");
        if (!currencyService.validateCurrencyParams(name, code, sign)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Can't save currency because of invalid parameters given. ");
            return;
        }
        if (currencyService.findByCode(code).isPresent()) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Currency with code %s already exists".formatted(code));
            return;
        }
        Currency savedEntity = currencyService.save(new Currency(name, code, sign));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON);

        PrintWriter writer = response.getWriter();
        writer.println(GSON.toJson(savedEntity));
        writer.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Currency> currencies = currencyService.findAll();

        response.setContentType(APPLICATION_JSON);
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();
        writer.println(GSON.toJson(currencies));
        writer.flush();
    }
}
