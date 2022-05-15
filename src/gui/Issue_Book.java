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
	private JTextField book_id;
	private JTextField period_d;

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
	public int stock(int id) {
	    int stock = 0;

        try {
        	Connection connection = Queries.conn();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rstock = stmt.executeQuery("SELECT stock FROM documents WHERE id='" + id +"' ");
            rstock.next();
            stock = rstock.getInt("stock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stock;
	}
	public Issue_Book() {
		
		
	    super("Issue Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    
		int count=count();
		
        String columns[] = {"IID","Book Name","Book ID","Issue Date","Return Date","Period","Fine"};
        String data[][] = new String[count][7];
	    
    	Connection connection = Queries.conn();
    	
    	
    	

    	try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM issue_book JOIN documents  ON issue_book.bookid=documents.id ");
            
            int i = 0;


            
            while (rs.next()) {

            	int id = rs.getInt("issue_book.id");
                String bookid = rs.getString("bookid");
                String bookname = rs.getString("documents.bookname");
                String issuedate = rs.getString("issuedate");
                String returndate = rs.getString("returndate");
                String period = rs.getString("period");
                String fine = rs.getString("fine");
               
                
                data[i][0] = id + "";
                data[i][1] = bookname;
                data[i][2] = bookid;
                data[i][3] = issuedate;
                data[i][4] = returndate;
                data[i][5] = period;
                data[i][6] = fine+ "";
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
	    scrollPane.setBounds(263, 10, 452, 265);
	    getContentPane().add(scrollPane);
	    
    	    JButton Add = new JButton("Submit");
    	    Add.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		String value = book_id.getText();
    	    		
    	    		if(book_id.getText().equals("")||period_d.getText().equals(""))
    		        {
    	    			JOptionPane.showMessageDialog(null,"Please Fill Complete Information !!");
    		        }
    	    		else if(stock(  Integer.parseInt(book_id.getText()))==0 ) {
    	    			
    	    			JOptionPane.showMessageDialog(null,"OUT OF STOCK !!");
    	    			
    	    		}
    	    		else {
    	    			
    	    			
    	                try {
    	                	PreparedStatement stmt = connection.prepareStatement("INSERT INTO issue_book(bookid,period) VALUES ('"+ book_id.getText() +"','" + period_d.getText()+"')");
    	                	stmt.executeUpdate(); 
				            ResultSet rs = stmt.executeQuery("SELECT * FROM issue_book WHERE id=(SELECT max(id) FROM issue_book);"); 

				            while (rs.next()) {
				                int id = rs.getInt("id");
				                String bookid = rs.getString("bookid");
				                String issuedate = rs.getString("issuedate");
				                String period = rs.getString("period");
				                String fine = rs.getString("fine");

        	    			columns[0] = id + "";
        	    			columns[2] = bookid;
        	    			columns[3] = issuedate;
        	    			columns[4] = "";
        	    			columns[5] = period;
        	    			columns[6] = fine+ "";
        	    			model.addRow(columns);
        	    		
        	    			book_id.setText("");
        	    			period_d.setText("");
				            }
    	                }
    	                catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    	                
    	                  int i=stock(  Integer.parseInt(value))-1;
                         try {
    	        	    	
							PreparedStatement st = connection.prepareStatement("UPDATE documents SET  stock = "+i+"   WHERE id = "+value+"");
							
							st.executeUpdate(); 
						} catch (SQLException e1) {
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
    	                
    	                book_id.setText((String)model.getValueAt(i, 2));
    	                
    	                period_d.setText((String)model.getValueAt(i, 5));

    	            }
    	        });
    	    
    	    book_id = new JTextField();
    	    book_id.setBounds(100, 25, 118, 19);
    	    getContentPane().add(book_id);
    	    book_id.setColumns(10);
    	    

    	    
    	    JLabel lblNewLabel_1 = new JLabel("Book ID:");
    	    lblNewLabel_1.setBounds(10, 29, 75, 13);
    	    getContentPane().add(lblNewLabel_1);
    	    
    	    JLabel lblNewLabel_5 = new JLabel("Period (days) :");
    	    lblNewLabel_5.setBounds(10, 172, 92, 14);
    	    getContentPane().add(lblNewLabel_5);
    	    
    	    JButton Clear = new JButton("Clear");
    	    Clear.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		
    	    		
    				book_id.setText("");
    				
    				period_d.setText("");
    	    	}
    	    });
    	    Clear.setBounds(95, 214, 75, 21);
    	    getContentPane().add(Clear);
    	    
    	    JButton Back = new JButton("Back");
    	    Back.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    	}
    	    });
    	    Back.setBounds(180, 214, 75, 21);
    	    getContentPane().add(Back);
    	    
    	    period_d = new JTextField();
    	    period_d.setBounds(100, 169, 118, 20);
    	    getContentPane().add(period_d);
    	    period_d.setColumns(10);
    	    
    	    JLabel lblNewLabel = new JLabel("Book Name :");
    	    lblNewLabel.setBounds(10, 128, 75, 14);
    	    getContentPane().add(lblNewLabel);
    	    
    	    JLabel book_name = new JLabel(".............................................");
    	    book_name.setBounds(95, 126, 128, 19);
    	    getContentPane().add(book_name);
    	    
    	    JLabel lblNewLabel_2 = new JLabel("User Name :");
    	    lblNewLabel_2.setBounds(10, 88, 75, 14);
    	    getContentPane().add(lblNewLabel_2);
    	    
    	    JLabel lblNewLabel_3 = new JLabel("..................................................");
    	    lblNewLabel_3.setBounds(95, 88, 128, 14);
    	    getContentPane().add(lblNewLabel_3);
    	    
    	    JButton search = new JButton("Search");
    	    search.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		
    	    		String bookid =book_id.getText();
    	    	    try {
    	    	    	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    	    	    	ResultSet rs = stmt.executeQuery( "SELECT * FROM issue_book  JOIN documents ON issue_book.bookid=documents.id where documents.id="+bookid+" ");
    	    	    	while (rs.next()) {

    	    	    		String bookname = rs.getString("documents.bookname");
    	    	    		book_name.setText(bookname);
    	    	    		}
    	    	    	     
    				} 
    	    	    
    	    	    catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    	    	}
    	    });
    	    search.setBounds(110, 55, 89, 23);
    	    getContentPane().add(search);
    	    

	}
}