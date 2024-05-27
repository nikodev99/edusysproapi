package com.edusyspro.api.utils;

import java.util.Random;

public class Generator {

    public static String generateDigitsAndLetters() {
        Random random = new Random();

        // Generate a random uppercase letter
        char letter1 = (char) (random.nextInt(26) + 'A');
        char letter2 = (char) (random.nextInt(26) + 'A');
        char letter3 = (char) (random.nextInt(26) + 'A');

        // Generate three random digits for each group
        int digits1 = random.nextInt(1000);
        int digits2 = random.nextInt(1000);
        int digits3 = random.nextInt(1000);

        // Combine the parts to form the accreditation number

        return String.format("%c%03d-%03d%c-%c%03d", letter1, digits1, digits2, letter2, letter3, digits3);
    }

}
