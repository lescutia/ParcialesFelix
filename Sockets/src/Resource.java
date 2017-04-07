import java.util.*;
import java.io.Serializable;
public class Resource implements Serializable
{
	String m_strOwner;
	List m_Elements;

	public Resource(String in_strOwner, List in_Elements)
	{
		this.m_strOwner = in_strOwner;
		this.m_Elements = in_Elements;
	}

	public List GetElements()
	{
		return m_Elements;
	}

	public String GetResourcesOwner()
	{
		return m_strOwner;
	}
}