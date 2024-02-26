package backend.enums;

public enum Carrera {
    INGENIERIA(1),
    MEDICINA(2),
    DERECHO(3),
    ARQUITECTURA(4),
    ADMINISTRACION(5);

    private final int codigo;

    Carrera(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Carrera getCarreraPorCodigo(int codigo) {

        for (int i = 0; i < Carrera.values().length; i++) {
            if(Carrera.values()[i].getCodigo() == codigo) {
                return Carrera.values()[i];
            }
        }
        throw new IllegalArgumentException("No existe carrera con el código: " + codigo);
    }
}
