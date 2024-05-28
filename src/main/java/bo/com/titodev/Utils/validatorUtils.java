package bo.com.titodev.Utils;

import java.util.regex.Pattern;

public class validatorUtils {

    // funciones estaticas de validacion de parametros

    public static boolean validateNumber(String id) {
        return id.matches("[0-9]+");
    }

    public static boolean validateString(String text) {
        if (text.length() == 0)
            return false;
        return true;
    }

    public static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

}
