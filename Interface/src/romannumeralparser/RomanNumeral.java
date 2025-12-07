package romannumeralparser;

public class RomanNumeral {

    private final String numeral;

    public RomanNumeral(String numeral) {
        this.numeral = numeral;
    }

    public static RomanNumeral fromArabic(short number) throws RomanNumeralRangeError {
        String numeral = "";

        // Trivial validation.
        if (1 > number || 3999 < number) {
            throw new RomanNumeralRangeError(number);
        }

        // Concatenate the whole numeral parts.
        short split[] = Internal.summationSplit(number);
        for (short n : split) {
            numeral += Internal.scaledArabicToNumeral(n);
        }

        return new RomanNumeral(numeral);
    }

    public boolean validate() {
        // Parses the values.
        short values[] = new short[numeral.length()];
        for (int i = 0; i < values.length; i++) {
            values[i] = Internal.parseSingleNumeral(numeral.charAt(i));
        }

        // Checks for errors in format.
        boolean fail = false;
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
     * Parses the Roman numeral to an Arabic representation of type short.
     *
     * @return The Roman numeral as a short data type.
     */
    public short parse() {
        // Validate.
        if (!validate()) {
            throw new RomanNumeralFormatError(numeral);
        }

        // Parses the values.
        short values[] = new short[numeral.length()];
        short acc = 0;

        for (int i = 0; i < values.length; i++) {
            values[i] = Internal.parseSingleNumeral(numeral.charAt(i));
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
