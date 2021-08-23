package com.cedsif.model;

public enum Category {
	
	CONSULTANT("Consultor"),
	MANAGER("Gestor"),
	ADMINISTRATOR("Administrador");
	
	private final String category;

	Category (String Category){
      this.category = Category;
    }

    public String getCategory() {
      return this.category;
    }
    
    

	

}
