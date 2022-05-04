package gui;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import DataBase.Queries;


public class Docs extends JFrame {
	private JTextField docISBN;
	private JTextField docTitle;
	private JTextField docAuth;
	private JTextField docPages;
	private JTextField docGenre;
	private JTextField Editions;
	
	 
     
 	/**
 	 * Launch the application.
 	 */
 	public static void main(String[] args) {
 		EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					Docs docs = new Docs();
 					docs.setVisible(true);
 					//docs.dispose();
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});
 	}
     
	public Docs() {
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("ISBN");
		getContentPane().add(lblNewLabel, "2, 2");
		
		docISBN = new JTextField();
		getContentPane().add(docISBN, "4, 2, fill, default");
		docISBN.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Title");
		getContentPane().add(lblNewLabel_1, "2, 4, right, default");
		
		docTitle = new JTextField();
		getContentPane().add(docTitle, "4, 4, fill, default");
		docTitle.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Author");
		getContentPane().add(lblNewLabel_2, "2, 6, right, default");
		
		docAuth = new JTextField();
		getContentPane().add(docAuth, "4, 6, fill, default");
		docAuth.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Pages");
		getContentPane().add(lblNewLabel_3, "2, 8, right, default");
		
		docPages = new JTextField();
		getContentPane().add(docPages, "4, 8, fill, default");
		docPages.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Genre");
		getContentPane().add(lblNewLabel_4, "2, 10, right, default");
		
		docGenre = new JTextField();
		getContentPane().add(docGenre, "4, 10, fill, default");
		docGenre.setColumns(10);
		
		JLabel lblEditions = new JLabel("Editions");
		getContentPane().add(lblEditions, "2, 12");
		
		Editions = new JTextField();
		getContentPane().add(Editions, "4, 12, fill, default");
		Editions.setColumns(10);
		
		JButton addBtn = new JButton("Add Document");
		getContentPane().add(addBtn, "4, 16");
		
		addBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e){ 
				 String book_isbn = docISBN.getText();
                 String book_name = docTitle.getText();
                 String book_publisher = docAuth.getText();
                 String book_genre = docGenre.getText();
                 //conevert int types to string 
                 int book_pages = Integer.parseInt(docPages.getText());
                 int editionsNbr = Integer.parseInt(Editions.getText());
                
                 
                 Connection connection= Queries.conn();
                 try {
                     //Creating statement
                     Statement stmt = connection.createStatement();
                     //Query to insert in the table.
                    
         			
         			stmt.executeUpdate("INSERT INTO books(book_isbn,book_name,book_publisher,book_genre,book_pages,book_editions) VALUES ('" + book_isbn + "','" + book_name + "','" + book_publisher + "','" + book_genre + "','" + book_pages + "','" + editionsNbr +"')");
         			
         			//stmt.executeUpdate("INSERT INTO user(username,password,email,admin,name,surname) VALUES ('" + F_Username.getText() + "','"+ F_Password.getText() +"','"+ F_Email.getText() +"','"+ "0" +"','"+ F_Name.getText() + "','" + F_Username.getText()+"')");
                     //Creating Dialog Box to display message.
                     JOptionPane.showMessageDialog(null, "Book added!");
                 } 
                 catch (Exception e1) {
                     //Creating Dialog box to show any error if occured!
                     JOptionPane.showMessageDialog(null, e1);
                 }
                 
			 }
		});
		
		
    }

	}




		
		
