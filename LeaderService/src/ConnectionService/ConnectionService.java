/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionService;
import Global.CGlobals;
import java.net.*;
import java.io.IOException;
/**
 *
 * @author gamaa
 */

public class ConnectionService {
    public Thread ConnectionService( ){
        
        Thread thread = new Thread (new Runnable(){
            @Override
            public void run(){
                try{

                    InetAddress group = InetAddress.getByName(CGlobals.m_strGroupId);
                    MulticastSocket socket = new MulticastSocket(CGlobals.m_iPortLeaderListener);
                    socket.joinGroup(group);
                    byte [] data = new byte[1024];
                    String msg;
                    System.out.println("[ConnectionService]: Listening for new nodes...");

                    while(true){

                        DatagramPacket packet = new DatagramPacket(data,data.length);
                        socket.receive(packet);
                        System.out.println("Received!");
                        msg = new String(packet.getData(), packet.getOffset(), packet.getLength());

                        System.out.println("[ConnectionService]: Request received: "+msg);
                        String replyMsg = InetAddress.getLocalHost().toString();
                        data = replyMsg.getBytes();
                        DatagramPacket replyPacket = new DatagramPacket(data, data.length, InetAddress.getByName(msg), CGlobals.m_iPortLeaderListener+1);
                        socket.send(replyPacket);

                    }

                }
                catch(SocketException e){
                        e.printStackTrace();
                }		
                catch(UnknownHostException e){
                        e.printStackTrace();
                }	
                catch(IOException e){
                        e.printStackTrace();
                }
            }
            

        
        });
        return thread;
    }
    
    public static void main(String args[]){
        ConnectionService cs = new ConnectionService();
        cs.ConnectionService();
    }
}
