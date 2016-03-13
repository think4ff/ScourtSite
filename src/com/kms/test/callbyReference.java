package com.kms.test;

class myNumber {
	
	myNumber() {}
	
	myNumber(int param)
	{
		value = param;
	}
	public int value;	
	
	int intValue()
	{
		return value;
	}
}

public class callbyReference {


	
	private static void swap( myNumber a, myNumber b )
	{
		myNumber temp = new myNumber( a.value ); 
		a.value = b.value; 
		b.value = temp.value;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		myNumber a = new myNumber(3);
		myNumber b = new myNumber(2);
		
		System.out.println( "a ==>" + a.intValue() );
		System.out.println( "b ==>" + b.intValue() );

		swap ( a, b);

		System.out.println( "a ==>" + a.intValue() );
		System.out.println( "b ==>" + b.intValue() );
		
	}

}
