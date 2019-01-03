package com.monika.klimczak.logic;

import javafx.util.Pair;

import java.util.*;
import java.util.function.*;

class Words {

    private List<WordPair> wordsPairs;

    Words() {
        this.wordsPairs = new ArrayList<>();
    }

    BiConsumer<String, Set<String>> add = (word, translations) ->
            wordsPairs.stream()
                    .filter(wordPair -> wordPair.word.equals(word))
                    .findFirst()
                    .ifPresentOrElse(wordPair -> wordPair.addTranslations.accept(translations),
                            () -> wordsPairs.add(new WordPair(word, translations)));

    BiFunction<String, Set<String>,  Boolean> contains = (word, translations) ->
            wordsPairs.contains(new WordPair(word, translations));

    Supplier<Boolean> areEmpty = () -> wordsPairs.isEmpty();

    BiConsumer<String, Set<String>> remove = (word, translations) ->
            wordsPairs.remove(new WordPair(word, translations));

    Function<String, Optional<WordPair>> findWordPairByWord = (word) ->
            wordsPairs.stream()
                    .filter(wordPair -> wordPair.word.equals(word))
                    .findFirst();

    BiConsumer<String, String> updateWord = (word, newWord) ->
            findWordPairByWord.apply(word).ifPresent(wordPair -> {
                wordsPairs.remove(wordPair);
                wordsPairs.add(new WordPair(newWord, wordPair.translations));
            });

    BiConsumer<String, Pair<Set<String>, Set<String>>> updateTranslations = (word, oldAndNewTranslations) ->
            findWordPairByWord.apply(word).ifPresent(foundWord -> {
                foundWord.translations.removeAll(oldAndNewTranslations.getKey());
                foundWord.translations.addAll(oldAndNewTranslations.getValue());
            });
}
