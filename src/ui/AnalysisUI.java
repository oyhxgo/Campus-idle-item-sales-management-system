package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class AnalysisUI extends JFrame {
    private JLabel titleLabel; // 标题标签
    private JLabel orderCountLabel; // 订单数量标签
    private JLabel productCountLabel; // 商品件数标签
    private JButton refreshButton; // 刷新按钮
    private JTextArea productListArea; // 用于显示所有商品的文本区
    private JPanel mainPanel; // 主面板

    public AnalysisUI() {
        this.setTitle("商品统计");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.LIGHT_GRAY); // 设置背景颜色
        prepareUI();
    }

    public void showUI() {
        this.add(mainPanel, BorderLayout.CENTER);
        this.pack(); // 自适应
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void prepareUI() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

//        titleLabel = new JLabel("当前平台的商品总量为：");
//        titleLabel.setFont(new Font("黑体", Font.BOLD, 20));
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        productCountLabel = new JLabel("当前平台的商品件数：0");
        productCountLabel.setFont(new Font("黑体", Font.PLAIN, 20));
        productCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        orderCountLabel = new JLabel("当前订单数量：0");
        orderCountLabel.setFont(new Font("黑体", Font.PLAIN, 20));
        orderCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 刷新按钮
        refreshButton = new JButton("刷新商品列表");
        refreshButton.setFont(new Font("黑体", Font.BOLD, 20));
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> {
            loadProductList(); // 刷新商品列表
            loadProductCount(); // 刷新商品件数
            loadOrderCount(); // 刷新订单数量
        });

        productListArea = new JTextArea(10, 40); // 设置显示所有商品的文本区
        productListArea.setEditable(false);
        productListArea.setFont(new Font("黑体", Font.PLAIN, 16));
        productListArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(productListArea); // 添加滚动条
        scrollPane.setBorder(BorderFactory.createTitledBorder("所有的商品列表"));

        // 添加所有组件
       // mainPanel.add(titleLabel);
        mainPanel.add(productCountLabel); // 添加商品件数标签
        mainPanel.add(orderCountLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 空白空间
        mainPanel.add(refreshButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 空白空间
        mainPanel.add(scrollPane); // 滚动区域

        // 初始化商品列表、商品件数和订单数量
        loadProductList();
        loadProductCount(); // 加载商品件数
        loadOrderCount();
    }

    public void loadProductList() {
        productListArea.setText(""); // 清空当前文本区
        ArrayList<String> productList = new ArrayList<>();

        // 从数据库加载数据
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT productid, productname, merchantname, count FROM product")) {

            while (rs.next()) {
                int productId = rs.getInt("productid");
                String productName = rs.getString("productname");
                String merchantName = rs.getString("merchantname");
                int count = rs.getInt("count");
                productList.add(productId + ". " + productName + " (商家: " + merchantName + ", 库存: " + count + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            productListArea.setText("加载商品列表失败！"); // 加载失败时的提示
            return;
        }

        // 将商品列表填充到文本区域
        for (String product : productList) {
            productListArea.append(product + "\n");
        }
    }

    public void loadProductCount() {
        int totalCount = 0;

        // 从数据库加载商品数量
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(count) AS totalCount FROM product")) {

            if (rs.next()) {
                totalCount = rs.getInt("totalCount");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            productCountLabel.setText("加载商品件数失败！"); // 加载失败时的提示
            return;
        }

        // 更新商品件数标签
        productCountLabel.setText("当前平台的商品总库存为：" + totalCount);
    }

    public void loadOrderCount() {
        int orderCount = 0;

        // 从数据库加载订单数量
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS orderCount FROM orders")) {

            if (rs.next()) {
                orderCount = rs.getInt("orderCount");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            orderCountLabel.setText("加载订单数量失败！"); // 加载失败时的提示
            return;
        }

        // 更新订单数量标签
        orderCountLabel.setText("当前订单数量：" + orderCount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnalysisUI analysisUI = new AnalysisUI();
            analysisUI.showUI();
        });
    }
}