package com.monika.klimczak.logic;

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

    Supplier<Boolean> areWordsEmpty = () -> wordsPairs.isEmpty();

    BiConsumer<String, Set<String>> remove = (word, translations) ->
            wordsPairs.remove(new WordPair(word, translations));

    Function<String, Optional<WordPair>> findWordPairByWord = (word) ->
            wordsPairs.stream()
                    .filter(wordPair -> wordPair.word.equals(word))
                    .findFirst();

    private Function<Set<String>, Optional<WordPair>> findWordPairByTranslations = (translations) ->
            wordsPairs.stream()
                    .filter(wordPair -> wordPair.translations.containsAll(translations))
                    .findFirst();

    BiConsumer<String, String> updateWord = (word, newWord) ->
            findWordPairByWord.apply(word).ifPresent(wordPair -> {
                wordsPairs.remove(wordPair);
                wordsPairs.add(new WordPair(newWord, wordPair.translations));
            });

    BiConsumer<Set<String>, Set<String>> updateTranslations = (oldTranslations, newTranslations) ->
            findWordPairByTranslations.apply(oldTranslations).ifPresent(wordPair -> {
                wordsPairs.remove(wordPair);

                Set<String> allNewTranslations = SetUtils.replaceSomeElements(
                        oldTranslations, newTranslations, wordPair.translations
                );

                wordsPairs.add(new WordPair(wordPair.word, allNewTranslations));
            });
}
