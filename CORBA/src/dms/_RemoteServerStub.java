package dms;


/**
* dms/_RemoteServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Dms.idl
* Wednesday, April 5, 2017 7:20:01 PM CDT
*/

public class _RemoteServerStub extends org.omg.CORBA.portable.ObjectImpl implements dms.RemoteServer
{

  public void GetFile (String fileName, dms.RemoteClient rCRef)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("GetFile", true);
                $out.write_string (fileName);
                dms.RemoteClientHelper.write ($out, rCRef);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                GetFile (fileName, rCRef        );
            } finally {
                _releaseReply ($in);
            }
  } // GetFile

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:dms/RemoteServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _RemoteServerStub
