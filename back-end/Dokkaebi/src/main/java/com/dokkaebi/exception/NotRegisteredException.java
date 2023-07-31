package com.dokkaebi.exception;

public class NotRegisteredException extends RuntimeException{
  private static final long serialVersionUID = 20230719L;
  public NotRegisteredException(String name) {
    super("NOT REGISTERED" + name);
  }
}
