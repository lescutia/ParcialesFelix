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
            
            InetAddress group = InetAddress.getByName("224.0.0.3");
            MulticastSocket socket = new MulticastSocket(port);
            DatagramSocket replySocket = new DatagramSocket();
            socket.joinGroup(group);
            byte [] data = new byte[1024];
            String msg;
            System.out.println("[ConnectionService]: Listening for new nodes...");
            
            while(true){
                
                DatagramPacket packet = new DatagramPacket(data,data.length);
                socket.receive(packet);
                msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                
                System.out.println("[ConnectionService]: Request received: "+msg);
                String replyMsg = InetAddress.getLocalHost().toString();
                data = replyMsg.getBytes();
                DatagramPacket replyPacket = new DatagramPacket(data, data.length, InetAddress.getByName(msg), port);
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
    
    public static void main(String args[]){
        ConnectionService cs = new ConnectionService();
        cs.ConnectionService(10000);
    }
}