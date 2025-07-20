package MemoryModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Environment {
    public final Map<String,Object> memoryMap=new LinkedHashMap<>();


    public void define(String name,Object value){

            memoryMap.put(name,value);
    }

    public Object getValue(String name) {
        if(memoryMap.containsKey(name)) return memoryMap.get(name);
        throw new RuntimeException("Undefined variable '" + name + "'.");
    }



}
