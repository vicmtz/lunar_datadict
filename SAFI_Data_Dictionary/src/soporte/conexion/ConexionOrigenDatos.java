package soporte.conexion;

import java.util.HashMap;

public class ConexionOrigenDatos {
    private static HashMap origenDatosMapa = null;

    public static HashMap getConexion() {
        if (origenDatosMapa == null) {
            origenDatosMapa = new HashMap();
        }
        return origenDatosMapa;
    }
}