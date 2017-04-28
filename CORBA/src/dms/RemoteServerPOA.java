package dms;


/**
* dms/RemoteServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Friday, April 28, 2017 9:20:10 AM CDT
*/

public abstract class RemoteServerPOA extends org.omg.PortableServer.Servant
 implements dms.RemoteServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("GetFile", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // dms/RemoteServer/GetFile
       {
         String FileName = in.read_string ();
         dms.RemoteClient rCRef = dms.RemoteClientHelper.read (in);
         this.GetFile (FileName, rCRef);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:dms/RemoteServer:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public RemoteServer _this() 
  {
    return RemoteServerHelper.narrow(
    super._this_object());
  }

  public RemoteServer _this(org.omg.CORBA.ORB orb) 
  {
    return RemoteServerHelper.narrow(
    super._this_object(orb));
  }


} // class RemoteServerPOA