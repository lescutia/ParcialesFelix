package dms;


/**
* dms/DataHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Friday, April 28, 2017 9:17:56 AM CDT
*/

public final class DataHolder implements org.omg.CORBA.portable.Streamable
{
  public byte value[] = null;

  public DataHolder ()
  {
  }

  public DataHolder (byte[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dms.DataHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dms.DataHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dms.DataHelper.type ();
  }

}
