package ui;

import dao.Login;
import entity.Admin;
import entity.Merchant;
import entity.NormalUser;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;


/*
*登陆界面
 */
//JFrame：一个窗体
public class LoginUI extends JFrame {
    private JPanel MainP;
    private JPanel name, password, type, button, title;//JPanel：面板
    private JLabel welcome, userNameLabel, userPasswordLabel, welcomehanzi_a, welcomehanzi_b;//标签
    private JTextField userName;//文本框
    private JPasswordField userPassword;//密码框
    private JComboBox<String> userType;
    /**
     * 用户类型下拉框
     */

    private JButton loginButton, registerButton;//登录按钮
    static NormalUser user;
    private HashMap<String, String> logintype = new HashMap<String, String>() {
        {
            put("我是买家", "user");

            put("我是卖家", "merchant");

            put("管理员", "admin");
        }
    };

    /**
     * 初始化login frame的属性 初始化容器类
     */
    public LoginUI() {
        this.setTitle("登录");
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBounds(500, 300, 500, 350); /** 设置窗体大小 */
        this.setResizable(true); /** 不可放大 */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用 System exit 方法退出应用程序。仅在应用程序中使用
        name = new JPanel();
        password = new JPanel();
        button = new JPanel(new GridLayout(1, 2));
        title = new JPanel();
//        title.setSize(500,100);
        type = new JPanel();
        registerButton = new JButton();
        MainP = new JPanel(new GridLayout(4, 1, 30, 20));

//        title.setBackground(Color.gray);
//        MainP.add(name);
//        MainP.add(password);
//        MainP.add(button);
//        MainP.add(type);
        prepareUI();
    }

    /**
     * 将组件添加到对应的面板容器中
     */
    public void showUI() {
        name.add(userNameLabel);
        name.add(userName);

        password.add(userPasswordLabel);
        password.add(userPassword);

        button.add(loginButton);
//            button.add(registerButton);
        type.add(userType);

        title.add(welcome);
        title.add(welcomehanzi_a);
        title.add(welcomehanzi_b);
        MainP.add(name);
        MainP.add(password);
        MainP.add(type);
        MainP.add(button);
        this.add(title);
        this.add(MainP);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * 组件初始化
     * 按钮触发事件 : loginButtonActionPerformed
     */
    private void prepareUI() {
        welcome = new JLabel("Welcome to", JLabel.CENTER);
        welcomehanzi_a = new JLabel(" 京西！         ");
        welcomehanzi_b = new JLabel("出闲置就上京西!");
        welcome.setFont((new Font("Impact", Font.BOLD, 25)));
        welcomehanzi_a.setFont(new Font("微软雅黑", Font.BOLD, 28));
//            welcomehanzi_a.setForeground(Co);
        welcomehanzi_b.setFont(new Font("微软雅黑", Font.BOLD, 25));
        welcome.setForeground(Color.pink);
        welcomehanzi_a.setForeground(Color.green);
        welcomehanzi_b.setForeground(Color.ORANGE);
        userNameLabel = new JLabel();

        userNameLabel.setFont((new Font("微软雅黑", Font.BOLD, 20)));
        userNameLabel.setText("用户名:");
        userNameLabel.setForeground(Color.black);
        userName =
                new JTextField(20);
        userName.setFont(new Font("微软雅黑", Font.BOLD, 20));

        userPasswordLabel = new JLabel();

        userPasswordLabel.setText("密码:");
        userPasswordLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
        userPassword = new JPasswordField(35);

        Dimension size = new Dimension(200, 30); // 设置你想要的宽度和高度
        userName.setPreferredSize(size);
        userPassword.setPreferredSize(size);
//            userPassword.setFont((new Font("黑体",0)));
        userType = new JComboBox<String>(new String[]{"我是买家", "我是卖家", "管理员"});
        userType.setSize(85, 35);
        userType.setFont(new Font("微软雅黑", Font.BOLD, 20));

        loginButton = new JButton("登录");
        loginButton.setFont(new Font("黑体", Font.BOLD, 25));
        registerButton = new JButton("注册");
        registerButton.setFont(new Font("黑体", Font.BOLD, 25));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginButtonActionPerformed(e);
            }
        });
    }

    private void loginButtonActionPerformed(ActionEvent e) {
        //获取用户输入的用户名和密码以及角色
        String username = userName.getText();
        String password = new String(userPassword.getPassword()); // 使用 getPassword() 获取密码
        String role = logintype.get((String) userType.getSelectedItem());
        Login v = new Login(username, password, role);
        int userId = v.validate(); //验证,调用Login中的validate（）方法
        if (v.in) {
            if (role.equals("user")) { // 使用 equals() 比较字符串
                this.user = new User(userId, "user", username);  //  这里接入UI 进行选择 闲置还是 资料
                JumpUI_U j=new JumpUI_U(userId);
                j.showUI();
//                UserUI mainUi = new UserUI(userId);
//                mainUi.showUI();
//                this.setVisible(false);
            } else if (role.equals("admin")) {
                this.user = new Admin(userId, "admin", username); //  这里接入UI 进行选择 闲置还是 资料
                JumpUI_A j=new JumpUI_A();
                j.showUI();
//                AdminUI mainUi = new AdminUI();
//                mainUi.showUI();
//                this.setVisible(false);
            } else if (role.equals("merchant")) {
                try (Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8",
                        "root", "8561826")) {
                    // 创建 statement 对象来执行 SQL
                    Statement statement = con.createStatement();
                    statement.execute("BEGIN;");

                    // 使用 userId 查询商家信息
                    String sql = "SELECT username, type FROM merchant WHERE userid = " + userId; // 修改此行
                    ResultSet rs = statement.executeQuery(sql);

                    // 检查商家类型
                    if (rs.next()) {
                        String merchantUsername = rs.getString("username"); // 获取商家用户名
                        int merchantType = rs.getInt("type"); // 获取商家类型（0 或 1）
                        System.out.println(merchantUsername);
                        JumpUI_M j=new JumpUI_M(merchantUsername);
                        j.showUI();

                    } else {
                        System.out.println("未找到商家信息。");
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace(); // 处理 SQL 异常
                }
            } else {
                //登录失败
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "用户名或密码错误！");
        }
    }
}
