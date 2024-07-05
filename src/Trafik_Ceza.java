import java.awt.*;

import java.sql.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Trafik_Ceza {

	private JFrame frame;

	
	public static void main(String[] args) throws SQLException {

		  String user, pass;
			user = "postgres";
		        pass = "17011906";
		        
					Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Trafik_Ceza", user,pass);
		    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Trafik_Ceza window = new Trafik_Ceza(conn);
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
				
	}

	
	public Trafik_Ceza(Connection conn) {
		initialize(conn);
	}

	
	private void initialize(Connection conn) {
		
       			
		
		
		
		
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(119, 136, 153));
		frame.setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton officerButton = new JButton("       OFFICERS");
		officerButton.setFont(new Font("Klee", Font.PLAIN, 13));
		officerButton.setHorizontalAlignment(SwingConstants.LEFT);
		officerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					OFFICERS frame = new OFFICERS(conn);
					frame.setVisible(true);
					frame.setTitle("OFFICERS");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
		});
		ImageIcon iconika=new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/icon1.png"); 
		officerButton.setIcon(iconika);
		//officerButton.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/icon1.png"));
		
		officerButton.setBounds(180, 76, 208, 52);
		frame.getContentPane().add(officerButton);
		
		JButton ticketOpButton_1 = new JButton(" TICKET OPERATIONS");
		ticketOpButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					TICKET_OPERATIONS frame = new TICKET_OPERATIONS ( conn);
					frame.setVisible(true);
					frame.setTitle("TICKET OPERATIONS ");
				} catch (Exception e) {
					e.printStackTrace();
				}

				
			}
		});
		ticketOpButton_1.setFont(new Font("Klee", Font.PLAIN, 13));
		ticketOpButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		ticketOpButton_1.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/icon2.png"));
	
	
		ticketOpButton_1.setBounds(180, 140, 208, 52);
		frame.getContentPane().add(ticketOpButton_1);
		
		JButton ticketButton_2 = new JButton("     TICKETS");
		ticketButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					TICKETS tframe = new TICKETS( conn);
					tframe.setVisible(true);
					tframe.setTitle("TICKETS");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
		});
		ticketButton_2.setHorizontalAlignment(SwingConstants.LEFT);
		ticketButton_2.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/1279751-200.png"));
		ticketButton_2.setFont(new Font("Klee", Font.PLAIN, 13));
		
		ticketButton_2.setBounds(180, 340, 208, 52);
		frame.getContentPane().add(ticketButton_2);
		
		JButton carOwnerButton_3 = new JButton("     CAR OWNERS");
		carOwnerButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String plateNo=JOptionPane.showInputDialog(null,"PLEASE ENTER THE PLATE NUMBER OF THE CAR TO SEARCH FOR ITS OWNER","PLATE NO",JOptionPane.QUESTION_MESSAGE);
				try {
					
			    while(!( plateNo.length()<=9)) {
			    	plateNo=JOptionPane.showInputDialog (null," THE PLATE NUMBER MUST CONTAIN 9 CHARACTERS","PLEASE ENTER AGAIN",JOptionPane.WARNING_MESSAGE);
				
				}
			    String query="Select c.cname,c.csurname from citizens c,cars cs where cs.platenum=? and cs.ossn=c.ssn ";
			    PreparedStatement p = conn.prepareStatement(query);
			    p.clearParameters();
		        p.setString(1, plateNo);

		        ResultSet r = p.executeQuery();
                
		        if(r.next()) {
		           
		           JOptionPane.showMessageDialog(null,"OWNER OF THE CAR WITH PLATE NO: " + plateNo+" IS "+r.getString(1)+" "+r.getString(2));
		           


		        }

		        p.close();
			    
				}
				catch(Exception e) {
					e.printStackTrace();
					
				}
				
				
				
			}
		});
		carOwnerButton_3.setFont(new Font("Klee", Font.PLAIN, 13));
		carOwnerButton_3.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/icon3.png"));
		carOwnerButton_3.setHorizontalAlignment(SwingConstants.LEFT);
		
		carOwnerButton_3.setBounds(180, 212, 208, 52);
		frame.getContentPane().add(carOwnerButton_3);
		
		JButton analysisButton = new JButton("       ANALYSIS");
		analysisButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ANALYSIS aframe = new ANALYSIS( conn);
					aframe.setVisible(true);
					aframe.setTitle("ANALYSIS");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		analysisButton.setIcon(new ImageIcon("/Users/gnll/Desktop/DataBase/VT_PROJE/icons/38811.png"));
		analysisButton.setHorizontalAlignment(SwingConstants.LEFT);
		analysisButton.setFont(new Font("Klee", Font.PLAIN, 13));
		analysisButton.setBounds(180, 276, 203, 52);
		frame.getContentPane().add(analysisButton);
		
		
	}
}

