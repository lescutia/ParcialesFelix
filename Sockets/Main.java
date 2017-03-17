import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Main extends JPanel implements ActionListener 
{
	JButton m_SharedFolder;
	JButton m_DownloadFolder;
	JButton m_Start;

   	JFileChooser chooser;
   	String choosertitle;

   	JFrame m_Frame;

  	public Main( JFrame in_frame ) 
  	{
		m_SharedFolder 	= new JButton("Shared Directory");
		m_DownloadFolder= new JButton("Download Directory");
		m_Start= new JButton("Start");

		m_SharedFolder.addActionListener(this);
		m_DownloadFolder.addActionListener(this);
		m_Start.addActionListener(this);
		add(m_SharedFolder);
		add(m_DownloadFolder);
		add(m_Start);
		m_Frame = in_frame;
	}

 	public void actionPerformed(ActionEvent e) 
 	{
 		String command = e.getActionCommand();

 		if( command.equals("Shared Directory") || command.equals("Download Directory") )
 		{
			int result;
			    
			chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle(choosertitle);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				if( command.equals("Shared Directory") )
				{
					Globals.m_strSharedDirPath = chooser.getSelectedFile().toString() + "\\";
					Globals.PrintMessage( "[Main]: SharedPath: " + Globals.m_strSharedDirPath ,Globals.m_bDebugMain);
				}
				else
				{
					Globals.m_strDownloadPath = chooser.getSelectedFile().toString() + "\\";
					Globals.PrintMessage( "[Main]: DownloadPath: " + Globals.m_strDownloadPath ,Globals.m_bDebugMain);
				}
			}
		}
		else if( command.equals("Start") )
		{
			if( Globals.m_strSharedDirPath != "" && Globals.m_strDownloadPath != "" || Globals.m_bIsLeader)
			{
				m_Frame.dispose();
				Runner();
			}
		}
	}
	public void Runner()
	{
		Node newNode = new Node();
		newNode.start();
		try
		{
			newNode.join();
		}
		catch(InterruptedException e)
		{
			
		}
		execLeaderSelection();
	}
	public void execLeaderSelection()
	{
		System.out.println("[MainexectLeaderSelection]");
	}
  	public Dimension getPreferredSize()
  	{
    	return new Dimension(500, 75);
	}
    
  	public static void main(String s[]) 
  	{
		JFrame frame = new JFrame("DMS");
		Main panel = new Main( frame );

		frame.addWindowListener
		(
			new WindowAdapter() 
			{
			    public void windowClosing(WindowEvent e) 
			    {
			      System.exit(0);
				}
			}
		);

		frame.getContentPane().add(panel,"Center");
		frame.setSize(panel.getPreferredSize());
		frame.setVisible(true);
	}
}