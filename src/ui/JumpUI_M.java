package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JumpUI_M extends JFrame {
    private JPanel selectionPanel; // 新增选择面板
    private JButton sellIdleButton, sellDataButton,logoutButton; // 选择卖闲置或卖资料的按钮
    String Merchantname;

    public JumpUI_M(String role) {
        this.setTitle("商家界面");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.Merchantname = role;

        selectionPanel = new JPanel(); // 初始化选择面板
        prepareUI();
    }

    public void showUI() {
        this.add(selectionPanel, BorderLayout.CENTER);
        this.setBounds(400, 200, 320, 250); // 设置窗口大小
        this.setResizable(false); // 不可调整大小
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void prepareUI() {
        // 使用 GridLayout 设置面板为 2 行 1 列
        selectionPanel.setLayout(new GridLayout(3, 1,10,20));

        // 创建选择按钮
        sellIdleButton = new JButton("卖出闲置");
        sellDataButton = new JButton("卖出资料");
        logoutButton = new JButton("退出");

        // 设置按钮字体
        Font buttonFont = new Font("黑体", Font.PLAIN, 20); // 字体样式和大小
        sellIdleButton.setFont(buttonFont);
        sellDataButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);
        // 设置按钮边框以增加间隔
        sellIdleButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 减小边距
        sellDataButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // 添加按钮的事件监听器
        sellIdleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到闲置相关卖出界面
                showSellIdleOptions();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到闲置相关卖出界面
                System.exit(0);
            }
        });
        sellDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到资料相关卖出界面
                showSellDataOptions();
            }
        });

        // 将按钮添加到选择面板
        selectionPanel.add(sellIdleButton);
        selectionPanel.add(sellDataButton);
        selectionPanel.add(logoutButton);
    }

    private void showSellIdleOptions() {
        // 这里可以实现卖闲置相关的功能界面
        JOptionPane.showMessageDialog(this, "跳转到卖闲置相关功能界面");
        //this.setVisible(false);
        // 在这里实例化并显示卖闲置的UI
        MerchantUI_1 sellIdleUi = new MerchantUI_1(this.Merchantname);
        sellIdleUi.showUI();
    }

    private void showSellDataOptions() {
        // 这里可以实现卖资料相关的功能界面
        JOptionPane.showMessageDialog(this, "跳转到卖资料相关功能界面");
        //this.setVisible(false);
        // 在这里实例化并显示卖资料的UI
        MerchantUI_2 sellDataUi = new MerchantUI_2(this.Merchantname);
        sellDataUi.showUI();
    }
}