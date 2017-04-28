package dms;


/**
* dms/RemoteServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Friday, April 28, 2017 9:20:10 AM CDT
*/

abstract public class RemoteServerHelper
{
  private static String  _id = "IDL:dms/RemoteServer:1.0";

  public static void insert (org.omg.CORBA.Any a, dms.RemoteServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static dms.RemoteServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (dms.RemoteServerHelper.id (), "RemoteServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static dms.RemoteServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_RemoteServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, dms.RemoteServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static dms.RemoteServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof dms.RemoteServer)
      return (dms.RemoteServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      dms._RemoteServerStub stub = new dms._RemoteServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static dms.RemoteServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof dms.RemoteServer)
      return (dms.RemoteServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      dms._RemoteServerStub stub = new dms._RemoteServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
