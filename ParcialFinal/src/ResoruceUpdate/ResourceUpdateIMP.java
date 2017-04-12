/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResoruceUpdate;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;
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
}
