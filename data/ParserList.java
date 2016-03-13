package com.syjung.pieceoflena.general;

import java.io.FileOutputStream;
import java.util.ArrayList;
import com.syjung.pieceoflena.base.GV;
import com.syjung.pieceoflena.base.WebContentsInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ParserList {
	ArrayList<WebContentsInfo> ListData;
	String html_header; 
	int start_pos; 
	String  FILENAME = "ParserList";
	
	public ParserList( ) {
		// TODO Auto-generated constructor stub
	}

	public void parsing(String webpage, ArrayList<WebContentsInfo> mArticleDatas,Handler hMainHander) {
		
		ParsingThread thread = new ParsingThread(webpage, mArticleDatas,hMainHander); 		
		thread.start();				
	}

	class ParsingThread extends Thread {
		String webpage;
		ArrayList<WebContentsInfo> mArticleDatas;
		Handler hMainHandler;
		String temp_str;		
		
		ParsingThread(String _webpage, ArrayList<WebContentsInfo> _mArticleDatas,Handler _hMainHandler){
			webpage = _webpage;
			mArticleDatas = _mArticleDatas;
			hMainHandler = _hMainHandler;
		}
		
		// title에 있는 <font ...>   </font>   <b> </b>  tag를 제거한다. 
		String remove_tag_in_title(String title)
		{
			int start;
			
			//GV.Log_E(FILENAME, "remove_tag_in_title","start");
			
			title = title.replaceAll( "<Font" , "<font");
			title = title.replaceAll( "</Font>" , "");
			title = title.replaceAll( "</font>" , "");
			title = title.replaceAll( "<b>" , "");
			title = title.replaceAll( "</b>" , "");
			title = title.replaceAll( "<B>" , "");
			title = title.replaceAll( "</B>" , "");
			
			start = title.indexOf("<font");
			if( start >= 0 )
			{
				start = title.indexOf(">") + 1;  // > 다음 문자부터.. 
				
				title = title.substring(start);				
			}
			
			//GV.Log_E(FILENAME, "remove_tag_in_title","end");

			return title;
		}
		
		public void file_write( String str )
		{
			try {
				FileOutputStream fos =   new FileOutputStream("sdcard/PieceOfLena_Log.txt" , true );//, Context.MODE_WORLD_READABLE);
				fos.write(str.getBytes());
				fos.close();
			} catch (Exception e) {;}
			
		}
		
		@Override
		public void run() {			

			int last_start_pos = 0;
			String category;
			
			// List Data 설정
			ListData = mArticleDatas;
			
			GV.Log_E(FILENAME, "run","1-start");
			
			// List Temp.html 생성과정 필요함
			int start = webpage.indexOf("><table border=0 cellspacing=0 cellpadding=0 width=100%>");
			start = webpage.indexOf("<table border=0 cellspacing=0 cellpadding=0 width=100%>",start + 10 );
			int end = webpage.indexOf("</table>", start) + new String("</table>").length();
			
			webpage = webpage.substring(start, end);
			GV.Log_E(FILENAME, "run","2- temp html");
			//GV.Log_E(FILENAME, "run","2.1- web page" + webpage);
			
			//file_write(webpage + "\n----------------------------------------------------------------------------------------------\n\n");
			
			// List 정리
			webpage = webpage.replaceAll("<tr><td colspan=10 background=skin/ruvin_gray_bo/dot.gif><img src=dot.gif height=1></td></tr>","");
			webpage = webpage.replaceAll("&nbsp;","");        
						
			start_pos = webpage.indexOf("notice_head.gif");
			
			GV.Log_E(FILENAME, "run","3-check point");

			//----------------------------------------------------------------------
			// 공지 Parsing			
			// 공지가 있는 경우
			GV.Log_E(FILENAME, "run","4-check point");

			if( start_pos >= 0 )
			{
				while( start_pos >= 0 )
				{

					WebContentsInfo ContentsInfo = new WebContentsInfo();
					
					// set no == 0  
					ContentsInfo.no = "";
					// Set value for notice
					ContentsInfo.category = 0;			
					
					// Get "url" 
					ContentsInfo.url = cut_value(webpage, "href=\"","\"" );
					
					if ( ContentsInfo.url == "" )
					{
						GV.Log_E(FILENAME, "run","4-check invalid url");
						break;
					}
					// Get "title" 
					ContentsInfo.title = remove_tag_in_title( cut_value(webpage, ">","</a" ));
					
					// Get "comment_count" 
					ContentsInfo.comment_count = cut_value(webpage, "fine3>","</" );					

					// Get Name 					
					ContentsInfo.name = cut_value(webpage, "hand>","<" );

					// Get "date" 
					ContentsInfo.date = cut_value(webpage, "\'>","<" );

					// Get "read_count" 
					ContentsInfo.read_count = cut_value(webpage, "fine2>","<" );
								
					// List에 Contents를 추가
					ListData.add(ContentsInfo);			

					last_start_pos = start_pos;
					// find next_notice
					start_pos = webpage.indexOf("notice_head.gif",start_pos );					
				}
				
				// start_pos 재설정
				start_pos = last_start_pos;
			}
			// 공지가 없는 경우
			else
			{
				start_pos = 0;
				GV.Log_E(FILENAME, "run","4-check point");
			}						
			
			// Get "no" 
		
			String temp_no = new String( cut_value(webpage, "width=20 nowrap>","<" ));

			GV.Log_E(FILENAME, "run","5-list parsing start");

			// 리스트 파싱작업
			while( start_pos >= 0 )
			{
				WebContentsInfo ContentsInfo = new WebContentsInfo();
				ContentsInfo.no = temp_no;				
								
				// Get "Category" 
				category = cut_value(webpage, "ruvin_gray_bo/"," border" );
				if( category.equals("notice_head.gif") )
				{
					ContentsInfo.category = 0;				
				}
				else if ( category.equals("new_head.gif") )
				{
					ContentsInfo.category = 1;				
				}
				else // old_head.gif, reply_head.gif, reply_new_head.gif					
				{
					ContentsInfo.category = 2;				
				}

				// Get "url" 
				ContentsInfo.url = cut_value(webpage, "href=\"","\"" );
				if ( ContentsInfo.url == "" )
				{
					GV.Log_E(FILENAME, "run","5-list invalid url");
					break;
				}
				
				// Get "title"
				ContentsInfo.title = remove_tag_in_title( cut_value(webpage, ">","</a" )); //cut_value(webpage, ">","<" );
				
				// Get "comment_count" 
				ContentsInfo.comment_count = cut_value(webpage, "fine3>","</" );			

				// Get Name 					
				ContentsInfo.name = cut_value(webpage, "hand>","<" );
				
				// Get "date" 
				ContentsInfo.date = cut_value(webpage, "\'>","<" );

				// Get "read_count" 
				ContentsInfo.read_count = cut_value(webpage, "fine2>","<" );
							
				// List에 Contents를 추가
				ListData.add(ContentsInfo);			

				// Get "no" 
				temp_str = cut_value(webpage, "width=20 nowrap>","<" );
				
				if( start_pos > 0 )
				temp_no = new String( temp_str );				
			} 

			GV.Log_E(FILENAME, "run","5-list parsing done");

			// send message to MainActivity			
		    Message message =  hMainHandler.obtainMessage();
		    Bundle bundle = new Bundle(); 
		    bundle.putInt("result", GV.SUCCESS);
		    message.setData(bundle);
		    hMainHandler.sendMessage(message);     
		}
		
		private String cut_value(String webpage, String start_str, String end_str) {
			// TODO Auto-generated method stub
			String value;
			int start,end;
			
			//GV.Log_E(FILENAME, "cut_value","start_str : " + start_str + ",end_str : " + end_str );
			
			//GV.Log_E(FILENAME, "cut_value","webpage : " + webpage );
			
			start = webpage.indexOf(start_str,start_pos);
			if( start <  0 )
			{ 
				start_pos = -1;
				//GV.Log_E(FILENAME, "cut_value","start < 0");
				return "";			
			}
			
			start +=  start_str.length();			

			end = webpage.indexOf(end_str,start);

			if( end <  0 )
			{ 
				start_pos = -1;
				//GV.Log_E(FILENAME, "cut_value","end < 0");
				return "";			
			}
			
			value = webpage.substring(start, end);
			start_pos = end + end_str.length();		
			
			return value;
		}

	}

}
