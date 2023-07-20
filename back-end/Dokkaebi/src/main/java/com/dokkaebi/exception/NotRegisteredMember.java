package com.dokkaebi.exception;

public class NotRegisteredMember extends RuntimeException{
  private static final long serialVersionUID = 20230719L;
  public NotRegisteredMember() {
    super("This Member Is Not On Database!!!");
  }
}
