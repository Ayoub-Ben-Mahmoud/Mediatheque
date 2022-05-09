package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class Return_Book extends JFrame {
	private JTable ReturnTable;
	private JTextField user_id;
	private JTextField return_date;
	private JTextField dayselp;
	private JTextField fine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Return_Book frame = new Return_Book();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	public int count() {
	    int count = 0;

        try {
        	Connection connection = Queries.conn();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rcount = stmt.executeQuery("SELECT COUNT(*)AS count FROM return_book");
            rcount.next();
            count = rcount.getInt("count");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public Return_Book() {
		
		
	    super("Return Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    
		int count=count();
		
        String columns[] = {"ID","Book Name ","Book ISBN","Return Date","Days Elp","Fine"};
        String data[][] = new String[count][6];
	    
    	Connection connection = Queries.conn();

    	
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(265, 10, 450, 265);
	    getContentPane().add(scrollPane);
	    
    	    JButton returnbook = new JButton("Return");
    	    returnbook.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		if(user_name.getText().equals("")||user_id.getText().equals("")||book_name.getText().equals("")||book_isbn.getText().equals("")||return_date.getText().equals("") ||dayselp.getText().equals(""))
    		        {
    	    			JOptionPane.showMessageDialog(null,"Please Fill Complete Information !!");
    		        }
    	    		else {
    	                try {
    	                	PreparedStatement stmt = connection.prepareStatement("INSERT INTO issue_book(username,userid,bookname,bookisbn,issuedate,returndate) VALUES ('" + user_name.getText() + "','"+ user_id.getText() +"','"+ book_name.getText() +"','"+ book_isbn.getText() + "','" + return_date.getText()+"','" + dayselp.getText()+"')");
    	                	stmt.executeUpdate(); 
				            ResultSet rs = stmt.executeQuery("SELECT * FROM issue_book WHERE id=(SELECT max(id) FROM issue_book);"); 

				            while (rs.next()) {
				                int id = rs.getInt("id");
				                String username = rs.getString("username");
				                String userid = rs.getString("userid");
				                String bookname = rs.getString("bookname");
				                String bookisbn = rs.getString("bookisbn");
				                String issuedate = rs.getString("issuedate");
				                String returndate = rs.getString("returndate");

        	    			columns[0] = id + "";
        	    			columns[1] = username;
        	    			columns[2] = userid;
        	    			columns[3] = bookname;
        	    			columns[4] = bookisbn;
        	    			columns[5] = issuedate;
        	    			columns[6] = returndate+ "";
     
        	    			model.addRow(columns);
        	    			
        	    			user_name.setText("");
        	    			user_id.setText("");
        	    			book_name.setText("");
        	    			book_isbn.setText("");
        	    			return_date.setText("");
        	    			dayselp.setText("");
				            }
  
    	                
    	                  
    	                }
    	                catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	    			

    	    		}
    	    	}
    	    });
    	    returnbook.setBounds(10, 214, 75, 21);
    	    getContentPane().add(returnbook);
    	    
    	    
    	    
    	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){ 
    	          @Override
    	          public void valueChanged(ListSelectionEvent e) {
    	                int i = table.getSelectedRow();
    	                user_name.setText((String)model.getValueAt(i, 1));
    	                user_id.setText((String)model.getValueAt(i, 2));
    	                book_name.setText((String)model.getValueAt(i, 3));
    	                book_isbn.setText((String)model.getValueAt(i, 4));
    	                return_date.setText((String)model.getValueAt(i, 5));
    	                dayselp.setText((String)model.getValueAt(i, 6));

    	            }
    	        });
    	    
    	    JLabel lblNewLabel = new JLabel("User Name:");
    	    lblNewLabel.setBounds(10, 84, 85, 13);
    	    getContentPane().add(lblNewLabel);
    	    
    	    user_id = new JTextField();
    	    user_id.addInputMethodListener(new InputMethodListener() {
    	    	public void caretPositionChanged(InputMethodEvent event) {
    	    	}
    	    	public void inputMethodTextChanged(InputMethodEvent event) {
    	    	
    	    	}
    	    });
    	    user_id.setBounds(93, 54, 118, 19);
    	    getContentPane().add(user_id);
    	    user_id.setColumns(10);
    	    
    	    return_date = new JTextField();
    	    return_date.setBounds(93, 108, 118, 19);
    	    getContentPane().add(return_date);
    	    return_date.setColumns(10);
    	    
    	    JLabel lblNewLabel_3 = new JLabel("Return Date:");
    	    lblNewLabel_3.setBounds(10, 112, 75, 13);
    	    getContentPane().add(lblNewLabel_3);
    	    
    	    JLabel lblNewLabel_4 = new JLabel("User ID:");
    	    lblNewLabel_4.setBounds(10, 58, 75, 13);
    	    getContentPane().add(lblNewLabel_4);
    	    
    	    dayselp = new JTextField();
    	    dayselp.setBounds(93, 138, 118, 20);
    	    getContentPane().add(dayselp);
    	    dayselp.setColumns(10);
    	    
    	    JLabel lblNewLabel_5 = new JLabel("Days Elapsed :");
    	    lblNewLabel_5.setBounds(10, 138, 75, 14);
    	    getContentPane().add(lblNewLabel_5);
    	    
    	    JButton Clear = new JButton("Clear");
    	    Clear.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		
    	    		user_name.setText("");
    				user_id.setText("");
    				book_name.setText("");
    				book_isbn.setText("");
    				return_date.setText("");
    				dayselp.setText("");
    	    	}
    	    });
    	    Clear.setBounds(94, 214, 75, 21);
    	    getContentPane().add(Clear);
    	    
    	    JButton Back = new JButton("Back");
    	    Back.setBounds(179, 214, 75, 21);
    	    getContentPane().add(Back);
    	    
    	    JLabel lblNewLabel_1 = new JLabel("Fine :");
    	    lblNewLabel_1.setBounds(10, 172, 46, 14);
    	    getContentPane().add(lblNewLabel_1);
    	    
    	    fine = new JTextField();
    	    fine.setBounds(93, 169, 118, 20);
    	    getContentPane().add(fine);
    	    fine.setColumns(10);
    	    

            
   

	}
}