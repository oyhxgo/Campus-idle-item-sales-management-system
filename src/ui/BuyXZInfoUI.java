package ui;

import dao.ProSQLInfo;
import entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuyXZInfoUI extends JFrame {     // 负责对数据库中数据进行可视化
    private JTable bookList;
    private JScrollPane table;
    private JPanel buylist;
    private JLabel tip;
    private int currentUserId; // 当前用户ID

    public BuyXZInfoUI(int userId) { // 构造函数接受当前用户ID
        this.currentUserId = userId; // 设置当前用户ID
        this.setTitle("闲置明细");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prepareUI();
    }

    public void showUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        table = new JScrollPane(bookList);

        tip = new JLabel("请点击下方操作区-购买 进行商品购买!");
        tip.setSize(300, 30);
        tip.setForeground(Color.RED);
        tip.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(tip, BorderLayout.NORTH);
        mainPanel.add(table, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void prepareUI() {
        ProSQLInfo userPro = new ProSQLInfo();
        ArrayList<Product> pros = userPro.getProList();

        String[] colsName = {"物品ID", "物品名称", "商家", "标签", "库存", "价格", "操作"};
        int Rows = pros.size();
        int Cols = colsName.length;
        Object[][] table = new Object[Rows][Cols];

        for (int i = 0; i < Rows; i++) {
            table[i][0] = pros.get(i).getProductid();
            table[i][1] = pros.get(i).getProductname();
            table[i][2] = pros.get(i).getMerchantname();
            table[i][3] = pros.get(i).getTag();
            table[i][4] = pros.get(i).getCount();
            table[i][5] = pros.get(i).getPrice();
            table[i][6] = "购买"; // 按钮的文本
        }

        bookList = new JTable(new DefaultTableModel(table, colsName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        bookList.setFont(new Font("黑体", 0, 15));
        bookList.setRowHeight(30);

        // 监听器，用于更新库存和插入订单
        setupButtonListeners(pros);
    }

    private void setupButtonListeners(ArrayList<Product> pros) {
        bookList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = bookList.rowAtPoint(evt.getPoint());
                int column = bookList.columnAtPoint(evt.getPoint());

                if (column == 6) { // 检查点击是否在操作列
                    if (row >= 0) {
                        Product selectedProduct = pros.get(row);
                        int currentCount = selectedProduct.getCount();
                        if (currentCount > 0) {
                            selectedProduct.setCount(currentCount - 1); // 更新内存中产品的数量
                            ((DefaultTableModel) bookList.getModel()).setValueAt(currentCount - 1, row, 4); // 更新表格视图
                            JOptionPane.showMessageDialog(null, "购买成功！");
                            // 数据库连接及更新库存
                            try (Connection con = DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826");
                                 PreparedStatement updateStmt = con.prepareStatement(
                                         "UPDATE product SET count = count - 1 WHERE productid = ?")) {
                                // 更新库存
                                updateStmt.setInt(1, selectedProduct.getProductid());
                                updateStmt.executeUpdate();
                                // 插入订单记录
                                insertOrder( currentUserId,selectedProduct.getProductid(),selectedProduct.getProductname(),selectedProduct.getMerchantname(),1); // 购买数量为1

                            } catch (SQLException e) {
                                e.printStackTrace(); // 输出异常信息到控制台
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "库存不足！");
                        }
                    }
                }
            }
        });
    }

    private void insertOrder(int userId,int productId,String productname,String merchantname, int quantity) {
        String sql = "INSERT INTO orders (user_id,product_id,product_name,merchant_name,quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.setString(3, productname);
            pstmt.setString(4, merchantname);
            pstmt.setInt(5, quantity);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // 输出异常信息到控制台
        }
    }
}