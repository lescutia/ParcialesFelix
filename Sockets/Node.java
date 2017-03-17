import java.net.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.net.SocketException;
import java.util.*;
import javax.swing.JOptionPane;
import java.lang.Exception;
public class Node extends Thread
{
	public Node(){}

	public List getResources()
	{
		File folder = new File(Globals.m_strSharedDirPath);
		File[] listOfFiles = folder.listFiles();
		List resources = new ArrayList();
		for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	        	resources.add(listOfFiles[i].getName());
	      	}
	    }
	    return resources;
	}
	
	public void findLeader()
	{
		try
		{
			String msg = InetAddress.getLocalHost().getHostAddress();
			InetAddress group = InetAddress.getByName(Globals.m_strGroupId);
			byte[] data = msg.getBytes();
			DatagramSocket socket = new DatagramSocket(Globals.m_iPortLeaderListener);
			DatagramPacket datagram = new DatagramPacket(data, data.length, group, Globals.m_iPortLeaderListener);
			int counter = 0;
			if( Globals.m_bDebugNode )
				System.out.println("[Node]: Looking for system leader");
			
			while(counter < 4)
			{
				try
				{
					socket.send(datagram);
					socket.setSoTimeout( Globals.m_iTimeOutLeaderSearch );
					data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data,data.length);
					socket.receive(packet);
					String remoteIP = packet.getAddress().toString();
					remoteIP = remoteIP.replace("/","");
					Globals.PrintMessage("[Node]: Response from: "+remoteIP, Globals.m_bDebugNode );
					Globals.m_strLeaderId = remoteIP;
					break;
				}
				catch(SocketTimeoutException e)
				{
					System.out.println("[Node]: Leader search timeout");
				}
				counter++;
			}
		}
		catch(UnknownHostException e)
		{
			e.getStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.getStackTrace();
		}
	}

	public void run() 
	{
		try
		{		
			if(Globals.m_bIsClient)
			{
				Node node = new Node();
				node.findLeader();
				
				if(Globals.m_strLeaderId == "")
				{
					if( Globals.m_bDebugNode )
						System.out.println("[Node]: No leader found");
					throw new LeaderNotFoundException();
				}
				else
				{
					ClientRunner cr = new ClientRunner(node);
					cr.start();
				}
			}
		}
		catch(LeaderNotFoundException e)
		{
			System.out.println("[NodeLNFException]");
		}
	}
}

class ClientRunner extends Thread
{
	Node node;
	public ClientRunner(Node node)
	{
		this.node = node;
	}
	public void run()
	{
		Runner();
	}
	public void Runner()
	{
		DynamicTreeDemo dtd = new DynamicTreeDemo();
		dtd.startGUI();
		FileTransmitter ft = new FileTransmitter();
		ft.start();
		ResourceUpdater ru = new ResourceUpdater(node);
		try
		{
			ru.start();
			while(ru.isAlive()){}
			if(!ru.isAlive())
			{
				//Globals.m_bCloseFT = true;
				dtd.dispose();
				ft.m_bStopFL = true;
				//ft.interrupt();
				throw new CriticalException();
			}			
		}
		catch(CriticalException e)
		{
			System.out.println("[NodeCriticalException/ClientRunner]");
		}
		
	}
}

class LeaderRunner extends Thread
{
	public LeaderRunner(){}
	public void run()
	{
		if(Globals.m_bIsLeader)
		{
			LeaderListen listener = new LeaderListen(Globals.m_iPortLeaderListener);
			listener.start();
			DataInfoManager updateListener = new DataInfoManager();
			updateListener.start();	
		}
	}
}

class LeaderNotFoundException extends Exception
{
	public LeaderNotFoundException() {}
	public LeaderNotFoundException(String message)
	{
        super(message);
    }
}

class CriticalException extends Exception
{
	public CriticalException() {}
	public CriticalException(String message)
	{
        super(message);
    }
}