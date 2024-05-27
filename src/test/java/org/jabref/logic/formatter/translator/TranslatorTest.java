package org.jabref.logic.formatter.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.jabref.logic.formatter.translator.Translator.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class for the Translator language translation class
 */
class TranslatorTest {

    private static Translator translator;

    @BeforeEach
    public void setUp() {
        translator = new Translator();
    }

    @Test
    public void translatorTestNormal() throws Exception {
        String expected = "Dies ist ein Text, der ins Englische übersetzt werden muss";
        String actual = translate("This is a text that needs to be translated into English");
        assertEquals(expected, actual);
    }

    @Test
    public void translatorTestShort() throws Exception {
        String expected = "Falke";
        String actual = translate("Falcon");
        assertEquals(expected, actual);
    }

    @Test
    public void translatorTestNormalFalse() throws Exception {
        String expected = "Die Antwort of die Frage ist ganz klar 42.";
        String actual = translate("Life, the universe and everthing are all important considerations.");
        assertNotEquals(expected, actual);
    }

    @Test
    public void translatorTestShortFalse() throws Exception {
        String expected = "Hase";
        String actual = translate("Squirrel");
        assertNotEquals(expected, actual);
    }
}
