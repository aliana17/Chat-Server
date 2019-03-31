import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FrmEmoji extends JFrame
{

	JScrollPane scrollForEmoji;
	 static boolean flag=true;
	
	FrmEmoji()
	{
		setLayout(new GridLayout());
		setResizable(false);
		setUndecorated(true);
		setForeground(Color.white);
		setBounds(FrmChatBox.frameXPosition+55,FrmChatBox.frameYPosition+470,180,100);
		
		
		if( flag)
		{
			setVisible(true);
			flag=false;
			
		}
		else
		{
			setVisible(false);
			flag=true;
		}
		
		scrollForEmoji= new JScrollPane();
		scrollForEmoji.setBounds(4,4,30,30);
		add(scrollForEmoji);
		
	}
	public static void main(String[] args) {
		

	}

}
