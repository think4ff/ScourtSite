package com.kms.core.data;

public class MyInt 
{
	public MyInt(int i) 
	{
		super();
		this.value = i;
	}
	
	public MyInt() 
	{
		super();
	}
	
	private int value;
	
	public void setValue( int i ) 
	{
	    this.value = i; 	
	}
	

	public int getValue() 
	{
		return this.value;
	}
}
