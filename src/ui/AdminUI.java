package ui;

import dao.AddPro;
import ui.sellUI_x_UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI extends JFrame {

    private JPanel searchBook, addBook, Analysis, delete,logout;

    private JButton searchBookButton, addBookButton, AnalysisButton,deleteButton,logoutButton;

    public AdminUI() {
        this.setTitle("管理员主界面");
        this.setLayout(new GridLayout(5, 1));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchBook = new JPanel();
        addBook = new JPanel();
        Analysis = new JPanel();
        logout = new JPanel();
        delete = new JPanel();
        prepareUI();
    }

    public void showUI() {
        searchBook.add(searchBookButton);
        addBook.add(addBookButton);
        Analysis.add(AnalysisButton);
        logout.add(logoutButton);
        delete.add(deleteButton);

        this.add(searchBook);
        this.add(addBook);
        this.add(Analysis);
        this.add(delete);
        this.add(logout);
        this.setBounds(400, 200, 420, 420); /** 设置窗体大小 */
        this.setResizable(false); /** 不可放大 */
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void prepareUI() {
        searchBookButton = new JButton("查找闲置");
        searchBookButton.setFont(new Font("黑体", 0, 20));

        addBookButton = new JButton("添加闲置");
        addBookButton.setFont(new Font("黑体", 0, 20));

        AnalysisButton = new JButton("统计分析");
        AnalysisButton.setFont(new Font("黑体", 0, 20));

        deleteButton = new JButton("删除闲置");
        deleteButton.setFont(new Font("黑体", 0, 20));

        logoutButton = new JButton("退出");
        logoutButton.setFont(new Font("黑体", 0, 20));

        //为按钮添加监听事件
        searchBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchActionPerformed(e);
            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBookButtonActionPerformed(e);
            }
        });

        AnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnalysisButtonActionPerformed(e);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteButtonActionPerformed(e);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             logoutButtonActionPerformed(e);
            }
        });
    }

    private void DeleteButtonActionPerformed(ActionEvent e) {
        DeleteXZUI s=new DeleteXZUI();
        s.showUI();
    }
    //查找图书

        public void  searchActionPerformed(ActionEvent e){
            SearchUI s=new SearchUI();
            s.showUI();
     }
        //添加图书
        private void addBookButtonActionPerformed (ActionEvent e){
            sellUI_x_UI a = new sellUI_x_UI();
        }
        //删除图书
        private void AnalysisButtonActionPerformed (ActionEvent e){
            AnalysisUI d = new AnalysisUI();
            d.showUI();
        }
        //退出
        private void logoutButtonActionPerformed (ActionEvent e){

            System.exit(0);
        }

}