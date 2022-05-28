package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataBase.Queries;

public class User_page extends JFrame {

	private JPanel contentPane;

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
	 * @param id 
	 */
	public User_page(String id) {
	    super("User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	    setVisible(true);
	    setResizable(false);

    	Connection connection = Queries.conn();
		contentPane.setLayout(null);
	    setVisible(true);
	    setResizable(false);
	    
	    
		JLabel name = new JLabel("Bonjour");
		name.setBounds(26, 24, 116, 13);
		contentPane.add(name);
	    setVisible(true);
	    setResizable(false);

		JButton Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				dispose();
			}
		});
		Logout.setBounds(415, 20, 85, 21);
		contentPane.add(Logout);
		
		JButton btnIssueBook = new JButton("Issue Book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Issue_Book IssueBook = new Issue_Book(id);
			}
		});
		btnIssueBook.setBounds(58, 86, 180, 44);
		contentPane.add(btnIssueBook);
		
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Return_Book ReturnBook = new Return_Book(id);
			}
		});
		btnReturnBook.setBounds(284, 86, 180, 44);
		contentPane.add(btnReturnBook);
		
		JButton btnIssueBook_1 = new JButton("Issue Book");
		btnIssueBook_1.setBounds(173, 180, 180, 44);
		contentPane.add(btnIssueBook_1);
	    
		
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
