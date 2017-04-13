/**************************************************************************
	file:	 	ConnectionService.java
	date:		2017/04/08 16:17	
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Class to find in the network the actual leader in the LAN or the
        service provider.
**************************************************************************/

package Connection;

import Global.CGlobals;
import java.io.*;
import java.net.*;

public class ConnectionService
{
    /*< Variable that count the number of attempts to find the leader. */
    int m_iTryAttempt;
    
    public int getCurrentAttempt() { return m_iTryAttempt; }
    
    public ConnectionService()
    {
        m_iTryAttempt = 0;
    }
    
    public Thread findLeader()
    {
        Thread thread = new Thread (new Runnable()
        {
            @Override
            public void run()
            {
                findLeaderExecution();
            }
        });
        return thread;
    }
    
    void findLeaderExecution()
    {
        try
        {
            String msg = CGlobals.m_strLocalHost;
            InetAddress group = InetAddress.getByName(CGlobals.m_strGroupId );
            InetAddress local = InetAddress.getByName( msg );
            byte[] data = msg.getBytes();
            DatagramSocket socket = new DatagramSocket( CGlobals.m_iPortLeaderListener, local );
            DatagramPacket datagram = new DatagramPacket( data, data.length, group , CGlobals.m_iPortLeaderListener );
            System.out.println("Connected at: "+socket.getLocalAddress());
            DatagramSocket receiverSocket = new DatagramSocket( CGlobals.m_iPortLeaderListener+1 );
            if ( CGlobals.m_bDebugConnection )
            {
                System.out.println( "[Node]: Looking for system leader" );
            }
            
            
            while ( m_iTryAttempt < 4 )
            {
                try
                {
                    socket.send( datagram );
                    receiverSocket.setSoTimeout(CGlobals.m_iTimeOutLeaderSearch );
                    data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket( data, data.length );
                    receiverSocket.receive( packet );
                    String remoteIP = packet.getAddress().toString();
                    remoteIP = remoteIP.replace( "/", "" );
                    if( CGlobals.m_bDebugConnection )
                        System.out.println( "[Connection]: Response from: " + remoteIP);
                    CGlobals.m_strLeaderId = remoteIP;
                    break;
                } 
                catch ( SocketTimeoutException e )
                {
                    if( CGlobals.m_bDebugConnection )
                        System.out.println( "[Connection]: Leader search timeout" );
                }
                m_iTryAttempt++;
            }
            socket.close();
            receiverSocket.close();
        } 
        catch(UnknownHostException uhe)
        {
            if( CGlobals.m_bDebugConnection && CGlobals.m_bDebugExceptions  )
                uhe.getStackTrace();
        }
        catch(IOException ioe)
        {
            if( CGlobals.m_bDebugConnection && CGlobals.m_bDebugExceptions  )
                ioe.getStackTrace();
        }
    }
}
