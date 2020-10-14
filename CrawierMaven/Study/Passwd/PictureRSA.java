package Study.Passwd;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class PictureRSA {
    public static void main(String[] args) throws IOException {
        System.out.println("请选择你想要进行的操作:1-加密  2-解密 3-退出");
        Scanner in = new Scanner(System.in);
        boolean flagTrue = true;
        // 定义RGB三个二维矩阵的数组
        String[] num = new String[]{"R","G","B"};

        String begin = "E:\\1-暂定\\mytlab\\test1\\其他\\";
        while(flagTrue) {
            System.out.print("请输入你的选择:");
            int flag = in.nextInt();
            switch (flag) {
                case 1:
                    // 加密
                    for(int i = 0;i < num.length;i++) {
                        Encryption(begin+num[i]+"1.xlsx",begin+num[i]+"4.xlsx");
                    }
                    break;
                case 2:
                    // 解密
                    for(int i = 0;i < num.length;i++) {
                        Decryption(begin+num[i]+"4.xlsx",begin+num[i]+"3.xlsx");
                    }
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

        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0;i<list2.size();i++) {
            List <Integer> row = new ArrayList<>();
            for(int j = 0;j < list2.get(i).size() ; j++) {
                // 获取数据并进行处理
                int math = Integer.parseInt(list2.get(i).get(j).split("[.]")[0]);
                // 加密
                row.add(RSA.jiami(math));
            }
            data.add(row);
        }

        File file1 = new File(end);
        OutputStream outputStream = new FileOutputStream(file1);
        writeExcel(data,outputStream);

        outputStream.close();
        System.out.println("加密后的第一个数据结果:"+data.get(1).get(1)+"加载成功!");
    }


    // 解密
    public static void Decryption(String end,String last) throws IOException {
        File file = new File(end);
        InputStream input = new FileInputStream(file);

        List<List<String>> list2 = readExcel(input,"");

        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0;i<list2.size();i++) {
            List <Integer> row = new ArrayList<>();
            for(int j = 0;j < list2.get(i).size() ; j++) {
                // 获取数据并进行处理
                int math = Integer.parseInt(list2.get(i).get(j).split("[.]")[0]);
                // 解密
                row.add(RSA.jiemi(math));
            }
            data.add(row);
        }

        File file1 = new File(last);
        OutputStream outputStream = new FileOutputStream(file1);
        writeExcel(data,outputStream);
        outputStream.close();
        input.close();

        System.out.println("解密后的数据的第一个结果:"+data.get(1).get(1)+"加载成功!");
    }

    // 从Excel中读取数据
    public static List<List<String>> readExcel(InputStream inputStream, String sheetName) {
        //定义工作簿
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            System.out.println("Excel中没有数据!");
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

    // 将数据写进Excel中
    public static void writeExcel(List<List<Integer>> list, OutputStream outputStream) {
        // 创建工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");
        //把List里面的数据写到excel中
        for (int i = 0; i < list.size(); i++) {
            //从第一行开始写入
            XSSFRow xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，并将数据存储进去
            List sub_list = list.get(i);
            for (int j = 0; j < sub_list.size(); j++) {
                XSSFCell xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((Integer) sub_list.get(j)); //设置单元格内容
            }
        }
        //用输出流写到excel
        try {
            xssfWorkbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 把单元格中的数据转换为字符串,并处理单元格中可能出现的其他数据情况
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
