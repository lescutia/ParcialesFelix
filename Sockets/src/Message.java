import java.io.Serializable;
import java.util.*;

public class Message implements Serializable
{
	public enum EMessageType {DISCOVERY, DATA_REQUEST, DATA_REPLY , UPDATE, REQUEST, REPLY}
	EMessageType m_MsgType;
	List m_Data;
	byte[] m_ByteData;
	int m_id;
	String m_FileName;
	public Message( EMessageType in_MsgType, int in_Id){
		this.m_MsgType	= in_MsgType;
		this.m_id		= in_Id;
	}
	public Message( EMessageType in_MsgType, int in_Id, String in_FileName){
		this.m_MsgType	= in_MsgType;
		this.m_id		= in_Id;
		this.m_FileName	= in_FileName; 
	}
	public Message( EMessageType in_MsgType, int in_Id, byte[] in_Data )
	{
		this.m_MsgType 	= in_MsgType;
		this.m_id		= in_Id;
		this.m_ByteData	= in_Data;
	}
	public Message( EMessageType in_MsgType, int in_Id, List in_Data )
	{
		this.m_MsgType	= in_MsgType;
		this.m_id 		= in_Id;
		this.m_Data 	= in_Data;
	}

	public List GetResources()
	{
		return m_Data;
	}

	public byte[] GetDataBytes()
	{
		return m_ByteData;
	}

	public String GetFileName()
	{
		return m_FileName;
	}

	public int GetID()
	{
		return m_id;
	}

	public EMessageType GetMsgType()
	{
		return m_MsgType;
	}
}