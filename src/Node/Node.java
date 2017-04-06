/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

/**
 *
 * @author gamaa_000
 */
import dms.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

public class Node {
    public static void main(String args[]){
        
        Server service = new Server();
        service.start();
        
    }
}

class Server extends Thread {
    
    String []args;
    void Server(String args[]){
        this.args = args;
    }
    void Run(){
        try{
            
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            
            RSObj rsobj = new RSObj();
            rsobj.setORB(orb);
            
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(rsobj);
            RemoteServer href = RemoteServerHelper.narrow(ref);
            
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            NameComponent path[] = ncRef.to_name("Compu de villela");
            ncRef.rebind(path, href);
            
            System.out.println("[Node]: Listener ready");
            
            while(true)
                orb.run();
            
        }
        catch(Exception e){
            System.out.println("[Node]: Exception");
            e.getStackTrace();
        }
    }
    
}

class Client extends Thread{
   
    String args[];
    void Client(){
        
    }
    
    void Run(){
        
        try{
        ORB orb = ORB.init(args,null);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        
        }
        catch(Exception e){
            System.out.println("[Client]: Exception");
            e.getStackTrace();
        }
        
    }
   
    
}
