package co.com.juandiego.mensajes.modelo;

/**
 * Created by Juan Diego on 11/06/2017.
 */

public class Cita {
    private String estado;
    private String mensaje;

    public Cita() {
    }

    public Cita(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
