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

public class Issue_Book extends JFrame {
	private JTable IsuueTable;
	private JTextField user_name;
	private JTextField user_id;
	private JTextField book_name;
	private JTextField book_isbn;
	private JTextField issue_date;
	private JTextField return_date;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Issue_Book frame = new Issue_Book();
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
			ResultSet rcount = stmt.executeQuery("SELECT COUNT(*)AS count FROM issue_book");
            rcount.next();
            count = rcount.getInt("count");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public Issue_Book() {
		
		
	    super("Issue Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    
		int count=count();
		
        String columns[] = {"ID","User Name","User ID","Book Name ","Book ISBN","Issue Date","Return Date"};
        String data[][] = new String[count][7];
	    
    	Connection connection = Queries.conn();

    	try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM issue_book");
            
            int i = 0;


            
            while (rs.next()) {

                int id = rs.getInt("id");
                String username = rs.getString("username");
                String userid = rs.getString("userid");
                String bookname = rs.getString("bookname");
                String bookisbn = rs.getString("bookisbn");
                String issuedate = rs.getString("issuedate");
                String returndate = rs.getString("returndate");

                
                data[i][0] = id + "";
                data[i][1] = username;
                data[i][2] = userid;
                data[i][3] = bookname;
                data[i][4] = bookisbn;
                data[i][5] = issuedate;
                data[i][6] = returndate+ "";
                i++;
                
              }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
			e1.printStackTrace();
        } 
    	
        

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(265, 10, 450, 265);
	    getContentPane().add(scrollPane);
	    
    	    JButton Add = new JButton("Add");
    	    Add.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		if(user_name.getText().equals("")||user_id.getText().equals("")||book_name.getText().equals("")||book_isbn.getText().equals("")||issue_date.getText().equals("") ||return_date.getText().equals(""))
    		        {
    	    			JOptionPane.showMessageDialog(null,"Please Fill Complete Information !!");
    		        }
    	    		else {
    	                try {
    	                	PreparedStatement stmt = connection.prepareStatement("INSERT INTO issue_book(username,userid,bookname,bookisbn,issuedate,returndate) VALUES ('" + user_name.getText() + "','"+ user_id.getText() +"','"+ book_name.getText() +"','"+ book_isbn.getText() + "','" + issue_date.getText()+"','" + return_date.getText()+"')");
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
        	    			issue_date.setText("");
        	    			return_date.setText("");
				            }
  
    	                
    	                  
    	                }
    	                catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	    			

    	    		}
    	    	}
    	    });
    	    Add.setBounds(10, 214, 75, 21);
    	    getContentPane().add(Add);
    	    
    	    
    	    
    	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){ 
    	          @Override
    	          public void valueChanged(ListSelectionEvent e) {
    	                int i = table.getSelectedRow();
    	                user_name.setText((String)model.getValueAt(i, 1));
    	                user_id.setText((String)model.getValueAt(i, 2));
    	                book_name.setText((String)model.getValueAt(i, 3));
    	                book_isbn.setText((String)model.getValueAt(i, 4));
    	                issue_date.setText((String)model.getValueAt(i, 5));
    	                return_date.setText((String)model.getValueAt(i, 6));

    	            }
    	        });
    	    
    	    JButton Update = new JButton("Update");
    	    Update.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		int i =table.getSelectedRow();
    	    		if(i>=0) {   	
    	    			String value = table.getModel().getValueAt(i, 0).toString();
    	        	    try {
    	        	    	
							PreparedStatement st = connection.prepareStatement("UPDATE issue_book SET username = ? , userid = ? , bookname = ? , bookisbn = ?  ,  issuedate = ? , returndate = ?   WHERE id = "+value+"");
							
							st.setString(1, user_name.getText());
							st.setString(2, user_id.getText());
							st.setString(3, book_name.getText());
							st.setString(4, book_isbn.getText());
							st.setString(5, issue_date.getText());
							st.setString(6, return_date.getText());

							st.executeUpdate(); 
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	        	    model.setValueAt(user_name.getText(), i, 1);
    	                model.setValueAt(user_id.getText(), i, 2);
    	                model.setValueAt(book_name.getText(), i, 3);
    	                model.setValueAt(book_isbn.getText(), i, 4);
    	                model.setValueAt(issue_date.getText(), i, 5);
    	                model.setValueAt(return_date.getText(), i, 6);

    	    		JOptionPane.showMessageDialog(null, "Updated Successfully");
    	    		}else
    	    		{
    	    		JOptionPane.showMessageDialog(null, "Please Select a Row First !!");
    	    		}
    	    	}
    	    });
    	    Update.setBounds(95, 214, 75, 21);
    	    getContentPane().add(Update);

    	    JButton Delete = new JButton("Delete");
    	    Delete.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    	
	    			int j=table.getSelectedRow();
	    			String value = table.getModel().getValueAt(j, 0).toString();
	    			model.addRow(new Object[]{""});
	    	        table.changeSelection(count, 0, true,false );
	    			
    	    		if(j>=0) {   	
    	    		
    	    		    model.removeRow(j);
    	    		    
                         try {
    	        	    	
							PreparedStatement st = connection.prepareStatement("DELETE FROM issue_book WHERE id = "+value+"");
					        st.executeUpdate(); 
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    	    		 
    	    		JOptionPane.showMessageDialog(null, "Deleted Successfully");
    	    		}else
    	    		{
    	    		JOptionPane.showMessageDialog(null, "Please Select a Row First !!");
    	    		}
    	    	}
    	    });
    	    Delete.setBounds(180, 214, 75, 21);
    	    getContentPane().add(Delete);
    	    
    	    JLabel lblNewLabel = new JLabel("User Name:");
    	    lblNewLabel.setBounds(10, 29, 85, 13);
    	    getContentPane().add(lblNewLabel);
    	    
    	    user_name = new JTextField();
    	    user_name.setBounds(83, 26, 118, 19);
    	    getContentPane().add(user_name);
    	    user_name.setColumns(10);
    	    
    	    user_id = new JTextField();
    	    user_id.setBounds(83, 52, 118, 19);
    	    getContentPane().add(user_id);
    	    user_id.setColumns(10);
    	    
    	    book_name = new JTextField();
    	    book_name.setBounds(83, 81, 118, 19);
    	    getContentPane().add(book_name);
    	    book_name.setColumns(10);
    	    
    	    book_isbn = new JTextField();
    	    book_isbn.setBounds(83, 110, 118, 19);
    	    getContentPane().add(book_isbn);
    	    book_isbn.setColumns(10);
    	    
    	    issue_date = new JTextField();
    	    issue_date.setBounds(83, 139, 118, 19);
    	    getContentPane().add(issue_date);
    	    issue_date.setColumns(10);
    	    

    	    
    	    JLabel lblNewLabel_1 = new JLabel("Book Name:");
    	    lblNewLabel_1.setBounds(10, 84, 75, 13);
    	    getContentPane().add(lblNewLabel_1);
    	    
    	    JLabel lblNewLabel_2 = new JLabel("Book ISBN:");
    	    lblNewLabel_2.setBounds(10, 113, 75, 13);
    	    getContentPane().add(lblNewLabel_2);
    	    
    	    JLabel lblNewLabel_3 = new JLabel("Issue Date:");
    	    lblNewLabel_3.setBounds(10, 142, 75, 13);
    	    getContentPane().add(lblNewLabel_3);
    	    
    	    JLabel lblNewLabel_4 = new JLabel("User ID:");
    	    lblNewLabel_4.setBounds(10, 58, 75, 13);
    	    getContentPane().add(lblNewLabel_4);
    	    
    	    return_date = new JTextField();
    	    return_date.setBounds(83, 169, 118, 20);
    	    getContentPane().add(return_date);
    	    return_date.setColumns(10);
    	    
    	    JLabel lblNewLabel_5 = new JLabel("Return Date :");
    	    lblNewLabel_5.setBounds(10, 172, 75, 14);
    	    getContentPane().add(lblNewLabel_5);
    	    
    	    JButton Clear = new JButton("Clear");
    	    Clear.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		
    	    		user_name.setText("");
    				user_id.setText("");
    				book_name.setText("");
    				book_isbn.setText("");
    				issue_date.setText("");
    				return_date.setText("");
    	    	}
    	    });
    	    Clear.setBounds(54, 246, 75, 21);
    	    getContentPane().add(Clear);
    	    
    	    JButton Back = new JButton("Back");
    	    Back.setBounds(139, 246, 75, 21);
    	    getContentPane().add(Back);
    	    

            
   

	}
}