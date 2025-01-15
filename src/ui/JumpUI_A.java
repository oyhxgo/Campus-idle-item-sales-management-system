package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JumpUI_A extends JFrame {
    private JPanel selectionPanel; // 新增选择面板
    private JButton userManagementButton, orderManagementButton,logoutButton; // 按钮选择用户管理或订单管理

    public JumpUI_A() {
        this.setTitle("管理员界面选择");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        selectionPanel = new JPanel(); // 初始化选择面板
        prepareUI(); // 调用UI准备方法
    }

    public void showUI() {
        this.add(selectionPanel, BorderLayout.CENTER);
        this.setBounds(400, 200, 320, 250); // 设置窗口大小
        this.setResizable(false); // 不可调整大小
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void prepareUI() {
        selectionPanel.setLayout(new GridLayout(3, 1,10,20));
        // 创建选择按钮
        userManagementButton = new JButton("管理闲置");
        orderManagementButton = new JButton("管理资料");
        logoutButton = new JButton("退出");
        selectionPanel.add(userManagementButton);
        selectionPanel.add(orderManagementButton);
        Font buttonFont = new Font("黑体", Font.PLAIN, 20); // 字体样式和大小
        userManagementButton.setFont(buttonFont);
        orderManagementButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);
        userManagementButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 减小边距
        orderManagementButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 添加按钮的事件监听器
        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 跳转到用户管理界面
                showUserManagement();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        orderManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到订单管理界面
                showOrderManagement();
            }
        });

        // 将按钮添加到选择面板
        selectionPanel.add(userManagementButton);
        selectionPanel.add(orderManagementButton);
        selectionPanel.add(logoutButton);
    }

    private void showUserManagement() {
        // 这里可以实现用户管理的功能界面
        JOptionPane.showMessageDialog(this, "跳转到用户管理功能界面");
        //this.setVisible(false);
        AdminUI userManagementUI = new AdminUI(); // 创建用户管理UI
        userManagementUI.showUI();
    }

    private void showOrderManagement() {
        // 这里可以实现订单管理的功能界面
        JOptionPane.showMessageDialog(this, "跳转到订单管理功能界面");
        //this.setVisible(false);
        Admin_2_UI orderManagementUI = new Admin_2_UI(); // 创建订单管理UI
        orderManagementUI.showUI();
    }
}