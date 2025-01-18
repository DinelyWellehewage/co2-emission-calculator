package com.dev.emissioncalculator.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    private final ArgumentParser argumentParser = new ArgumentParser();

    @Test
    void testParseArguments_SimpleKeyValue() {
        String[] args = {"--start", "Hamburg", "--end", "Berlin", "--transportation-method", "diesel-car-medium"};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertEquals(3, result.size());
        assertEquals("Hamburg", result.get("--start"));
        assertEquals("Berlin", result.get("--end"));
        assertEquals("diesel-car-medium", result.get("--transportation-method"));
    }

    @Test
    void testParseArguments_WithEqualsSign() {
        String[] args = {"start=Hamburg", "end=Berlin", "transportation-method=diesel-car-medium"};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertEquals(3, result.size());
        assertEquals("Hamburg", result.get("start"));
        assertEquals("Berlin", result.get("end"));
        assertEquals("diesel-car-medium", result.get("transportation-method"));
    }

    @Test
    void testParseArguments_MixedStyle() {
        String[] args = {"--start", "Hamburg", "end=Berlin", "--transportation-method", "diesel-car-medium"};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertEquals(3, result.size());
        assertEquals("Hamburg", result.get("--start"));
        assertEquals("Berlin", result.get("end"));
        assertEquals("diesel-car-medium", result.get("--transportation-method"));
    }

    @Test
    void testParseArguments_MissingValueAfterKey() {
        String[] args = {"--start", "--end", "--transportation-method"};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertEquals(3, result.size());
        assertNull(result.get("--start"));
        assertNull(result.get("--end"));
        assertNull(result.get("--transportation-method"));
    }

    @Test
    void testParseArguments_EmptyArguments() {
        String[] args = {};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseArguments_ArgumentWithEqualsAndValue() {
        String[] args = {"key1=value1", "key2=value2"};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test
    void testParseArguments_SingleArgument() {
        String[] args = {"--start", "Hamburg"};

        Map<String, String> result = argumentParser.parseArguments(args);

        assertEquals(1, result.size());
        assertEquals("Hamburg", result.get("--start"));
    }
}