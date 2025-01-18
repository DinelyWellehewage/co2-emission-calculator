package com.dev.emissionCalculator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for parsing command line arguments into a key value map.
 * Supports arguments in the form of `key=value` or `--key value`.
 */

public class ArgumentParser {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentParser.class);
    /**
     * Returns a map of key-value pairs for command line arguments.
     *
     * @param args an array of command-line arguments
     * @return a map where key are argument name and values are argument values
     */

    public Map<String, String> parseArguments(String[] args) {
        logger.info("Parsing command-line arguments: {}",Arrays.toString(args));
        Map<String, String> argsMap = new HashMap<>();
        for (String arg : args) {
            if (arg.contains("=")) {
                String[] keyValue = arg.split("=");
                if (keyValue.length == 2) {
                    argsMap.put(keyValue[0], keyValue[1]);
                }
            } else if (arg.startsWith("--")) {
                String key = arg;
                String value = null;
                int index = Arrays.asList(args).indexOf(arg);
                if (index + 1 < args.length && !args[index + 1].startsWith("--")) {
                    value = args[index + 1];
                }
                argsMap.put(key, value);
            }
        }
        logger.info("Completed parsing arguments. Parsed map: {}",argsMap);
        return argsMap;
    }
}
