package com.cedsif.model;

public enum Category {
	
	C("Consultor"),
	G("Gestor"),
	A("Administrador");
	
	private final String category;

	Category (String Category){
      this.category = Category;
    }

    public String getCategory() {
      return this.category;
    }
    
    

	

}
