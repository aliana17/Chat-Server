import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
public class FrmChatBoxServer extends JFrame
{
	JTextArea  txtWriteMsg,txtGroupChat,txtUser;
	JPanel   panelMsgEmoji;
	JButton  btnSend,btnUser1,btnUser2,btnUsers[]=new JButton[20],btnEmoji;
    
    JScrollPane scrollForUsers,scrollForMsg,scrollForMessageWindow;
    JList<String> listActiveUser,listAllUser;
    DefaultListModel<String> model1,model2;
    JFrame diaEmoji;
    Dimension d;
    int totalUsers[],i=0;
    static int frameXPosition,frameYPosition;
    Thread loginThread;
    String code="",msg="";
    Vector<ClientDetails> vecClient;
	FrmChatBoxServer()
	{
		super("Let's Chat I'm Server");
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,850,650);
		//setLocationRelativeTo(null);
	    d= Toolkit.getDefaultToolkit().getScreenSize();
		d.width=(d.width-getSize().width)/2;
		d.height=(d.height-getSize().height)/2;
		frameXPosition=d.width;
		frameYPosition= d.height;
		setLocation(d.width,d.height);
		//getContentPane().setBackground(new Color(75, 70, 170));  for background color
		
		setContentPane(new JLabel(new ImageIcon(new ImageIcon("images/chat_wallpaper.jpg").getImage().getScaledInstance(850,650,Image.SCALE_SMOOTH))));
		
		
		
//----------------------------------------txt Area for send button------------------------------------------		
		txtWriteMsg = new JTextArea(" write msg...");
		
		txtWriteMsg.setBackground(Color.WHITE);
		scrollForMsg= new JScrollPane(txtWriteMsg);
		add(scrollForMsg);
		scrollForMsg.setBounds(50,540, 500,50); 
		scrollForMsg.setForeground(new Color(255,255,255,255));
		txtWriteMsg.setAutoscrolls(true);
		txtWriteMsg.setFont(new Font("chiller",Font.BOLD,20));
		txtWriteMsg.setForeground(new Color(0,0,0,70));
		txtWriteMsg.setLineWrap(true);
		txtWriteMsg.setWrapStyleWord(true);
		txtWriteMsg.setCaretPosition(1);
		txtWriteMsg.setAutoscrolls(true);
		txtWriteMsg.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,150)));
		
		//txtWriteMsg.setFocusable(false);   to make text area inactive to write
		//txtWriteMsg.requestFocus(false);
		if(txtWriteMsg.getText().isEmpty())
		{
			txtWriteMsg.setForeground(new Color(0,0,0,70));
		    txtWriteMsg.setText(" write msg...");
		}
		txtWriteMsg.addMouseListener(new MouseAdapter() {   // mouseMotionListener is not working here and in notepad also
			public void mouseClicked(MouseEvent me)
			{
				if(me.getButton()==1)
				if(txtWriteMsg.getText().equals(" write msg..."))
				   txtWriteMsg.setText("");
				   txtWriteMsg.setForeground(new Color(0,0,0));
				  // txtWriteMsg.setCaretPosition(1);
		    }
			
			
			
		});
		
		
		
//----------------------------------------txt Area for group chat------------------------------------------		
				txtGroupChat = new JTextArea(2,2);            //"\u1f638"
				txtGroupChat.setAutoscrolls(true);
				txtGroupChat.setFont(new Font("chiller",Font.BOLD,24));
				txtGroupChat.setForeground(new Color(0,0,0));
				txtGroupChat.setLineWrap(true);
				txtGroupChat.setWrapStyleWord(true);
				//txtGroupChat.setCaretPosition(1);  // will show error 
				
				txtGroupChat.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,150)));
				txtGroupChat.setFocusable(false);  // to make text area inactive to write
				//txtGroupChat.requestFocus(false);
				txtGroupChat.setEditable(false);   // to make text area in active to write
				
				scrollForMessageWindow = new JScrollPane(txtGroupChat);
				scrollForMessageWindow .setBounds(50,0, 500,540); // must  set by scroll bar not txtarea here
				scrollForMessageWindow .setAlignmentX(RIGHT_ALIGNMENT);
				
			    add(scrollForMessageWindow);		
			    
			 /*   txtGroupChat.getDocument().addDocumentListener(new DocumentListener() {  // no adapter available
			    	public void insertUpdate(DocumentEvent e)
			    	{
			    		adjustHeight();
			    	}
			    	 public void removeUpdate(DocumentEvent e)
			    	    {
			    	        adjustHeight();
			    	    }

			    	   
			    	    public void changedUpdate(DocumentEvent e)  {}
			    });*/
//------------------------------------------send Button--------------------------------------------------
		
		btnSend = new JButton("send");
		btnSend.setFont(new Font("chiller",Font.BOLD,34));
		btnSend.setBounds(560,540,100,50);
	   // setComponentZOrder(btnSend,0);
	   // System.out.println(getComponentZOrder(this));
	   // System.out.println(getComponentZOrder(txtGroupChat));
		btnSend.setForeground(new Color(56, 11, 14));
		btnSend.setBorderPainted(false);
		btnSend.setContentAreaFilled(false);
		//btnSend.setFocusTraversalKeysEnabled(true);
		btnSend.setFocusPainted(false);
		
		add(btnSend);
		btnSend.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me)
			{
				btnSend.setForeground(Color.red);
			}
			public void mouseExited(MouseEvent me)
			{
				btnSend.setForeground(new Color(56, 11, 14));
			}
			
		});
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				if(!(txtWriteMsg.getText().equals(" write msg..."))&& !(txtWriteMsg.getText().isEmpty()))
				{
					txtGroupChat.append("\n"+txtWriteMsg.getText());
					for(int i=0;i<vecClient.size();i++){
						vecClient.get(i).out.println("msg");
						vecClient.get(i).out.println("Admin");
						vecClient.get(i).out.println(txtWriteMsg.getText());
					}
				    txtWriteMsg.setText("");
				}
			}
		});
		
//--------------------------------------------------------------active user window-----------------
	
		

		model1 =new DefaultListModel<>();
		listActiveUser= new JList(model1);
		listActiveUser.setSelectionForeground(new Color(0,0,0,255));
		listActiveUser.setSelectionBackground(new Color(204, 20, 51,100));
		listActiveUser.setFont(new Font("chiller",Font.BOLD,24));
		
		/*for(int i=0;i<FrmServer.cnt;i++)
		{
		model1.addElement(FrmServer.data[i][0]);
	    model1.getElementAt(i).addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent ae)
	         {
	        	 System.out.println("hi");
	         }
           });
		}*/
		
		
		scrollForUsers = new JScrollPane(listActiveUser);
		
		scrollForUsers.setBounds(650,50,250,450);
		add(scrollForUsers);
		
		
//----------------------------------Emoji Button--------------------------------------------------------
		
		btnEmoji = new JButton("(:");
		btnEmoji.setFont(new Font("chiller",Font.BOLD,24));
		btnEmoji.setBounds(0,540,80,50);
	    btnEmoji.setAlignmentX(LEFT_ALIGNMENT);
		btnEmoji.setForeground(new Color(56, 11, 14));
		btnEmoji.setBorderPainted(false);
		btnEmoji.setContentAreaFilled(false);
		//btnEmoji.setFocusTraversalKeysEnabled(true);
		btnEmoji.setFocusPainted(false);
		
		add(btnEmoji);
		btnEmoji.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me)
			{
				btnEmoji.setForeground(Color.red);
			}
			public void mouseExited(MouseEvent me)
			{
				btnEmoji.setForeground(new Color(56, 11, 14));
			}
			
		});
		btnEmoji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				/*diaEmoji = new JFrame();
				diaEmoji.setBounds(d.width,d.height,200,150);
				//diaEmoji.setLocationByPlatform(true);
				FrmChatBox.this.setVisible(true);*/
				
				
				new FrmEmoji();
				
			}
		});
		
		//chatting code
		try{
			InetAddress inet=InetAddress.getLocalHost();
			ServerSocket ss=new ServerSocket(2000,500,inet);
			vecClient=new Vector<>();
			loginThread=new Thread(){
				public void run(){
					try{
						while(true){
							Socket cs=ss.accept();
							BufferedReader in=new BufferedReader(new InputStreamReader(cs.getInputStream()));
							PrintWriter out=new PrintWriter(cs.getOutputStream(),true);
							String lst="";
							for(int j=0;j<model1.size();j++){
								lst+=model1.getElementAt(j);
								lst+=":";
							}
							if(!lst.equals(""))
								lst=lst.substring(0,lst.length()-1);
							out.println("currentUsers");
							out.println(lst);
							code=in.readLine();
							if(code.equals("login")){
								String userId=in.readLine();
								Thread t=new Thread(){
									public void run(){//Receiver of Server
										while(true){
											try{
												code=in.readLine();
												if(code.equalsIgnoreCase("privatemsg")){
													String uid1=in.readLine();
													String uid2=in.readLine();
													msg=in.readLine();
													PrintWriter pout=null;
													for(int i=0;i<vecClient.size();i++){
														if(uid2.equals(vecClient.get(i).userId)){
															pout=vecClient.get(i).out;
															System.out.println("out found");
															System.out.println(uid2);
															break;
														}
													}
													if(pout!=null){
														System.out.println("pout not null");
														pout.println("privatemsg");
														pout.println(uid1);
														pout.println(msg);
													}
												}
												if(code.equalsIgnoreCase("msg")){
													String uid="";
													uid=in.readLine();
													msg=in.readLine();
													for(int i=0;i<vecClient.size();i++){
														if(in!=vecClient.get(i).in){
															vecClient.get(i).out.println("msg");
															vecClient.get(i).out.println(uid);
															vecClient.get(i).out.println(msg);
														}
													}
													txtGroupChat.append("\n"+uid+":" +msg);
												}
												if(code.equalsIgnoreCase("logout")){
													msg=in.readLine();
													model1.removeElement(msg);
													for(int i=0;i<vecClient.size();i++){
														vecClient.get(i).out.println("logout");
														vecClient.get(i).out.println(msg);	
													}
												}
											}
											catch(IOException e){
												e.printStackTrace();
											}
										}
									}
								};
								t.start();
								vecClient.addElement(new ClientDetails(cs,out,in,userId,t));
								model1.addElement(userId);
								for(int i=0;i<vecClient.size();i++){
									if(!vecClient.get(i).userId.equals(userId)){
										vecClient.get(i).out.println("newlogin");
										vecClient.get(i).out.println(userId);
									}
								}
								
							}
						}
					}
					catch(IOException e){
						e.printStackTrace();
					}
				}
			};
			loginThread.start();
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		

		
		setVisible(true);
		
	}
//-------------------------------------------------------------------------------------------------------
	
	
	
	
	 private void adjustHeight()
	    {
	        int rows = txtGroupChat.getLineCount();
	        txtGroupChat.setRows(rows);
	    }

}
class ClientDetails{
	Socket s;
	PrintWriter out;
	BufferedReader in;
	String userId;
	Thread receiverThread;
	ClientDetails(Socket s,PrintWriter out,BufferedReader in,String userId,Thread receiverThread){
		this.s=s;
		this.out=out;
		this.in=in;
		this.userId=userId;
		this.receiverThread=receiverThread;
	}
}