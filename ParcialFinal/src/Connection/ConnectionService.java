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
    
    /*
        This thread permits the execution of the method findLeaderExecution to detect if 
        there is a leader or not into the system. 
    */

    /**
     *
     * @return the thread for the leader selection. 
     */

    public Thread findLeaderThread()
    {
        //This thread is made for the leader selection. 
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
    
    public Thread keepAliveThread()
    {
        Thread thread = new Thread (new Runnable()
        {
            Boolean keepAliveFlag = true;
            @Override
            public void run()
            {
                System.out.println("!!!!!!!!!!!!!!!");
                while(keepAliveFlag){
                    try{
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e){
                        System.out.println("[ConnectionService]: KeepAlive Interruped!!!!!");
                        keepAliveFlag = false;
                    }
                }
            }
        });
        return thread;
    }
    
    /*
        Method to detect if there is a leader into the system. It uses sockets to do it. 
    */
    void findLeaderExecution()
    {
        try
        {
            /*
                Variables declaration and sockets initialization. 
                This is going to get the group IP and the local IP. 
            */
            String msg = "NewNode";//CGlobals.m_strLocalHost;
            //Variables for the IP management
            InetAddress group = InetAddress.getByName(CGlobals.m_strGroupId );
            InetAddress local = InetAddress.getByName( CGlobals.m_strLocalHost );
            byte[] data = msg.getBytes();
            DatagramSocket socket = new DatagramSocket( CGlobals.m_iPortLeaderListener, local );
            DatagramPacket datagram = new DatagramPacket( data, data.length, group , CGlobals.m_iPortLeaderListener );
            System.out.println("Connected at: "+socket.getLocalAddress());
            DatagramSocket receiverSocket = new DatagramSocket( CGlobals.m_iPortLeaderListener+1 );
            /*
                Looking for the system leader is a message that it is going to be printed if
                the system is looking to another leader. 
            */
            if ( CGlobals.m_bDebugConnection )
                System.out.println( "[Node]: Looking for system leader" );
            
            /*
                This while contains a validation to do when the getting leader attempts are less than 4. 
                If there are less than 4 it will active a receiverSocket to manage the time of the system users
                and select a new leader through the LeaderService application. 
            */
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
    
    void keepAliveServiceExecution()
    {
        try
        {
            /*
                Variables declaration and sockets initialization. 
                This is going to get the group IP and the local IP. 
            */
            String msg = "KeepAlive";//CGlobals.m_strLocalHost;
            //Variables for the IP management
            InetAddress leader = InetAddress.getByName(CGlobals.m_strLeaderId );
            byte[] data = msg.getBytes();
            DatagramSocket socket = new DatagramSocket( );
            DatagramPacket datagram = new DatagramPacket( data, data.length, leader , CGlobals.m_iPortLeaderListener );
            System.out.println("Connected at: "+socket.getLocalAddress());
            /*
                Looking for the system leader is a message that it is going to be printed if
                the system is looking to another leader. 
            */
            if ( CGlobals.m_bDebugConnection )
                System.out.println( "[ConnectionService]: Sending KeepAlive" );
            socket.send( datagram );
            socket.close();
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
