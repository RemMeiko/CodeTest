package Study.Passwd;

import Study.Passwd.RSA;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class StrongPictureRSA {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("请选择你想要进行的操作:1-加密  2-解密 3-退出");
        Scanner in = new Scanner(System.in);
        boolean flagTrue = true;
        String[] num = new String[2];

        String begin = "E:\\1-暂定\\mytlab\\test1\\Picture\\";
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
                    // 加密
                    int j = 1;
                    System.out.print("请输入关键字:");
                    String key2 = in.next();
                    while(j < 3) {
                        int Num = j;
                        Decryption(begin+key2+Num+".xlsx",begin+key2+(Num+1)+".xlsx");
                        j++;
                    }
/*                    while( j < num.length) {
                        System.out.print("指定文件:");
                        num[j] = in.next();
                        j++;
                    }*/

                    // 解密
                    break;
                case 3:
                    flagTrue = false;
                    break;
            }
        }
    }


    // 加密
    public static void Encryption(String begin,String end) throws IOException, InterruptedException {
        // begin = "E:\\1-暂定\\mytlab\\G.xlsx";
        // end = "E:\\1-暂定\\mytlab\\G2.xlsx";
        int TestNum = 0;

        File file = new File(begin);
        InputStream input = new FileInputStream(file);

        // 1.读取需要加密的数据文件
        XSSFWorkbook targetWorkbook = new XSSFWorkbook(input);
        XSSFSheet targetSheet = targetWorkbook.getSheetAt(0);
        List<String> targetListString = new ArrayList<>();
        for(int i = 0;i < targetSheet.getPhysicalNumberOfRows();i++) {
            XSSFRow targetRow = targetSheet.getRow(i);
            targetListString.clear();
            // 获取每一格的数据存储进列表中
            for(int targetCell = 0;targetCell < targetRow.getPhysicalNumberOfCells();targetCell++) {
                targetListString.add(String.valueOf(targetRow.getCell(targetCell)));
            }
            // 将读取的字符串数据转换成整形数据
            List<Integer> targetListNum = new ArrayList<>();
            for (String s: targetListString) {
                // 转换后对数据进行加密然后存储
                targetListNum.add(RSA.jiami(Integer.parseInt(s.split("[.]")[0])));
            }

        // 2.将数据存储到另一个表格中,为写进Excel做准备
            // 先读取一遍获取已有数据-->方便追加数据
            FileInputStream inputStream = new FileInputStream(end);
            XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet intputSheet = inputWorkbook.getSheetAt(0);
            inputStream.close();
            Thread.sleep(10);
            // 用来进行输出
            FileOutputStream outputStream = new FileOutputStream(end);
            XSSFRow outputRow = intputSheet.createRow(intputSheet.getLastRowNum()+1);
            for(int j = 0; j < targetListNum.size();j++) {
                outputRow.createCell(j).setCellValue(targetListNum.get(j));
            }
            // 写如文件中
            outputStream.flush();
            inputWorkbook.write(outputStream);
            outputStream.close();
            TestNum++;
            System.out.println("第"+ TestNum+"行加载成功!");
        }
        input.close();
        System.out.println("加载成功!");
    }


    // 解密
    public static void Decryption(String end,String last) throws IOException {

        // end = "E:\\1-暂定\\mytlab\\G2.xlsx";
        // last = "E:\\1-暂定\\mytlab\\G3.xlsx";

        File file = new File(end);
        InputStream input = new FileInputStream(file);

        List<List<String>> list2 = readExcel(input,"");

/*        for(List a: list2) {
            System.out.println(a);
        }*/

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
/*
        for(List a: data) {
            System.out.println(a);
        }*/
    }

    /*
     * 读取Excel文件的内容
     * @param inputStream excel文件，以InputStream的形式传入
     * @param sheetName sheet名字
     * @return 以List返回excel中内容
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
     *
     * @param list         传入要写的内容，此处以一个List内容为例，先把要写的内容放到一个list中
     * @param outputStream 把输出流怼到要写入的Excel上，准备往里面写数据
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
     *
     * @param xssfCell 单元格
     * @return 字符串
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
