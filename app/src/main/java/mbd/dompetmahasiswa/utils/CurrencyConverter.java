package mbd.dompetmahasiswa.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Naufal on 27/03/2018.
 */

public class CurrencyConverter {
    public String convertToIDR(int value) {
        Locale localeID = new Locale("in", "ID");

        NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance(localeID);
        numberFormatCurrency.setMaximumFractionDigits(0);

        return numberFormatCurrency.format(value);
    }
}
