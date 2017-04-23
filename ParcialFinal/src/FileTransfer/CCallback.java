/**************************************************************************
	file:	 	CCallBack.java
	date:		2017/04/08 17:17	
	author:		Luis Eduardo Villela Zavala, Xitlali Moran Soltero
	Contact:    	luisedo21@gmail.com

	brief: Interface used to declare the writeData method. 
**************************************************************************/
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
