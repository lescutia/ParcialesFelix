package dms;

/**
* dms/RemoteServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Friday, April 28, 2017 9:20:10 AM CDT
*/

public final class RemoteServerHolder implements org.omg.CORBA.portable.Streamable
{
  public dms.RemoteServer value = null;

  public RemoteServerHolder ()
  {
  }

  public RemoteServerHolder (dms.RemoteServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dms.RemoteServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dms.RemoteServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dms.RemoteServerHelper.type ();
  }

}
