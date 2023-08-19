package com.umityasincoban.amasia_fide.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
@UtilityClass
public final class TemplateUtil {

    public static String convertTemplate(String template, HashMap<String, String> parameters) {
        StringBuilder result = new StringBuilder(template);
        parameters.forEach((key, value) -> {
            String bracketedKey = "[" + key + "]"; // Anahtara köşeli parantez ekleyin
            int index;
            while ((index = result.indexOf(bracketedKey)) != -1) {
                result.replace(index, index + bracketedKey.length(), value);
            }
        });
        return result.toString().replace("\\n", "\n"); // \\n kaçış dizisini gerçek yeni satır karakteriyle değiştirin
    }
    
}
