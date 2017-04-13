/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

/**
 *
 * @author gamaa
 */
public class CGlobals {
    
    /********************************************************
                    Network Variables
    ********************************************************/
    /*< ID of the group to which the multicast will be sent. */
    public static String m_strGroupId   = "224.0.0.5";
    /*< ID of the leader of the group. */
    public static String m_strLeaderId  = "";
    /*< Local host IP address. */
    public static String m_strLocalHost = "";  
    
    
    /********************************************************
                    Port Variables
    ********************************************************/
    /*< Port to find leader and leader listener to attend new nodes. */
    public static int m_iPortLeaderListener     = 10000;
    /*< Time in miliseconds to wait for leader reply. */
    public static int m_iRemoteObjectPort 	= 1500;
    
}
