import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

public class DataInfoManager extends Thread{
	Timer timer;
	List m_lstResources;
	public DataInfoManager(){
		timer = new Timer();
	}
	
	
	public void run()
	{
		timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
            	Globals.PrintMessage("[DataInfoManager - Timer]: Refreshing resource list", Globals.m_bDebugDIMng );
            	m_lstResources = new ArrayList();
            }
        }, Globals.m_iPortDataInfoManager,Globals.m_iPortDataInfoManager);
		m_lstResources = new ArrayList();
		try
		{
			ServerSocket serverSocket = new ServerSocket(Globals.m_iPortDataInfoManager);
			while(true)
			{
				Globals.PrintMessage("[DataInfoManager]: Listening for news", Globals.m_bDebugDIMng );
				Socket clientSocket = serverSocket.accept();
				new DataInfoManagerDispatcher(clientSocket).start();
			}
		}	
		catch(IOException e)
		{
			System.out.println("[DataInfoManagerIOException]");
		}
	}

	class DataInfoManagerDispatcher extends Thread
	{
		private Socket socket;
	    public DataInfoManagerDispatcher(Socket clientConnection)
	    {   
	        this.socket = clientConnection;
	    }
	    public void run()
	    {
	    	try
	    	{
	    		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				String remote = socket.getInetAddress().toString();
				remote = remote.replace("/","");
				Message ObjMsg = (Message)inputStream.readObject();
				switch(ObjMsg.GetMsgType())
				{
					case REQUEST:
						{
							try{
								Globals.PrintMessage("[DataInfoManager]: Info request received from " + remote, Globals.m_bDebugDIMng );
								Globals.PrintMessage("[DataInfoManager]: Preparing resource table", Globals.m_bDebugDIMng );
								//Make a reply for the request
								FileInputStream fileIn = new FileInputStream("resources.obj");
            					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            					List listObject = (ArrayList)objectIn.readObject();
            					Message msg = new Message(Message.EMessageType.REPLY,15,listObject);
								System.out.println("[DataInfoManager]: Sending table to "+remote);
								ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
								outputStream.writeObject(msg);
								outputStream.flush();
								socket.close();
								fileIn.close();
							}
							catch(IOException e){
								System.out.println(e.getMessage());
							}	
						}
						break;
					case UPDATE:
						{
							if( Globals.m_bDebugDIMng )
							{
								System.out.println("[DataInfoManager]: Update received from "+remote);
							}	
							Resource newResource = new Resource(socket.getInetAddress().getHostAddress(), ObjMsg.GetResources() );
							for( int i = 0; i < m_lstResources.size(); i++ )
							{
								Resource tmp = (Resource)m_lstResources.get(i);
								if( tmp.GetResourcesOwner().equals(newResource.GetResourcesOwner()) )
								{
									m_lstResources.remove(i);
									break;
								}
							}

							if( Globals.m_bDebugDIMng )
								System.out.println(m_lstResources);

							m_lstResources.add( newResource );
							if( Globals.m_bDebugDIMng ){
								System.out.println("[DataInfoManager]: Resources: "+newResource.GetElements() );
							}
							try{
								FileOutputStream fos = new FileOutputStream("resources.obj");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								oos.writeObject(m_lstResources);
								fos.close();
								oos.close();
							}
							catch(Exception e){
								e.getStackTrace();
							}
						}
						break;
					default:
						if( Globals.m_bDebugDIMng )
							System.out.println("[DataInfoManager]: Message Type doesn't correspond: " + ObjMsg.GetMsgType());
						break;
				}
	    	}
	    	catch(SocketException e)
	    	{
	    		if( Globals.m_bDebugDIMng )
					System.out.println("[DataInfoManagerDispatcherSocketException]");
	    	}
	    	catch(IOException e)
			{
				if( Globals.m_bDebugDIMng )
					System.out.println("[DataInfoManagerDispatcherIOException]");
			}
			catch(ClassNotFoundException e)
			{
				if( Globals.m_bDebugDIMng )
					System.out.println("[DataInfoManagerDispatcherCNFException]");
			}
	    }
	}
}