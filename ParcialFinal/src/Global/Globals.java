package Global;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
    public static boolean m_bDebugConnection	= true;
    public static boolean m_bDebugExceptions    = true;
    
    
    /********************************************************
			Network Variables
    ********************************************************/
	/*< ID of the group to which the multicast will be sent. */
	public static String m_strGroupId   = "224.0.0.3";
	/*< ID of the leader of the group. */
	public static String m_strLeaderId  = "";
	
    /********************************************************
			Port Variables
    ********************************************************/
	/*< Port to find leader and leader listener to attend new nodes. */
	public static int m_iPortLeaderListener         = 10000;
        /*< Time in miliseconds to wait for leader reply. */
        public static int m_iTimeOutLeaderSearch 	= 2000;
        
    /********************************************************
                        Resource Variables
    ********************************************************/
        public static String m_strDownloadPath = "";
        public static String m_strSharedDirPath = "";
}
