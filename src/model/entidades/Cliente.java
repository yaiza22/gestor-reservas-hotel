package model.entidades;

import model.enums.TipoDocIdentidad;

public class Cliente extends Usuario{

    //-------------------------------------------------------------
    //Atributos
    //-------------------------------------------------------------
    private String nacionalidad;
    private String paisResidencia;

    //-------------------------------------------------------------
    //Constructor
    //-------------------------------------------------------------

    public Cliente(String id, TipoDocIdentidad tipoDocIdentidad, String numDocIdentidad, String nombre, String telefono, String correo, String password, String nacionalidad, String paisResidencia) {
        super(id, tipoDocIdentidad, numDocIdentidad, nombre, telefono, correo, password);
        this.nacionalidad = nacionalidad;
        this.paisResidencia = paisResidencia;
    }

    //-------------------------------------------------------------
    //Métodos
    //-------------------------------------------------------------

    /**
     * Nacionalidad del cliente
     * @return nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Le asigna una nacionalidad al cliente
     * @param nacionalidad
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * País de residencia del cliente
     * @return país de residencia del cliente
     */
    public String getPaisResidencia() {
        return paisResidencia;
    }

    /**
     * Le asigna un país de residencia al cliente
     * @param paisResidencia
     */
    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    @Override
    public String getTipoUsuario() {
        return "CLIENTE";
    }
}
