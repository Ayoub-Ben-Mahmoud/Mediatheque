package gui;

import java.awt.BorderLayout;
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

public class User_Mangement extends JFrame {
	private JTable UserTable;
	private JTextField F_Username;
	private JTextField F_Name;
	private JTextField F_Surname;
	private JTextField F_Email;
	private JTextField F_Password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User_Mangement frame = new User_Mangement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	public int count() {
	    int count = 0;

        try {
        	Connection connection = Queries.conn();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rcount = stmt.executeQuery("SELECT COUNT(*)AS count FROM user");
            rcount.next();
            count = rcount.getInt("count");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public User_Mangement() {
		
		
	    super("User Mangement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    
		int count=count();
		
        String columns[] = { "Uid","Username","Password","Email","Name","Surname","Admin" };
        String data[][] = new String[count][7];
	    
    	Connection connection = Queries.conn();

    	try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            
            int i = 0;


            
            while (rs.next()) {

                int uid = rs.getInt("uid");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Boolean admin = rs.getBoolean("admin");

                
                data[i][0] = uid + "";
                data[i][1] = username;
                data[i][2] = password;
                data[i][3] = email;
                data[i][4] = name;
                data[i][5] = surname;
                data[i][6] = admin+ "";
                i++;
                
              }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
			e1.printStackTrace();
        } 
    	
        

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(265, 10, 450, 265);
	    getContentPane().add(scrollPane);
	    
    	    JButton btnInsert = new JButton("insert");
    	    btnInsert.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		if(F_Username.getText().equals("")||F_Password.getText().equals("")||F_Email.getText().equals("")||F_Surname.getText().equals("")||F_Name.getText().equals(""))
    		        {
    	    			JOptionPane.showMessageDialog(null,"Please fill the input");
    		        }
    	    		else {
    	                try {
    	                	PreparedStatement stmt = connection.prepareStatement("INSERT INTO user(username,password,email,admin,name,surname) VALUES ('" + F_Username.getText() + "','"+ F_Password.getText() +"','"+ F_Email.getText() +"','"+ "0" +"','"+ F_Name.getText() + "','" + F_Surname.getText()+"')");
				            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE uid=(SELECT max(uid) FROM user);"); 

				            while (rs.next()) {
				                int id = rs.getInt("uid");
				                String username = rs.getString("username");
				                String password = rs.getString("password");
				                String email = rs.getString("email");
				                String name = rs.getString("name");
				                String surname = rs.getString("surname");
				                Boolean admin = rs.getBoolean("admin");

        	    			columns[0] = id + "";
        	    			columns[1] = username;
        	    			columns[2] = password;
        	    			columns[3] = email;
        	    			columns[4] = name;
        	    			columns[5] = surname;
        	    			columns[6] = admin+ "";
     
        	    			model.addRow(columns);
        	    			
        	    			F_Username.setText("");
        	    			F_Password.setText("");
        	    			F_Email.setText("");
        	    			F_Name.setText("");
        	    			F_Surname.setText("");
				            }
  
    	                
    	                  
    	                }
    	                catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	    			

    	    		}
    	    	}
    	    });
    	    btnInsert.setBounds(10, 214, 75, 21);
    	    getContentPane().add(btnInsert);
    	    
    	    
    	    
    	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){ 
    	          @Override
    	          public void valueChanged(ListSelectionEvent e) {
    	                int i = table.getSelectedRow();
    	                F_Username.setText((String)model.getValueAt(i, 1));
    	                F_Password.setText((String)model.getValueAt(i, 2));
    	                F_Email.setText((String)model.getValueAt(i, 3));
    	                F_Name.setText((String)model.getValueAt(i, 4));
    	                F_Surname.setText((String)model.getValueAt(i, 5));

    	            }
    	        });
    	    
    	    JButton btnUpdate = new JButton("update");
    	    btnUpdate.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		int i =table.getSelectedRow();
    	    		if(i>=0) {   	
    	    			String value = table.getModel().getValueAt(i, 0).toString();
    	        	    try {
    	        	    	
							PreparedStatement st = connection.prepareStatement("UPDATE user SET username = ? , password = ? , email = ? , admin = ?  ,  name = ? , surname = ?   WHERE uid = "+value+"");
							
							st.setString(1, F_Username.getText());
							st.setString(2, F_Password.getText());
							st.setString(3, F_Email.getText());
							st.setString(4, "0");
							st.setString(5, F_Name.getText());
							st.setString(6, F_Surname.getText());

							st.executeUpdate(); 
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	        	    model.setValueAt(F_Username.getText(), i, 1);
    	                model.setValueAt(F_Password.getText(), i, 2);
    	                model.setValueAt(F_Email.getText(), i, 3);
    	                model.setValueAt(F_Name.getText(), i, 4);
    	                model.setValueAt(F_Surname.getText(), i, 5);

    	    		JOptionPane.showMessageDialog(null, "Updated Successfully");
    	    		}else
    	    		{
    	    		JOptionPane.showMessageDialog(null, "Please Select a Row First !!");
    	    		}
    	    	}
    	    });
    	    btnUpdate.setBounds(95, 214, 75, 21);
    	    getContentPane().add(btnUpdate);

    	    JButton btnDelete = new JButton("delete");
    	    btnDelete.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		int i =table.getSelectedRow()-1;
	    			String value = table.getModel().getValueAt(i, 0).toString();

    	    		if(i>=0) {   	
        	    		model.removeRow(i);

    	        	    try {
    	        	    	
							PreparedStatement st = connection.prepareStatement("DELETE FROM user WHERE uid = "+value+"");
					        st.executeUpdate(); 
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	    		JOptionPane.showMessageDialog(null, "Deleted Successfully");
    	    		}else
    	    		{
    	    		JOptionPane.showMessageDialog(null, "Please Select a Row First !!");
    	    		}
    	    	}
    	    });
    	    btnDelete.setBounds(180, 214, 75, 21);
    	    getContentPane().add(btnDelete);
    	    
    	    JLabel lblNewLabel = new JLabel(" Username :");
    	    lblNewLabel.setBounds(10, 53, 85, 13);
    	    getContentPane().add(lblNewLabel);
    	    
    	    F_Username = new JTextField();
    	    F_Username.setBounds(83, 50, 118, 19);
    	    getContentPane().add(F_Username);
    	    F_Username.setColumns(10);
    	    
    	    F_Password = new JTextField();
    	    F_Password.setBounds(83, 76, 118, 19);
    	    getContentPane().add(F_Password);
    	    F_Password.setColumns(10);
    	    
    	    F_Name = new JTextField();
    	    F_Name.setBounds(83, 105, 118, 19);
    	    getContentPane().add(F_Name);
    	    F_Name.setColumns(10);
    	    
    	    F_Surname = new JTextField();
    	    F_Surname.setBounds(83, 134, 118, 19);
    	    getContentPane().add(F_Surname);
    	    F_Surname.setColumns(10);
    	    
    	    F_Email = new JTextField();
    	    F_Email.setBounds(83, 163, 118, 19);
    	    getContentPane().add(F_Email);
    	    F_Email.setColumns(10);
    	    

    	    
    	    JLabel lblNewLabel_1 = new JLabel("Name :");
    	    lblNewLabel_1.setBounds(40, 108, 45, 13);
    	    getContentPane().add(lblNewLabel_1);
    	    
    	    JLabel lblNewLabel_2 = new JLabel("Surname :");
    	    lblNewLabel_2.setBounds(22, 137, 63, 13);
    	    getContentPane().add(lblNewLabel_2);
    	    
    	    JLabel lblNewLabel_3 = new JLabel("Email :");
    	    lblNewLabel_3.setBounds(40, 166, 45, 13);
    	    getContentPane().add(lblNewLabel_3);
    	    
    	    JLabel lblNewLabel_4 = new JLabel("  Password :");
    	    lblNewLabel_4.setBounds(10, 82, 75, 13);
    	    getContentPane().add(lblNewLabel_4);
    	    

            
   

	}
}
