import java.awt.BorderLayout;
import java.math.*;
import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TICKET_OPERATIONS extends JFrame {

	private JPanel contentPane;
	private JLabel showTicLabel;
	 private final DefaultTableModel results;
	 private JTextField allTicinput;
	 private JTextField expiredTicInput;
	 private JTextField balanceSsn;
	 private JTextField showBalance;

	
	public TICKET_OPERATIONS(Connection conn) {
		setBounds(100, 100, 864, 625);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		showTicLabel = new JLabel("SHOW TICKETS");
		showTicLabel.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/show-icon-png-15.png"));
		showTicLabel.setFont(new Font("Klee", Font.PLAIN, 20)); 
		showTicLabel.setBounds(405, 20, 192, 32);
		contentPane.add(showTicLabel);
		
		String[] payTic= {"PAY ALL TICKETS","SELECT WHICH TICKET YOU WANT TO PAY"};
		
		JLabel lblPayTickets = new JLabel("PAY TICKETS");
		lblPayTickets.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/unnamed.png"));
		lblPayTickets.setFont(new Font("Klee", Font.PLAIN, 20));
		lblPayTickets.setBounds(42, 4, 171, 37);
		contentPane.add(lblPayTickets);
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/car-police-ticket-traffic-icon-traffic-ticket-png-512_512.png"));
		lblNewLabel.setBounds(708, 441, 150, 150);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/img_354758.png"));
		lblNewLabel_1.setBounds(758, 0, 100, 218);
		contentPane.add(lblNewLabel_1);
		
		JButton payAllTicketsBtn = new JButton("ALL TICKETS");
		payAllTicketsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ssn=JOptionPane.showInputDialog(null,"PLEASE ENTER SSN","SSN",JOptionPane.QUESTION_MESSAGE);
				try {
			    while( ssn.length()!=9) {
			    	ssn=JOptionPane.showInputDialog (null," PLEASE ENTER VALID SSN(ex:123456789)","ENTER SSN",JOptionPane.WARNING_MESSAGE);
				}  
			    String query = "select * from  payalltickets( '" + ssn + "')";;
			    Statement s = conn.createStatement();
		        ResultSet r = s.executeQuery(query);
		        
		        if(r.next()) {
		        	
		            String message = r.getString(1);
		            JOptionPane.showMessageDialog(null, message);
		          

		        }
				}
				catch(Exception e) {
					e.printStackTrace();
					
				}

				
			}
		});
		payAllTicketsBtn.setBounds(17, 86, 117, 29);
		contentPane.add(payAllTicketsBtn);
		
		JButton aTicketBtn = new JButton("A TICKET");
		aTicketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String ssn=JOptionPane.showInputDialog(null,"PLEASE ENTER SSN","SSN",JOptionPane.QUESTION_MESSAGE);
				String tno=JOptionPane.showInputDialog(null,"PLEASE ENTER TICKET NUMBER","TICKET NO",JOptionPane.QUESTION_MESSAGE);
				try {
			    while( ssn.length()!=9) {
			    	ssn=JOptionPane.showInputDialog (null," PLEASE ENTER VALID SSN(ex:123456789)","ENTER SSN",JOptionPane.WARNING_MESSAGE);
				}  
			    String query = "select * from  payticket( '" + ssn + "','"+tno+"')";;
			    Statement s = conn.createStatement();
		        ResultSet r = s.executeQuery(query);
		        
		        if(r.next()) {
		        	
		            String message = r.getString(1);
		            JOptionPane.showMessageDialog(null, message);
		          

		        }
				}
				catch(Exception e) {
					e.printStackTrace();
					
				}

				
			}
		});
		aTicketBtn.setBounds(146, 86, 117, 29);
		contentPane.add(aTicketBtn);
		
		
		
		
		
		Object[] columnNames = {"NAME", "SURNAME", "TICKET NO", "TICKET PRICE"};
		results = new DefaultTableModel(columnNames, 0);
		JTable tbl = new JTable(results);
		JScrollPane sp = new JScrollPane(tbl);
		sp.setFont(new Font("Klee", Font.PLAIN, 12));
		sp.setBounds(17, 127, 679, 439);
		contentPane.add(sp); 
		
		allTicinput = new JTextField();
		allTicinput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			String ssn=allTicinput.getText();
			try {

		         
		         String sqll = "select c.cname, c.csurname, gt.tno, t.price\n"
		                 + "from citizens c, giventickets gt, tickets t \n"
		                 + "where c.ssn=gt.gssn and gt.tno=t.tid and gt.gssn= ?\n"
		                 + "order by gt.gdate\n";
		         PreparedStatement p = conn.prepareStatement(sqll);

		         p.clearParameters();
		         p.setString(1, ssn);


		         ResultSet r = p.executeQuery();
		         results.setRowCount(0);
		         while(r.next()) {
		                String name = r.getString(1);
		                String surname = r.getString(2);
		                String tno = r.getString(3);
		                int price = r.getInt(4);
		                Object[] satir ={name,surname,tno,price};
		                results.addRow(satir);
		            }

		         p.close();
		         String query = "select findsum('" + ssn + "')";

		         PreparedStatement pr = conn.prepareStatement(query);;
		         ResultSet rs = pr.executeQuery();

		            while(rs.next()) {
		                int sum = rs.getInt(1);
		                String totalrow="TOTAL: ";
		                //????
		                Object[] satir ={totalrow, null, null, sum};
		                results.addRow(satir);
		            }
		            
		         pr.close();
			}
			
			catch(SQLException e ) {
				e.printStackTrace();
			}
			
			}
			
		});
		allTicinput.setFont(new Font("Klee", Font.PLAIN, 13));
		allTicinput.setText("ENTER SSN");
		allTicinput.setBounds(371, 86, 130, 26);
		contentPane.add(allTicinput);
		allTicinput.setColumns(10);
		
		expiredTicInput = new JTextField();
		expiredTicInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String ssnn=expiredTicInput.getText();
				try {

			         
			         String sql = "select c.cname, c.csurname, gt.tno, t.price\n"
			                 + "from citizens c, giventickets gt, tickets t \n"
			                 + "where gt.texpiredate < current_date and c.ssn=gt.gssn and gt.tno=t.tid and gt.gssn= ?\n"
			                 + "order by gt.gdate\n";
			         PreparedStatement p = conn.prepareStatement(sql);

			         p.clearParameters();
			         p.setString(1, ssnn);


			         ResultSet r = p.executeQuery();
			         results.setRowCount(0);
			         while(r.next()) {
			                String name = r.getString(1);
			                String surname = r.getString(2);
			                String tno = r.getString(3);
			                int price = r.getInt(4);
			                Object[] satir ={name,surname,tno,price};
			                results.addRow(satir);
			            }
			         

			         p.close();
			         
				}
				
				catch(SQLException e ) {
					e.printStackTrace();
				}
				

				
			}
		});
		expiredTicInput.setFont(new Font("Klee", Font.PLAIN, 13));
		expiredTicInput.setText("ENTER SSN");
		expiredTicInput.setBounds(533, 86, 130, 26);
		contentPane.add(expiredTicInput);
		expiredTicInput.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ALL TICKETS");
		
		lblNewLabel_2.setFont(new Font("Klee", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(391, 64, 81, 21);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("EXPIRED");
		lblNewLabel_3.setFont(new Font("Klee", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(566, 64, 53, 21);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("BALANCE");
		lblNewLabel_4.setFont(new Font("Klee", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(745, 260, 60, 21);
		contentPane.add(lblNewLabel_4);
		
		balanceSsn = new JTextField();
		balanceSsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String snn=balanceSsn.getText();
				try {
					String query = "select  findworth(?) ";
		             PreparedStatement p = conn.prepareStatement(query);
		             // PreparedStatement p = conne.prepareStatement(query);

		            
		            

		             p.clearParameters();
		             p.setString(1, snn);


		             ResultSet r = p.executeQuery();

		                if(r.next()) {

		                    long bal = r.getLong(1);
		                    		
		                    showBalance.setText(""+bal+"€");

		                }
		             p.close();
					
				}
				catch(SQLException e) {
					
					
					e.printStackTrace();
				}

			}
		});
		balanceSsn.setText("ENTER SSN");
		balanceSsn.setFont(new Font("Klee", Font.PLAIN, 13));
		balanceSsn.setColumns(10);
		balanceSsn.setBounds(715, 281, 130, 26);
		contentPane.add(balanceSsn);
		
		showBalance = new JTextField();
		showBalance.setBackground(new Color(143, 188, 143));
		showBalance.setBounds(708, 319, 150, 26);
		contentPane.add(showBalance);
		showBalance.setColumns(10);
		
		
	}
}
