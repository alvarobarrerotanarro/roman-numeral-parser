package romannumeralparser;

/**
 * Allows the conversion between  Roman numerals to Arabic ones and vice versa.
 */
public class RomanNumeral {

    /**
     * The greatest value that may be represented using Roman numerals.
     */
    public static final int MAX_VALUE = 3999;

    /**
     * The smallest value that may be represented using Roman numerls.
     */
    public static final int MIN_VALUE = 1;

    private final String numeral;

    /**
     * @param numeral Any valid Roman numeral within the interval [I, MMMCMXCIX] (that is [1, 3999]). If a wrong Roman numeral is provided, it will not throw until you try to parse the numeral; luckily you can use the validate() method before parsing, so you can ensure weather the numeral is correctly written before parsing.
     */
    public RomanNumeral(String numeral) {
        this.numeral = numeral;
    }

    /**
     * You may want to parse an Arabic numeral to a Roman numeral by using this method.
     * @param number Any number within the interval [1, 3999].
     * @return A Roman numeral that represents number.
     * @throws RomanNumeralRangeError In case number is out of the interval.
     */
    public static RomanNumeral fromArabic(short number) throws RomanNumeralRangeError {
        String numeral = "";

        // Trivial validation.
        if (1 > number || 3999 < number) {
            throw new RomanNumeralRangeError(number);
        }

        // Concatenate the whole numeral parts.
        short split[] = Internal.summationSplit(number);
        for (short n : split) {
            numeral += scaledArabicToNumeral(n);
        }

        return new RomanNumeral(numeral);
    }

    /* Parsing utilities */

    /**
     * Allows to calculate weather if number will need to be represented using Roman numeral substraction. For instance: 4 will return true since it is represented as IV (5 - 1).
     * Only the first digit of the given number is relevant, the remaining are ignored.
     * @param number Any integer number within the interval [1, 3999].
     * @return true in case Roman numeral substraction is needed to represent the number, false otherwise.
     */
    private static boolean requiresNumeralSubstraction(short number) {
        int scale = Internal.getProportionalScale(number);
        int pos = number / scale;
        return pos == 4 || pos == 9;
    }

    /**
     * Allows to parse a given Arabic number to a Roman number. Only the first digit of the number is relevant (it assumes it will receive on of the addends returned by the summationSplit() method). Take for instance the number 742, it will ignore the last two digits and multiply the result by its proportional scale (700) and finaly parse the number, so the result will be 'DCC'.
     * @param number Any integer number within the interval [1, 3999].
     * @return A Roman numeral representation of the first digit of number * by its proportional scale.
     */
    private static String scaledArabicToNumeral(short number) {
        String numeral = "";
        short numerals[] = Internal.getNumeralBoundaries(number);

        boolean needsSubstraction = requiresNumeralSubstraction(number);

        short scale = Internal.getProportionalScale(number);
        int factor = number / scale;
        int reps;

        // Trivial numerals.
        if (number < 1) {
            return numeral;
        } else if (number > 999) {
            numeral += "M".repeat((number / 1000) % 10);
            return numeral;
        } else if (numerals[1] == number) {
            numeral = String.valueOf(Internal.getNumeralChar(numerals[1]));
            return numeral;
        }

        // Concatenate the numerals.
        if (needsSubstraction) {
            reps = Math.abs(numerals[1] - (factor * scale)) / scale;
            numeral = String.valueOf(Internal.getNumeralChar(scale)).repeat(reps) + Internal.getNumeralChar(numerals[1]);
        } else {
            reps = Math.abs(numerals[0] - (factor * scale)) / scale;
            numeral = Internal.getNumeralChar(numerals[0]) + String.valueOf(Internal.getNumeralChar(scale)).repeat(reps);
        }

        return numeral;
    }


    /**
     * Validates the Roman numeral format.
     * @return true in case the validation is successful, false otherwise.
     */
    public boolean validate() {
        boolean fail = false;

        // Parses the values and checks for bad numerals.
        if (numeral.isEmpty()) {
            fail = true;
            return !fail;
        }

        short values[] = new short[numeral.length()];

        for (int i = 0; !fail && i < values.length; i++) {
            if (!Internal.isValidNumeral(numeral.charAt(i))) {
               fail = true;
            } else {
                values[i] = Internal.getNumeralValue(numeral.charAt(i));
            }
        }
        if (fail) {
            return !fail;
        }

        // Checks for errors in format.
        int counter = 0;
        short prev, current;
        current = values[0];

        for (int i = 1; !fail && i < values.length; i++) {
            // Update the prev numeral.
            prev = current;
            current = values[i];

            // Bad numeral check.
            if (!Internal.isValidNumeral(prev)) {
                fail = true;
            }

            // Bad substraction check.
            else if (
                    current > prev &&
                            !(
                                    current / Internal.getProportionalScale(current) == 5 &&
                                            Internal.getNumeralPos(prev) == Internal.getNumeralPos(current) - 1
                                            ||
                                            current / Internal.getProportionalScale(current) == 1 &&
                                                    Internal.getNumeralPos(prev) == Internal.getNumeralPos(current) - 2
                            )
            ) {
                fail = true;
            }

            // Repetitions check.
            if (current == prev) {
                counter++;
            } else {
                counter = 0;
            }

            if (counter >= 3) {
                fail = true;
            }
        }

        return !fail;
    }

    /**
     * Parses the Roman numeral to Arabic format, that is, a short data type. Before parsing the numeral a format validation layer checks the format.
     * @return The Roman numeral as a short data type.
     * @throws RomanNumeralFormatError In case the validation layer detected a format error.
     */
    public short parse() throws RomanNumeralFormatError {
        // Validate.
        if (!validate()) {
            throw new RomanNumeralFormatError(numeral);
        }

        // Parses the values.
        short values[] = new short[numeral.length()];
        short acc = 0;

        for (int i = 0; i < values.length; i++) {
            values[i] = Internal.getNumeralValue(numeral.charAt(i));
        }

        // Finds the valleys ands makes then negative.
        for (int i = 1; i < values.length; i++) {
            if (values[i - 1] - values[i] < 0) {
                /**
                 * Previous equal symbols should all be negative
                 * in order to write numbers like IIIX (7).
                 */
                boolean broken_chain = false;
                for (int j = i - 1; !broken_chain && j >= 0; j--) {
                    if (Math.abs(values[j]) == Math.abs(values[i - 1]))
                        values[j] *= -1;
                    else
                        broken_chain = true;
                }
            }
        }

        // Gets the accumulation.
        for (int i = 0; i < values.length; i++) {
            acc += values[i];
        }

        return acc;
    }

    @Override
    public String toString() {
        return this.numeral;
    }
}
