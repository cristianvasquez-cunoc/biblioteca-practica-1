package backend.lectortxt;

public class RegistroFallidoException extends Exception{

    private String textoError;
    private int tipo;

    public RegistroFallidoException(String mensajeError, String textoError, int tipo){
        super(mensajeError);
        this.textoError = textoError;
        this.tipo = tipo;
    }

    public String getTextoError() {
        return textoError;
    }

    public int getTipo() {
        return tipo;
    }
}
