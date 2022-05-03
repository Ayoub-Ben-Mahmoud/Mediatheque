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
	    super("Login");
		setBounds(100, 100, 507, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
	    setVisible(true);

		

		
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
	              ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE username='"+username+"' AND password='"+password+"'"); 
	              if(rs.next()==false) { 
	                  System.out.print("No user");  
	                  JOptionPane.showMessageDialog(null,"Wrong Username/Password!");
	 
	              }
	              else {
	            	  {
	                      String admin = rs.getString("admin"); //user is admin
	                      //System.out.println(admin);
	                      String uid = rs.getString("uid"); //Get user ID of the user
	                      if(admin.equals("1")) { //If boolean value 1
	                    	  dispose();
	                    	  Admin_page User_page = new Admin_page(uid);
	                      }
	                      else{
	                    	  dispose();
	                    	  User_page User_page = new User_page(uid);

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
