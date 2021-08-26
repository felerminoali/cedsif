package com.cedsif.model;

public enum Status {

	O("Open"),
	U("Urgent"),
	P("In Progress"),
	R("In Review"),
	S("Resolved"),
	C("Closed"),;
	
	private final String description;

	Status (String description){
      this.description = description;
    }

    public String getDescription() {
      return this.description;
    }
}
