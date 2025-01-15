package dao;

import entity.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddPro {
    private Product p1;
    public boolean s = false;

    public AddPro(Product p1) {
        this.p1 = p1;
        execute();
    }

    public void execute() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8",
                "root", "8561826")) {
            // 创建statement类对象
            Statement statement = con.createStatement();
            statement.execute("begin");

            // 修改SQL语句，确保所有字段都有值
            String sql = "INSERT INTO product (productname, merchantname, price, tag, count) " +
                    "VALUES (\"" + p1.getProductname() + "\", \"" +
                    p1.getMerchantname() + "\", " + p1.getPrice() + ", \"" +
                    p1.getTag() + "\", " + p1.getCount() + ")";
            // 执行更新并检查结果
            if (statement.executeUpdate(sql) == 1) {
                s = true;
            }

            statement.execute("commit");
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}