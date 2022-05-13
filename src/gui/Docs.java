package gui;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import DataBase.Queries;

public class Docs extends JFrame {
	public Docs() {
		//Connection to DataBase 
		
		Connection connection= Queries.conn();
		
		getContentPane().setLayout(null);
		
		JLabel labelISBN = new JLabel("ISBN");
		labelISBN.setBounds(70, 56, 46, 14);
		getContentPane().add(labelISBN);
		
		JLabel labelTitle = new JLabel("Title");
		labelTitle.setBounds(70, 94, 46, 14);
		getContentPane().add(labelTitle);
		
		JTextField textISBN = new JTextField();
		textISBN.setBounds(167, 53, 200, 20);
		getContentPane().add(textISBN);
		textISBN.setColumns(10);
		
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
		
		JButton btnAdd = new JButton("Add Book");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
                     //Creating statement
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("INSERT INTO documents(ISBN ,bookname,genre,author,pages,stock) VALUES ('" + textISBN.getText() + "','" + textTitle.getText() + "','" + textG.getText() + "','" + textAuth.getText() + "','" + txtP.getText() + "','" + txtS.getText() +"')");
				 }
				 catch(Exception e1) {
					 e1.printStackTrace();
				 }
			}
		});
		btnAdd.setBounds(151, 311, 89, 23);
		getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("Delete Book");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnDelete.setBounds(311, 311, 89, 23);
		getContentPane().add(btnDelete);
		
		JButton btnView = new JButton("View Books");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnView.setBounds(311, 363, 89, 23);
		getContentPane().add(btnView);
		
		JButton btnNewButton_3 = new JButton("Back");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(151, 363, 89, 23);
		getContentPane().add(btnNewButton_3);
		
	}     
 	public static void main(String[] args) {
 		EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					Docs docs = new Docs();
 					docs.setVisible(true);
 					docs.setExtendedState(JFrame.MAXIMIZED_BOTH);
 					//docs.dispose();
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});
 	}}

