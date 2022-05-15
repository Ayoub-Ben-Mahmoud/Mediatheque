package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataBase.Queries;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;


///**
//* Launch the application.
//*/
//public static void main(String[] args) {
//	EventQueue.invokeLater(new Runnable() {
//		public void run() {
//			try {
//				String id = null;
//				User_page frame = new User_page(id);
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	});
//}



public class Admin_page extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param id 
	 */
	public Admin_page(String id) {
	    super("Admin Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Book Issue Mangement");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin_IssueBook bookmn = new Admin_IssueBook(id);
				  dispose();
			}
		});
		btnNewButton.setBounds(245, 72, 180, 44);
		contentPane.add(btnNewButton);
		
		
		
		JButton btnGutilisateurs = new JButton("User Mangement");
		btnGutilisateurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
           	  User_Mangement userm = new User_Mangement(id);
			  dispose();

			}
		});
		
    	Connection connection = Queries.conn();


		
		btnGutilisateurs.setBounds(44, 72, 180, 44);
		contentPane.add(btnGutilisateurs);
		
		JLabel name = new JLabel("Hello");
		name.setBounds(26, 24, 116, 13);
		contentPane.add(name);
		
		JButton Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				dispose();
			}
		});
		Logout.setBounds(415, 20, 85, 21);
		contentPane.add(Logout);
		
	    setVisible(true);
	    setResizable(false);
	    
    	try {
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM users WHERE id="+id+"");
            while (rs.next()) {
                String uname = rs.getString("name");
            	name.setText("Hello  "+uname);	

            }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
