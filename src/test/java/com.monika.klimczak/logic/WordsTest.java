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

        assertTrue(words.areEmpty());
    }

    @Test
    void testAddNewWord() {
        Words words = new Words();
        words.add("cat", singleton("kot"));

        assertTrue(words.contains("cat", singleton("kot")));
    }

    @Test
    void testDeleteWord() {
        Words words = new Words();
        words.add("dog", singleton("pies"));
        words.remove("dog", singleton("pies"));

        assertFalse(words.contains("dog", singleton("pies")));
    }

    @Test
    void testUpdateWord() {
        Words words = new Words();
        words.add("cat", singleton("pies"));
        words.updateWord("cat", "dog");

        assertFalse(words.contains("cat", singleton("pies")));
        assertTrue(words.contains("dog", singleton("pies")));
    }

    @Test
    void testUpdateTranslation() {
        Words words = new Words();
        words.add("cat", singleton("pies"));
        words.updateTranslation(singleton("pies"), singleton("kot"));

        assertFalse(words.contains("cat", singleton("pies")));
        assertTrue(words.contains("cat", singleton("kot")));
    }

    @Test
    void testMultipleTranslations() {
        Words words = new Words();
        words.add("orange", Set.of("pomarancz", "pomarancza"));

        assertTrue(words.contains("orange", Set.of("pomarancz", "pomarancza")));
    }

    @Test
    void testUpdateOneTranslationWhenThereAreMultipleTranslations() {
        Words words = new Words();
        words.add("thing", Set.of("kulka", "obiekt"));
        words.updateTranslation(singleton("kulka"), singleton("rzecz"));

        assertFalse(words.contains("thing", Set.of("kulka", "obiekt")));
        assertTrue(words.contains("thing", Set.of("rzecz", "obiekt")));
    }

    @Test
    void updateMultipleTranslations() {
        Words words = new Words();
        words.add("thing", Set.of("kulka", "kubek"));
        words.updateTranslation(Set.of("kulka", "kubek"), Set.of("rzecz", "obiekt"));

        assertFalse(words.contains("thing", Set.of("kulka", "kubek")));
        assertTrue(words.contains("thing", Set.of("rzecz", "obiekt")));
    }

    @Test
    void updateTranslationWhenOtherWordPairHasSameTranslation() {
        Words words = new Words();
        words.add("zip", toSet.apply("zamek"));
        words.add("case", toSet.apply("zamek"));
        words.get("case").ifPresent(word -> word.updateTranslation("zamek", "sprawa"));

        assertTrue(words.contains("zip", singleton("zamek")));
        assertFalse(words.contains("case", singleton("zamek")));
        assertTrue(words.contains("case", singleton("sprawa")));
    }

    @Test
    void mergeWordPairsWhenTheyHaveSameWord() {
        Words words = new Words();
        words.add("case", toSet.apply("sprawa"));
        words.add("case", toSet.apply("walizka"));

        assertTrue(words.contains("case", Set.of("sprawa", "walizka")));
    }
}