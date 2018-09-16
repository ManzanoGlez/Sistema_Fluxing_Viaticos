package manzano.utj.sistemafluxing.Modelos;

public class Usuario {

    private String nombre;
    private String correo;
    private String contrasena;
    private String celular;
    private String info_dispositivo;
    private String tipo;
    private String fecha_cumpleaños;
    private String lugar_nacimiento;
    private String RFC;

    public Usuario(String nombre, String correo, String contrasena, String celular, String info_dispositivo, String tipo, String fecha_cumpleaños, String lugar_nacimiento, String RFC) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.celular = celular;
        this.info_dispositivo = info_dispositivo;
        this.tipo = tipo;
        this.fecha_cumpleaños = fecha_cumpleaños;
        this.lugar_nacimiento = lugar_nacimiento;
        this.RFC = RFC;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getInfo_dispositivo() {
        return info_dispositivo;
    }

    public void setInfo_dispositivo(String info_dispositivo) {
        this.info_dispositivo = info_dispositivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha_cumpleaños() {
        return fecha_cumpleaños;
    }

    public void setFecha_cumpleaños(String fecha_cumpleaños) {
        this.fecha_cumpleaños = fecha_cumpleaños;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }
}
