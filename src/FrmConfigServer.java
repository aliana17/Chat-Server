import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.io.*;
public class FrmConfigServer extends JInternalFrame {
	JLabel lblicon,lblHostName,lblHostIp,lblLogin,lblOr;
	JTextField txtHostName,txtHostIp;
	JButton btnSave,btnCancel;
	ImageIcon icologin;
	int frmWidth=400,frmHeight=400;
	int lblheight=35,lblwidth=170,txtheight=35,txtwidth=200,btnheight=50,btnwidth=150;
	int x,y;
	Font fontStyle,flabel,fbtn,ftext;
	static FrmClientSignup frmSignup;
	Properties p;
	FileInputStream fis;

	public FrmConfigServer() {
		// TODO Auto-generated constructor stub
		Dimension desktop=Toolkit.getDefaultToolkit().getScreenSize();
		x=(desktop.width-frmWidth)/2;
		y=(desktop.height-frmHeight)/2;
		
		setLayout(null);
		setSize(frmWidth,frmHeight);
		setLocation(x,y-50);
		setResizable(false);
		//setUndecorated(true);
		setContentPane(new JLabel((new ImageIcon(new ImageIcon("images/loginbg.jpg").getImage().getScaledInstance(frmWidth,frmHeight,Image.SCALE_DEFAULT)))));  // to add image in back ground

		icologin=new ImageIcon("images/loginicon.png");
		lblicon=new JLabel(new ImageIcon(icologin.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		lblicon.setBounds(5,5,100,50);
		add(lblicon);
		
		lblLogin = new JLabel("HOST INFO");
		fontStyle= new Font("AR HERMANN",Font.BOLD,35);
		lblLogin.setFont(fontStyle);
		lblLogin.setBounds(100,10,300,50);
		lblLogin.setForeground(Color.ORANGE);
		add(lblLogin);

		lblHostName=new JLabel("Host Name : ");
		flabel=new Font("Ravie",Font.PLAIN,20);
		lblHostName.setFont(flabel);
		lblHostName.setBounds(0, 80, lblwidth, lblheight);
		add(lblHostName);
		
		lblOr=new JLabel("or");
		flabel=new Font("Forte",Font.BOLD+Font.ITALIC,20);
		lblOr.setFont(flabel);
		lblOr.setBounds((frmWidth-lblwidth)/2,155,lblwidth,lblheight);
		add(lblOr);
		
		lblHostIp=new JLabel("IP Add : ");
	    flabel=new Font("Ravie",Font.PLAIN,20);
		lblHostIp.setFont(flabel);
		lblHostIp.setBounds(0, 230, lblwidth, lblheight);
		add(lblHostIp);
		
		txtHostName=new JTextField();
		ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		txtHostName.setFont(ftext);
		txtHostName.setBounds(180, 80, txtwidth, txtheight);
		add(txtHostName);
		
		txtHostIp=new JTextField();
	    ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		txtHostIp.setFont(ftext);
		txtHostIp.setBounds(180, 230, txtwidth, txtheight);
		add(txtHostIp);
		
		p=new Properties(); 
		try {
			fis=new FileInputStream("documents/host.txt");
			try {
				p.load(fis);
				fis.close();
			} 
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String s1=p.getProperty("name");
			String s2=p.getProperty("ip");
			txtHostName.setText(s1);
			txtHostIp.setText(s2);

		}
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnSave=new JButton("Save");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnSave.setFont(fbtn);
		btnSave.setBounds(20, 320, btnwidth, btnheight);
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Properties p2=new Properties();
					p2.setProperty("name", txtHostName.getText());
					p2.setProperty("ip", txtHostIp.getText());
					FileOutputStream fos=new FileOutputStream("documents/host.txt");
					p2.store(fos, null);
					fos.close();
					dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnCancel=new JButton("Cancel");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnCancel.setFont(fbtn);
		btnCancel.setBounds(220, 320, btnwidth, btnheight);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				int n=JOptionPane.showConfirmDialog(FrmConfigServer.this,"Are you sure , you want to cancel..?", "Login", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
				  dispose();
				else
					return;
			}
		});
		
		setVisible(true);
	}

}
