package com.exampledeliverynew.deliverynew.commons.exception;

import java.io.Serializable;

public interface ErrorMessage extends Serializable {

    String getMessage();
    int getCode();

}
