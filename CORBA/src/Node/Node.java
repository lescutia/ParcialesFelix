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

public class Node
{

    public static void main( String args[] )
    {

	Client service = new Client();
	service.start();

    }


}

class Server extends java.lang.Thread
{

    String serviceName;

    public Server( String name )
    {
	serviceName = name;
    }


    @Override
    public void run()
    {
	try
	{
	    String args[] = null;
	    Properties props = new Properties();
	    props.put("org.omg.CORBA.ORBInitialPort", "1050");
	    props.put("org.omg.CORBA.ORBInitialHost", "10.0.5.138");
	    ORB orb = ORB.init(args, props);

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

	    while ( true )
	    {
		orb.run();
	    }

	}
	catch ( Exception e )
	{
	    System.out.println("[Servant]: Exception");
	    e.getStackTrace();
	}
    }


}

class Client extends Thread
{

    void Client()
    {
    }

    @Override
    public void run()
    {
	try
	{
	    String args[] = null;
	    Properties props = new Properties();
	    props.put("org.omg.CORBA.ORBInitialPort", "1050");
	    props.put("org.omg.CORBA.ORBInitialHost", "10.0.5.138");

	    ORB orb = ORB.init(args, props);

	    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	    RemoteServer rsobj = RemoteServerHelper.narrow(ncRef.resolve_str("fileserver"));
	    System.out.println("[Client]: Requesting service");

	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    rootpoa.the_POAManager().activate();

	    RCObj rcobj = new RCObj();
	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(rcobj);
	    RemoteClient rcref = RemoteClientHelper.narrow(ref);
	    rsobj.GetFile("naive.pdf", rcref);
	    orb.run();

	}
	catch ( Exception e )
	{
	    System.out.println("[Client]: Exception");
	    e.getStackTrace();
	}
    }
}
