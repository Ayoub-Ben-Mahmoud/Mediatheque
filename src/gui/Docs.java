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
//import net.proteanit.sql.DbUtils;


public class Docs extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Docs frame = new Docs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Docs() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
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
		
		JLabel lblNewLabel = new JLabel("Book ID");
		contentPane.add(lblNewLabel, "2, 2, right, default");
		
		textField = new JTextField();
		contentPane.add(textField, "4, 2");
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("User ID");
		contentPane.add(lblNewLabel_1, "2, 4, right, default");
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, "4, 4, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Period");
		contentPane.add(lblNewLabel_2, "2, 6, right, default");
		
		textField_2 = new JTextField();
		contentPane.add(textField_2, "4, 6, fill, default");
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Issued Day (DD-MM-YY)");
		contentPane.add(lblNewLabel_3, "2, 8, right, default");
		
		textField_3 = new JTextField();
		contentPane.add(textField_3, "4, 8, fill, default");
		textField_3.setColumns(10);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(submitBtn, "4, 14, center, default");
	//Testing Issued Books
	JButton issued_but=new JButton("View Issued Books");//creating instance of JButton to view the issued books
    issued_but.setBounds(280,20,160,25);//x axis, y axis, width, height 
    issued_but.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                 
                JFrame f = new JFrame("Users List");
                //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 
                 
                Connection connection = null;
				try {
					connection = Queries.conn();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                String sql="select * from Documents";
                try {
                    Statement stmt = connection.createStatement();
                     stmt.executeUpdate("mediatheque");
                    stmt=connection.createStatement();
                    ResultSet rs=stmt.executeQuery(sql);
                    JTable book_list= new JTable();
   //                book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                     
                    JScrollPane scrollPane = new JScrollPane(book_list);
 
                    f.getContentPane().add(scrollPane);
                    f.setSize(800, 400);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null, e1);
                }                                   
    }
        }
    );
     
}
}
