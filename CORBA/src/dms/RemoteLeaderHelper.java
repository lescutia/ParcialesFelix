package dms;


/**
* dms/RemoteLeaderHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Friday, April 28, 2017 9:19:52 AM CDT
*/

abstract public class RemoteLeaderHelper
{
  private static String  _id = "IDL:dms/RemoteLeader:1.0";

  public static void insert (org.omg.CORBA.Any a, dms.RemoteLeader that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static dms.RemoteLeader extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (dms.RemoteLeaderHelper.id (), "RemoteLeader");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static dms.RemoteLeader read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_RemoteLeaderStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, dms.RemoteLeader value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static dms.RemoteLeader narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof dms.RemoteLeader)
      return (dms.RemoteLeader)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      dms._RemoteLeaderStub stub = new dms._RemoteLeaderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static dms.RemoteLeader unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof dms.RemoteLeader)
      return (dms.RemoteLeader)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      dms._RemoteLeaderStub stub = new dms._RemoteLeaderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
