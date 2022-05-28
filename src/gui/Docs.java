package gui;


import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import DataBase.Queries;
import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Docs extends JFrame {
	
 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			try {
				String uid = null;
				Docs docs = new Docs(uid);
				docs.setVisible(true);
				docs.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	//private JTable table;
	//private DefaultTableModel model;
	public int count() {
	    int count = 0;
        try {
        	Connection connection = Queries.conn();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rcount = stmt.executeQuery("SELECT COUNT(*)AS count FROM documents");
            rcount.next();
            count = rcount.getInt("count");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public Docs(String uid) {
		//Connection to DataBase 
		Connection conn= Queries.conn();
		getContentPane().setLayout(null);
		
		
		
		JLabel labelTitle = new JLabel("Title");
		labelTitle.setBounds(70, 94, 46, 14);
		getContentPane().add(labelTitle);
		
		JTextField textTitle = new JTextField();
		textTitle.setBounds(167, 91, 200, 20);
		getContentPane().add(textTitle);
		textTitle.setColumns(10);
		
		JLabel labelAuth = new JLabel("Author");
		labelAuth.setBounds(70, 130, 46, 14);
		getContentPane().add(labelAuth);
		
		JTextField textAuth = new JTextField();
		textAuth.setBounds(167, 127, 200, 20);
		getContentPane().add(textAuth);
		textAuth.setColumns(10);
		
		JLabel lblP = new JLabel("Pages");
		lblP.setBounds(70, 215, 46, 14);
		getContentPane().add(lblP);
		
		JTextField txtP = new JTextField();
		txtP.setBounds(167, 212, 200, 20);
		getContentPane().add(txtP);
		txtP.setColumns(10);
		
		JLabel lblS = new JLabel("Stock");
		lblS.setBounds(70, 253, 46, 14);
		getContentPane().add(lblS);
		
		JTextField txtS = new JTextField();
		txtS.setBounds(169, 250, 198, 20);
		getContentPane().add(txtS);
		txtS.setColumns(10);
		
		JLabel labelG = new JLabel("Genre");
		labelG.setBounds(70, 174, 46, 14);
		getContentPane().add(labelG);
		
		JTextField textG = new JTextField();
		textG.setBounds(167, 171, 200, 20);
		getContentPane().add(textG);
		textG.setColumns(10);
		
		
		
		int count = count();
		
		String columns[] = {"id","Title","Author","Genre","Pages","Stock"};
		String data[][] = new String [count][6];
		//DefaultTableModel model = new DefaultTableModel(data, columns);
		JTable table = new JTable(data,columns);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setBounds(644, 140, -85, 14);
		//final Object [] row = new Object[0];
		//model.setColumnIdentifiers(column);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(424, 11, 367, 357);
		getContentPane().add(scrollPane);
		//scrollPane.add(table);
		//scrollPane.setViewportView(table);
		//getContentPane().add(table);
		//model.addRow(row);
       /* table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	    int selectedRowIndex = table.getSelectedRow();
   

        	    textTitle.setText(model.getValueAt(selectedRowIndex, 1).toString());
        	    textAuth.setText(model.getValueAt(selectedRowIndex, 2).toString());
        	    txtP.setText(model.getValueAt(selectedRowIndex, 3).toString());
        	    txtS.setText(model.getValueAt(selectedRowIndex, 4).toString());
        	    textG.setText(model.getValueAt(selectedRowIndex, 5).toString());


               
        	}
        });*/
		
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
 
            ResultSet rs = stmt.executeQuery("SELECT * FROM documents");

            int i = 0;

            while (rs.next()) {
 
                int id = rs.getInt("id");
                String title = rs.getString("bookname");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                String pages = rs.getString("pages");
                String stock = rs.getString("stock");

                data[i][0] = id + "";
                data[i][1] = title;
                data[i][2] = author;
                data[i][3] = genre;
                data[i][4] = pages;
                data[i][5] = stock;
                i++;

              }
 
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } 
		
		JButton btnAdd = new JButton("Add Book");
		btnAdd.setBounds(193, 292, 117, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textTitle.getText().equals("")||textAuth.getText().equals("")||txtP.getText().equals("")||txtS.getText().equals("")||textG.getText().equals(""))
		        {
					JOptionPane.showMessageDialog(null,"Please fill the inputs");
		        }
				else {
					try {
	                     //Creating statement
						 PreparedStatement stmt = conn.prepareStatement("INSERT INTO documents(bookname,genre,author,pages,stock) "
						 + "VALUES ('" + textTitle.getText() + "','" + textG.getText() + "','" + textAuth.getText() + "','" + txtP.getText() + "','" + txtS.getText() +"')");
						 stmt.executeUpdate();
						 ResultSet rs = stmt.executeQuery("SELECT * FROM documents WHERE id=(SELECT max(id) FROM documents);");
						 int i=0;
						 while (rs.next()) {
				                String id = rs.getString("id");
				                String title = rs.getString("bookname");
				                String author = rs.getString("author");
				                String genre = rs.getString("genre");
				                String pages = rs.getString("pages");
				                String stock = rs.getString("stock");
				                
				                columns[0] = id + "";
	        	    			columns[1] = title;
	        	    			columns[2] = author;
	        	    			columns[3] = genre;
	        	    			columns[4] = pages;
	        	    			columns[5] = stock;
	        	    			i++;
	        	    			//model.addRow(columns);
						 }
                     JOptionPane.showMessageDialog(null, "Document inserted succefully !");
					 }
					 catch(Exception e1) {
						 e1.printStackTrace();
					 }
					}
				}
			});
			getContentPane().add(btnAdd);
				
		//Update Books
		JButton btnNewButton = new JButton("Update Book");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i =table.getSelectedRow();
	    		if(i>=0) {   	
	    			String value = table.getModel().getValueAt(i, 0).toString();
	    			
	    			 try {
 	        	    	
							PreparedStatement st = conn.prepareStatement("UPDATE documents SET bookname = ? , genre = ? , author = ?  ,  pages = ? , stock = ?   WHERE id = "+value+"");
							
							st.setString(1, textTitle.getText());
							st.setString(2, textAuth.getText());
							st.setString(3, textG.getText());
							st.setInt(4, Integer.parseInt(txtP.getText()));
							st.setInt(5, Integer.parseInt(txtS.getText()));
							st.executeUpdate(); 
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    			table.setValueAt(textTitle.getText(), i, 1);
 	                table.setValueAt(textAuth.getText(), i, 2);
 	                table.setValueAt(txtP.getText(), i, 3);
 	                table.setValueAt(txtS.getText(), i, 4);
 	                table.setValueAt(textG.getText(), i, 5);

 	    		JOptionPane.showMessageDialog(null, "Updated Successfully");
 	    		}else
 	    		{
 	    		JOptionPane.showMessageDialog(null, "Please Select a Row First !!");
 	    		}
			}
		});
		btnNewButton.setBounds(193, 326, 117, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnDelete = new JButton("Delete Book");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				String value = table.getModel().getValueAt(i, 0).toString();
				table.changeSelection(count, 0, true, false);
				if (i>0) {
					table.removeRowSelectionInterval(i, i);
                try {
       	    	
					PreparedStatement st = conn.prepareStatement("DELETE FROM documents WHERE id = "+value+"");
					
			        st.executeUpdate(); 
			        
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   		 
                JOptionPane.showMessageDialog(null, "Deleted Successfully");
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select a Row First !!");
				}
			}
		});
		btnDelete.setBounds(193, 360, 117, 23);
		getContentPane().add(btnDelete);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
          	  Admin_page User_page = new Admin_page(uid);

				dispose();

			}
		});
		back.setBounds(203, 393, 85, 21);
		getContentPane().add(back);
		
	}
		

}

