package com.cnpc.dj.party.exception;


public class ThridPartyException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ThridPartyException(String message)
    {
        super(message);
    }
	
	public ThridPartyException(String message, Throwable cause)
    {
        super(message, cause);
    }
	

}


