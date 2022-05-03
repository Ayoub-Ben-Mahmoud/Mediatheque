package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin_page extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String id = null;
					Admin_page frame = new Admin_page(id);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param id 
	 */
	public Admin_page(String id) {
	    super("Admin Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
			}
		});
		btnNewButton.setBounds(242, 34, 161, 44);
		contentPane.add(btnNewButton);
		
		
		
		JButton btnGutilisateurs = new JButton("Gerer utilisateurs");
		btnGutilisateurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  dispose();
           	  User_Mangement userm = new User_Mangement();		
			}
		});
		
		btnGutilisateurs.setBounds(41, 34, 161, 44);
		contentPane.add(btnGutilisateurs);
	    setVisible(true);
	    setResizable(false);
	    
	}
}
