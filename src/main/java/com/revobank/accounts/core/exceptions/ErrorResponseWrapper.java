package com.revobank.accounts.core.exceptions;

import java.util.Map;

public record ErrorResponseWrapper(int status,
                                   String message,
                                   long timestamp,
                                   Map<String, String> errors) {

}
