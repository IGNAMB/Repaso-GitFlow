/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofase3;

import java.util.Date;

/**
 *
 * @author fping
 */
public class Usuario {
    private int idUsuario;
    private String password;
    private String nombreCompleto;
    private Date fechaIng;
    private String email;

    // Constructor
    public Usuario(int idUsuario, String password, String nombreCompleto, Date fechaIng, String email) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.fechaIng = fechaIng;
        this.email = email;
    }

    // Getters y setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaIng() {
        return fechaIng;
    }

    public void setFechaIng(Date fechaIng) {
        this.fechaIng = fechaIng;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
