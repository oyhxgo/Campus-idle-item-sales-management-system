package dao;


import entity.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProSQLInfo_ZL {  // 对购买商品进行数据库存储
//    private int userId;

//    public BorrowInfo(int userId){
//        this.userId=userId;
//    }

    public ArrayList<Product> getProList(){
        //ArrayList<Book> books=new ArrayList<>();
        ArrayList<Product>products=new ArrayList<>();
        //1.getConnection()方法，连接MySQL数据库！！
        try(Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8",
                "root","8561826")){
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement=con.createStatement();
            statement.execute("begin");
            //要执行的SQL语句
            String sql= "select productid,productname,merchantname,price,tag,count from product ";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()) {
                if (rs.getString("tag").equals("资料")) {
                    //Book book = new Book();
                    Product p = new Product();
                    p.setProductid(rs.getInt("productid"));
                    p.setProductname(rs.getString("productname"));
                    p.setMerchantname(rs.getString("merchantname"));
                    p.setPrice(rs.getFloat("price"));
                    p.setTag(rs.getString("tag"));
                    p.setCount(rs.getInt("count"));
                    //books.add(book);
                    products.add(p);
                }
            }
            statement.execute("commit");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        return products;
    }
}
