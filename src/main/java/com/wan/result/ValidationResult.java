package com.wan.result;

import com.wan.exception.BaseException;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<String> msgErrors = new ArrayList<>();
    private List<BaseException> exceptionsErrors = new ArrayList<>();

    public boolean hasErrors() {
        return !msgErrors.isEmpty() || !exceptionsErrors.isEmpty();
    }

    public void addErrorMsg(String errorMessage) {
        msgErrors.add(errorMessage);
    }

    public void addErrorException(BaseException baseException) {
        exceptionsErrors.add(baseException);
    }
    public List<String> getMsgErrors() {
        return msgErrors;
    }

    public List<BaseException> getExceptionsErrors() {
        return exceptionsErrors;
    }
}
