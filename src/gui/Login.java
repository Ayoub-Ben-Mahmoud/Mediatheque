package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataBase.Queries;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField F_user;
	private JTextField F_password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(58, 24, 130, 39);
		lblUsername.setFont(new Font("Lucida Handwriting", Font.PLAIN, 19));
		contentPane.add(lblUsername);
		
		F_user = new JTextField();
		F_user.setBounds(184, 31, 194, 32);
		contentPane.add(F_user);
		F_user.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password  :");
		lblPassword.setBounds(58, 73, 130, 39);
		lblPassword.setFont(new Font("Lucida Handwriting", Font.PLAIN, 19));
		contentPane.add(lblPassword);
		
		F_password = new JTextField();
		F_password.setBounds(184, 74, 194, 32);
		contentPane.add(F_password);
		F_password.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Segoe Print", Font.BOLD, 14));
		btnLogin.setBounds(209, 133, 130, 32);
		contentPane.add(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {  //Perform action
	         
	        public void actionPerformed(ActionEvent e){ 
	 
	        String username = F_user.getText(); //Store username entered by the user in the variable "username"
	        String password = F_password.getText(); //Store password entered by the user in the variable "password"
	         
	        if(username.equals("")) //If username is null
	        {
	            JOptionPane.showMessageDialog(null,"Please enter username"); //Display dialog box with the message
	        } 
	        else if(password.equals("")) //If password is null
	        {
	            JOptionPane.showMessageDialog(null,"Please enter password"); //Display dialog box with the message
	        }
	        else { //If both the fields are present then to login the user, check wether the user exists already
	            //System.out.println("Login connect");
	        	Connection connection = Queries.conn(); //Connect to the database
	            try
	            {
	            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	              ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE name='"+username+"' AND password='"+password+"'"); //Execute query
	              if(rs.next()==false) { //Move pointer below
	                  System.out.print("No user");  
	                  JOptionPane.showMessageDialog(null,"Wrong Username/Password!"); //Display Message
	 
	              }
	              else {
	                rs.beforeFirst();  //Move the pointer above
	                while(rs.next())
	                {
	                  String admin = rs.getString("admin"); //user is admin
	                  if(admin.equals("0")) { //If boolean value 1
		                    System.out.println("not admin");  
	                  }
	                  else{
		                    System.out.println("admin");  

	                  }
	              }
	              }
	            }
	            catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	    }               
	    });
		
		
		
	

	
}	
}
