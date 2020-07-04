package com.rainiersoft.tankgauge.core;

public class Tag 
{

	/**
	 * A model class that represents a tag.
	 *
	 */
	    private String name;
	    private String value;
	    private String id;
	    private TagType tagType;
	    private int noOfRegisters;
	    
	    
	    private String extra1;
	    
	     
	    public String getExtra1() {
			return extra1;
		}

		public void setExtra1(String extra1) {
			this.extra1 = extra1;
		}

		public int getNoOfRegisters() {
			return noOfRegisters;
		}

		public void setNoOfRegisters(int noOfRegisters) {
			this.noOfRegisters = noOfRegisters;
		}

		public Tag(String name, String value, TagType tagType, String id, int noOfRgisters) {
	        this.name = name;
	        this.value = value;
	        this.tagType = tagType;
	        this.id= id;
	        this.noOfRegisters = noOfRgisters;
	    }

		public Tag(String name, String value, TagType tagType, String id) {
	        this.name = name;
	        this.value = value;
	        this.tagType = tagType;
	        this.id= id;
	        this.noOfRegisters = 1;
	    }
	    
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getValue() {
	        return value;
	    }
	    
	    public String getId()
	    {
	    	return id;
	    }
	 
	    public void setId(String id)
	    {
	    	this.id = id;
	    }
	    public void setValue(String value) {
	        this.value = value;
	    }
	    
	    public TagType getTagType() {
	        return tagType;
	    }
	 
	    public void setTagType(TagType tagType) {
	        this.tagType = tagType;
	    }
	    

}