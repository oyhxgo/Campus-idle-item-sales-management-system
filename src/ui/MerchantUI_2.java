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
public class MerchantUI_2 extends JFrame {       // 出资料的商家UI
    private JPanel sellrest,OrderList,searchrest,logout;
    public String merchantname;
    private JButton sellrestButton,OrderListButton,searchrestButton,logoutButton;
    public  MerchantUI_2(String role){
        this.setTitle("资料商家");
        this.setLayout(new GridLayout(4,1));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        merchantname=role;
        sellrest = new JPanel();
        OrderList = new JPanel();
        searchrest = new JPanel();
        logout = new JPanel();
        prepareUI();
    }

    public void showUI(){
        sellrest.add(sellrestButton);
        OrderList.add(OrderListButton);
        searchrest.add(searchrestButton);
        logout.add(logoutButton);

        this.add(sellrest);
        this.add(searchrest);
        this.add(OrderList);
        this.add(logout);
        this.setBounds(400,200,420, 420); /** 设置窗体大小 */
        this.setResizable(false); /** 不可放大 */
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void prepareUI(){
        sellrestButton=new JButton("卖资料");
        sellrestButton.setFont(new Font("黑体",0,24));

        OrderListButton=new JButton("订单记录");
        OrderListButton.setFont(new Font("黑体",0,24));

        searchrestButton=new JButton("查找资料");
        searchrestButton.setFont(new Font("黑体",0,24));

        logoutButton=new JButton("退出");
        logoutButton.setFont(new Font("黑体",0,22));

        //借阅记录
        sellrestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sellrestActionPeformed(e);
            }
        });
        //借阅
        OrderListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrderListActionPerformed(e);
            }
        });
        //查询
        searchrestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                searchrestActionPerformed(e);
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
    public void sellrestActionPeformed (ActionEvent e){   // 卖资料
        System.out.println("卖资料");
        sellUI_z_UI s1=new sellUI_z_UI();
//        BorrowInfoUI b=new BorrowInfoUI();
//        b. showUI();
    }
    //借阅图书
    public void  OrderListActionPerformed(ActionEvent e){     //  订单记录
        System.out.println("正在查询中...");
        OrderListUI_M m1=new OrderListUI_M(merchantname);
//        BorrowUI b=new BorrowUI();
//        b.showUI();
    }
    //查找图书
    public void  searchrestActionPerformed(ActionEvent e){     // 查询闲置
        System.out.println("查询资料");
        SearchUI s=new SearchUI();
        s.showUI();
//        SearchUI s=new SearchUI();
//        s.showUI();
    }
    //退出
    public void  logoutButtonActionPerformed(ActionEvent e){
        System.out.println("您已退出!");
        System.exit(0);
    }

}
