package com.cedsif.model;

public enum Relatedness {

	
	Filho("Filho(a)"),
	Neto("Neto(a)"),
	Primo("Primo(a)"),
	Irmao("Irmão(a)"),
	Tio("Tio(a)"),
	Pai("Pai(Mãe)"),
	Avo("Avo")
	;
	
	private final String description;

	Relatedness (String description){
      this.description = description;
    }

    public String getDescription() {
      return this.description;
    }
}
