package com.monika.klimczak.logic;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@EqualsAndHashCode
class WordPair {

    final String word;
    final Set<String> translations = new HashSet<>();

    WordPair(final String word, final Set<String> translations) {
        this.word = word;
        this.translations.addAll(translations);
    }

    @EqualsAndHashCode.Exclude
    BiConsumer<String, String> updateTranslation = (translation, newTranslation) -> {
        this.translations.remove(translation);
        this.translations.add(newTranslation);
    };

    @EqualsAndHashCode.Exclude
    Consumer<Set<String>> addTranslations = this.translations::addAll;
}
