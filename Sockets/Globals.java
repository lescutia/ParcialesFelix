public class Globals 
{
	/********************************************************
						Debug Flags
	********************************************************/
   /*< Flag to active or disable debug mode in class Node. */
   public static boolean m_bDebugNode 		= true;
   /*< Flag to active or disable debug mode in class DataInfoManager. */
	public static boolean m_bDebugDIMng 	= true;
	/*< Flag to active or disable debug mode in class LeaderListen. */
	public static boolean m_bDebugLeader 	= true;
	/*< Flag to active or disable debug mode in class DynamicTreeDemo. */
	public static boolean m_bDebugTree 		= false;
   /*< Flag to active or disable debug mode in class Main. */
	public static boolean m_bDebugMain	 	= true;

	/********************************************************
						Port Variables
	********************************************************/
	/*< Port to transmit files between users. */
	public static int m_iPortFileTransmiter 	= 16000;
	/*< Port to listen control messages. */
	public static int m_iPortDataInfoManager 	= 15000;
	/*< Port to send resource tables to Leader and receive general table of resources. */
	public static int m_iPortResourceUpdater 	= 15000;
	/*< Port to request the updated table of resources to the leader. */
	public static int m_iPortRefresh 			= 15000;
	/*< Port to find leader and leader listener to attend new nodes. */
	public static int m_iPortLeaderListener 	= 10000;
   /*< Time in miliseconds to wait for leader reply. */
   public static int m_iTimeOutLeaderSearch 	= 2000;
   /********************************************************
						Network Variables
	********************************************************/
	/*< ID of the group to which the multicast will be sent. */
	public static String m_strGroupId = "224.0.0.3";
	/*< ID of the leader of the group. */
	public static String m_strLeaderId = "";

	/********************************************************
						Resource Variables
	********************************************************/
   public static String m_strDownloadPath = "";
   public static String m_strSharedDirPath = "";

   static public void PrintMessage( String in_strMessage, boolean in_bPrint )
   {
   	if( in_bPrint )
   		System.out.println(in_strMessage);
   }
   /********************************************************
                  State Variable
   ********************************************************/
   public static boolean m_bIsLeader = true;
   public static boolean m_bIsClient = false;
}