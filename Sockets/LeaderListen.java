import java.net.*;
import java.lang.instrument.Instrumentation;
import java.io.IOException;
public class LeaderListen extends Thread {
	int port;
	public LeaderListen(int port)
	{
		this.setName("LeaderListen-Thread");
		this.port = port;
	}
	
	public void run()
	{
		try{
			InetAddress group = InetAddress.getByName(Globals.m_strGroupId);
			MulticastSocket socket = new MulticastSocket(this.port);
			DatagramSocket replySocket = new DatagramSocket();
			socket.joinGroup(group);
			byte [] data = new byte[1024];
			String msg;
			Globals.PrintMessage("[Leader Listener]: Listening for new nodes...", Globals.m_bDebugLeader );
			//if( Globals.m_bDebugLeader )
	      		//System.out.println("[Leader Listener]: Listening for new nodes...");
			while(true){
				DatagramPacket packet = new DatagramPacket(data,data.length);
	      		socket.receive(packet);
	      		msg = new String(packet.getData(), packet.getOffset(), packet.getLength());

    			System.out.println("[Leader Listener]: Request received: "+msg);
    			String replyMsg = InetAddress.getLocalHost().toString();
				data = replyMsg.getBytes();
    			DatagramPacket replyPacket = new DatagramPacket(data, data.length, InetAddress.getByName(msg), Globals.m_iPortLeaderListener);
    			replySocket.send(replyPacket);
	      	}
		}
		catch(SocketException e){
			e.getMessage();
		}		
		catch(UnknownHostException e){
			e.getMessage();
		}	
		catch(IOException e){
			e.getStackTrace();
		}
			
	}
}
