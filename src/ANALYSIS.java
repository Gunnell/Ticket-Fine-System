import java.awt.BorderLayout;
import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ANALYSIS extends JFrame {

	private JPanel contentPane;
	private final DefaultTableModel results;

	
	public ANALYSIS(Connection conn) {
		
		setBounds(100, 100, 585, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton highestTicketButton = new JButton("CAR BRAND WITH THE HIGHEST TICKETS");
		highestTicketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query="Select c.brand\n"
						+ "From cars c , giventickets gt\n"
						+ "Where gt.plateno = c.platenum\n"
						+ "Group by c.brand\n"
						+ "Order by count(*) desc\n"
						+ "Limit 1";
				  

			  	Statement s = null;
		        try {
		        	ResultSet r;
		            s = conn.createStatement();
		            r= s.executeQuery(query);
		            if (r.next()) {
		            JOptionPane.showMessageDialog(null,"CAR BRAND WITH THE HIGHEST TICKETS IS  " + r.getString(1)); 
		            }
		           
		           
		            s.close();
			         }
		        catch(SQLException e){
		            e.printStackTrace();
			}
				
				
				
				
				
				
				
				
			}
		});
		highestTicketButton.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/higher-than-383f7259398185304c86dac3bd62b1dc.png"));
		highestTicketButton.setFont(new Font("Klee", Font.PLAIN, 15));
		highestTicketButton.setBounds(106, 247, 364, 52);
		contentPane.add(highestTicketButton);
		
		JButton top5Button = new JButton("            TOP 5 TICKETS");
		top5Button.setHorizontalAlignment(SwingConstants.LEFT);
		top5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String query="select t.tname from tickets t , giventickets gt where t.tid = gt.tno group by t.tid having count(tno) > 5 order by count(tno) desc";
				Statement s = null;
		        try {
		        	ResultSet r;
		            s = conn.createStatement();
		            r= s.executeQuery(query);
		            
		 			results.setRowCount(0);
		           
		          while(r.next()) {
		        	    String tname=r.getString(1);
						
						Object[] satir ={tname};
						results.addRow(satir);
		        	  
		        	  
		          }
		            s.close();
			         }
		        catch(SQLException e){
		            e.printStackTrace();
			}
				
		        
				
				
				
				
				
				
				
			}
		});
		top5Button.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/141-1419706_top-5-icon-stock-vector-579965893-top-5.png"));
		top5Button.setFont(new Font("Klee", Font.PLAIN, 15));
		top5Button.setBounds(106, 311, 364, 52);
		contentPane.add(top5Button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/computer-icons-sign-violation-d6d8ccef4faa972e645cea1170d3dcb6.png"));
		lblNewLabel.setBounds(378, 51, 106, 100);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/kissclipart-digital-analytics-clipart-google-analytics-digital-277e14f3483e4145.png"));
		lblNewLabel_1.setBounds(6, 0, 253, 256);
		contentPane.add(lblNewLabel_1);
		
		Object[] columnNames = {""};
		results = new DefaultTableModel(columnNames, 0);
		JTable tbl = new JTable(results);
		JScrollPane sp = new JScrollPane(tbl);
		sp.setFont(new Font("Klee", Font.PLAIN, 12));
		sp.setBounds(36, 375, 507, 123);
		contentPane.add(sp); 
	}
}
