package entity;

public class User extends NormalUser {


    public User(int userId, String userName, String role) {
        super(userId, userName, role);//继承父类构造方法
    }
}
