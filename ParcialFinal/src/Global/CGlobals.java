/**************************************************************************
	file:	 	Globals.java
	date:		2017/04/08 17:38
	author:		Luis Escutia, Gamaliel Palomo
	Contact:    	escutialuis93@gmail.com

	brief: Contains control variables to manage the program.
**************************************************************************/

package Global;
import java.io.*;
//import java.lang.System.*;
import java.util.Scanner;

public class CGlobals 
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
    public static String m_strGroupId   = "224.0.0.5";
    /*< ID of the leader of the group. */
    public static String m_strLeaderId  = "";
    /*< Local host IP address. */
    public static String m_strLocalHost = "";
	
    /********************************************************
			Port Variables
    ********************************************************/
    /*< Time in miliseconds to wait for leader reply. */
    public static int m_iTimeOutLeaderSearch 	= 2000;
    /*< Port to find leader and leader listener to attend new nodes. */
    public static int m_iPortLeaderListener     = 10000;
    /*< Time in miliseconds to wait for leader reply. */
    public static int m_iRemoteObjectPort 	= 1500;
    
    /********************************************************
                        Resource Variables
    ********************************************************/
    public static String m_strDownloadPath = "";
    public static String m_strSharedDirPath = "";
    
    public static void saveConfig()
    {
        try
        {
            FileWriter fw = new FileWriter( "config.txt" );
            PrintWriter pw = new PrintWriter( fw );
            
            pw.println( m_strDownloadPath );
            pw.println( m_strSharedDirPath );
            pw.close();
        }
        catch( IOException e ) {}
    }
    
    public static void loadConfig()
    {
        try
        {
            m_strLocalHost = Connection.NetworkUtils.getLocalIP();
                System.setProperty("java.rmi.server.hostname",m_strLocalHost);
            
            File file = new File( "config.txt" );
            Scanner br = new Scanner( file );
            
            if( br.hasNext() )
            {
                m_strDownloadPath = br.nextLine();
                m_strSharedDirPath = br.nextLine();
            }
            br.close();
        }
        catch( IOException e ) {}
    }
    
}
