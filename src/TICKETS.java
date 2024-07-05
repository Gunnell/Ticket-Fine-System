import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.*;

public class TICKETS extends JFrame {

	private JPanel contentPane;
	private final DefaultTableModel results;
	
	public TICKETS(Connection conn) {
		setResizable(false);
		
		setBounds(100, 100, 869, 698);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton giveNewTicketButton = new JButton("   GIVE NEW TICKET");
		giveNewTicketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
				
				String ticketno = JOptionPane.showInputDialog("ENTER THE TICKET NO" );
			    String ssn      = JOptionPane.showInputDialog("ENTER THE CITIZEN SSN (AS IN FORMAT: 123456789)");
			    String pno      = JOptionPane.showInputDialog("ENTER THE PLATE NUMBER ");
			    String pid      = JOptionPane.showInputDialog("ENTER THE POLICE ID (AS IN FORMAT: 123-456)");
			    String gdate    = JOptionPane.showInputDialog("ENTER THE DATE (AS IN FORMAT: YYYY-MM-DD)");
			    String address  = JOptionPane.showInputDialog("ENTER THE ADDRESS");
			    String edate   = JOptionPane.showInputDialog("ENTER THE EXPIRE DATE (AS IN FORMAT: YYYY-MM-DD)"); //check edate>gdate and some other checks
			    
			    Statement s=null;
			    
			    try {
			    String query = "insert into giventickets (tno, gssn, plateno, policeid, gdate, gaddress, texpiredate) values ( '" + ticketno + "', '" + ssn + "', '" + pno + "' ,'" + pid + "', '" + gdate + 
			                       "', '" + address + "', ' " + edate + " ')";
			    		
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
			    }catch(SQLException e){
			            e.printStackTrace();
			    }


			
				
				
			}
				
				
				
				
			
		});
		giveNewTicketButton.setFont(new Font("Klee", Font.PLAIN, 13));
		giveNewTicketButton.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/police-cop-justice-05-512.png"));
		giveNewTicketButton.setHorizontalAlignment(SwingConstants.LEFT);
		giveNewTicketButton.setBounds(398, 560, 213, 52);
		contentPane.add(giveNewTicketButton);
		
		JButton allTicketsButton = new JButton("    ALL TICKETS");
		allTicketsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			
				try {


			         String query = "select * from listtickets order by id";
			         Statement s = conn.createStatement();
			         ResultSet r = s.executeQuery(query);
			         results.setRowCount(0);
			         while(r.next()) {
			                int id = r.getInt(1);
			                String tno = r.getString(2);
			                String name = r.getString(3);
			                String surname = r.getString(4);
			                String pname = r.getString(5);
			                String psurname = r.getString(6);
			                String pno = r.getString(7);
			                String gdate = r.getString(8);
			                String edate = r.getString(9);

			                Object[] satir ={id, tno, name, surname, pname, psurname, pno, gdate, edate};
			                results.addRow(satir);
			            }

			         s.close();


			     } catch( Exception e) {
			         System.err.println( e.getClass().getName() + ": " + e.getMessage());
			         System.exit(0);
			     }
			}
		});

			
				
				
				
				
				
		
		allTicketsButton.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/ic_clear_all_48px-512.png"));
		allTicketsButton.setFont(new Font("Klee", Font.PLAIN, 13));
		allTicketsButton.setHorizontalAlignment(SwingConstants.LEFT);
		allTicketsButton.setBounds(603, 388, 213, 52);
		contentPane.add(allTicketsButton);
		
		JButton ticketsTodayButton = new JButton("TICKETS ISSUED TODAY");
		ticketsTodayButton.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/Today_date_calendar_event_day-512.png"));
		ticketsTodayButton.setFont(new Font("Klee", Font.PLAIN, 13));
		ticketsTodayButton.setHorizontalAlignment(SwingConstants.LEFT);
		ticketsTodayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String querytoday="Select * from giventickets where gdate=current_date";
				try {


					String query="Select * from listtickets where gdate=current_date";
			         Statement s = conn.createStatement();
			         ResultSet r = s.executeQuery(query);
			         results.setRowCount(0);
			         while(r.next()) {
			                int id = r.getInt(1);
			                String tno = r.getString(2);
			                String name = r.getString(3);
			                String surname = r.getString(4);
			                String pname = r.getString(5);
			                String psurname = r.getString(6);
			                String pno = r.getString(7);
			                String gdate = r.getString(8);
			                String edate = r.getString(9);

			                Object[] satir ={id, tno, name, surname, pname, psurname, pno, gdate, edate};
			                results.addRow(satir);
			            }

			         s.close();


			     } catch( Exception e) {
			         System.err.println( e.getClass().getName() + ": " + e.getMessage());
			         System.exit(0);
			     }
			}
		});
				
				
				
				
		
		ticketsTodayButton.setBounds(499, 479, 213, 52);
		contentPane.add(ticketsTodayButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/traffic-icon-png-16.png"));
		lblNewLabel.setBounds(6, 410, 309, 254);
		contentPane.add(lblNewLabel);
		
		
		
		
		Object[] columnNames = {"ID", "TICKET NO", "NAME", "LAST NAME","PFNAME","PLNAME","PLATE NO","DATE","EXPIRED DATE"};
		results = new DefaultTableModel(columnNames, 0);
		JTable tbl = new JTable(results);
		JScrollPane sp = new JScrollPane(tbl);
		sp.setFont(new Font("Klee", Font.PLAIN, 12));
		sp.setBounds(6, 0, 857, 292);
		contentPane.add(sp); 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
