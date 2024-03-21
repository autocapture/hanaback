package com.aimskr.ac2.common.exception;

public class AutoReturnFailedException extends Exception{

    private static final long serialVersionUID=-1L;
    public int id;
    public AutoReturnFailedException (int id, String message) {
        super(message);
        this.id=id;
    }
    public AutoReturnFailedException (int id, String message, Throwable e) {
        super(message, e);
        this.id=id;
    }
    @Override
    public String toString(){
        return id+": "+getMessage();
    }

}
