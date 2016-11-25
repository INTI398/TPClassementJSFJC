package com.classement.exception;

public class UnknownConcurrentException extends RuntimeException {
	private static final long serialVersionUID = -5034522564194578372L;

	
	public UnknownConcurrentException(int no) {
		super("Le concurrent au num�ro de dossard " + no + " n'existe pas.");
	} 
}
