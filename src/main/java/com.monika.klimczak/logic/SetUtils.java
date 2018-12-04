package com.monika.klimczak.logic;

import java.util.HashSet;
import java.util.Set;

class SetUtils {

    static Set<String> replaceSomeElements(final Set<String> elementsToReplace,
                                           final Set<String> replacements,
                                           final Set<String> elements) {
        Set<String> newElements = new HashSet<>(){};
        newElements.addAll(replacements);
        elements.stream()
                .filter(element -> !elementsToReplace.contains(element))
                .forEach(newElements::add);

        return newElements;
    }

}
