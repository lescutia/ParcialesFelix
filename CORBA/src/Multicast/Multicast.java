/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.MulticastSocket;
/**
 *
 * @author gamaa_000
 */
public class Multicast {
    public void Multicast(){
	
    }
    
    
    public void startListener(){
	
	new Thread(new Runnable(){
	    public void run(){
		try{
		    InetAddress group = InetAddress.getByName(Node.Globals.m_strGroupId);
		    MulticastSocket socket = new MulticastSocket(Node.Globals.m_iPortLeaderListener);
		    DatagramSocket replySocket = new DatagramSocket();
		    socket.joinGroup(group);
		    byte [] data = new byte[1024];
		    String msg;
		    Node.Globals.PrintMessage("[Multicast/Listener]: Listening for new nodes...", Node.Globals.m_bDebugLeader );
		    while(true){
			DatagramPacket packet = new DatagramPacket(data,data.length);
			socket.receive(packet);
			msg = new String(packet.getData(), packet.getOffset(), packet.getLength());

			System.out.println("[Multicast/Listener]: Request received: "+msg);
			String replyMsg = InetAddress.getLocalHost().toString();
			data = replyMsg.getBytes();
			DatagramPacket replyPacket = new DatagramPacket(data, data.length, InetAddress.getByName(msg), Node.Globals.m_iPortLeaderListener);
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
	}).start();
	
    }
    
    
    public void findLeader(){
	
	new Thread(new Runnable(){
	    
	    public void run(){
		
		try{
		    String msg = InetAddress.getLocalHost().getHostAddress();
		    InetAddress group = InetAddress.getByName(Node.Globals.m_strGroupId);
		    byte[] data = msg.getBytes();
		    DatagramSocket socket = new DatagramSocket(Node.Globals.m_iPortLeaderListener);
		    DatagramPacket datagram = new DatagramPacket(data, data.length, group, Node.Globals.m_iPortLeaderListener);
		    int counter = 0;
		    if( Node.Globals.m_bDebugNode )
			    System.out.println("[Multicast/findLeader]: Looking for system leader");	    

		    while(counter < 4)
		    {
			try
			{
			    socket.send(datagram);
			    socket.setSoTimeout( Node.Globals.m_iTimeOutLeaderSearch );
			    data = new byte[1024];
			    DatagramPacket packet = new DatagramPacket(data,data.length);
			    socket.receive(packet);
			    String remoteIP = packet.getAddress().toString();
			    remoteIP = remoteIP.replace("/","");
			    Node.Globals.PrintMessage("[Node]: Response from: "+remoteIP, Node.Globals.m_bDebugNode );
			    Node.Globals.m_strLeaderId = remoteIP;
			    break;
			}
			catch(SocketTimeoutException e)
			{
			    System.out.println("[Multicast/findLeader]: Leader search timeout");
			}
			catch(IOException e){
			    e.getStackTrace();
			}
			counter++;
		    }

		}
		catch(UnknownHostException e)
		{
		    e.getStackTrace();
		}
		catch(SocketException e){
		    e.getStackTrace();
		}		
		
	    }
	    
	}).start();
	
    }
}
