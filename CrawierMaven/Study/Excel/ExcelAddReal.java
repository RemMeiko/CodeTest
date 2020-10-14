package Study.Excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelAddReal {
    /**
     * 向Excel中追加内容
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{

        List<Student> stuList2 = new ArrayList<Student>();
        stuList2.add(new Student(15,"小明","深圳南山"));
        stuList2.add(new Student(16,"小王","深圳宝安"));
        stuList2.add(new Student(17,"小张","深圳罗湖"));

        FileInputStream in = new FileInputStream("E:\\1-暂定\\1.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;

        FileOutputStream out = new FileOutputStream("E:\\1-暂定\\1.xlsx");
        //从第二行开始追加列
        /*row=sheet.getRow(1);
        row.createCell(3).setCellValue("AAA");
        row.createCell(4).setCellValue("BBB");*/

        //追加列数据
        for(int i=0;i<stuList2.size();i++){
            Student student = stuList2.get(i);
            row = sheet.getRow(i+1);
            row.createCell(3).setCellValue(student.getAge());
            row.createCell(4).setCellValue(student.getName());
            row.createCell(5).setCellValue(student.getAddress());
        }

        /*//追加行数据
        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据
        row.createCell(0).setCellValue("测试数据"); //设置第一个（从0开始）单元格的数据
        row.createCell(1).setCellValue("haha"); //设置第二个（从0开始）单元格的数据*/

        try {
            out.flush();
            workbook.write(out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}