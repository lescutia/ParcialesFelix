/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceUpdate;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
/**
 *
 * @author gamaa
 */
public class ResourceUpdateIMP extends UnicastRemoteObject implements ResourceUpdate{
    public ResourceUpdateIMP() throws RemoteException
    {
    }
    
    @Override
    public void update(String owner, ArrayList<String> list) throws RemoteException{
        System.out.println("Elements in "+owner);
        for(String element : list){
            System.out.println(element);
        }
    } 
    @Override
    public ArrayList<ArrayList<String>> getTable() throws RemoteException{
        
        return null;
    }
    
    public static void main(String args[]){
        try{
            ResourceUpdateIMP r = new ResourceUpdateIMP();
            r.startServerDaemon();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }
    public void startServerDaemon(){
        if ( System.getSecurityManager() == null )
            System.setProperty( "java.security.policy", "security.policy" );
        try{
            LocateRegistry.createRegistry( 1500 );
            ResourceUpdateIMP rsIMPObj = new ResourceUpdateIMP();
            Naming.rebind( "//localhost:" + 1500 + "/UpdateServer", rsIMPObj );
            System.out.println("UpdateServer ready");
        }
        catch(RemoteException e){
            e.getStackTrace();
        }
        catch(MalformedURLException e){
            e.getStackTrace();
        }
    }
}
