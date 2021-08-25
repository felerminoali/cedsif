package com.cedsif.model;

public enum Shift {

	M("Manhã"),
	T("Tarde");
	
	private final String description;

	Shift (String description){
      this.description = description;
    }

    public String getDescription() {
      return this.description;
    }
}
