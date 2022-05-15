package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public Return_Book() {
		
		
	    super("Return Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    
		int count=count();
		
        String columns[] = {"IID","Book Name ","Book ID","Issue Date","Return Date","Period","Fine"};
        String data[][] = new String[count][7];
	    
    	Connection connection = Queries.conn();

    	try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM issue_book JOIN documents  ON issue_book.bookid=documents.id");
            
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
	    scrollPane.setBounds(151, 10, 564, 265);
	    getContentPane().add(scrollPane);
	    
    	    JButton returnbook = new JButton("Return");
    	    returnbook.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		
    	    		long millis=System.currentTimeMillis();
    	    		java.sql.Date Now=new java.sql.Date(millis);
    	    		String date =Now.toString();
    	    		int i =table.getSelectedRow();
    	    		String value = table.getModel().getValueAt(i, 0).toString();
    	    		
    	    		if(i>=0 )
    		        {
    	    			if(data[i][4]==null){
    	    				try {
        	        	    	
    							PreparedStatement st = connection.prepareStatement("UPDATE issue_book SET  returndate = ?   WHERE id = "+value+"");
    							st.setString(1, date);
    							st.executeUpdate(); 
    						} catch (SQLException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
                            model.setValueAt(Now, i, 4);
                            
                            String valuestock = table.getModel().getValueAt(i, 2).toString();
                            int j=stock(  Integer.parseInt(valuestock))+1;
                            try {
       	        	    	
    							PreparedStatement st = connection.prepareStatement("UPDATE documents SET  stock = "+j+"   WHERE id = "+valuestock+"");
    							
    							st.executeUpdate(); 
    						} catch (SQLException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
                            
                            
                            String issue = table.getModel().getValueAt(i, 3).toString();
                            Date issue_date;
							try {
								issue_date = new SimpleDateFormat("yyyy-MM-dd").parse(issue);
								String elp = table.getModel().getValueAt(i, 5).toString();
								int elpsed=Integer.parseInt(elp);  
								long date1 =issue_date.getTime() ;
								long date2=Now.getTime();
								
								long diff=date2 - date1;
								int c=(int)diff;
								c=c/(1000 * 60 * 60* 24);
								int Fine = (c-elpsed)*100;
								String fine=String.valueOf(Fine);
								if(c>elpsed) {
									
									
									 try {
				       	        	    	
			    							PreparedStatement st = connection.prepareStatement("UPDATE issue_book SET  fine = ?   WHERE id = "+value+"");
			    							st.setString(1, fine);
			    							st.executeUpdate(); 
			    						} catch (SQLException e1) {
			    							// TODO Auto-generated catch block
			    							e1.printStackTrace();
			    						}
									 model.setValueAt(fine, i, 6);
									
									 System.out.print(fine)		;																				
									
									
									JOptionPane.showMessageDialog(null, "You Have To Pay A FINE :"+ Fine+"DT !!");
									JOptionPane.showMessageDialog(null, "Book Returned Successfully!!");
								}
								else {
									 JOptionPane.showMessageDialog(null, "Book Returned Successfully!!");
								}
								
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            
                           
                         
                            
                            
                            
                           
    	    			}
    	    			else {
    	    				JOptionPane.showMessageDialog(null, "Already Returned!! Select A Book to Return");
    	    			}
                        
    		        }
    	    		else {
    	    			JOptionPane.showMessageDialog(null, "Please Select a Book To Return !!");
    	    		}
    	    	}
    	    });
    	    returnbook.setBounds(20, 189, 102, 31);
    	    getContentPane().add(returnbook);
    	    
    	    
    	    
    	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){ 
    	          @Override
    	          public void valueChanged(ListSelectionEvent e) {
    	                int i = table.getSelectedRow();
    	               
    	              
    	               

    	            }
    	        });
    	    
    	    JLabel lblNewLabel_4 = new JLabel("User Name:");
    	    lblNewLabel_4.setBounds(37, 56, 75, 13);
    	    getContentPane().add(lblNewLabel_4);
    	    
    	    JButton Back = new JButton("Back");
    	    Back.setBounds(20, 231, 102, 31);
    	    getContentPane().add(Back);
    	    
    	    JLabel user_name = new JLabel("............");
    	    user_name.setBounds(47, 80, 46, 14);
    	    getContentPane().add(user_name);
    	    

            
   

	}
}