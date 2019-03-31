import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class FrmClientSignup extends JInternalFrame{
	JLabel lblIcon,lblUser,lblPassword,lblSignup,lblConfirm;
	JTextField txtUser;
	JPasswordField jpfPassword,jpfConfirm;
	JButton btnSignup,btnCancel;
	ImageIcon icoSignup;
	int frmWidth=400,frmHeight=400;
	int lblheight=35,lblwidth=170,txtheight=35,txtwidth=200,btnheight=50,btnwidth=120;
	int x,y;
	Font fontStyle,flabel,fbtn,ftext;

	public FrmClientSignup() {
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

		icoSignup=new ImageIcon("images/loginicon.png");
		lblIcon=new JLabel(new ImageIcon(icoSignup.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		lblIcon.setBounds(5,5,100,50);
		add(lblIcon);
		
		lblSignup = new JLabel("USER SIGNUP");
		fontStyle= new Font("AR HERMANN",Font.BOLD,35);
		lblSignup.setFont(fontStyle);
		lblSignup.setBounds(100,10,300,50);
		lblSignup.setForeground(Color.ORANGE);
		add(lblSignup);

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
		
		lblConfirm=new JLabel("Confirm : ");
	    flabel=new Font("Ravie",Font.PLAIN,25);
		lblConfirm.setFont(flabel);
		lblConfirm.setBounds(0, 220, lblwidth, lblheight);
		add(lblConfirm);
		
		txtUser=new JTextField();
		ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		txtUser.setFont(ftext);
		txtUser.setBounds(180, 80, txtwidth, txtheight);
		add(txtUser);
		
		jpfPassword=new JPasswordField();
	    ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		jpfPassword.setFont(ftext);
		jpfPassword.setBounds(180, 150, txtwidth, txtheight);
		add(jpfPassword);
		
		jpfConfirm=new JPasswordField();
	    ftext=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		jpfConfirm.setFont(ftext);
		jpfConfirm.setBounds(180, 220, txtwidth, txtheight);
		add(jpfConfirm);
		
		btnSignup=new JButton("SignUp");
		fbtn= new Font("Forte",Font.BOLD,25);
	    btnSignup.setFont(fbtn);
		btnSignup.setBounds(10,300, btnwidth, btnheight);
		add(btnSignup);
		btnSignup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String s1=txtUser.getText();
				String s2=new String(jpfPassword.getPassword());
				String s3=new String(jpfConfirm.getPassword());
				if(!s2.equals(s3)){
					JOptionPane.showMessageDialog(FrmClientSignup.this,"Password and Confirm Password mismatch", "Login", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Database db=new Database();
				db.runOtherQuery("insert into users(user,pass) values('"+s1+"','"+s2+"')");
				JOptionPane.showMessageDialog(FrmClientSignup.this,"Your signup is successfull!! , you may proceed to login", "Login", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				new FrmClientLogin();
			}
		});
		
		btnCancel=new JButton("Cancel");
		fbtn= new Font("Forte",Font.BOLD,25);
	    btnCancel.setFont(fbtn);
		btnCancel.setBounds(270,300, btnwidth, btnheight);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				int n=JOptionPane.showConfirmDialog(FrmClientSignup.this,"Are you sure , you want to cancel..?", "Login", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
				  dispose();
				else
					return;
			}
		});
		setVisible(true);
	}
}
