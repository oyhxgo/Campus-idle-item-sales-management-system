package dao;


import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchPro {
    private String searchInfo, searchType;//查询信息，查询类型
    private HashMap<String, String> type = new HashMap<String, String>() {
        {
            put("商品ID", "productid");
            put("商品名称", "productname");
            put("商家", "merchantname");
            put("价格", "price");
            put("库存", "count");
        }
    };
    public  SearchPro(String searchInfo, String searchType) {
        this.searchInfo = searchInfo;
        this.searchType = searchType;
    }

    public ArrayList<Product> getBook() {
        ArrayList<Product> pros = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8","root","8561826")) {
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            statement.execute("begin;");
            //要执行的SQL语句
            String sql = "select * from product where " + type.get(this.searchType) +"=" + "\"" + this.searchInfo+"\"";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
             while(rs.next()) {
                 Product book = new Product();
                 book.setProductid(rs.getInt("productid"));
                 book.setProductname(rs.getString("productname"));
                 book.setMerchantname(rs.getString("merchantname"));
                 book.setPrice(rs.getFloat("price"));
                 book.setCount(rs.getInt("count"));
                 pros.add(book);
             }
            statement.execute("commit ");
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
                return pros;
    }
}
