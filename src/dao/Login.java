package dao;

import java.sql.*;
import java.util.HashMap;

public class Login {

        private static int userId;
        private String userName;
        private String userPassword;
        private static  String role;
        public boolean in = false;

        //获取用户输入的用户名和密码
    public Login(String userName, String userPassword, String role) {
            this.userName = userName;
            this.userPassword = userPassword;
            this.role = role;
        }

        //登陆验证
        //驱动程序名
        //String driver = "com.mysql.jc.jdbc.Driver";
        //URL指向要访问的数据库名booksystem
        //遍历查询结果集
        //加载驱动程序
        //1.getConnection()方法，连接MySQL数据库！！
        public int validate () {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8",
                    "root","8561826")) {
                //2.创建statement类对象，用来执行SQL语句！！
                Statement statement = con.createStatement();
                statement.execute("begin;");
                //要执行的SQL语句
                String sql = "select userpassword from "+ role +" where username=\"" + userName + "\"";
                //3.ResultSet类，用来存放获取的结果集！！
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    String truePass = rs.getString("userpassword");//数据库里的userpassword
                    if (this.userPassword.equals(truePass)) {
                        in = true;
                    }
                }
                sql = "select userid from "+ role +" where username=\"" + userName + "\"";
                rs = statement.executeQuery(sql);
                while (rs.next()) {
                    int userid = rs.getInt("userid");//数据库里的userid
                    this.userId = userid;
                }
                statement.execute("commit;");
            } catch (SQLException e1) {
                //数据库连接失败异常处理
                e1.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return this.userId;
        }
    }



