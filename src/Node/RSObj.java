/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;
import dms.*;
import org.omg.CORBA.*;
/**
 *
 * @author gamaa_000
 */
public class RSObj extends RemoteServerPOA{
  
    private ORB orb;
 
    public void setORB(ORB orb_val) {
      orb = orb_val; 
    }
    
    public void GetFile(String fileName, dms.RemoteClient rCRef){
        
    }
}
