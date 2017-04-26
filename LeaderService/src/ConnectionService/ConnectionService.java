/**************************************************************************
	file:	 	ConnectionService.java
	date:		2017/04/08 16:17	
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Class to initialize the connection servide for the leader. 
        * Includes a thread for the leader management.  
        service provider.
**************************************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionService;

import Global.CGlobals;
import ResourceUpdate.ResourceUpdateIMP;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.rmi.RemoteException;

/**
 *
 * @author gamaa
 */

public class ConnectionService
{
    ArrayList<String> aliveNodes;
    public ConnectionService()
    {
        aliveNodes = new ArrayList<>();
    }
    /**
     *
     * @return the created thread. 
     */
    
    public Thread TimerThread()
    {
        Thread thread = new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                while(true){
                    try
                    {
                        updateTable();
                        Thread.sleep(2000);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
            }
        });
        
        return thread;        
    }
    
    public Thread ConnectionService()
    {
        
        Thread thread = new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                
                try
                {
                    InetAddress group = InetAddress.getByName( CGlobals.m_strGroupId );
                    //Socket to make the control of the multicast. 
                    MulticastSocket socket = new MulticastSocket( CGlobals.m_iPortLeaderListener );
                    socket.joinGroup( group );
                    byte[] data = new byte[1024];
                    String msg;
                    System.out.println( "[ConnectionService]: Listening for new nodes..." );

                    while ( true )
                    {
                        DatagramPacket packet = new DatagramPacket( data, data.length );
                        socket.receive( packet );
                        String packetIPAddress = packet.getAddress().toString();
                        packetIPAddress = packetIPAddress.replace( "/", "" );
                        msg = new String( packet.getData(), packet.getOffset(), packet.getLength() );
                        if ( msg.equals( "KeepAlive" ) )
                        {
                            //System.out.println("[ConnectionService]: KeepAlive received from: "+packetIPAddress);
                            if(aliveNodes.indexOf( packetIPAddress )<0){
                                aliveNodes.add(packetIPAddress);
                            }
                        }
                        else if ( msg.equals( "NewNode" ) )
                        {
                            System.out.println( "[ConnectionService]: Request received: " + packetIPAddress );
                            String replyMsg = InetAddress.getLocalHost().toString();
                            //String replyMsg = packet.getAddress().toString();
                            data = replyMsg.getBytes();
                            DatagramPacket replyPacket = new DatagramPacket( data, data.length, packet.getAddress(), CGlobals.m_iPortLeaderListener + 1 );
                            socket.send( replyPacket );
                        }
                        else if ( CGlobals.m_bDebugCS )
                        {
                            System.out.println( "[ConnectionService]: Error message \"" + msg + "\" can't be processed." );
                        }
                    }
                }
                catch ( SocketException | UnknownHostException e ) {}
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }

        } );
        return thread;
    }

    void updateTable()
    {
        
        try{
           System.out.println("AliveNodes: "+aliveNodes);
           ResourceUpdateIMP.getInstance().updateTable( aliveNodes ); 
           aliveNodes.clear();
        }
        catch(RemoteException e){}
        
    }
}
