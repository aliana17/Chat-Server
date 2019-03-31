import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrmClient extends JFrame {
	JMenuBar mbar;
	JMenu file;
	JMenuItem mitenter,mitconfig,mitexit;
	JLabel lbl;
	static JDesktopPane jdp;
	JToolBar tools;
	JButton btnenter,btnconfig,btnexit;
	JPanel statusbar;
	ImageIcon icoenter,icoconfig,icoexit;
	Font fontStyle;
	static FrmClientLogin frmLogin;
	static FrmConfigServer frmConfig;
	int frmwidth=700;
	int frmheight=500;
	
	public FrmClient() {
		// TODO Auto-generated constructor stub
		jdp=new JDesktopPane() 
			{
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					ImageIcon ii=new ImageIcon("images/mainchat.png");
					Dimension d=getSize();
					g.drawImage(ii.getImage(),0,0,d.width,d.height,null);
				}	
			};
		add(jdp,BorderLayout.CENTER);
		
		icoenter = new ImageIcon("images/chaticon.png");
		icoconfig = new ImageIcon("images/configure.png");
		icoexit = new ImageIcon("images/exit.png");
		
		mbar=new JMenuBar();
		file=new JMenu("File");
		mitenter=new JMenuItem("Enter ChatRoom");
		mitenter.setIcon(new ImageIcon(icoenter.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING)));
		mitenter.setMnemonic(KeyEvent.VK_E);
		mitenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		mitenter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frmLogin=new FrmClientLogin();
				jdp.add(frmLogin);
			}
			
		});
		mitconfig=new JMenuItem("Configure Server");
		mitconfig.setIcon(new ImageIcon(icoconfig.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING)));
		mitconfig.setMnemonic(KeyEvent.VK_C);
		mitconfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		mitconfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frmConfig=new FrmConfigServer();
				jdp.add(frmConfig);
			}
			
		});
		mitexit=new JMenuItem("Exit");
		mitexit.setIcon(new ImageIcon(icoexit.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING)));
		mitexit.setMnemonic(KeyEvent.VK_X);
		mitexit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		mitexit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
	    fontStyle= new Font("AR HERMANN",Font.BOLD,35);
		file.add(mitenter);
		file.add(mitconfig);
		file.add(mitexit);
		file.setFont(fontStyle);
		mbar.add(file);
		
		tools=new JToolBar();
		btnenter=new JButton();
		btnenter.setIcon(new ImageIcon(icoenter.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING)));
		btnenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frmLogin=new FrmClientLogin();
				jdp.add(frmLogin);
			}
			
		});
		btnconfig=new JButton();
		btnconfig.setIcon(new ImageIcon(icoconfig.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING)));
		btnconfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frmConfig=new FrmConfigServer();
				jdp.add(frmConfig);
			}
			
		});
		btnexit=new JButton();
		btnexit.setIcon(new ImageIcon(icoexit.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING)));
		btnexit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		tools.add(btnenter);
		tools.add(btnconfig);
		tools.add(btnexit);
		add(tools,BorderLayout.NORTH);
		
		statusbar=new JPanel();
		statusbar.setBackground(Color.LIGHT_GRAY);
		lbl=new JLabel("");
		lbl.setPreferredSize(new Dimension(500,40));
		statusbar.add(lbl);	
		add(statusbar,BorderLayout.SOUTH);

		setJMenuBar(mbar);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
}
