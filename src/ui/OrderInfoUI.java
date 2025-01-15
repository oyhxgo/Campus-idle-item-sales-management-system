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
public class OrderInfoUI extends JFrame {    // 用户购买订单查询 UI
    private JTable bookList;
    private JScrollPane table;
    private JButton contact; // Button to contact the merchant

    public OrderInfoUI() {
        this.setTitle("订单明细");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prepareUI();
    }

    public void showUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        table = new JScrollPane(bookList);
        mainPanel.add(table, BorderLayout.CENTER);

        // Initialize and add the contact button
        contact = new JButton("联系商家");
        contact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookList.getSelectedRow();
                if (selectedRow != -1) { // Ensure a row is selected
                    Orders selectedOrder = getOrdersList().get(selectedRow);
                    showContactMerchantUI(selectedOrder);
                } else {
                    JOptionPane.showMessageDialog(OrderInfoUI.this, "请先选择一个订单！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(contact, BorderLayout.SOUTH); // Add button to the bottom of the panel

        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void prepareUI() {
        ArrayList<Orders> odrs = getOrdersList();
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

        bookList.setFont(new Font("黑体", 0, 15));
        bookList.setRowHeight(30);
    }

    private void showContactMerchantUI(Orders selectedOrder) {
        // Create a dialog for contacting the merchant
        JDialog contactDialog = new JDialog(this, "联系商家", true);
        contactDialog.setSize(400, 300);
        contactDialog.setLayout(new BorderLayout());

        // Display selected merchant information
        JLabel merchantLabel = new JLabel("商家: " + selectedOrder.getMerchant_name()+"  在下方-向我们反映商家");
        contactDialog.add(merchantLabel, BorderLayout.NORTH);
        JTextArea messageArea = new JTextArea("请输入您的消息...");
        contactDialog.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        JButton sendButton = new JButton("发送消息");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to send the message (e.g., save to database, send email, etc.)
                String message = messageArea.getText();
                System.out.println("Message sent to merchant: " + message);
                contactDialog.dispose(); // Close the dialog after sending
            }
        });
        contactDialog.add(sendButton, BorderLayout.SOUTH);
        contactDialog.setLocationRelativeTo(this);
        contactDialog.setVisible(true);
    }

    public ArrayList<Orders> getOrdersList() {
        ArrayList<Orders> ords = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8", "root", "8561826")) {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM orders";
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
