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
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField F_user;
	private JPasswordField F_password;

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
	    super("Login");
		setBounds(100, 100, 482, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
	    setVisible(true);

		

		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(47, 24, 130, 39);
		lblUsername.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		contentPane.add(lblUsername);
		
		F_user = new JTextField();
		F_user.setBounds(184, 31, 194, 32);
		contentPane.add(F_user);
		F_user.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password  :");
		lblPassword.setBounds(47, 73, 130, 39);
		lblPassword.setFont(new Font("Lucida Handwriting", Font.BOLD, 19));
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Segoe Print", Font.BOLD, 14));
		btnLogin.setBounds(81, 136, 130, 32);
		contentPane.add(btnLogin);
		
		F_password = new JPasswordField();
		F_password.setFont(new Font("Tahoma", Font.BOLD, 15));
		F_password.setBounds(184, 73, 194, 32);
		contentPane.add(F_password);
		
		JButton btnregistration = new JButton("register");
		btnregistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	          	Registration Registration = new Registration();
				dispose();
			}
		});
		btnregistration.setFont(new Font("Segoe Print", Font.BOLD, 14));
		btnregistration.setBounds(221, 136, 130, 32);
		contentPane.add(btnregistration);
		
		btnLogin.addActionListener(new ActionListener() {  //Perform action
	         
	        public void actionPerformed(ActionEvent e){ 
	 
	        String username = F_user.getText(); 
	        String password = F_password.getText();
	         
	        if(username.equals(""))
	        {
	            JOptionPane.showMessageDialog(null,"Please enter username"); 	        } 
	        else if(password.equals(""))
	        {
	            JOptionPane.showMessageDialog(null,"Please enter password"); 
	        }
	        else { 
	        	Connection connection = Queries.conn();
	            try
	            {
	            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	              ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"'"); 
	              if(rs.next()==false) { 

	            	  JOptionPane.showMessageDialog(null,"Wrong Username/Password!");
	 
	              }
	              else {
	            	  {
	                      String admin = rs.getString("admin"); //user is admin
	                      //System.out.println(admin);
	                      String id = rs.getString("id"); //Get user ID of the user
	                      if(admin.equals("1")) { //If boolean value 1
	                    	  dispose();
	                    	  Admin_page User_page = new Admin_page(id);
	                      }
	                      else{
	                    	  dispose();
	                    	  User_page User_page = new User_page(id);

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
