package ui;

import dao.OrderInfo;
import dao.ProSQLInfo;

import entity.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class OrderListUI_M extends JFrame { // 用户购买订单查询 UI
    private JTable bookList;
    private JScrollPane table;
    private JButton contact; // Button to contact the merchant
    private String merchantname;

    public OrderListUI_M(String role) {
        setMerchantname(role);
        this.setTitle("订单明细");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prepareUI();
        this.pack();
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true);
    }

    public String getMerchantname() {
        return this.merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public void showUI() {
        // Ensure to refresh the UI if needed
        this.setVisible(true);
    }

    private void prepareUI() {
        //System.out.println("商家名字:"+getMerchantname());
        ArrayList<Orders> odrs = getOrdersListByMerchant(getMerchantname());
        String[] colsName = {"订单ID", "用户ID", "产品ID", "商品名称", "商家名称", "购买数量", "订单日期"};
        int Rows = odrs.size();
        int Cols = colsName.length;
        Object[][] tableData = new Object[Rows][Cols];

        for (int i = 0; i < Rows; i++) {
            tableData[i][0] = odrs.get(i).getOrder_id();
            tableData[i][1] = odrs.get(i).getUser_id();
            tableData[i][2] = odrs.get(i).getProduct_id();
            tableData[i][3] = odrs.get(i).getProduct_name();
            tableData[i][4] = odrs.get(i).getMerchant_name();
            tableData[i][5] = odrs.get(i).getQuantity();
            tableData[i][6] = odrs.get(i).getOrder_date();
        }

        bookList = new JTable(new DefaultTableModel(tableData, colsName) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        });

        bookList.setFont(new Font("黑体", Font.PLAIN, 15));
        bookList.setRowHeight(30);
        bookList.setSelectionBackground(new Color(173, 216, 230)); // Light blue selection
        bookList.setSelectionForeground(Color.BLACK); // Black text on selection

        table = new JScrollPane(bookList);
        table.setBorder(BorderFactory.createTitledBorder("订单列表")); // Add a border title
        this.add(table, BorderLayout.CENTER);
        // Initialize and add the contact button
        //contact = new JButton("联系商家");
//        contact.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = bookList.getSelectedRow();
//                if (selectedRow != -1) { // Ensure a row is selected
//                    Orders selectedOrder = odrs.get(selectedRow);
//                    showContactMerchantUI(selectedOrder);
//                } else {
//                    JOptionPane.showMessageDialog(OrderListUI_M.this, "请先选择一个订单！", "错误", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
        //this.add(contact, BorderLayout.SOUTH); // Add button to the bottom of the panel
    }

//    private void showContactMerchantUI(Orders selectedOrder) {
//        // Create a dialog for contacting the merchant
//        //JDialog contactDialog = new JDialog(this, "联系商家", true);
//        //contactDialog.setSize(400, 300);
//       // contactDialog.setLayout(new BorderLayout());
//
//        // Display selected merchant information
//        //JLabel merchantLabel = new JLabel("商家: " + selectedOrder.getMerchant_name() + "  在下方-向我们反映商家");
//        //contactDialog.add(merchantLabel, BorderLayout.NORTH);
//        //JTextArea messageArea = new JTextArea("请输入您的消息...");
//        contactDialog.add(new JScrollPane(messageArea), BorderLayout.CENTER);
//        JButton sendButton = new JButton("发送消息");
//        sendButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Logic to send the message (e.g., save to database, send email, etc.)
//                String message = messageArea.getText();
//                System.out.println("Message sent to merchant: " + message);
//                contactDialog.dispose(); // Close the dialog after sending
//            }
//        });
//        contactDialog.add(sendButton, BorderLayout.SOUTH);
//        contactDialog.setLocationRelativeTo(this);
//        contactDialog.setVisible(true);
//    }

    public ArrayList<Orders> getOrdersListByMerchant(String merchantName) {
        ArrayList<Orders> ords = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826")) {
            Statement statement = con.createStatement();
            // 根据输入的商家名称来构建SQL查询
            String sql = "SELECT * FROM orders WHERE merchant_name='" + merchantName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Orders p = new Orders();
                p.setOrder_id(rs.getInt("order_id"));
                p.setUser_id(rs.getInt("user_id"));
                p.setProduct_id(rs.getInt("product_id"));
                p.setProduct_name(rs.getString("product_name"));
                p.setMerchant_name(rs.getString("merchant_name"));
                p.setQuantity(rs.getInt("quantity"));
                p.setOrder_date(rs.getDate("order_date"));
                ords.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ords;
    }
}