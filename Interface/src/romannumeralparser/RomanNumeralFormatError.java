package romannumeralparser;

public class RomanNumeralFormatError extends RuntimeException {
    public RomanNumeralFormatError(String numeral) {
        super(String.format("Wrong Roman numeral format detected: '%s'", numeral));
    }
}
