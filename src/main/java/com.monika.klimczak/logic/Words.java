package com.monika.klimczak.logic;

import java.util.*;

class Words {

    private List<WordPair> wordsPairs;

    Words() {
        this.wordsPairs = new ArrayList<>();
    }

    void add(final String word, final Set<String> translations) {
        wordsPairs.stream()
                .filter(wordPair -> wordPair.word.equals(word))
                .findFirst()
                .ifPresentOrElse(wordPair -> wordPair.addTranslations(translations),
                        () -> wordsPairs.add(new WordPair(word, translations)));
    }

    boolean contains(final String word, final Set<String> translations) {
        return wordsPairs.contains(new WordPair(word, translations));
    }

    boolean areEmpty() {
        return wordsPairs.isEmpty();
    }

    void remove(final String word, final Set<String> translations) {
        wordsPairs.remove(new WordPair(word, translations));
    }

    void updateWord(final String word, final String newWord) {
        Optional<WordPair> foundWordPair = wordsPairs.stream()
                .filter(wordPair -> wordPair.word.equals(word))
                .findFirst();

        foundWordPair.ifPresent(pair -> {
            wordsPairs.remove(pair);
            wordsPairs.add(new WordPair(newWord, pair.translations));
        });
    }

    void updateTranslation(final Set<String> oldTranslations, final Set<String> newTranslations) {
        Optional<WordPair> foundPair = wordsPairs.stream()
                .filter(wordPair -> wordPair.translations.containsAll(oldTranslations))
                .findFirst();

        foundPair.ifPresent(pair -> {
            wordsPairs.remove(pair);

            Set<String> allNewTranslations = SetUtils.replaceSomeElements(
                    oldTranslations, newTranslations, pair.translations
            );

            wordsPairs.add(new WordPair(pair.word, allNewTranslations));
        });
    }

    Optional<WordPair> get(String word) {
        return wordsPairs.stream()
                .filter(wordPair -> wordPair.word.equals(word))
                .findFirst();
    }
}
