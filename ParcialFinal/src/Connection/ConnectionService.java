/**************************************************************************
	file:	 	ConnectionService.java
	date:		2017/04/08 16:17	
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Class to find in the network the actual leader in the LAN or the
        service provider.
**************************************************************************/

package Connection;

import Global.Globals;
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
    
    public void findLeader()
    {
        new Thread (new Runnable()
        {
            @Override
            public void run()
            {
                findLeaderExecution();
            }
        }).start();
    }
    
    void findLeaderExecution()
    {
        try
        {
            String msg = InetAddress.getLocalHost().getHostAddress();
            InetAddress group = InetAddress.getByName( Globals.m_strGroupId );
            byte[] data = msg.getBytes();
            DatagramSocket socket = new DatagramSocket( Globals.m_iPortLeaderListener );
            DatagramPacket datagram = new DatagramPacket( data, data.length, group, Globals.m_iPortLeaderListener );
            
            if ( Globals.m_bDebugConnection )
            {
                System.out.println( "[Node]: Looking for system leader" );
            }
            
            
            while ( m_iTryAttempt < 4 )
            {
                try
                {
                    socket.send( datagram );
                    socket.setSoTimeout( Globals.m_iTimeOutLeaderSearch );
                    data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket( data, data.length );
                    socket.receive( packet );
                    String remoteIP = packet.getAddress().toString();
                    remoteIP = remoteIP.replace( "/", "" );
                    if( Globals.m_bDebugConnection )
                        System.out.println( "[Connection]: Response from: " + remoteIP);
                    Globals.m_strLeaderId = remoteIP;
                    break;
                } 
                catch ( SocketTimeoutException e )
                {
                    if( Globals.m_bDebugConnection )
                        System.out.println( "[Connection]: Leader search timeout" );
                }
                m_iTryAttempt++;
            }
            socket.close();

        } 
        catch(UnknownHostException uhe)
        {
            if( Globals.m_bDebugConnection && Globals.m_bDebugExceptions  )
                uhe.getStackTrace();
        }
        catch(IOException ioe)
        {
            if( Globals.m_bDebugConnection && Globals.m_bDebugExceptions  )
                ioe.getStackTrace();
        }
    }
}
