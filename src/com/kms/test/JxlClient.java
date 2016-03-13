package com.kms.test;
import com.kms.core.io.JxlUtil;
import jxl.write.WritableWorkbook;

public class JxlClient { 
    private static final JxlUtil util = new JxlUtil(); 
    public static void main(String[] args) { 
        if(args.length == 0){ 
            args = new String[2]; 
            args[0] = "default.xls"; 
            args[1] = "defaultSheet"; 
        } 
        if(args.length == 1){ 
            String[] tempArgs = new String[2]; 
            tempArgs[0] = args[0]; 
            tempArgs[1] = "defaultSheet"; 
            args = tempArgs; 
        } 
        //  defaultSheet 생성 및 데이타 입력 .. 
        WritableWorkbook workBook = util.createWorkBook(args[0], args[1]); 
//        util.mergeCells(workBook.getSheet(args[1]), 0, 1, 4, 1); 
        //WritableCellFormat centerAlignedAllBoldformattedCell = util.createFormattedCell(10, null, true, false, null, null, null, Alignment.CENTRE); 
        //WritableCellFormat bottomBoldFormatedCell = util.createFormattedCell(6, null, false, false, null, Border.BOTTOM,null); 
        //Header 
        //util.addCellToSheet(0, 1, "Header", null, workBook.getSheet(args[1])); 
        //Column 1,2,3,4,5 
        util.addCellToSheet(0, 0, "Subtitle1", null, workBook.getSheet(args[1])); 
        util.addCellToSheet(1, 0, "Subtitle2", null, workBook.getSheet(args[1])); 
        util.addCellToSheet(2, 0, "Subtitle3", null, workBook.getSheet(args[1])); 
        util.addCellToSheet(3, 0, "Subtitle4", null, workBook.getSheet(args[1])); 
        util.addCellToSheet(4, 0, "Subtitle5", null, workBook.getSheet(args[1]));
        ////////////////
        
        // test sheet 생성 및 데이타 입력 .. 
        util.createSheet("test", workBook);
//        util.mergeCells(workBook.getSheet("test"), 0, 1, 4, 1); 
//        WritableCellFormat centerAlignedAllBoldformattedCell2 = util.createFormattedCell(10, null, true, false, null, null, null, Alignment.CENTRE); 
//        WritableCellFormat bottomBoldFormatedCell2 = util.createFormattedCell(6, null, false, false, null, Border.BOTTOM,null); 
        //Header 
        //util.addCellToSheet(0, 1, "ㅋㅋㅋ", null, workBook.getSheet("test")); 
        //Column 1,2,3,4,5 
        util.addCellToSheet(0, 0, "1234567", null, workBook.getSheet("test")); 
        util.addCellToSheet(1, 0, "abcdefg", null, workBook.getSheet("test")); 
        util.addCellToSheet(2, 0, "zzz", null, workBook.getSheet("test")); 
        util.addCellToSheet(3, 0, "한글", null, workBook.getSheet("test")); 
        util.addCellToSheet(4, 0, "Subtitle5", null, workBook.getSheet("test"));
                
        util.flush(workBook);
        
        System.out.print("Done!!!");
        
    }
}
