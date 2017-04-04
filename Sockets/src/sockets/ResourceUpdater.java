import java.net.*;
import java.io.*;
import java.net.SocketException;
public class ResourceUpdater extends Thread{
	Node node;
	String leader;
	Socket socket;
	ObjectOutputStream outputStream;
	Message msg;
	public ResourceUpdater(Node node)
	{	
		this.node = node;
		this.setName("ResourceUpdater-Thread");
	}

	public void run()
	{
		boolean bFlag = true;
		while(bFlag)
		{
			if( bFlag )
				bFlag = updaterLoop();
			
			if( !bFlag )
				bFlag = checkLeader();	
		}
		System.out.println("[ResourceUpdater]: Leader lost");
	}

	private boolean updaterLoop()
	{
		try
		{
			if(Globals.m_strLeaderId.equals(""))
				throw new SocketException();
			Socket socket = new Socket(Globals.m_strLeaderId, Globals.m_iPortResourceUpdater);
			msg = new Message(Message.EMessageType.UPDATE, 0, node.getResources());
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(msg);
			outputStream.flush();		
			socket.close();
			System.out.println("[ResourceUpdater]: Update sent");
			sleep(4000);	
		}
		catch(SocketException se)
		{
			System.out.println("[ResourceUpdaterSocketException]");
			return false;
		}
		catch(IOException e){
			System.out.println("[ResourceUpdaterIOException]");
		}
		catch(InterruptedException e){
			System.out.println("[ResourceUpdaterSocketException]");
		}
		return true;
	}
	private boolean checkLeader()
	{
		for(int round = 1 ; round <= 4 ; round++)
		{
			try {
				sleep(1000);
                Socket socket = new Socket();
                socket.setReuseAddress(true);
                SocketAddress sa = new InetSocketAddress(InetAddress.getByName(Globals.m_strLeaderId), Globals.m_iPortResourceUpdater);
                socket.connect(sa, 1000);
                return true;
            }
            catch (InterruptedException e)
            {
            	System.out.println("[ResourceUpdaterInterruptedException]");
            }
            catch (IOException e)
            {
            	System.out.println("[ResourceUpdaterIOException]");	
            }
		}
		return false;
	}
}