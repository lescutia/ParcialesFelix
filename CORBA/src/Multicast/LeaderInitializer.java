/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;

import dms.RemoteLeaderHelper;
import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import dms.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 *
 * @author gamaa_000
 */
public class LeaderInitializer extends Thread{
    @Override
    public void run(){
	try{
	    
	    String args[] = null;
	    Properties props = new Properties();
	    props.put("org.omg.CORBA.ORBInitialPort", "1050");
	    props.put("org.omg.CORBA.ORBInitialHost", "localhost");
	    ORB orb = ORB.init(args, props);
	    
	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    rootpoa.the_POAManager().activate();
	    
	    LeaderObj leaderobj = new LeaderObj();
	    
	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(leaderobj);
	    RemoteLeader href = RemoteLeaderHelper.narrow(ref);
	    
	    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	    
	    NameComponent path[] = ncRef.to_name("leaderservice");
	    ncRef.rebind(path, href);
	    
	    System.out.println("[LeaderInitializer]: Leader ready");
	    
	    while ( true )
	    {
		orb.run();
	    }
	    
	}
	catch(Exception e){
	    System.out.println("[LeaderInitializer Servant]: Exception");
	    e.getStackTrace();
	}
    }
}
