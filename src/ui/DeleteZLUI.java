package ui;

import dao.ProSQLInfo;
import dao.ProSQLInfo_ZL;
import entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteZLUI extends JFrame { // 负责对数据库中数据进行可视化
    private JTable bookList;
    private JScrollPane table;
    private JLabel tip;
    private int currentUserId; // 当前用户ID

    public DeleteZLUI() { // 构造函数接受当前用户ID
        //this.currentUserId = userId; // 设置当前用户ID
        this.setTitle("资料明细");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prepareUI();
    }

    public void showUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        table = new JScrollPane(bookList);

        tip = new JLabel("请点击下方操作区-选择商品进行删除操作!");
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
        ProSQLInfo_ZL userPro = new ProSQLInfo_ZL();
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
            table[i][6] = "删除"; // 按钮的文本改为删除
        }

        bookList = new JTable(new DefaultTableModel(table, colsName) {
            public boolean isCellEditable(int row, int column) {
                return false; // 表格不可编辑
            }
        });

        bookList.setFont(new Font("黑体", 0, 15));
        bookList.setRowHeight(30);

        // 监听器，用于删除商品
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
                        int productId = selectedProduct.getProductid();
                        int confirm = JOptionPane.showConfirmDialog(null, "确定要删除该商品吗？", "删除商品", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            deleteProduct(productId); // 删除商品

                            // 从产品列表中移除商品
                            pros.remove(row);
                            loadProductData(); // 重新加载商品数据
                        }
                    }
                }
            }
        });
    }

    private void deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE productid = ?";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "商品删除成功！");

        } catch (SQLException e) {
            e.printStackTrace(); // 输出异常信息到控制台
            JOptionPane.showMessageDialog(null, "商品删除失败！");
        }
    }

    private void loadProductData() {
        // 重新加载商品数据
        ProSQLInfo_ZL userPro = new ProSQLInfo_ZL();
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
            table[i][6] = "删除"; // 按钮的文本改为删除
        }

        bookList.setModel(new DefaultTableModel(table, colsName)); // 更新表格模型
    }

    public static void main(String[] args) {
        // 示例用户ID实现，您需要用实际的用户ID替代。
        SwingUtilities.invokeLater(() -> new DeleteZLUI().showUI());
    }
}