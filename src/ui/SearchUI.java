package ui;

import dao.SearchPro;
import entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchUI extends JFrame {
    private JPanel searchAreaPanel;
    private JComboBox<String> searchType;//查询类型
    private JTextField searchArea;
    private JButton search;
    private DefaultTableModel tableModel;//表格模型
    private JTable proList;//显示表格
    private JScrollPane bookTablePanel;//带有滚动条的面板


    public SearchUI(){
        this.setTitle("查询");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //this.setLayout(new BorderLayout());
        searchAreaPanel=new JPanel(new FlowLayout());
        prepareUI();
    }
    public void showUI(){
        searchAreaPanel.add(searchType);
        searchAreaPanel.add(searchArea);
        searchAreaPanel.add(search);

        this.add(searchAreaPanel,BorderLayout.NORTH);
        this.add(bookTablePanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        }
        private void prepareUI(){
        searchType=new JComboBox<>(new String[]{"商品名称","商品ID","商家"});
        searchType.setFont(new Font("黑体",0,20));

        searchArea=new JTextField();
        searchArea.setFont(new Font("黑体",0,20));
        searchArea.setColumns(20);

        search=new JButton("搜索");
        search.setFont(new Font("黑体",0,20));
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                searchActionPerformed(e);
            }
        });

        tableModel = new DefaultTableModel(){
                public boolean isCellEditable(int row, int column) {
                    return false;//表格不可编辑
                }
        };

            proList=new JTable(tableModel);
            proList.setFont(new Font("黑体",0,20));
            proList.setAutoscrolls(true);
            proList.setRowHeight(30);
            bookTablePanel=new JScrollPane(proList);
        }
        public void searchActionPerformed(ActionEvent e){

        String type=(String)searchType.getSelectedItem();
            SearchPro searchbook=new SearchPro(searchArea.getText(),type);
        ArrayList<Product>pros=searchbook.getBook();
        String[] colsName={"商品ID","商品名称","商家","价格","件数"};
         int Rows=pros.size();
        int Cols=colsName.length;
        Object[][] table=new Object[Rows][Cols];
        for(int i=0;i<Rows;i++){
            table[i][0]=pros.get(i).getProductid();
            table[i][1]=pros.get(i).getProductname();
            table[i][2]=pros.get(i).getMerchantname();
            table[i][3]=pros.get(i).getPrice();
            table[i][4]=pros.get(i).getCount();
        }
        tableModel.setDataVector(table,colsName);
      }
    }
