package MemoryModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Environment {
    public final Map<String,Object> memoryMap=new LinkedHashMap<>();
    private final Environment enclosing;

    public Environment() {
        this.enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }



    public void define(String name,Object value){

            memoryMap.put(name,value);
    }

    public Object getValue(String name) {
        if(memoryMap.containsKey(name)) return memoryMap.get(name);
        throw new RuntimeException("Undefined variable '" + name + "'.");
    }

    public void assign(String name, Object value) {
        if (memoryMap.containsKey(name)) {
            memoryMap.put(name, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeException("Undefined variable '" + name + "'.");
    }




}
