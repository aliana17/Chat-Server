import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
class FrmServer extends JFrame implements ActionListener{
	JMenu file;
	JMenuBar mbar;
	JDialog j;
	JMenuItem showuser,createchatroom,exit;
	static int cnt;                 // change 
	static String data[][]=null;   // change   defined as static
	FrmServer(){
		file=new JMenu("File");
		showuser=new JMenuItem("ShowUser");
		createchatroom=new JMenuItem("CreateChatRoom");
		exit=new JMenuItem("Exit");
		file.add(showuser);
		file.add(createchatroom);
		file.add(exit);
		mbar=new JMenuBar();
		mbar.add(file);
		showuser.addActionListener(this);
		createchatroom.addActionListener(this);
		exit.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setJMenuBar(mbar);
		setSize(400,400);
		setVisible(true);
	    
	}	
	public void actionPerformed(ActionEvent ae){
		JMenuItem mi=(JMenuItem)ae.getSource();
		if(mi==showuser){
			String head[]={"UserName","PassWord"};
			Database db=new Database();
			
			try{
				ResultSet rst=db.runSelectQuery("select count(*) from users");
				rst.next();
				 cnt=rst.getInt(1);                        // change** cnt declared as static
				db.closeDatabase();
				data=new String[cnt][2];
				rst=db.runSelectQuery("select * from users");
				int i=1;
				while(rst.next()){
					data[i-1][0]=rst.getString(1);
					data[i-1][1]=rst.getString(2);
					i++;
				}
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
			j=new JDialog(this,"Login Users",true);
			JTable jtbl=new JTable(data,head);
			JScrollPane jsp=new JScrollPane(jtbl);
			j.add(jsp);
			jtbl.setEnabled(false);  // so no one can change the data
			j.setSize(400,400);
			j.setVisible(true);		
			
		}
		else if(mi==createchatroom){
			new FrmChatBoxServer();
		}
		else if(mi==exit){
			dispose();
		}
	}
}