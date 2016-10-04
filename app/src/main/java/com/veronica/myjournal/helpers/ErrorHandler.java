package com.veronica.myjournal.helpers;

import com.veronica.myjournal.interfaces.IErrorHandler;

/**
 * Created by Veronica on 10/4/2016.
 */
public class ErrorHandler implements IErrorHandler{

    private String mErrorMsg;

    @Override
    public void setErrorMessage(String msg) {
        this.mErrorMsg = msg;
    }

    @Override
    public String getErrorMessage() {
        return this.mErrorMsg;
    }
}
