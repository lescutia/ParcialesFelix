package dms;


/**
* dms/fileListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Friday, April 7, 2017 12:10:22 PM CDT
*/

public final class fileListHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public fileListHolder ()
  {
  }

  public fileListHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dms.fileListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dms.fileListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dms.fileListHelper.type ();
  }

}
