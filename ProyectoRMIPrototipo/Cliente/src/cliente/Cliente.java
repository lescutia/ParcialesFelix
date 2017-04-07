package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.Scanner;
import middlewere.IAutenticacion;
import middlewere.IServidor;

/**
 *
 * @author luise
 */
public class Cliente 
{
    private static int SesionActual = 0;
    private static IServidor Serv;
   // private static IAutenticacion Aute;
    private static BufferedReader LeerArchivo = new BufferedReader(new InputStreamReader(System.in));
    private static int Puerto = 1495;
    
    public static void main(String[] args) throws Exception
    {
        
        //Aute = (IAutenticacion) Naming.lookup("rmi://localhost:" + Puerto + "/ImplServ");
        IAutenticacion  Aute =  (IAutenticacion) Naming.lookup("rmi://localhost/ImplServ");
        6
        String NombreUsuario;
        Scanner leer = new Scanner(System.in);
        do
        {
            System.out.println("Por favor escribe tu nombre: ");
            NombreUsuario = leer.nextLine();
            SesionActual = Aute.autenticarRepositorio(NombreUsuario);
        }while(SesionActual == 0);
    }
    
}