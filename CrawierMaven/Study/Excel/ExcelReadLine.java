package Study.Excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReadLine {
    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream("E:\\1-暂定\\测试\\1.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;

        XSSFRow xssfRowFirst = sheet.getRow(0);
        List<String> list = new ArrayList<String>();
        for(int cellIndex = 0;cellIndex < xssfRowFirst.getPhysicalNumberOfCells();cellIndex++) {
            list.add(String.valueOf(xssfRowFirst.getCell(cellIndex)));
        }

        List<Integer> list1 = new ArrayList<>();
        for (String s: list) {
            list1.add(Integer.parseInt(s.split("[.]")[0]));
        }

        FileInputStream in2 = new FileInputStream("E:\\1-暂定\\测试\\2.xlsx");
        XSSFWorkbook workbookOut = new XSSFWorkbook(in2);
        XSSFSheet sheetOut = workbookOut.getSheetAt(0);
        XSSFRow row1;


        FileOutputStream out = new FileOutputStream("E:\\1-暂定\\测试\\2.xlsx");

        for(int k = 0;k < 1000;k++) {
            row1 = sheetOut.createRow(sheetOut.getLastRowNum()+1);
            for(int i = 0;i < list.size();i++) {
                row1.createCell(i).setCellValue((Integer) list1.get(i));
            }
        }

        try {
            out.flush();
            workbookOut.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
