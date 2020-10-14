package Excel;

import java.util.List;

/**
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 10:13
 * Description: 示例程序入口类
 */ //
public class Excel {
    public static void main(String[] args) {
        // 设定Excel文件所在路径
        String excelFileName = "E:\\资料\\大二(下)\\实训\\单片机\\18物联1-2班花名册.xlsx";
        // 读取Excel文件内容
        List<ExcelDataVO> readResult = ExcelReader.readExcel(excelFileName);

        // todo 进行业务操作
    }

}