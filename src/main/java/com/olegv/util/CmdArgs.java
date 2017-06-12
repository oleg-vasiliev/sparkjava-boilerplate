package com.olegv.util;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * Small util for work with commandline arguments like "switches" (app.jar -port 4567) or "targets" (app.jar 4567).
 */
public class CmdArgs {
    
    private String[] args = null;
    
    private HashMap<String, Integer> switchIndexes = new HashMap<>();
    private TreeSet<Integer> takenIndexes = new TreeSet<>();
    
    public CmdArgs(String[] args) {
        parse(args);
    }
    
    public void parse(String[] arguments) {
        this.args = arguments;
        //locate switches.
        switchIndexes.clear();
        takenIndexes.clear();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                switchIndexes.put(args[i], i);
                takenIndexes.add(i);
            }
        }
    }
    
    public String[] args() {
        return args;
    }
    
    public String arg(int index) {
        return args[index];
    }
    
    public boolean switchPresent(String switchName) {
        return switchIndexes.containsKey(switchName);
    }
    
    public String switchValue(String switchName) {
        return switchValue(switchName, null);
    }
    
    public String switchValue(String switchName, String defaultValue) {
        if (!switchIndexes.containsKey(switchName)) return defaultValue;
        
        int switchIndex = switchIndexes.get(switchName);
        if (switchIndex + 1 < args.length) {
            takenIndexes.add(switchIndex + 1);
            return args[switchIndex + 1];
        }
        return defaultValue;
    }
    
    public Long switchLongValue(String switchName) {
        return switchLongValue(switchName, null);
    }
    
    public Long switchLongValue(String switchName, Long defaultValue) {
        String switchValue = switchValue(switchName, null);
        
        if (switchValue == null) return defaultValue;
        return Long.parseLong(switchValue);
    }
    
    public Integer switchIntValue(String switchName) {
        return switchIntValue(switchName, null);
    }
    
    public Integer switchIntValue(String switchName, Integer defaultValue) {
        String switchValue = switchValue(switchName, null);
        
        if (switchValue == null) return defaultValue;
        return Integer.parseInt(switchValue);
    }
    
    public Double switchDoubleValue(String switchName) {
        return switchDoubleValue(switchName, null);
    }
    
    public Double switchDoubleValue(String switchName, Double defaultValue) {
        String switchValue = switchValue(switchName, null);
        
        if (switchValue == null) return defaultValue;
        return Double.parseDouble(switchValue);
    }
    
    
    public String[] switchValues(String switchName) {
        if (!switchIndexes.containsKey(switchName)) return new String[0];
        
        int switchIndex = switchIndexes.get(switchName);
        
        int nextArgIndex = switchIndex + 1;
        while (nextArgIndex < args.length && !args[nextArgIndex].startsWith("-")) {
            takenIndexes.add(nextArgIndex);
            nextArgIndex++;
        }
        
        String[] values = new String[nextArgIndex - switchIndex - 1];
        for (int j = 0; j < values.length; j++) {
            values[j] = args[switchIndex + j + 1];
        }
        return values;
    }
    
    public String[] targets() {
        String[] targetArray = new String[args.length - takenIndexes.size()];
        int targetIndex = 0;
        for (int i = 0; i < args.length; i++) {
            if (!takenIndexes.contains(i)) {
                targetArray[targetIndex++] = args[i];
            }
        }
        return targetArray;
    }
    
}
