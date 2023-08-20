import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HangmanManagerTest {

    public HangmanManager hangman;

    @BeforeEach
    void setUp() {
        List<String> dict = new ArrayList<>();
        dict.add("ally");
        dict.add("beta");
        dict.add("cool");
        dict.add("deal");
        dict.add("else");
        dict.add("flew");
        dict.add("good");
        dict.add("hope");
        dict.add("ibex");
        hangman = new HangmanManager(dict, 4, 7);
    }

    @Test
    void words() {
        SortedSet<String> initWords = new TreeSet<>();
        initWords.add("ally");
        initWords.add("beta");
        initWords.add("cool");
        initWords.add("deal");
        initWords.add("else");
        initWords.add("flew");
        initWords.add("good");
        initWords.add("hope");
        initWords.add("ibex");
        assertEquals(initWords, hangman.words());
        hangman.record('e');
        initWords.remove("beta");
        initWords.remove("deal");
        initWords.remove("else");
        initWords.remove("flew");
        initWords.remove("hope");
        initWords.remove("ibex");
        assertEquals(initWords, hangman.words());
        hangman.record('o');
        initWords.remove("ally");
        assertEquals(initWords, hangman.words());
        hangman.record('c');
        initWords.remove("cool");
        assertEquals(initWords, hangman.words());
    }

    @Test
    void guessesLeft() {
        assertEquals(7, hangman.guessesLeft());
        hangman.record('e');
        assertEquals(6, hangman.guessesLeft());
        hangman.record('o');
        assertEquals(6, hangman.guessesLeft());
        hangman.record('c');
        assertEquals(5, hangman.guessesLeft());
        hangman.record('g');
        assertEquals(5, hangman.guessesLeft());
        hangman.record('d');
        assertEquals(5, hangman.guessesLeft());
    }

    @Test
    void guesses() {
        Set<Character> chars = new TreeSet<>();
        assertEquals(chars, hangman.guesses());

        hangman.record('e');
        chars.add('e');
        assertEquals(chars, hangman.guesses());

        hangman.record('o');
        chars.add('o');
        assertEquals(chars, hangman.guesses());

        hangman.record('c');
        chars.add('c');
        assertEquals(chars, hangman.guesses());

        hangman.record('g');
        chars.add('g');
        assertEquals(chars, hangman.guesses());

        hangman.record('d');
        chars.add('d');
        assertEquals(chars, hangman.guesses());
    }

    @Test
    void pattern() {
        assertEquals("- - - -", hangman.pattern());

        assertEquals(0, hangman.record('e'));
        assertEquals("- - - -", hangman.pattern());

        assertEquals(2, hangman.record('o'));
        assertEquals("- o o -", hangman.pattern());

        assertEquals(0, hangman.record('c'));
        assertEquals("- o o -", hangman.pattern());

        assertEquals(1, hangman.record('g'));
        assertEquals("g o o -", hangman.pattern());

        assertEquals(1, hangman.record('d'));
        assertEquals("g o o d", hangman.pattern());

        // Check if set is empty
        hangman = new HangmanManager(List.of(), 3, 2);
        assertThrows(IllegalStateException.class, () -> hangman.pattern());

    }

    @Test
    void constructor() {
        List<String> fakeDict = new ArrayList<>();
        fakeDict.add("hello");
        // Length != 0
        assertThrows(IllegalArgumentException.class, () -> new HangmanManager(fakeDict, 0, 2));
        // Length > 0
        assertThrows(IllegalArgumentException.class, () -> new HangmanManager(fakeDict, -1, 2));
        // Max > 0
        assertThrows(IllegalArgumentException.class, () -> new HangmanManager(fakeDict, 0, -1));
    }

    @Test
    void record() {
        // No doubles
        assertEquals(0, hangman.record('e'));
        assertThrows(IllegalArgumentException.class, () -> hangman.record('e'));

        // Num guesses at least 1
        List<String> fakeDict = new ArrayList<>();
        fakeDict.add("hello");
        hangman = new HangmanManager(fakeDict, 5, 1);
        hangman.record('g');
        assertThrows(IllegalStateException.class, () -> hangman.record('c'));

        // If list of words is empty -> IllState
        List<String> emptyList = new ArrayList<>();
        hangman = new HangmanManager(emptyList, 5, 1);
        assertThrows(IllegalStateException.class, () -> hangman.record('e'));
    }
}