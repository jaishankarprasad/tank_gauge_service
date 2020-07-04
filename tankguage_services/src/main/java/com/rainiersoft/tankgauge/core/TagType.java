package com.rainiersoft.tankgauge.core;


public class TagType {

	private String name;
 
    public TagType(String name) {
        super();
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
     
    public String toString() {
        return this.name;
    }
     
}