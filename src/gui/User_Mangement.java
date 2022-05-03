package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
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
	 */
	public User_Mangement() {
	    super("User Mangement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    

	    
    	Connection connection = Queries.conn();
    	try {
    		
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM user"); //Execute query
         
            Statement s = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rcount = s.executeQuery("SELECT COUNT(*) FROM user");
            rcount.next();
            int count = rcount.getInt(1);

            String columns[] = { "Uid", "Username","Name","Surname", "Email","Admin" };
            String data[][] = new String[count][6];
          
            int i = 0;
            while (rs.next()) {

              int id = rs.getInt("uid");
              String username = rs.getString("username");
              String email = rs.getString("email");
              String nom = rs.getString("name");
              String prenom = rs.getString("surname");
              Boolean admin = rs.getBoolean("admin");

              
              data[i][0] = id + "";
              data[i][1] = username;
              data[i][2] = email;
              data[i][3] = nom;
              data[i][4] = prenom;
              data[i][5] = admin+ "";
              i++;
              
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
							stmt.executeUpdate("INSERT INTO user(username,password,email,admin,name,surname) VALUES ('" + F_Username.getText() + "','"+ F_Password.getText() +"','"+ F_Email.getText() +"','"+ "0" +"','"+ F_Name.getText() + "','" + F_Username.getText()+"')");
				            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE uid=(SELECT max(uid) FROM user);"); //Execute query

				            rcount.next();
				            while (rs.next()) {
				                int id = rs.getInt("uid");
				                String username = rs.getString("username");
				                String email = rs.getString("email");
				                String nom = rs.getString("name");
				                String prenom = rs.getString("surname");
				                Boolean admin = rs.getBoolean("admin");

        	    			columns[0] = id + "";
        	    			columns[1] = username;
        	    			columns[2] = email;
        	    			columns[3] = nom;
        	    			columns[4] = prenom;
        	    			columns[5] = admin+ "";
     
        	    			model.addRow(columns);
        	    			
        	    			F_Username.setText("");
        	    			F_Password.setText("");
        	    			F_Name.setText("");
        	    			F_Surname.setText("");
        	    			F_Email.setText("");
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
    	    
    	    
    	    JButton btnNewButton_1 = new JButton("update");
    	    btnNewButton_1.setBounds(95, 214, 75, 21);
    	    getContentPane().add(btnNewButton_1);
    	    
    	    JButton btnNewButton_2 = new JButton("delete");
    	    btnNewButton_2.setBounds(180, 214, 75, 21);
    	    getContentPane().add(btnNewButton_2);
    	    
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
    	    

            
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
			e1.printStackTrace();
        }    

	}
}
