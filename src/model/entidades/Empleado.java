package model.entidades;

import model.enums.Rol;
import model.enums.TipoDocIdentidad;

public class Empleado extends Usuario {

    private Rol rol;


    public Empleado(String id, TipoDocIdentidad tipoDocIdentidad, String numDocIdentidad, String nombre, String telefono, String correo, String password) {
        super(id, tipoDocIdentidad, numDocIdentidad, nombre, telefono, correo, password);
        this.rol = rol;
    }
    //-----------------------------------------
    //Métodos
    //-----------------------------------------
    public boolean gestionarPermisos(Rol rolRequerido) {
        return this.rol == Rol.ADMINISTRADOR || this.rol == rolRequerido;
    }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
