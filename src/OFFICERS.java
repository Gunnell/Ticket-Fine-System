import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.Action;
import javax.swing.ImageIcon;
import java.io.*;
import javax.swing.JTable;

public class OFFICERS extends JFrame {

	private JPanel contentPane;
	private JTextField lname;
	private JTextField fname;
    private Connection konnekt;
    private final DefaultTableModel results;
	
	public OFFICERS(Connection conn) {
		konnekt=conn;
	    setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(119, 136, 153));
		contentPane.setForeground(new Color(47, 79, 79));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton delete_officer = new JButton("DELETE OFFICER");
		delete_officer.setIcon(new ImageIcon("/Users/gnll/Downloads/WhatsApp Image 2020-12-19 at 19.21.47 (1).jpeg"));
		delete_officer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pid=JOptionPane.showInputDialog(null,"PLEASE ENTER THE OFFICER ID OF THE OFFICER TO BE DELETED","ID",JOptionPane.QUESTION_MESSAGE);
				try {
			    while( pid.length()!=7 || pid.charAt(3)!='-') {
			    	pid=JOptionPane.showInputDialog (null," PLEASE ENTER VALID ID(ex:123-456)","ENTER ID",JOptionPane.WARNING_MESSAGE);
				
				}  
			    
				}
				catch(Exception e) {
					e.printStackTrace();
					
				}
				if(pid!=null) {
				
				JOptionPane.showConfirmDialog(null, "DO YOU CONFIRM TO DELETE OFFICER WITH ID"+pid);
				
			
				if(!check(pid))
				 	JOptionPane.showMessageDialog(null, "THIS POLICE DOES NOT EXIST ");
				

				
				
				else {
					polis_sil(pid);

				JOptionPane.showMessageDialog(null, "OFFICER WITH "+pid+"HAS BEEN DELETED");
								}
				
			}
			}
		});
		delete_officer.setFont(new Font(",Klee", Font.PLAIN, 13));
		delete_officer.setForeground(new Color(0, 0, 0));
		delete_officer.setBounds(22, 6, 157, 47);
		contentPane.add(delete_officer);
		
		JLabel lblNewLabel = new JLabel(" ENTER OFFICER'S INFORMATION TO SEE");
		lblNewLabel.setFont(new Font("Klee", Font.PLAIN, 13));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(22, 62, 289, 16);
		contentPane.add(lblNewLabel);
		
		fname = new JTextField();
		fname.setText("    FIRST NAME");
		fname.setForeground(new Color(128, 128, 128));
		fname.setFont(new Font("Klee", Font.PLAIN, 13));
		fname.setColumns(10);
		fname.setBounds(21, 128, 130, 26);
		contentPane.add(fname);

		
		lname = new JTextField();
		
		String[] opts = { "PEOPLE WHO ARE MOST PUNISHED BY THE OFFICER", "PEOPLE WHO ARE MOST PUNISHED BY THE OFFICER AND HIS/HER PARTNER" };
		JComboBox comboBox = new JComboBox(opts);
		lname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String input_fname=fname.getText();
				String input_lname=lname.getText(); 
				
			
			
			
			
		        String selected = (String) comboBox.getSelectedItem();
		        if(selected.equals("PEOPLE WHO ARE MOST PUNISHED BY THE OFFICER")) {
		        	
		        	
		        	goster_cezalı_1(input_fname ,input_lname);
		        	
		        }
		        else {
		        	
		        	goster_cezalı_2(input_fname,input_lname);
		        }
				

				
			}
		}); 
		lname.setForeground(new Color(128, 128, 128));
		lname.setFont(new Font("Klee", Font.PLAIN, 13));
		lname.setText("    LAST NAME");
		lname.setBounds(170, 128, 130, 26);  	

		contentPane.add(lname);
		lname.setColumns(10);
		
				
		
	
		comboBox.setFont(new Font("Klee", Font.PLAIN, 13));
		comboBox.setBounds(21, 90, 383, 27);
		contentPane.add(comboBox);
		
		Object[] columnNames = {"FIRST NAME", "LAST NAME"};
		results = new DefaultTableModel(columnNames, 0);
		JTable tbl = new JTable(results);
		JScrollPane sp = new JScrollPane(tbl);
		sp.setFont(new Font("Klee", Font.PLAIN, 12));
		sp.setBounds(22, 166, 281, 78);
		contentPane.add(sp); 
		  		
		
	}
	
	 
	
	
		
	public boolean check(String pold){
		

	  boolean kontrol=true;
	  String query="select fname from police where pid='"+pold+"'";
	  

	  	Statement s = null;
        try {
        	ResultSet r;
            s = konnekt.createStatement();
            r= s.executeQuery(query);
                      if (!r.next()) {
               kontrol =false;// boyle biri yok
               
            
            }
           
            konnekt.setAutoCommit(false);
            konnekt.commit();
            s.close();
	}catch(SQLException e){
            e.printStackTrace();
	}
        return kontrol;
	  }
	
	public void polis_sil(String pid) {
		String query="select deletepolice('"+pid+"')";
		PreparedStatement p=null;
		try {
			ResultSet r;
			p=konnekt.prepareStatement(query);
			r=p.executeQuery();
			 konnekt.setAutoCommit(false);
	            konnekt.commit();
	            p.close();
			
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	 
	 public void goster_cezalı_1(String fname,String lname){
	 String query="Select cname,csurname From police ,citizens ,giventickets Where fname=?  And lname=? And pid=policeid and gssn=ssn Group by ssn Order by count(*) desc limit 3";
			
	 
	
        
		try  {
			
		

			PreparedStatement p=konnekt.prepareStatement(query);
			
			
			
			p.clearParameters();  
			p.setString(1,fname);
			p.setString(2,lname);
			ResultSet r=p.executeQuery();
			results.setRowCount(0);
			
			
			
			while(r.next()) {
				String cname=r.getString(1);
				String clname=r.getString(2);
				Object[] satir ={cname, clname};
				results.addRow(satir);
				
				
				
				
			}
			
            p.close();
			 konnekt.setAutoCommit(false);
	            konnekt.commit();

			
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
	 }
	 
	    
	
	 
	 
	 public void goster_cezalı_2(String fname,String lname){
	    String query="Select cname,csurname From giventickets g,police p1,citizens c  Where p1.fname=? And p1.lname=? And g.policeid=p1.pid and g.gssn=c.ssn "
	    		+ "Intersect Select cname,csurname From giventickets g,police p1,police p2,citizens c Where p1.partnerid=p2.pid and p2.pid=g.policeid and g.gssn=c.ssn and p1.fname="
	    		+ "? And p1.lname=? ";
			
  

	  
	   
	  
			
        try {
        	 PreparedStatement p = konnekt.prepareStatement(query);
        	 

 			p.clearParameters();  
 			p.setString(1,fname);
 			p.setString(2,lname);
 			p.setString(3,fname);
 			p.setString(4,lname);
 			ResultSet r=p.executeQuery();
 			results.setRowCount(0);
 			
          while(r.next()) {
        	    String cname=r.getString(1);
				String clname=r.getString(2);
				Object[] satir ={cname, clname};
				results.addRow(satir);
        	          	  
          }
            konnekt.setAutoCommit(false);
            konnekt.commit();
            p.close();
	}catch(SQLException e){
            e.printStackTrace();
	}	
	 }
	   
	   
	   
	  
	   }
	 
	 
	 
	 
	
 
	
	
	 
