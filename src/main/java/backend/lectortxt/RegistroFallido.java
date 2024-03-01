package backend.lectortxt;

import backend.enums.TipoRegistroFallido;

public class RegistroFallido {
    private String texto;
    private String error;
    private TipoRegistroFallido tipo;

    public RegistroFallido(String texto, String error, int codigo) {
        this.texto = texto;
        this.error = error;
        this.tipo = TipoRegistroFallido.getTipoRegistroFallidoByCodigo(codigo);
    }
}
