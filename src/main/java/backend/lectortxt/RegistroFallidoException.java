package backend.lectortxt;

public class RegistroFallidoException extends Exception{

    private String textoError;
    public RegistroFallidoException(String mensajeError, String textoError){
        super(mensajeError);
        this.textoError = textoError;
    }

    public String getTextoError() {
        return textoError;
    }
}
