package romannumeralparser;

public class RomanNumeralRangeError extends RuntimeException {
    public RomanNumeralRangeError(int number) {
        super(String.format("Roman numerals cannot represent values out of the interval [1, 3999]: '%d'", number));
    }
}
