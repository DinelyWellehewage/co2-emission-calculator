package com.dev.emissionCalculator.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {

    public Map<String, String> parseArguments(String[] args) {
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
        return argsMap;
    }
}
