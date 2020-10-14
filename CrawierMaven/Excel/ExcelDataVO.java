package Excel;

/**
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 11:33
 * Description: 读取Excel时，封装读取的每一行的数据
 */
public class ExcelDataVO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private String id;

    /**
     * 性别
     */
    private String hex;

    /**
     * 班级
     */
    private String banji;


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getHex() {
        return hex;
    }

    public String getBanji() {
        return banji;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }
}
