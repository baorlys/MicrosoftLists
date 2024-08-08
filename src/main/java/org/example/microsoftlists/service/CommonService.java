package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.InvalidValueException;
import org.example.microsoftlists.exception.NameExistsException;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    private CommonService() {
        // Empty constructor
    }
    public static void throwIsNull(Object obj, String msg) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
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
