package com.kms.test;


public class myTest {
		
	static // �ݾ�(���� ������ "&nbsp"�� ����)
	String toMoney(String str) {

     if (str != null) str = str.trim();
 
     if (str == null || str.equals("&nbsp") || str.length() == 0)
      {
  	     return "&nbsp";
      }

		str = new java.math.BigDecimal(Double.parseDouble(str)).toString();

		java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,##0.##");

		return formatter.format(Double.parseDouble(str));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println( "Test Scope " + myTest.toMoney("1,059,383".replaceAll(",","")) );
		
		String sagun_no ="20 09 Ÿ �� 37 7 2";
		
		System.out.println("remove space : [" + sagun_no.replaceAll(" ", "") + "]"); 
		
		String outValue = "";
		String value = "123456789110001";  //1231234500000  1234123412341234 
		
		//log.info("value:"+value);

		int cnt = value.length();
		
		// ī���ȣ(16�ڸ�)     1234-1234-****-1234 
		if(cnt == 16 ){
			outValue = value.substring(0, 4) + "-" + value.substring(4, 8) + "-" + "****" + "-" +  value.substring(12, 16);
		}
		// ���Ű��¹�ȣ(15�ڸ�) 123456789-**-0001 
		else if(cnt == 15 ){
			outValue = value.substring(0, 9) + "-" + "**" + "-"  + value.substring(11, 15);
		// ���Ű��¹�ȣ(13�ڸ�) 123-12345-***-** 
		}else if(cnt == 13 ){
			outValue = value.substring(0, 3) + "-" + value.substring(3, 8) + "-***-**";
		}
		else{
			outValue = "";
		}
		
		System.out.println("outValue:" + outValue); 
		
		String money = "123,456,789";
			System.out.println( "money : " +  money.replaceAll( "," , "" ) );	
		
		String keyword = "�������ȸ";
		String tempMemo = "ȫ�浿[�������ȸ20140516�����Ϸ�]";			
		
		int pos = tempMemo.indexOf( keyword );
		if( pos >= 0)
		{
			int start = pos + keyword.length();
			int end = start + 8; 
			System.out.println( tempMemo.substring( start , end ));	
		}
		
		String text="123/456/789"; 
		System.out.println( text.replaceAll("/",""));
		System.out.println( text.replaceAll("\\n",""));
				
		String temp = "123456 (�ѱ�)";
		System.out.println( temp.substring(0, 6));

		String temp2 = "2013��ȸ45918";
		System.out.println( temp2.substring(0, 6));

		
		
        int auction_no = Integer.parseInt( "123" );        
        System.out.println(String.format("013%07d", auction_no));
        
        auction_no = Integer.parseInt( "1" );        
        System.out.println(String.format("013%07d", auction_no));
        auction_no = Integer.parseInt( "12" );        
        System.out.println(String.format("013%07d", auction_no));
        auction_no = Integer.parseInt( "123" );        
        System.out.println(String.format("013%07d", auction_no));
        auction_no = Integer.parseInt( "1234" );        
        System.out.println(String.format("013%07d", auction_no));
        auction_no = Integer.parseInt( "12345" );        
        System.out.println(String.format("013%07d", auction_no));
        auction_no = Integer.parseInt( "123456" );        
        System.out.println(String.format("013%07d", auction_no));
        auction_no = Integer.parseInt( "1234567" );        
        System.out.println(String.format("013%07d", auction_no));
	}

}
