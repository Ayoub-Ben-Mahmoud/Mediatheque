package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataBase.Queries;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Toolkit;

public class Registration extends JFrame {

	private JPanel F_password;
	private JTextField F_Username;
	private JTextField F_Email;
	private JTextField F_Name;
	private JTextField F_Surname;
	private JPasswordField F_Password;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					String id = null;
//					User_page frame = new User_page(id);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Registration() {
	    super("Registration");
		setBounds(100, 100, 418, 367);
		F_password = new JPanel();
		F_password.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(F_password);
		F_password.setLayout(null);
		
		JLabel username = new JLabel("username :");
		username.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		username.setBounds(26, 36, 125, 13);
		F_password.add(username);
		
		JLabel password = new JLabel("password :");
		password.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		password.setBounds(26, 76, 125, 20);
		F_password.add(password);
		
		JLabel Email = new JLabel("Email :");
		Email.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		Email.setBounds(26, 123, 125, 19);
		F_password.add(Email);
		
		JLabel name = new JLabel("name :");
		name.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		name.setBounds(26, 167, 125, 19);
		F_password.add(name);
		
		F_Username = new JTextField();
		F_Username.setBounds(161, 32, 194, 32);
		F_password.add(F_Username);
		F_Username.setColumns(10);
		
		F_Email = new JTextField();
		F_Email.setBounds(161, 122, 194, 32);
		F_password.add(F_Email);
		F_Email.setColumns(10);
		
		F_Name = new JTextField();
		F_Name.setBounds(161, 166, 194, 32);
		F_password.add(F_Name);
		F_Name.setColumns(10);
		
		JLabel surname = new JLabel("surname :");
		surname.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		surname.setBounds(26, 206, 125, 20);
		F_password.add(surname);
		
		F_Surname = new JTextField();
		F_Surname.setColumns(10);
		F_Surname.setBounds(161, 209, 194, 32);
		F_password.add(F_Surname);
		
    	Connection connection = Queries.conn();

		
		JButton Register = new JButton("Register");
		Register.setFont(new Font("Segoe Print", Font.BOLD, 14));
		Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(F_Username.getText().equals("")||F_Password.getText().equals("")||F_Email.getText().equals("")||F_Surname.getText().equals("")||F_Name.getText().equals(""))
		        {
	    			JOptionPane.showMessageDialog(null,"Please fill the input");
		        }
	    		else {
	                try {
	                	PreparedStatement st = connection.prepareStatement("INSERT INTO users(username,password,email,admin,name,surname) VALUES ('" + F_Username.getText() + "','"+ F_Password.getText() +"','"+ F_Email.getText() +"','"+ "0" +"','"+ F_Name.getText() + "','" + F_Surname.getText()+"')");
						st.executeUpdate(); 
						
		    			JOptionPane.showMessageDialog(null,"Registration Successful !");

		    			Login login = new Login();
		  			    dispose();
	                }
	                catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

	    			

	    		}
	    	}
			
		});
		Register.setBounds(191, 270, 130, 32);
		F_password.add(Register);
		
		F_Password = new JPasswordField();
		F_Password.setFont(new Font("Tahoma", Font.BOLD, 11));
		F_Password.setBounds(161, 78, 194, 32);
		F_password.add(F_Password);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				dispose();
			}
		});
		back.setFont(new Font("Segoe Print", Font.BOLD, 14));
		back.setBounds(51, 270, 130, 32);
		F_password.add(back);
		setResizable(false);
	    setVisible(true);
	    
	    
	    
	    
	    
	    
	    

	}
}
