/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionService;
import java.net.*;
import java.io.IOException;
/**
 *
 * @author gamaa
 */

public class ConnectionService {
    public void ConnectionService( int port ){
        try{
            
            InetAddress group = InetAddress.getByName("224.0.0.5");
            MulticastSocket socket = new MulticastSocket(port);
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
                DatagramPacket replyPacket = new DatagramPacket(data, data.length, InetAddress.getByName(msg), port);
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
    
    public static void main(String args[]){
        ConnectionService cs = new ConnectionService();
        cs.ConnectionService(10000);
    }
}
