import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
public class FrmPrivateChatBox extends JFrame
{
	JTextArea  txtWriteMsg,txtGroupChat;
	JPanel   panelMsgEmoji;
	JButton  btnSend,btnUser1,btnUser2,btnEmoji;
    JScrollPane scrollForUsers,scrollForMsg,scrollForMessageWindow;
    JList<String> listActiveUser,listAllUser;
    DefaultListModel<String> model1,model2;
    JFrame diaEmoji;
    Dimension d;
    int totalUsers[],i=0;
    String userId1,userId2;
    static int frameXPosition,frameYPosition;
    Thread receiverThread;
    BufferedReader in;
    PrintWriter out;
    String code,msg;
    FrmPrivateChatBox(String userId1,String userId2,PrintWriter out,BufferedReader in)
	{
		super("Private Chat " + userId1 +" to " + userId2);
		this.userId1=userId1;
		this.userId2=userId2;
		this.out=out;
		this.in=in;
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				//out.println("logout");
				//out.println(userId);
				//receiverThread.stop();
				dispose();
			}
		});
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
				    out.println("privatemsg");
				    out.println(userId1);
				    out.println(userId2);
				    out.println(txtWriteMsg.getText());
				    txtWriteMsg.setText("");
				}
			}
		});
		
//--------------------------------------------------------------active user window-----------------
	
		

		
		
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
		
	
		receiverThread=new Thread(){
			public void run(){
				while(true){
					try{
						String s1=in.readLine();
						if(s1.equalsIgnoreCase("msg")){
							String userId=in.readLine();
							String msg=in.readLine();
							txtGroupChat.append("\n"+userId+": "+msg);
						}
					}
					catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		};
		receiverThread.start();
		setVisible(true);
		
	}
//-------------------------------------------------------------------------------------------------------
	
	
	
	
	 private void adjustHeight()
	    {
	        int rows = txtGroupChat.getLineCount();
	        txtGroupChat.setRows(rows);
	    }

}
