//package dao;
//
//import entity.Book;
//
//import java.sql.*;
//
//public class DeleteBook {
//    public boolean s=false;//1
//    private Book book;
//
//    public DeleteBook(Book book){
//        this.book=book;
//        execute();
//    }
//
//    private void execute(){
//        try(Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8","root","8561826")){
//            //创建statement类对象
//            Statement statement = con.createStatement();
//            statement.execute("begin;");
//            // 确认图书未被借阅
//            ResultSet rs = statement.executeQuery("select bookstatus from book1 where bookid=" + this.book.getBookId());
//            if(rs.next() && rs.getInt("bookstatus") == 0){
//                statement.executeUpdate("delete from book1 where bookid=" + this.book.getBookId());
//                s = true;
//            }
//            statement.execute("commit");
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }catch(Exception e2){
//            e2.printStackTrace();
//        }
//
//
//    }
//
//}
