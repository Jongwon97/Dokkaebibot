package com.dokkaebi.exception;

import java.io.Serial;

public class NotAuthorizedException extends RuntimeException{
  private static final long serialVersionUID = 20230724L;
  public NotAuthorizedException() {
    super("NOT AUTHORIZED");
  }

}
