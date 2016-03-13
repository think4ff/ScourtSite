package com.syjung.pieceoflena.general;

import java.util.HashMap;

import com.syjung.pieceoflena.base.GV;
import com.syjung.pieceoflena.base.WebContentsInfo;
import android.content.*;


public class ParserContents {
	String  webpage,webbody,contents_header; 
	Context main_context;
	int     cur_pos;
	String  FILENAME = "ParserContents";

	final private String web_header = 	
	"<HTML>	<HEAD>	<title>Piece Of Lena - Article</title>\n" +	        
	"	    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-16le\"> </HEAD> \n" + 
	"<body topmargin='0'  leftmargin='0' marginwidth='0' marginheight='0'  bgcolor=white >\n" +
	"<style>\n" + 
	"BODY,TD,SELECT,input,DIV,form,TEXTAREA,center,option,pre,blockquote,span {font-size:8pt; font-family:verdana;}\n" +
	"A:link {font-size:8pt;font-family:verdana;color:#333333;text-decoration:none;}\n" +
	"A:visited {font-size:8pt;font-family:verdana;color:#333333;text-decoration:none;}\n" +
	"A:active {font-size:8pt;font-family:verdana;color:#333333;text-decoration:none;}\n" +
	"A:hover {font-size:8pt;font-family:verdana;color;#E00102;text-decoration:none;}\n"+
	"</style>\n"+
	"<table border=0 cellspacing=0 cellpadding=0 >\n";
	//final private String web_header2="<html><head>test</head> <body> <table > \n";
	final private String web_tail = "</body></html>";
	final private String header_start_str = "<tr height=23>";
	final private String body_start_str = "<table border=0 cellspacing=5 cellpadding=5 width=";
	final private String body_end_str = "<table border=0 cellspacing=0 cellpadding=6 width=";
	final private String comment_end_str = "<table border=0 cellspacing=0 width=";
	final private String url_end_str = "<form method=post name=list action=list_all.php>";
	
	WebContentsInfo HeaderInfo;

	public ParserContents(Context _main_context, String _webpage) {
		// TODO Auto-generated constructor stub
		main_context = _main_context;
		webpage = _webpage;
		
		HeaderInfo = new  WebContentsInfo();
	}
	
	private String cut_value(String webpage, String start_str, String end_str) {
		// TODO Auto-generated method stub
		String value;
		int start,end;
		
		if( cur_pos < 0 )
		{
		    GV.Log_E(FILENAME, "cut_value", "invalid cur_pos");
			return "";	
		}
		start = webpage.indexOf(start_str,cur_pos);
		
		//GV.Log_D(FILENAME, "cut_value", "start_pos:" + start_str + ":" + start );
		if( start <  0 )
		{ 
			cur_pos = -1;
			GV.Log_E(FILENAME, "cut_value", "can't find start keyword");
			return "";			
		}
		
		start +=  start_str.length();			
		end = webpage.indexOf(end_str,start);
		//GV.Log_D(FILENAME, "cut_value", "end_pos:" +  end_str + ":" + end );
					
		if( end <  0 )
		{ 
	          GV.Log_E(FILENAME, "cut_value", "can't find end keyword");

//		    end = -1;
			cur_pos = -1;
			return "";			
		}
		
		value = webpage.substring(start, end);
		cur_pos = end + end_str.length();		
		
		//GV.Log_D(FILENAME, "cut_value", "value:" + value );

		return value;
	}
	
	String cut_value2( String keyword, String webpage, String end_chker,String start_str, String end_str)
	{
		String result = new String();
		int pos_end_chker=0,pos_start_str=0;
		cur_pos = webpage.indexOf( keyword );		
		pos_end_chker = webpage.indexOf( end_chker, cur_pos );
		pos_start_str = webpage.indexOf( start_str, cur_pos );
		
        GV.Log_D(FILENAME, "cut_value2", "pos_end_chker:" + pos_end_chker + ",pos_start_str : " + pos_start_str); 
		if( pos_end_chker != -1 && pos_end_chker < pos_start_str  )
	    {
	        return "";
	    }
		
		if(  cur_pos >= 0 )
		{
			result = cut_value(webpage, start_str, end_str );					 
		}
		
		return result;
	}

	public void parsing_contents_header( String header )
	{

		GV.Log_D(FILENAME, "parsing_header", "start....................................................." );
		// 이름. 
		HeaderInfo.name = cut_value(header, "style=cursor:hand>", "</span>" );
		GV.Log_D(FILENAME, "parsing_header", "name:" + HeaderInfo.name );
		
		// 제목
		HeaderInfo.title = cut_value(header, "<td style='word-break:break-all;'>", "</td>" );	
		// Tag를 제거한다.
		HeaderInfo.title = remove_tag_in_title(HeaderInfo.title);
		GV.Log_D(FILENAME, "parsing_header", "title:" + HeaderInfo.title );
		
		// 홈페이지
		HeaderInfo.homepage = cut_value2("homepage.gif",header,"-->", "<a href='", "'");
		//GV.Log_D(FILENAME, "parsing_header", "homepage:" + HeaderInfo.homepage );
		
		// upload1 url
//		HeaderInfo.upload1_url = cut_value2("upload1.gif",header, "<A target = '_blank' href='", "'>");
//		GV.Log_D(FILENAME, "parsing_header", "upload1 url:" + HeaderInfo.upload1_url );
//
//		if( HeaderInfo.upload1_url.length() != 0 )
//		{
//			HeaderInfo.upload1_url = "http://www.pieceoflena.com/ver2/bbs/" + HeaderInfo.upload1_url;			
//		}
			
		// upload1 name
		HeaderInfo.upload1_name = cut_value2("upload1.gif",header, "-->", "filenum=1'>", "</td>").replaceAll("</a>", "");
		GV.Log_D(FILENAME, "parsing_header", "upload1:" + HeaderInfo.upload1_name );
		
		// upload2 url
//		HeaderInfo.upload2_url = cut_value2("upload2.gif",header, "<A target = '_blank' href='", "'>");
//		GV.Log_D(FILENAME, "parsing_header", "upload1 url:" + HeaderInfo.upload2_url );
//
//		if( HeaderInfo.upload2_url.length() != 0 )
//		{
//			HeaderInfo.upload2_url = "http://www.pieceoflena.com/ver2/bbs/" + HeaderInfo.upload2_url;			
//		}
		 // upload2 name
		HeaderInfo.upload2_name = cut_value2("upload2.gif",header,"-->", "filenum=2'>", "</td>").replaceAll("</a>", "");
		GV.Log_D(FILENAME, "parsing_header", "upload2:" + HeaderInfo.upload2_name );
		
		 // link1
		HeaderInfo.link1 = cut_value2("link1.gif",header,"-->", "target=_blank>", "</a>");
		GV.Log_D(FILENAME, "parsing_header", "link1:" + HeaderInfo.link1 );
		 		 
		 // link2
		HeaderInfo.link2 = cut_value2("link2.gif",header,"-->",  "target=_blank>", "</a>");
		GV.Log_D(FILENAME, "parsing_header", "link2:" + HeaderInfo.link2 );

		GV.Log_D(FILENAME, "parsing_header", "end......................................................." );
	}
	
	public WebContentsInfo getContentsHeader()
	{
	    return HeaderInfo;
	}
	
	// title에 있는 <font ...>   </font>   <b> </b>  tag를 제거한다. 
	String remove_tag_in_title(String title)
	{
		int start;
		
		title = title.replaceAll("\n", "");
		title = title.replaceAll("\t", "");
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
		
		return title;
	}
	public String parsing()
	{
		int body_start,body_end,header_start,header_end,comment_end;
		int idx=1,find_pos,backup_record_start,backup_record_end,urlinfo_end; 
		String urlinfo_block,tempStr;
		
		// find start & end point
		header_start = webpage.indexOf(header_start_str);
		header_end = body_start = webpage.indexOf(body_start_str);
		body_end = webpage.indexOf(body_end_str);
        comment_end = webpage.indexOf( comment_end_str );       

		GV.Log_D(FILENAME, "parsing-1", "cur_pos:" + cur_pos + ",start : " + header_start + ",end : " + header_end );
		
		// web header 
		contents_header = webpage.substring(header_start, header_end);
		parsing_contents_header( contents_header );
		
		GV.Log_D(FILENAME, "parsing-1-1", "body_start:" + body_start + ",body_end:" + body_end );

		// web body
		webbody = web_header + webpage.substring(body_start, body_end) + web_tail; 
//		webpage = "<html> <body> test # % <br><br><br><br><br><br><br><br>1</body> </html>";   //  , '\','?'
		
		GV.Log_D(FILENAME, "parsing-2", "replaceall" );

		webbody = webbody.replaceAll("%", "%25");
		// 첨부 이미지 처리
		webbody = webbody.replaceAll("src=data/new_free", "src=http://www.pieceoflena.com/ver2/bbs/data/new_free/");
		webbody = webbody.replaceAll("src=data/pds", "src=http://www.pieceoflena.com/ver2/bbs/data/pds/");
		webbody = webbody.replaceAll("src=data/lena", "src=http://www.pieceoflena.com/ver2/bbs/data/lena/");
	
		//webpage = webpage.replaceAll("skin/ruvin_gray_bo/", "src=http://www.pieceoflena.com/ver2/bbs/skin/ruvin_gray_bo/");
		webbody = webbody.replaceAll("href='download.php", "href='http://www.pieceoflena.com/ver2/bbs/download.php");
			
		// emoticon 처리
	    webbody = webbody.replaceAll("<img src = '../img/icon/", "<img src = 'http://www.pieceoflena.com/ver2/img/icon/");

	    //webbody = webbody.replaceAll("<img src=data/new_free/", "<img src=http://www.pieceoflena.com/ver2/bbs/data/new_free/");	    
	    	    
	    //-------------------------------------------------------------------------------------------------------
	    // url 정보 파싱
	    // 글쓰기, 리플,편집,삭제 있는 
		GV.Log_D(FILENAME, "parsing-3", "url 정보파싱" );
		
	    cur_pos = 0;
	    urlinfo_end = webpage.indexOf(url_end_str);
	    urlinfo_block = webpage.substring(body_end, urlinfo_end);
	    
	    HeaderInfo.url_new = "write.php" + cut_value(urlinfo_block, "write.php", "'>");
	    HeaderInfo.url_reply = "write.php" + cut_value(urlinfo_block, "write.php", "'>");
	    HeaderInfo.url_edit = cut_value(urlinfo_block, "write.php", "'>");
	    tempStr = cut_value(urlinfo_block, "delete.php", ">");
                
        if( HeaderInfo.url_edit.length() != 0 ) 
        {
            HeaderInfo.url_edit = "write.php" + HeaderInfo.url_edit;
        }
        
        if( tempStr.length() != 0 ) 
        {
            cur_pos = 0;            
            HeaderInfo.del_board_name = cut_value(tempStr, "id=", "&");
            HeaderInfo.del_contents_idx = cut_value(tempStr, "no=", "'");
            GV.Log_D(FILENAME, "parsing-1-3", "tempStr :" + tempStr );

            GV.Log_D(FILENAME, "parsing-1-3", "del_board_name :" + HeaderInfo.del_board_name + ",del_contents_idx=" + HeaderInfo.del_contents_idx );
        }
       
//        GV.Log_D(FILENAME, "parsing-1-1", "url_reply :" + HeaderInfo.url_reply );
//        GV.Log_D(FILENAME, "parsing-1-2", "url_edit :" + HeaderInfo.url_edit );

		//-------------------------------------------------------------------------------------------------------
		//Comment parsing을 진행한다.. 
		GV.CommentList.clear();
		GV.Log_D(FILENAME, "parsing-4", "Comment 정보파싱" );
		
		cur_pos = body_end;
		//GV.Log_D(FILENAME, "parsing-2", "end_pos:" + comment_end );
		
		String tempName = new String( cut_value(webpage, "cursor:hand>", "</span>"));
		while( cur_pos < comment_end )
		{
			WebContentsInfo comment = new WebContentsInfo();
			
			GV.Log_D(FILENAME, "parsing-4-1", "cur_pos:" + cur_pos );
			comment.no = Integer.toString(idx++);
			// name
			comment.name = tempName;
			backup_record_start = cur_pos;
			
			// date
			comment.date = cut_value(webpage, "<span title='", "'>");
			// title
			comment.title = cut_value(webpage, "class=fine>", "</td>").replaceAll("<br />", "");		
			
			// del_comment.php가 해당 사람 레코드 범위에 있는지 판단한다.
			find_pos = webpage.indexOf("href='del_comment.php", backup_record_start);			
						
			// 레코드에 존재한다면..
			if( find_pos < cur_pos )
			{
				backup_record_end = cur_pos; 
				cur_pos = find_pos;
				comment.comment_del_url =  cut_value(webpage, "href='", "'>");
				cur_pos = backup_record_end;				
			}	
			else
			{
				comment.comment_del_url = "";
			}
			
			GV.CommentList.add(comment);
			
			// find name 
			tempName =  cut_value(webpage, "cursor:hand>", "</span>");			
		}		
		
		//------------------------------------------------------------------------------------------
		// 댓글 달기 정보를 얻는다.. 
		GV.Log_D(FILENAME, "parsing-5", "Comment 달기 정보파싱" );

		cur_pos = webpage.indexOf("comment_ok.php");		
		//GV.Log_D(FILENAME, "parsing-3", "cur_pos:" + cur_pos );
		
		HashMap<String, String>  CommentFormWrite = new HashMap<String, String>();
		
		//GV.Log_D(FILENAME, "parsing-4", "cur_pos:" + cur_pos );
		
		CommentFormWrite.put("page",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("id",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("no",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("select_arrange",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("desc",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("page_num",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("keyword",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("category",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("sn",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("ss",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("sc",  cut_value(webpage, "value=", ">") );
		CommentFormWrite.put("mode",  cut_value(webpage, "value=", ">") );
		//GV.nickname =  cut_value(webpage, "textarea>", "</textarea>");		

		// Comment 작성 form 정보
		GV.CommentFormWrite = CommentFormWrite;
		
		GV.Log_D("ParserContents |", "parsing", "start view contents");
		

		return webbody;
	}
	
	
	
}
