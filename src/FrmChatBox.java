import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
public class FrmChatBox extends JFrame
{
	JTextArea  txtWriteMsg,txtGroupChat,txtUser;
	JPanel   panelMsgEmoji;
	JButton  btnSend,btnUser1,btnUser2,btnUsers[]=new JButton[20],btnEmoji;
	Vector<FrmPrivateChatBox> frmVector=new Vector<>();
    JScrollPane scrollForUsers,scrollForMsg,scrollForMessageWindow;
    JList<String> listActiveUser,listAllUser;
    DefaultListModel<String> model1,model2;
    JFrame diaEmoji;
    Dimension d;
    int totalUsers[],i=0;
    String userId;
    static int frameXPosition,frameYPosition;
    Thread receiverThread;
    BufferedReader in;
    PrintWriter out;
    String code,msg;
    FrmChatBox(String userId)
	{
		super("Let's Chat User Name is " + userId);
		this.userId=userId;
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				out.println("logout");
				out.println(userId);
				receiverThread.stop();
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
				    out.println("msg");
				    out.println(userId);
				    out.println(txtWriteMsg.getText());
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
		
		//for(int i=0;i<FrmServer.cnt;i++)
		//{
		//model1.addElement(FrmServer.data[i][0]);
	   /* model1.getElementAt(i).addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent ae)
	         {
	        	 System.out.println("hi");
	         }
           });*/
		//}
		
		
		scrollForUsers = new JScrollPane(listActiveUser);
		
		scrollForUsers.setBounds(650,50,250,450);
		add(scrollForUsers);
		listActiveUser.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent me){
				if(me.getClickCount()==2){
					new FrmPrivateChatBox(userId, (String)listActiveUser.getSelectedValue(),out,in);
				}
			}
		});
		
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
				FileInputStream fis=new FileInputStream("documents/host.txt");
				Properties p=new Properties();
				p.load(fis);
				InetAddress inet=InetAddress.getByName(p.getProperty("name"));
				Socket s=new Socket(inet,2000);
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				out=new PrintWriter(s.getOutputStream(),true);
				code=in.readLine();
				if(code.equalsIgnoreCase("currentUsers")){
					msg=in.readLine();
					String cusers[]=msg.split(":");
					for(int i=0;i<cusers.length;i++)
						model1.addElement(cusers[i]);
				}
				out.println("login");
				out.println(userId);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		receiverThread=new Thread(){
			public void run(){
				while(true){
					try{
						String s1=in.readLine();
						if(s1.equalsIgnoreCase("newlogin")){
							String s2=in.readLine();
							model1.addElement(s2);
						}
						else if(s1.equalsIgnoreCase("logout")){
							String s2=in.readLine();
							model1.removeElement(s2);
						}
						else if(s1.equalsIgnoreCase("msg")){
							String userId=in.readLine();
							String msg=in.readLine();
							txtGroupChat.append("\n"+userId+": "+msg);
						}
						else if(s1.equalsIgnoreCase("privatemsg")){
							String uid1=in.readLine();
							String msg=in.readLine();
							boolean found=false;
							System.out.println(frmVector.size());
							for(int i=0;i<frmVector.size();i++){
								System.out.println(uid1);
								System.out.println(userId);
								System.out.println(msg);
								
								if(frmVector.get(i).userId1.equals(uid1) && frmVector.get(i).userId2.equals(userId)){
									frmVector.get(i).txtGroupChat.append(uid1+":"+msg);
									found=true;
									System.out.println("inside found");
									System.out.println(frmVector.get(i).userId1);
									System.out.println(frmVector.get(i).userId1);
									break;
								}
							}
							if(found==false){
								FrmPrivateChatBox pchat=new FrmPrivateChatBox(userId,uid1,out,in);
								pchat.txtGroupChat.append(uid1+":"+msg);
								frmVector.addElement(pchat);
							}
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
