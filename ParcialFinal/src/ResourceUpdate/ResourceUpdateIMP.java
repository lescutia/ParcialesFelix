/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceUpdate;
import Global.CGlobals;
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
        System.out.println("[ResourceUpdate]: Elements in "+owner);
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
            //r.startServerDaemon();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }
    public Thread ServerDaemon(){
        
        Thread thread = new Thread (new Runnable(){
            @Override
            public void run(){
                if ( System.getSecurityManager() == null )
                    System.setProperty( "java.security.policy", "security.policy" );
                try{
                    LocateRegistry.createRegistry( CGlobals.m_iRemoteObjectPort );
                    ResourceUpdateIMP rsIMPObj = new ResourceUpdateIMP();
                    Naming.rebind( "//"+CGlobals.m_strLocalHost+":"+CGlobals.m_iRemoteObjectPort+"/UpdateServer", rsIMPObj );
                    System.out.println("[ResourceUpdate]: UpdateServer ready");
                }
                catch(RemoteException e){
                    e.getStackTrace();
                }
                catch(MalformedURLException e){
                    e.getStackTrace();
                }
            }
        });
        
        return thread;      
        
    }
}
