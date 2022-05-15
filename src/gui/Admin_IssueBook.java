package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DataBase.Queries;
import javax.swing.JTable;
import java.awt.ScrollPane;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JList;
import java.awt.List;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Admin_IssueBook extends JFrame {
	private JTable IsuueTable;
	private JTextField bookid;
	private JTextField issue_date;
	private JTextField return_date;
	private JTextField userid;
	private JTextField finetf;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Admin_IssueBook frame = new Admin_IssueBook();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

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
	


	          
	public Admin_IssueBook(String uid) {
		
		
	    super("Issue Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 328);
	    setVisible(true);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    
		int count=count();
		
        String columns[] = {"Id","User id","User Name","Book Id","Book Name","Issue Date","Return Date","Fine"};
        String data[][] = new String[0][7];
	    
    	Connection connection = Queries.conn();


        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	    int selectedRowIndex = table.getSelectedRow();
        	       

               userid.setText(model.getValueAt(selectedRowIndex, 1).toString());
               bookid.setText(model.getValueAt(selectedRowIndex, 3).toString());
               issue_date.setText(model.getValueAt(selectedRowIndex, 5).toString());
               return_date.setText(model.getValueAt(selectedRowIndex, 6).toString());
               finetf.setText(model.getValueAt(selectedRowIndex, 7).toString());


               
        	}
        });
        
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(265, 10, 450, 265);
	    getContentPane().add(scrollPane);
	    

	    
  	    DefaultComboBoxModel m = new DefaultComboBoxModel();
	    JComboBox list = new JComboBox();
	   
 
	    list.setBounds(83, 25, 118, 19);
	    getContentPane().add(list);
    	list.setModel(m);

 	    
    	
            
     


    
	    


		    list.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		model.setRowCount(0);

		    		try {
		    			
		                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

						String selectedvalue = list.getSelectedItem().toString();
						String result = selectedvalue.substring(0,selectedvalue.lastIndexOf(":"));

						//ResultSet rs = stmt.executeQuery("SELECT * FROM (SELECT * FROM issue_book JOIN users ON issue_book.userid=users.id  where users.id="+result+" )AS Column1, (SELECT documents.bookname FROM issue_book JOIN documents ON issue_book.bookid=documents.id )AS Column2 ");
						ResultSet rs = stmt.executeQuery( "SELECT * FROM issue_book JOIN users ON issue_book.userid=users.id JOIN documents ON issue_book.bookid=documents.id where users.id="+result+" ");

						while (rs.next()) {

						    String id = rs.getString("issue_book.id");
						    String userid = rs.getString("users.id");
						    String bookid = rs.getString("documents.id");
						    String username = rs.getString("users.username");
						    String bookname = rs.getString("documents.bookname");
						    String issuedate = rs.getString("issuedate");
						    String returndate = rs.getString("returndate");
						    String fine = rs.getString("fine");

	    	    			columns[0] = id + "";
	    	    			columns[1] = userid + "";
	    	    			columns[2] = username + "";
	    	    			columns[3] = bookid + "";
	    	    			columns[4] = bookname + "";
	    	    			columns[5] = issuedate + "";
	    	    			columns[6] = returndate + "";
	    	    			columns[7] = fine + "";

	    	    			model.addRow(columns);


						  }

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

		    	}
		    });
		    
	    	try {
	            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            ResultSet rs = stmt.executeQuery("SELECT DISTINCT users.id,users.username FROM issue_book INNER JOIN users ON issue_book.userid=users.id");

	            

	            while (rs.next()) {

	                String username = rs.getString("username");
	                String id = rs.getString("id");
	                String value=id+":"+username;


	                m.addElement(value);
	              }
	    
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
				e1.printStackTrace();
	        } 
	    	
	    	
	    	
    	    JButton Add = new JButton("Add");
    	    Add.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		if(bookid.getText().equals("")||issue_date.getText().equals("") ||return_date.getText().equals(""))
    		        {
    	    			JOptionPane.showMessageDialog(null,"Please Fill Complete Information !!");
    		        }
    	    		else {
    	                try {
    	                	PreparedStatement stmt = connection.prepareStatement("INSERT INTO issue_book(userid,bookid,issuedate,returndate) VALUES ('" +userid.getText()+ "','" + bookid.getText() +"','" + issue_date.getText()+"','" + return_date.getText()+"')");
    	                	stmt.executeUpdate(); 
				            ResultSet rs = stmt.executeQuery("SELECT * FROM issue_book WHERE id=(SELECT max(id) FROM issue_book);"); 

				            while (rs.next()) {
				                int id = rs.getInt("id");
				                String usesId = rs.getString("userid");
				                String bookId = rs.getString("bookid");
				                String issuedate = rs.getString("issuedate");
				                String returndate = rs.getString("returndate");
				                String fine = rs.getString("fine");

        	    			columns[0] = id + "";
        	    			columns[2] = usesId;
        	    			columns[3] = bookId;
        	    			columns[4] = issuedate;
        	    			columns[5] = returndate;
        	    			columns[6] = fine;

        	    			model.addRow(columns);
        	    			
        	    			userid.setText("");
        	    			bookid.setText("");
        	    			issue_date.setText("");
        	    			return_date.setText("");
        	    			finetf.setText("");

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
    	    
    	    
    	    
    	    
    	    
    	    JButton Update = new JButton("Update");
    	    Update.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		int i =table.getSelectedRow();
    	    		if(i>=0) {   	
    	    			String value = table.getModel().getValueAt(i, 0).toString();
    	        	    try {
    	        	    	
							PreparedStatement st = connection.prepareStatement("UPDATE issue_book SET userid = ? , bookid = ?  ,  issuedate = ? , returndate = ? , fine = ?   WHERE id = "+value+"");
							
							st.setString(1, userid.getText());
							st.setString(2, bookid.getText());
							st.setString(3, issue_date.getText());
							st.setString(4, return_date.getText());
							st.setString(5, finetf.getText());

							st.executeUpdate(); 
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

    	                model.setValueAt(issue_date.getText(), i, 5);
    	                model.setValueAt(return_date.getText(), i, 6);
    	                model.setValueAt(finetf.getText(), i, 7);

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
    	    
    	    bookid = new JTextField();
    	    bookid.setEnabled(false);
    	    bookid.setBounds(83, 81, 118, 19);
    	    getContentPane().add(bookid);
    	    bookid.setColumns(10);
    	    
    	    issue_date = new JTextField();
    	    issue_date.setBounds(83, 110, 118, 19);
    	    getContentPane().add(issue_date);
    	    issue_date.setColumns(10);
    	    

    	    
    	    JLabel bookidlb = new JLabel("Book Id:");
    	    bookidlb.setBounds(10, 84, 75, 13);
    	    getContentPane().add(bookidlb);
    	    
    	    JLabel lblNewLabel_3 = new JLabel("Issue Date:");
    	    lblNewLabel_3.setBounds(10, 113, 75, 13);
    	    getContentPane().add(lblNewLabel_3);
    	    
    	    return_date = new JTextField();
    	    return_date.setBounds(83, 140, 118, 20);
    	    getContentPane().add(return_date);
    	    return_date.setColumns(10);
    	    
    	    JLabel lblNewLabel_5 = new JLabel("Return Date :");
    	    lblNewLabel_5.setBounds(10, 143, 75, 14);
    	    getContentPane().add(lblNewLabel_5);
    	    
    	    JButton Clear = new JButton("Clear");
    	    Clear.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		
    				userid.setText("");
    				bookid.setText("");
    				issue_date.setText("");
    				return_date.setText("");
    				finetf.setText("");

    	    	}
    	    });
    	    Clear.setBounds(54, 246, 75, 21);
    	    getContentPane().add(Clear);
    	    
    	    JButton btnBack = new JButton("Back");
    	    btnBack.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
              	  Admin_page User_page = new Admin_page(uid);
    			  dispose();


    	    	}
    	    });
    	    
    	    userid = new JTextField();
    	    userid.setEnabled(false);
    	    userid.setBounds(83, 54, 118, 19);
    	    getContentPane().add(userid);
    	    userid.setColumns(10);
    	    
    	    JLabel useridLb = new JLabel("User Id");
    	    useridLb.setBounds(10, 57, 45, 13);
    	    getContentPane().add(useridLb);
    	    
    	    finetf = new JTextField();
    	    finetf.setColumns(10);
    	    finetf.setBounds(83, 170, 118, 20);
    	    getContentPane().add(finetf);
    	    
    	    JLabel finelbl = new JLabel("Fine :");
    	    finelbl.setBounds(10, 173, 75, 14);
    	    getContentPane().add(finelbl);
    	    
    	    JButton back = new JButton("Back");
    	    back.addActionListener(new ActionListener() {
    	    	public void actionPerformed(ActionEvent e) {
    	    		Admin_page User_page = new Admin_page(uid);
      			    dispose();
    	    	}
    	    });
    	    back.setBounds(144, 245, 75, 21);
    	    getContentPane().add(back);
    	    
    	    JLabel name = new JLabel("");
    	    name.setBounds(10, 10, 119, 13);
    	    getContentPane().add(name);
    	    
 
    	   
            
    	    try {
                Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    			ResultSet rs = st.executeQuery("SELECT * FROM users WHERE id="+uid+"");
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