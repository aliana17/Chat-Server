import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrmMain extends JFrame {
	JButton btnServer,btnClient,btnCancel;
	JLabel lblChat;
	ImageIcon ii;
	int frmHeight=400,frmWidth=400,btnHeight=50,btnWidth=250;
	int x,y;
	Font fontStyle,fbtn;
	FrmMain(){
		Dimension desktop=Toolkit.getDefaultToolkit().getScreenSize();
		x=(desktop.width-frmWidth)/2;
		y=(desktop.height-frmHeight)/2;
		
		setLayout(null);
		setSize(frmWidth,frmHeight);
		setLocation(x,y);
		setResizable(false);
		setUndecorated(true);

		ii=new ImageIcon("images/main.png");
	    setContentPane(new JLabel(new ImageIcon(ii.getImage().getScaledInstance(frmWidth,frmHeight, Image.SCALE_SMOOTH))));
	    
	    lblChat=new JLabel("CHAT ROOM");
	    fontStyle= new Font("CHILLER",Font.BOLD,65);
	    lblChat.setFont(fontStyle);
	    lblChat.setBounds(50,50,300,50);
		lblChat.setForeground(Color.RED);
		add(lblChat);
		
		btnServer=new JButton("Start Server");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnServer.setFont(fbtn);
		btnServer.setBounds(20, 170, btnWidth, btnHeight);
		btnServer.setBackground(Color.CYAN);
		add(btnServer);
		btnServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new FrmServer();
			}
		});
		
		btnClient=new JButton("Start Client");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnClient.setFont(fbtn);
		btnClient.setBounds(130, 230, btnWidth, btnHeight);
		btnClient.setBackground(Color.ORANGE);
		add(btnClient);
		btnClient.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new FrmClient();
			}
		});		
		add(btnClient);
		
		btnCancel=new JButton("Cancel");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnCancel.setFont(fbtn);
		btnCancel.setBounds(220, 340, btnWidth, btnHeight);
		btnCancel.setForeground(Color.red);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				int n=JOptionPane.showConfirmDialog(FrmMain.this,"Are you sure , you want to cancel..?", "Login", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
				  dispose();
				else
					return;
			}
		});
		
		setSize(400,400);
		setVisible(true);
	}
	public static void main(String[] args) {
		new FrmMain();
	}
}
