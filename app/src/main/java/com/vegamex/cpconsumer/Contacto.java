package com.vegamex.cpconsumer;

import java.io.Serializable;

public class Contacto implements Serializable {
    int id;
    String usuario;
    String email;
    String telefono;
    String fechaNacimiento;

    public Contacto() {
    }

    public Contacto(int id, String usuario, String email, String telefono, String fechaNacimiento) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}