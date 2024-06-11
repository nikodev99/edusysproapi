package com.edusyspro.api.utils;

import java.util.List;

public class Text {

    public static String listToArrayStringLike(List<String> elements) {
        if (elements == null) return null;
        StringBuilder stringBuilder = new StringBuilder("[");
        elements.forEach(element -> stringBuilder.append(element).append(","));
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static List<String> arrayStringLikeToList(String element) {
        if (element == null) return null;
        String cleanedInput = element.replaceAll("[\\[\\]\\s]", "");
        String[] elements = cleanedInput.split(",");
        return List.of(elements);
    }

}
