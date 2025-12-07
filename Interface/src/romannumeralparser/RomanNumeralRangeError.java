package romannumeralparser;

public class RomanNumeralRangeError extends RuntimeException {
    public RomanNumeralRangeError(int number) {
        super(String.format("Roman numbers are only able to represent numbers within the interval [1, 3999]: %d", number));
    }
}
