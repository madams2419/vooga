package authoring.util;

import java.util.function.Predicate;
import java.util.regex.Pattern;


/**
 * The goal of this class is to figure out whether a String will parse into a particular type. Some
 * possible types to check are actual Java types, such as ints and doubles, but others are alpha
 * (all letters), lowercase alpha (all lowercase letters), and uppercase alpha (all uppercase
 * letters). There's also a method to determine whether the length of a String is in a certain
 * range.
 * 
 * @author Natalie
 *
 */
public class StringChecker {

    private enum Type {
        INTEGER("-?\\d+"),
        DOUBLE("-?\\d+(\\.\\d+)?"),
        ALPHA("[a-zA-Z]+"),
        LOWERCASE_ALPHA("[a-z]+"),
        UPPERCASE_ALPHA("[A-Z]+"),
        NEGATIVE("-\\d+(\\.\\d+)?");

        private String myString;

        private Type (String s) {
            myString = s;
        }

        private boolean matches (String match) {
            return Pattern.matches(myString, match);
        }
    }

    /**
     * Checks if a String can be parsed into an integer.
     * e.g. 5, 17, -142
     * 
     * @param string
     * @return
     */
    public static boolean isInteger (String string) {
        return Type.INTEGER.matches(string);
    }

    /**
     * Checks if a String can be parsed into a double.
     * e.g. 1.3, -0.2, 5
     * 
     * @param string
     * @return
     */
    public static boolean isDouble (String string) {
        return Type.DOUBLE.matches(string);
    }

    /**
     * Checks if a String is made up entirely of letters.
     * e.g. asdf, NFSDLeijf, hello, LETTERS
     * 
     * @param string
     * @return
     */
    public static boolean isAlpha (String string) {
        return Type.ALPHA.matches(string);
    }

    /**
     * Checks if a String is made up entirely of lowercase letters.
     * e.g. asdf, hello
     * 
     * @param string
     * @return
     */
    public static boolean isLowerCaseAlpha (String string) {
        return Type.LOWERCASE_ALPHA.matches(string);
    }

    /**
     * Checks if a String is made up entirely of uppercase letters.
     * e.g. LETTERS, HI
     * 
     * @param string
     * @return
     */
    public static boolean isUpperCaseAlpha (String string) {
        return Type.UPPERCASE_ALPHA.matches(string);
    }
    
    /**
     * Checks if a String is negative. Implicitly checks if it's a double. 
     * 
     * @param string
     * @return
     */
    public static boolean isNegativeDouble (String string) {
        return Type.NEGATIVE.matches(string);
    }

    /**
     * Checks if a String's length is within a certain range.
     * 
     * @param strings
     * @return
     */
    public static boolean hasLengthInRange (int minLength, int maxLength, String string) {
        int length = string.length();
        return (length >= minLength && length <= maxLength);
    }
    
    /**
     * Does the same as isInteger but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean areIntegers (String ... strings) {
        return areX(s -> isInteger(s), strings);
    }

    /**
     * Does the same as isDouble but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean areDoubles (String ... strings) {
        return areX(s -> isDouble(s), strings);
    }

    /**
     * Does the same as isAlpha but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean areAlpha (String ... strings) {
        return areX(s -> isAlpha(s), strings);
    }

    /**
     * Does the same as isLowerCaseAlpha but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean areLowerCaseAlpha (String ... strings) {
        return areX(s -> isLowerCaseAlpha(s), strings);
    }

    /**
     * Does the same as isUpperCaseAlpha but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean areUpperCaseAlpha (String ... strings) {
        return areX(s -> isUpperCaseAlpha(s), strings);
    }

    /**
     * Does the same as isNegativeDouble but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean areNegative (String ... strings) {
        return areX(s -> isNegativeDouble(s), strings);
    }
    
    private static boolean areX (Predicate<String> p, String ... strings) {
        for (String s : strings) {
            if (!p.test(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Does the same as hasLengthInRange but for potentially multiple inputs.
     * 
     * @param strings
     * @return
     */
    public static boolean haveLengthsInRange (int minLength, int maxLength, String ... strings) {
        return areX(s -> hasLengthInRange(minLength, maxLength, s), strings);
    }

    /**
     * Gives examples on checking different types of inputs.
     * 
     * @param args
     */
    public static void main (String[] args) {
        String[] inputs = { "asdf", "71", "   asdf", "   71", "asdf123", "AsDf",
                           "ASDF", "123.1", "   123.1", "hi!", "-142" };

        System.out.println("**Checking each input**\n");

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
            System.out.println();
        }

        System.out.println("**Checking three inputs at once**\n");

        checkAll(inputs[0], inputs[5], inputs[6]);
    }

    /**
     * Shows how to use the single-input methods.
     * 
     * @param s
     */
    private static void check (String s) {
        System.out.println("\"" + s + "\"");
        System.out.println("\tInteger: " + isInteger(s) + " ");
        System.out.println("\tDouble: " + isDouble(s) + " ");
        System.out.println("\tAlpha: " + isAlpha(s) + " ");
        System.out.println("\tLowercase alpha: " + isLowerCaseAlpha(s) + " ");
        System.out.println("\tUppercase alpha: " + isUpperCaseAlpha(s));
        System.out.println("\tIs between 3 and 5 characters long: " + hasLengthInRange(3, 5, s));
        System.out.println("\tIs negative: " + isNegativeDouble(s));
    }

    /**
     * Shows how to use the multiple-input methods.
     * 
     * @param strings
     */
    private static void checkAll (String ... strings) {
        for (String s : strings) {
            System.out.print("\"" + s + "\" ");
        }
        System.out.println();
        System.out.println("\tIntegers: " + areIntegers(strings) + " ");
        System.out.println("\tDoubles: " + areDoubles(strings) + " ");
        System.out.println("\tAlpha: " + areAlpha(strings) + " ");
        System.out.println("\tLowercase alpha: " + areLowerCaseAlpha(strings) + " ");
        System.out.println("\tUppercase alpha: " + areUpperCaseAlpha(strings));
        System.out.println("\tAre between 3 and 5 characters long: " +
                           haveLengthsInRange(3, 5, strings));
        System.out.println("\tAre negative: " + areNegative(strings));
    }
}
