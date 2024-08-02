package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.InvalidValueException;
import org.example.microsoftlists.exception.NameExistsException;

public class CommonService {
    private CommonService() {
        // hide the constructor
    }

    public static void throwIsExists(boolean isExists, String msg) throws NameExistsException {
        if (isExists) {
            throw new NameExistsException(msg);
        }
    }

    public static void throwIsInvalidValue(boolean isInvalid, String msg) throws InvalidValueException {
        if (isInvalid) {
            throw new InvalidValueException(msg);
        }
    }
}
