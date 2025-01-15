package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 普通用户登陆后显示的主界面
 * 包含功能入口：用户借阅信息 借阅图书  查询图书  登出
 * 按钮触发事件：
 * borrowbookActionPeformed
 * logoutButtonActionPerformed
 * searchBookButtonActionPerformed checkBookActionPerformed
 */
public class UserUI_2 extends JFrame {
    private JPanel ZLInfo,OrderList,searchXZ,logout;
    int userid;
    private JButton ZLInfoButton,OrderListButton,searchXZButton,logoutButton;
    public  UserUI_2(int userid){
        this.setTitle("用户界面");
        this.setLayout(new GridLayout(4,1));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.userid=userid;
        ZLInfo = new JPanel();
        OrderList = new JPanel();
        searchXZ = new JPanel();
        logout = new JPanel();
        prepareUI();
    }

    public void showUI(){
        ZLInfo.add(ZLInfoButton);
        OrderList.add(OrderListButton);
        searchXZ.add(searchXZButton);
        logout.add(logoutButton);
        this.add(ZLInfo);
        this.add(OrderList);
        this.add(searchXZ);
        this.add(logout);
        this.setBounds(400,200,420, 420); /** 设置窗体大小 */
        this.setResizable(false); /** 不可放大 */
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void prepareUI(){
        ZLInfoButton=new JButton("流通资料");
        ZLInfoButton.setFont(new Font("黑体",0,20));

        OrderListButton=new JButton("订单记录");
        OrderListButton.setFont(new Font("黑体",0,20));

        searchXZButton=new JButton("查询资料");
        searchXZButton.setFont(new Font("黑体",0,20));

        logoutButton=new JButton("退出");
        logoutButton.setFont(new Font("黑体",0,20));

        //借阅记录
        ZLInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                borrowBookActionPeformed(e);
            }
        });
        //借阅
        OrderListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                checkBookActionPerformed(e);
            }
        });
        //查询
        searchXZButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                searchActionPerformed(e);
            }
        });
        //退出
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                logoutButtonActionPerformed(e);
            }
        });
    }
    //借阅记录
    public void borrowBookActionPeformed (ActionEvent e){
        //BuyXZInfoUI b=new BuyXZInfoUI(this.userid);
        BuyZLInfoui b=new BuyZLInfoui(this.userid);
        b.showUI();
    }
    //借阅图书
    public void  checkBookActionPerformed(ActionEvent e){
        OrderInfoUI b=new OrderInfoUI();
        b.showUI();
    }
    //查找图书
    public void  searchActionPerformed(ActionEvent e){
        SearchUI s=new SearchUI();
        s.showUI();
    }
    //退出
    public void  logoutButtonActionPerformed(ActionEvent e){
        System.exit(0);
    }

}
