package model.entidades;

import model.enums.TipoDocIdentidad;

public abstract class Usuario {
    private String id;
    private TipoDocIdentidad tipoDocIdentidad;
    private String numDocIdentidad;
    private String nombre;
    private String telefono;
    private String correo;
    private String password;

    // Constructor
    public Usuario(String id, TipoDocIdentidad tipoDocIdentidad, String numDocIdentidad,
                   String nombre, String telefono, String correo, String password) {
        this.id = id;
        this.tipoDocIdentidad = tipoDocIdentidad;
        this.numDocIdentidad = numDocIdentidad;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
    }

    public boolean iniciarSesion(String password) {
        return this.password != null && this.password.equals(password);
    }

    public void actualizarDatos(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public TipoDocIdentidad getTipoDocIdentidad() {
        return tipoDocIdentidad;
    }
    public void setTipoDocIdentidad(TipoDocIdentidad tipoDocIdentidad) {
        this.tipoDocIdentidad = tipoDocIdentidad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNumDocIdentidad() {
        return numDocIdentidad;
    }
    public void setNumDocIdentidad(String numDocIdentidad) {
        this.numDocIdentidad = numDocIdentidad;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getTipoUsuario() { return "CLIENTE"; }

    @Override
    public String toString() {
        return nombre + " (" + tipoDocIdentidad + ". " + numDocIdentidad + ")";
    }
}
