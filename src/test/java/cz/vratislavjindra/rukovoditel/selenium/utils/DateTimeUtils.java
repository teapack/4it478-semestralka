package cz.vratislavjindra.rukovoditel.selenium.utils;

import java.time.LocalDateTime;

/**
 * Date and time utility class.
 *
 * @author Vratislav Jindra
 * @version 202001111502
 */
public class DateTimeUtils {

    /**
     * Private constructor to prevent direct instantiation.
     */
    private DateTimeUtils() {
    }

    /**
     * Returns the current date in the 'YYYY-MM-DD' format.
     *
     * @return The current date in the 'YYYY-MM-DD' format.
     */
    public static String getCurrentDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String result = currentDateTime.getYear() + "-";
        int currentMonth = currentDateTime.getMonthValue();
        if (currentMonth < 10) {
            result += "0";
        }
        result += currentMonth + "-";
        int currentDayOfMonth = currentDateTime.getDayOfMonth();
        if (currentDayOfMonth < 10) {
            result += "0";
        }
        result += currentDayOfMonth;
        return result;
    }
}