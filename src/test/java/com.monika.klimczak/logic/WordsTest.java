package com.monika.klimczak.logic;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordsTest {

    private Function<String, Set<String>> toSet = translation -> Stream.of(translation).collect(Collectors.toSet());

    @Test
    void testEmptyWordsAtStart() {
        Words words = new Words();

        assertTrue(words.areWordsEmpty.get());
    }

    @Test
    void testAddNewWord() {
        Words words = new Words();
        words.add.accept("cat", singleton("kot"));

        assertTrue(words.contains.apply("cat", singleton("kot")));
    }

    @Test
    void testDeleteWord() {
        Words words = new Words();
        words.add.accept("dog", singleton("pies"));
        words.remove.accept("dog", singleton("pies"));

        assertFalse(words.contains.apply("dog", singleton("pies")));
    }

    @Test
    void testUpdateWord() {
        Words words = new Words();
        words.add.accept("cat", singleton("pies"));
        words.updateWord.accept("cat", "dog");

        assertFalse(words.contains.apply("cat", singleton("pies")));
        assertTrue(words.contains.apply("dog", singleton("pies")));
    }

    @Test
    void testUpdateTranslation() {
        Words words = new Words();
        words.add.accept("cat", singleton("pies"));
        words.updateTranslations.accept(singleton("pies"), singleton("kot"));

        assertFalse(words.contains.apply("cat", singleton("pies")));
        assertTrue(words.contains.apply("cat", singleton("kot")));
    }

    @Test
    void testMultipleTranslations() {
        Words words = new Words();
        words.add.accept("orange", Set.of("pomarancz", "pomarancza"));

        assertTrue(words.contains.apply("orange", Set.of("pomarancz", "pomarancza")));
    }

    @Test
    void testUpdateOneTranslationWhenThereAreMultipleTranslations() {
        Words words = new Words();
        words.add.accept("thing", Set.of("kulka", "obiekt"));
        words.updateTranslations.accept(singleton("kulka"), singleton("rzecz"));

        assertFalse(words.contains.apply("thing", Set.of("kulka", "obiekt")));
        assertTrue(words.contains.apply("thing", Set.of("rzecz", "obiekt")));
    }

    @Test
    void updateMultipleTranslations() {
        Words words = new Words();
        words.add.accept("thing", Set.of("kulka", "kubek"));
        words.updateTranslations.accept(Set.of("kulka", "kubek"), Set.of("rzecz", "obiekt"));

        assertFalse(words.contains.apply("thing", Set.of("kulka", "kubek")));
        assertTrue(words.contains.apply("thing", Set.of("rzecz", "obiekt")));
    }

    @Test
    void updateTranslationWhenOtherWordPairHasSameTranslation() {
        Words words = new Words();
        words.add.accept("zip", toSet.apply("zamek"));
        words.add.accept("case", toSet.apply("zamek"));
        words.findWordPairByWord.apply("case").ifPresent(word -> word.updateTranslation.accept("zamek", "sprawa"));

        assertTrue(words.contains.apply("zip", singleton("zamek")));
        assertFalse(words.contains.apply("case", singleton("zamek")));
        assertTrue(words.contains.apply("case", singleton("sprawa")));
    }

    @Test
    void mergeWordPairsWhenTheyHaveSameWord() {
        Words words = new Words();
        words.add.accept("case", toSet.apply("sprawa"));
        words.add.accept("case", toSet.apply("walizka"));

        assertTrue(words.contains.apply("case", Set.of("sprawa", "walizka")));
    }
}