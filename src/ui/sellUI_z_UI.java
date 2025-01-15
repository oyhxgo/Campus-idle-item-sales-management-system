package ui;

import dao.AddPro;
import entity.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class sellUI_z_UI {

    private JPanel mainPanel;
    private JLabel title, productIdLabel, productNameLabel, merchantNameLabel, tagLabel, stockLabel,productPriceLabel;
    private JTextField productIdField, productNameField, merchantNameField, tagField, stockField,productPriceField;
    private JButton submitButton;

    public sellUI_z_UI() {
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("提交出售物品");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        title = new JLabel("我要出售资料", JLabel.CENTER);
        title.setFont(new Font("黑体", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));

        // Product ID
//        productIdLabel = new JLabel("物品ID:");
//        productIdField = new JTextField();
//        mainPanel.add(productIdLabel);
//        mainPanel.add(productIdField);

        // Product Name
        productNameLabel = new JLabel("物品名称:");
        productNameField = new JTextField();
        mainPanel.add(productNameLabel);
        mainPanel.add(productNameField);


        productPriceLabel = new JLabel("物品价格:");
        productPriceField = new JTextField();
        mainPanel.add(productPriceLabel);
        mainPanel.add(productPriceField);
        // Merchant Name
        merchantNameLabel = new JLabel("商家名称:");
        merchantNameField = new JTextField();
        mainPanel.add(merchantNameLabel);
        mainPanel.add(merchantNameField);

        // Tags
        tagLabel = new JLabel("标签:");
        tagField = new JTextField();
        mainPanel.add(tagLabel);
        mainPanel.add(tagField);

        // Stock
        stockLabel = new JLabel("库存:");
        stockField = new JTextField();
        mainPanel.add(stockLabel);
        mainPanel.add(stockField);

        // Submit Button
        submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        mainPanel.add(new JLabel());  // Empty label for spacing
        mainPanel.add(submitButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void handleSubmit() {
        // 创建Product实例并设置属性
        Product product = new Product();
        //product.setProductid(Integer.parseInt(productIdField.getText())); // 设置产品ID
        product.setProductname(productNameField.getText()); // 设置产品名称
        product.setPrice(Float.parseFloat(productPriceField.getText())); // 设置产品价格
        product.setMerchantname(merchantNameField.getText()); // 设置商家名称
        product.setTag(tagField.getText()); // 设置标签
        product.setCount(Integer.parseInt(stockField.getText())); // 设置库存

        // 打印产品信息以验证
        System.out.println("提交的产品信息：");
        //System.out.println("物品ID: " + product.getProductid());
        System.out.println("物品名称: " + product.getProductname());
        System.out.println("物品价格: " + product.getPrice());
        System.out.println("商家名称: " + product.getMerchantname());
        System.out.println("标签: " + product.getTag());
        System.out.println("库存: " + product.getCount());
        AddPro temp=new AddPro(product);

        // 这里可以将product存储到数据库的方法调用在此
        // 例如：productDAO.saveProduct(product);

        // 清空输入框
        clearFields();
        JOptionPane.showMessageDialog(null, "提交成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        //productIdField.setText("");
        productNameField.setText("");
        productPriceField.setText("");
        merchantNameField.setText("");
        tagField.setText("");
        stockField.setText("");
    }

    public void showUI() {
        // This could be left empty or used to trigger the initialization if needed
    }
}