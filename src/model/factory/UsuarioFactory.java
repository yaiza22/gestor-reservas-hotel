package model.factory;

import model.entidades.Cliente;
import model.entidades.Empleado;
import model.entidades.Usuario;
import model.enums.Rol;
import model.enums.TipoDocIdentidad;

public class UsuarioFactory {

    private UsuarioFactory() { }

    public static Usuario crear(String tipo, String id, TipoDocIdentidad tipoDoc, String numDoc,
                                 String nombre, String telefono, String correo, String password, Rol rol, String nacionalidad, String paisResidencia) {
        String tipoNormalizado = tipo == null ? "" : tipo.trim().toUpperCase();
        return switch (tipoNormalizado) {
            case "CLIENTE" -> new Cliente(id, tipoDoc, numDoc, nombre, telefono, correo, password, nacionalidad, paisResidencia);
            case "EMPLEADO" -> new Empleado(id, tipoDoc, numDoc, nombre, telefono, correo, password, rol);
            default -> throw new IllegalArgumentException("Tipo de usuario desconocido: " + tipo);
        };
    }
}
