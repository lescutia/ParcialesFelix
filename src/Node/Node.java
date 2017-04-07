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
        
        Server service = new Server("Gamaliel",args);
        service.start();
        
    }
}

class Server extends java.lang.Thread {
    String serviceName;
    String args[];
    public Server(String name, String []args){
        serviceName = name;
        this.args = args;
    }
    @Override
    public void run(){
        try{
            
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "1050");
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            ORB orb = ORB.init(new String [1], props);       
            
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            
            RSObj rsobj = new RSObj();
            rsobj.setORB(orb);
            
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(rsobj);
            RemoteServer href = RemoteServerHelper.narrow(ref);
            
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            NameComponent path[] = ncRef.to_name(serviceName);
            ncRef.rebind(path, href);
            
            System.out.println("[Servant]: Listener ready");
            
            while(true)
                orb.run();
            
        }
        catch(Exception e){
            System.out.println("[Servant]: Exception");
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
            
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "1050");
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            
            ORB orb = ORB.init(new String [1],props);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            RemoteServer rsobj = (RemoteServer) RemoteServerHelper.narrow(ncRef.resolve_str("Gamaliel"));
            
            System.out.println("[Client]: Requesting service");
            
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            RCObj rcobj = new RCObj();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(rcobj);
            RemoteClient rcref = RemoteClientHelper.narrow(ref);
            
            rsobj.GetFile("ExampleFILE", rcref);
            
        }
        catch(Exception e){
            System.out.println("[Client]: Exception");
            e.getStackTrace();
        }
        
    }
   
    
}
