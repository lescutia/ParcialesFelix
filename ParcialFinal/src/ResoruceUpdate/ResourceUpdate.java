/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResoruceUpdate;
import java.rmi.*;
import java.util.ArrayList;

/**
 *
 * @author gamaa
 */
public interface ResourceUpdate extends Remote{
    
    /*
    *   @Param fileList is the list of available resources.
    */
    public void update(String owner, ArrayList<String> fileList) throws RemoteException;
    public ArrayList<ArrayList<String>> getTable() throws RemoteException;
}
