package middlewere;
import java.rmi.Remote;

/**
 *
 * @author luise
 */
public class Archivo implements Remote
{
    private static final long serialVersionUID = 6473037307367070437L;
    private String NombreArchivo, TipoArchivo;
    
    public Archivo(String NombreArchivo, String TipoArchivo)
    {
        this.NombreArchivo = NombreArchivo;
        this.TipoArchivo = TipoArchivo;
    }
    
    public String getNombreArchivo()
    {
        return NombreArchivo;
    }
    
    public String getTipoArchivo()
    {
        return TipoArchivo;
    }
}
