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
        System.out.println("ENV DEFINE: " + name + " = " + value + " in " + this);


        memoryMap.put(name,value);
    }

    public Object getValue(String name) {
        if (memoryMap.containsKey(name)) {
            System.out.println("ENV GET: " + name + " in " + this);
            return memoryMap.get(name);
        }

        if (enclosing != null) {
            return enclosing.getValue(name); // ✅ lookup in parent
        }

        throw new RuntimeException("Undefined variable '" + name + "'.");
    }

    public void assign(String name, Object value) {
        if (memoryMap.containsKey(name)) {
            memoryMap.put(name, value);
            return;
        }

        else if (enclosing != null) {
            enclosing.assign(name, value); // ✅ assign in parent
            return;
        }

        throw new RuntimeException("Undefined variable '" + name + "'.");
    }






}
