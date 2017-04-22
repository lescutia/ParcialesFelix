/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileTransfer;
import java.rmi.*;
/**
 *
 * @author gamaa_000
 */
public interface CCallback extends Remote
{
    public void writeData(String inFileName, byte []data , int dataLength,long fileLength) throws RemoteException;
}
