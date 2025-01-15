package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JumpUI_U extends JFrame {
    private JPanel selectionPanel; // 新增选择面板
    private JButton idleButton, dataButton,logoutButton; // 选择闲置或资料的按钮
    int userid;

    public JumpUI_U(int userid) {
        this.setTitle("用户界面");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.userid = userid;

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
        selectionPanel.setLayout(new GridLayout(3, 1, 10, 20)); // 减小间距

        // 创建选择按钮
        idleButton = new JButton("购买闲置");
        dataButton = new JButton("购买资料");
        logoutButton = new JButton("退出");

        // 设置按钮字体
        Font buttonFont = new Font("黑体", Font.PLAIN, 18); // 调整字体大小
        idleButton.setFont(buttonFont);
        dataButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        // 设置按钮边框以增加间隔
        idleButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 减小边距
        dataButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 添加按钮的事件监听器
        idleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIdleOptions();
            }
        });

        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDataOptions();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // 将按钮添加到选择面板
        selectionPanel.add(idleButton);
        selectionPanel.add(dataButton);
        selectionPanel.add(logoutButton);
    }

    private void showIdleOptions() {
        // 这里可以实现闲置相关的功能界面
        JOptionPane.showMessageDialog(this, "跳转到闲置相关功能界面");
        UserUI mainUi = new UserUI(this.userid);
        mainUi.showUI();
    }

    private void showDataOptions() {
        // 这里可以实现资料相关的功能界面
        JOptionPane.showMessageDialog(this, "跳转到资料相关功能界面");
        UserUI_2 mainUi = new UserUI_2(this.userid);
        mainUi.showUI();
    }
}