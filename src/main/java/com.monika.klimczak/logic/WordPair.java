package com.monika.klimczak.logic;

import java.util.Set;

public class WordPair {

    final String word;
    final Set<String> translations;

    WordPair(final String word, final Set<String> translations) {
        this.word = word;
        this.translations = translations;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that instanceof WordPair) {
            return this.word.equals(((WordPair) that).word)
                    && this.translations.equals(((WordPair) that).translations);
        }

        return false;
    }

    void updateTranslation(final String translation, final String replacement) {
        this.translations.remove(translation);
        this.translations.add(replacement);
    }

    void addTranslations(final Set<String> translations) {
        this.translations.addAll(translations);
    }
}
