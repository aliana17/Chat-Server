import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class FrmClientLogin extends JInternalFrame {
	JLabel lblicon,lblUser,lblPassword,lblLogin;
	JTextField txtUser;
	JPasswordField jpfPassword;
	JButton btnLogin,btnSignup,btnCancel;
	ImageIcon icologin;
	int frmWidth=400,frmHeight=400;
	int lblheight=35,lblwidth=170,txtheight=35,txtwidth=200,btnheight=50,btnwidth=150;
	int x,y;
	Font fontStyle,flabel,fbtn,ftext;
	static FrmClientSignup frmSignup;

	public FrmClientLogin() {
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
		
		lblLogin = new JLabel("USER LOGIN");
		fontStyle= new Font("AR HERMANN",Font.BOLD,35);
		lblLogin.setFont(fontStyle);
		lblLogin.setBounds(100,10,300,50);
		lblLogin.setForeground(Color.ORANGE);
		add(lblLogin);

		lblUser=new JLabel("User Id : ");
		flabel=new Font("Ravie",Font.PLAIN,25);
		lblUser.setFont(flabel);
		lblUser.setBounds(0, 80, lblwidth, lblheight);
		add(lblUser);
		
		lblPassword=new JLabel("Password : ");
	    flabel=new Font("Ravie",Font.PLAIN,20);
		lblPassword.setFont(flabel);
		lblPassword.setBounds(0, 150, lblwidth, lblheight);
		add(lblPassword);
		
		txtUser=new JTextField();
		ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		txtUser.setFont(ftext);
		txtUser.setBounds(180, 80, txtwidth, txtheight);
		add(txtUser);
		
		jpfPassword=new JPasswordField();
		//jpfPassword.setEchoChar('*');
	    ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		jpfPassword.setFont(ftext);
		jpfPassword.setBounds(180, 150, txtwidth, txtheight);
		add(jpfPassword);
		
		btnLogin=new JButton("Login");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnLogin.setFont(fbtn);
		btnLogin.setBounds(20, 230, btnwidth, btnheight);
		add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String s1=txtUser.getText();
				String s2=new String(jpfPassword.getPassword());
				Database db=new Database();
				try{
					ResultSet rst=db.runSelectQuery("select * from users where user='"+s1+"' and pass='"+s2+"'");
					if(rst.next()){
						JOptionPane.showMessageDialog(FrmClientLogin.this,"Your login is successfull!!", "Login", JOptionPane.INFORMATION_MESSAGE);
						db.closeDatabase();
						dispose();
						new FrmChatBox(s1);
					}
					else{
						db.closeDatabase();
						JOptionPane.showMessageDialog(FrmClientLogin.this,"Invalid user or password", "Login", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		});
		
		btnSignup=new JButton("Sign Up");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnSignup.setFont(fbtn);
		btnSignup.setBounds(220, 230, btnwidth, btnheight);
		add(btnSignup);
		btnSignup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				frmSignup=new FrmClientSignup();
				FrmClient.jdp.add(frmSignup);
			}
			
		});
		
		btnCancel=new JButton("Cancel");
		fbtn= new Font("Forte",Font.BOLD,30);
	    btnCancel.setFont(fbtn);
		btnCancel.setBounds((frmWidth-btnwidth)/2, 320, btnwidth, btnheight);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				int n=JOptionPane.showConfirmDialog(FrmClientLogin.this,"Are you sure , you want to cancel..?", "Login", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
				  dispose();
				else
					return;
			}
		});
		
		setVisible(true);
	}
}
