/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla3.model;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author cbcar
 */
public class EmpleadoDto {
    public SimpleStringProperty id;
    public SimpleStringProperty nombre;
    public SimpleStringProperty pApellido;
    public SimpleStringProperty sApellido;
    public SimpleStringProperty cedula;
    public ObjectProperty<String> genero;
    public SimpleStringProperty correo;
    public SimpleStringProperty usuario;
    public SimpleStringProperty clave;
    public SimpleBooleanProperty administrador;
    public SimpleBooleanProperty estado;
    public ObjectProperty<LocalDate> fIngreso;
    public ObjectProperty<LocalDate> fSalida;
    private Boolean modificado;

    public EmpleadoDto() {
        this.modificado = false;
        this.id = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.pApellido = new SimpleStringProperty();
        this.sApellido = new SimpleStringProperty();
        this.cedula = new SimpleStringProperty();
        this.genero = new SimpleObjectProperty("M");
        this.correo = new SimpleStringProperty();
        this.administrador = new SimpleBooleanProperty(false);
        this.usuario = new SimpleStringProperty();
        this.clave = new SimpleStringProperty();
        this.fIngreso = new SimpleObjectProperty();
        this.fSalida = new SimpleObjectProperty();
        this.estado = new SimpleBooleanProperty(true);
    }
    
    public EmpleadoDto(Empleado empleado) {
        this();
        this.id.set(empleado.getId().toString());
        this.nombre.set(empleado.getNombre());
        this.pApellido.set(empleado.getPrimerApellido());
        this.sApellido.set(empleado.getSegundoApellido());
        this.cedula.set(empleado.getCedula());
        this.genero.set(empleado.getGenero());
        this.correo.set(empleado.getCorreo());
        this.administrador.setValue(empleado.getAdministrador().equalsIgnoreCase("S"));
        this.usuario.set(empleado.getUsuario());
        this.clave.set(empleado.getClave());
        this.fIngreso.set(empleado.getFechaIngreso());
        this.fSalida.set(empleado.getFechaSalida());
        this.estado.setValue(empleado.getEstado().equalsIgnoreCase("A"));
    }

    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty())
            return Long.valueOf(id.get());
        else
            return null;
    }

    public void setId(Long id) {
        this.id.setValue(id.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getpApellido() {
        return pApellido.get();
    }

    public void setpApellido(String pApellido) {
        this.pApellido.set(pApellido);
    }

    public String getsApellido() {
        return sApellido.get();
    }

    public void setsApellido(String sApellido) {
        this.sApellido.set(sApellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.set(clave);
    }

    public String getAdministrador() {
        return administrador.getValue()?"S":"N";
    }

    public void setAdministrador(String administrador) {
        this.administrador.setValue(administrador.equalsIgnoreCase("S"));
    }

    public String getEstado() {
        return estado.getValue()?"A":"I";
    }

    public void setEstado(String estado) {
        this.estado.setValue(estado.equalsIgnoreCase("A"));
    }

    public LocalDate getfIngreso() {
        return fIngreso.getValue();
    }

    public void setfIngreso(LocalDate fIngreso) {
        this.fIngreso.setValue(fIngreso);
    }

    public LocalDate getfSalida() {
        return fSalida.getValue();
    }

    public void setfSalida(LocalDate fSalida) {
        this.fSalida.setValue(fSalida);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.nombre);
        hash = 83 * hash + Objects.hashCode(this.pApellido);
        hash = 83 * hash + Objects.hashCode(this.sApellido);
        hash = 83 * hash + Objects.hashCode(this.cedula);
        hash = 83 * hash + Objects.hashCode(this.genero);
        hash = 83 * hash + Objects.hashCode(this.correo);
        hash = 83 * hash + Objects.hashCode(this.usuario);
        hash = 83 * hash + Objects.hashCode(this.clave);
        hash = 83 * hash + Objects.hashCode(this.administrador);
        hash = 83 * hash + Objects.hashCode(this.estado);
        hash = 83 * hash + Objects.hashCode(this.fIngreso);
        hash = 83 * hash + Objects.hashCode(this.fSalida);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpleadoDto other = (EmpleadoDto) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        return true;
        
        
    }
    
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EmpleadoDto{id=").append(id.get());
        sb.append(", nombre=").append(nombre.get());
        sb.append(", pApellido=").append(pApellido.get());
        sb.append(", sApellido=").append(sApellido.get());
        sb.append(", cedula=").append(cedula.get());
        sb.append(", genero=").append(genero.get());
        sb.append('}');
        return sb.toString();
    }

    
    
    
}
