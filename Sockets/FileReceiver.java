import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.lang.Exception;
import java.net.SocketException;
import java.io.IOException;

public class FileReceiver extends Thread{
	String owner;
	String path;
	String fileName;
	public FileReceiver(String owner, String path, String fileName)
	{
		this.owner		= owner;
		this.path		= path;
		this.fileName	= fileName;
	}

	public void run() 
	{
		try{
			Socket socket = new Socket(InetAddress.getByName(owner),Globals.m_iPortFileTransmiter);
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			Message msg = new Message(Message.EMessageType.DATA_REQUEST, 20, fileName);
			outputStream.writeObject(msg);
			outputStream.flush();		
			ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
			Message inputMsg = (Message)objectIn.readObject();
			outputStream.close();
			socket.close();
			byte[] file = inputMsg.GetDataBytes();
			String tmpPath = path + fileName;
		    FileOutputStream fos = new FileOutputStream(tmpPath);
			fos.write(file);
			fos.close();
		}
		catch(SocketException e)
		{
			System.out.println("[FileReceiverSocketException]");
		}
		catch(IOException e){
			System.out.println("[FileReceiverIOException]");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("[FileReceiverCNFException]");
		}
	}
}