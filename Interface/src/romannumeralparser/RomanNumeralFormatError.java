package romannumeralparser;

public class RomanNumeralFormatError extends RuntimeException {
    public RomanNumeralFormatError(String numeral) {
        super(String.format("Malformed Roman numeral: %s", numeral));
    }
}
