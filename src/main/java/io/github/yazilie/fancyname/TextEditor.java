package io.github.yazilie.fancyname;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.*;

public class TextEditor {
    private static final Map<TextModification, Component> CACHE = new HashMap<>();

    private record TextModification(Component original, String replace, Component replacement) {}
    
    public static Component modifyText(Component original, String replace, Component replacement) {
        FormattedCharSequence formattedCharSequence = original.getVisualOrderText();
        String originalString = original.getString().replaceAll("§.?", "");

        if(!originalString.contains(replace)) return original;

        TextModification key = new TextModification(original, replace, replacement);
        if(CACHE.containsKey(key)) return CACHE.get(key);

        List<Integer> matches = findAll(originalString, replace);
        Iterator<Integer> iterator = matches.iterator();
        final int[] next = {iterator.next()};

        final int[] index = {0};
        final int[] skip = {0};

        MutableComponent modified = Component.empty();

        formattedCharSequence.accept((ignored, style, c) -> {
            if(next[0] == index[0]) {
                modified.append(replacement);

                skip[0] += replace.length() - 1;
                if(iterator.hasNext()) next[0] = iterator.next();
            } else if(skip[0] == 0) {
                modified.append(Component.literal(new String(Character.toChars(c))).withStyle(style));
            } else {
                --skip[0];
            }

            index[0] += Character.charCount(c);
            return true;
        });

        CACHE.put(key, modified);
        return modified;
    }

    private static List<Integer> findAll(String string, String sub) {
        List<Integer> matches = new ArrayList<>();

        for(int i = 0; i <= string.length() - sub.length(); ++i) {
            if(string.startsWith(sub, i)) {
                matches.add(i);
            }
        }

        return matches;
    }

    public static void cleanCache() {
        CACHE.clear();
    }
}
