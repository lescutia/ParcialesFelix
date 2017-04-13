/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import Connection.*;
import FileTransfer.*;
import Global.*;
import ResourceUpdate.*;
/**
 *
 * @author gamaa
 */
public class MainManager {
    public static void main(String args[]){
        
        CGlobals.m_strLocalHost = NetworkUtils.getLocalIP();
        LeaderSearchGUI lsGUI = new LeaderSearchGUI();
        lsGUI.setVisible(true);
        
        try{
            ConnectionService cs = new ConnectionService();
            Thread findLeader = cs.findLeader();
            findLeader.start();
            findLeader.join();
            lsGUI.dispose();
            if(CGlobals.m_strLeaderId.equals(""))
            {
                System.out.println("[Manager]: No leader found");
                LeaderAlertGUI alert = new LeaderAlertGUI();
                alert.setVisible(true);
            }                
            else
            {
                MainGUI mainGUI = new MainGUI();
                mainGUI.setVisible(true);
            }
        }
        catch(InterruptedException e){e.printStackTrace();}
        
    }
    public void startLeaderSearch(){
        
    }
    public void startFileService(){
        
    }
    
}
