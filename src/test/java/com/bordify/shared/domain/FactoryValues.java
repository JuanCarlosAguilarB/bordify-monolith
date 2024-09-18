package com.bordify.shared.domain;

import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class FactoryValues {

    public static String generateRandomAlphanumeric(int length) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        return generateRandomizeString(length, characters);
    }

    public static String generateRandomAlphanumeric(int start, int end) {

        int length = generateRandomValue(start, end);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return generateRandomizeString(length, characters);
    }

    public static String generateRandomEmail() {

        int usernameEmailLenght = generateRandomValue(5,10);
        int domainEmailLenght = generateRandomValue(2,5);
        return generateRandomString(usernameEmailLenght) + "@" + generateRandomString(domainEmailLenght) + ".com";
    }

    public static String generateRandomString(int length) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return generateRandomizeString(length, characters);
    }

    public static String generateRandomPhoneNumber() {
        return "+1" + generateRandomValue(1000000000);
    }

    public static String generateRandomizeString(int length, String baseString){
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = generateRandomValue(length);
            sb.append(baseString.charAt(index));
        }
        return sb.toString();
    }

}
