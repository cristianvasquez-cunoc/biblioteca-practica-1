package backend.enums;

public enum TipoRegistroFallido {

    ESTUDIANTE(1),
    LIBRO(2),
    PRESTAMO(3);

    private final int codigo;

    TipoRegistroFallido(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoRegistroFallido getTipoRegistroFallidoByCodigo(int codigo) {
        for (int i = 0; i < TipoRegistroFallido.values().length; i++) {
            if(TipoRegistroFallido.values()[i].getCodigo() == codigo) {
                return TipoRegistroFallido.values()[i];
            }
        }
        throw new IllegalArgumentException("No existe un registro fallido con el código: " + codigo);

    }
}
