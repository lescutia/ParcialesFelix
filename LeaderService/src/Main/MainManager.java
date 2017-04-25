/**************************************************************************
	file:	 	MainManager.java
	date:		2017/04/08 16:17	
	author:		Luis Escutia; Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: The main class of the Leader Service.
**************************************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import ConnectionService.*;
import ResourceUpdate.*;
import Global.*;
import java.rmi.RemoteException;
/**
 *
 * @author gamaa
 */
public class MainManager {
    public static void main(String args[]){
        
        ConnectionService cs = new ConnectionService();
        Thread leaderListener = cs.ConnectionService();
        leaderListener.start();
        
        try{
            
            ResourceUpdateIMP ru = new ResourceUpdateIMP();
            Thread fsDaemon = ru.ServerDaemon();
            fsDaemon.start();

        }
        catch(RemoteException e){e.printStackTrace();}
        
    }
}
