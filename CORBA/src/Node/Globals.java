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
public class Globals
{
    /********************************************************
		    Debug Flags
    ********************************************************/
    
    /*< Flag to active or disable debug mode in class Node. */
    public static boolean m_bDebugNode 		= true;
    /*< Flag to active or disable debug mode in class LeaderListen. */
    public static boolean m_bDebugLeader 	= true;
    
    
    /********************************************************
		    Port Variables
    ********************************************************/
    
    /*< Port to find leader and leader listener to attend new nodes. */
	public static int m_iPortLeaderListener		= 10000;
    /*< Time in miliseconds to wait for leader reply. */
	public static int m_iTimeOutLeaderSearch	= 2000;
	public static int m_iORBInitialPort		= 1050;

	
    /********************************************************
		    Network Variables
    ********************************************************/
    
    /*< ID of the group to which the multicast will be sent. */
    public static String m_strGroupId	= "224.0.0.3";
    /*< ID of the leader of the group. */
    public static String m_strLeaderId	= "";
    
    /**
    * ******************************************************
    *		    Resource Variables
    *******************************************************
     */
    public static String m_strDownloadPath  = "C:\\Users\\gamaa_000\\Desktop\\Resources\\";
    public static String m_strSharedDirPath = "C:\\Users\\gamaa_000\\Desktop\\Resources\\";

    static public void PrintMessage( String in_strMessage, boolean in_bPrint )
    {
	 if( in_bPrint )
		 System.out.println(in_strMessage);
    }
}
