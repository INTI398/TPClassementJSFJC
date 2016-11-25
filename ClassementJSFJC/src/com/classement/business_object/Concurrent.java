package com.classement.business_object;

import java.io.Serializable;

public class Concurrent implements Serializable {
	private static final long serialVersionUID = -8532746189132861552L;
	
	private String nom;
	private String prenom;
	private float temps; 
	private int noDossard;
	
	public int getNoDossard() {
		return noDossard;
	}
	public void setNoDossard(int noDossard) {
		this.noDossard = noDossard;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public float getTemps() {
		return temps;
	}
	public void setTemps(float temps) {
		this.temps = temps;
	}
	

}
