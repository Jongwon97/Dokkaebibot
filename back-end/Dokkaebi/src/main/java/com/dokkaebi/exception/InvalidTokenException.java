package com.dokkaebi.exception;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = -2238030302650813813L;
	
	public InvalidTokenException() {
		super("ACCESS TOKEN IS NOT VALID\nPLEASE LOGIN AGAIN");
	}
}
