package Mysql;

import java.sql.*;

public class contect {

    public static void main(String[] args) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.cj.jdbc.Driver";
        //URL指向要访问的数据库名test
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "root";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //要执行的SQL语句
            String sql = "select * from myclass";

            /*//插入数据
            String sqladd = "insert into MyClass values(4,'ali',1,11.2)";
            int s = statement.executeUpdate(sqladd);
            System.out.println(s);

            //删除数据
            String delete = "delete from MyClass where id=3";
            int de = statement.executeUpdate(delete);
            System.out.println(de);

            //修改数据
            String update = "update MyClass set name='Marry' where id=2";
            int up = statement.executeUpdate(update);
            System.out.println(up);*/

            //查询
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("执行结果如下所示:");
            System.out.println("-----------------");
            System.out.println("id" + "\t" + "姓名");
            System.out.println("-----------------");
            String id;
            String name;
            while(rs.next()){
                //获取id这列数据
                id = rs.getString("id");
                //获取name这列数据
                name = rs.getString("name");

                //输出结果
                System.out.println(id + "\t" + name);
            }
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch(Exception e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }// TODO: handle exception
        finally{
            System.out.println("数据库数据成功获取！！");
        }
    }

}