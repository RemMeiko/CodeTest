package Study.Passwd;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class EasyPictureRSA {
    public static void main(String[] args) throws IOException {
        System.out.println("请选择你想要进行的操作:1-加密  2-解密 3-退出");
        Scanner in = new Scanner(System.in);
        boolean flagTrue = true;
        String[] num = new String[2];

        String begin = "E:\\1-暂定\\mytlab\\test1\\";
        while(flagTrue) {
            System.out.print("请输入你的选择:");
            int flag = in.nextInt();
            switch (flag) {
                case 1:
                    // 加密
                    int i = 1;
                    System.out.print("请输入关键字:");
                    String key = in.next();
                    while(i < 3) {
                        int Num = i;
                        Encryption(begin+key+Num+".xlsx",begin+key+(Num+1)+".xlsx");
                        i++;
                    }
                    break;
                case 2:
                    // 解密
                    int j = 1;
                    System.out.print("请输入关键字:");
                    String key2 = in.next();
                    while(j < 3) {
                        int Num = j;
                        Decryption(begin+key2+Num+".xlsx",begin+key2+(Num+1)+".xlsx");
                        j++;
                    }
                    // 退出
                    break;
                case 3:
                    flagTrue = false;
                    break;
            }
        }
    }

    // 加密
    public static void Encryption(String begin,String end) throws IOException {
        File file = new File(begin);
        InputStream input = new FileInputStream(file);

        //测试读取数据
        List<List<String>> list2 = readExcel(input,"");
        RSA rsa = new RSA();

        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0;i<list2.size();i++) {
            List <Integer> row = new ArrayList<>();
            for(int j = 0;j < list2.get(i).size() ; j++) {
                String str = list2.get(i).get(j);
                String[] str1 = str.split("[.]");
                //System.out.println(str);
                int math = Integer.parseInt(str1[0]);
                int jiamiDate = RSA.jiami(math);
                String strjia = String.valueOf(jiamiDate);
                row.add(jiamiDate);
            }
            data.add(row);
        }

        File file1 = new File(end);
        OutputStream outputStream = new FileOutputStream(file1);
        writeExcel(data,outputStream);

        outputStream.close();
        System.out.println("加密后的第一个数据结果:"+data.get(1).get(1));
        System.out.println("加载成功!");
    }


    // 解密
    public static void Decryption(String end,String last) throws IOException {

        File file = new File(end);
        InputStream input = new FileInputStream(file);

        List<List<String>> list2 = readExcel(input,"");
        RSA rsa = new RSA();

        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0;i<list2.size();i++) {
            List <Integer> row = new ArrayList<>();
            for(int j = 0;j < list2.get(i).size() ; j++) {
                String str = list2.get(i).get(j);
                String[] str1 = str.split("[.]");
                //System.out.println(str);
                int math = Integer.parseInt(str1[0]);
                int jiamiDate = RSA.jiemi(math);
                String strjia = String.valueOf(jiamiDate);
                row.add(jiamiDate);
            }
            data.add(row);
        }

        File file1 = new File(last);
        OutputStream outputStream = new FileOutputStream(file1);
        writeExcel(data,outputStream);
        outputStream.close();
        input.close();

        System.out.println("解密后的数据的第一个结果"+data.get(1).get(1));
        System.out.println("加载成功!");

    }

    /*
     * 读取Excel文件的内容
     */
    public static List<List<String>> readExcel(InputStream inputStream, String sheetName) {
        //定义工作簿
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            System.out.println("Excel data file cannot be found!");
        }

        //定义工作表
        XSSFSheet xssfSheet;
        if (sheetName.equals("")) {
            // 默认取第一个子表
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }

        List<List<String>> list = new ArrayList<>();

        //定义行
        //默认第一行为标题行，index = 0
        //XSSFRow titleRow = xssfSheet.getRow(0);

        //循环取每行的数据
        for (int rowIndex = 0; rowIndex <= xssfSheet.getPhysicalNumberOfRows(); rowIndex++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
            if (xssfRow == null) {
                continue;
            }

            List<String> map = new ArrayList<>();
            //循环取每个单元格(cell)的数据
            for (int cellIndex = 0; cellIndex < xssfRow.getPhysicalNumberOfCells(); cellIndex++) {
                //XSSFCell titleCell = titleRow.getCell(cellIndex);
                XSSFCell xssfCell = xssfRow.getCell(cellIndex);
                map.add(getString(xssfCell));
                // map.put(getString(titleCell), getString(xssfCell));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 把内容写入Excel
     */
    public static void writeExcel(List<List<Integer>> list, OutputStream outputStream) {
        //创建工作簿
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet("表格一");

        //创建行
        XSSFRow xssfRow;

        //创建列，即单元格Cell
        XSSFCell xssfCell;

        //把List里面的数据写到excel中
        for (int i = 0; i < list.size(); i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            List sub_list = list.get(i);
            for (int j = 0; j < sub_list.size(); j++) {
                xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((Integer) sub_list.get(j)); //设置单元格内容
            }
        }
        //用输出流写到excel
        try {
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 把单元格的内容转为字符串
     */
    public static String getString(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        if (xssfCell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else if (xssfCell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if(xssfCell.getCellType() == CellType.BLANK){
            return "这里么有";
        }else{
            return xssfCell.getStringCellValue();
        }
    }

}
