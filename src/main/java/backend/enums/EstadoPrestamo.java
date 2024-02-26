package backend.enums;

public enum EstadoPrestamo {

    FINALIZADO(1), PENDIENTE(2);

    private final int codigo;

    private EstadoPrestamo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static EstadoPrestamo getEstadoPrestamoPorCodigo(int codigo) {

        for (int i = 0; i < EstadoPrestamo.values().length; i++) {
            if(EstadoPrestamo.values()[i].getCodigo() == codigo) {
                return EstadoPrestamo.values()[i];
            }
        }
        throw new IllegalArgumentException("No existe estado del prestamo con el código: " + codigo);
    }
}
