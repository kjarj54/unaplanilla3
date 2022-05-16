/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla3.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cbcar
 */
@Entity
@Table(name = "PLAM_EMPLEADOS", schema = "una")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByEmpId", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByCedulaNombreApellidos", query = "SELECT e FROM Empleado e WHERE UPPER(e.nombre) like :nombre and UPPER(e.cedula) like :cedula and UPPER(e.primerApellido) like :pApellido and UPPER(e.segundoApellido) like :sApellido", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
    /*@NamedQuery(name = "Empleado.findByEmpNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empleado.findByEmpPapellido", query = "SELECT e FROM Empleado e WHERE e.primerApellido = :primerApellido"),
    @NamedQuery(name = "Empleado.findByEmpSapellido", query = "SELECT e FROM Empleado e WHERE e.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Empleado.findByEmpCedula", query = "SELECT e FROM Empleado e WHERE e.cedula = :cedula"),
    @NamedQuery(name = "Empleado.findByEmpGenero", query = "SELECT e FROM Empleado e WHERE e.genero = :genero"),
    @NamedQuery(name = "Empleado.findByEmpCorreo", query = "SELECT e FROM Empleado e WHERE e.correo = :correo"),
    @NamedQuery(name = "Empleado.findByEmpAdministrador", query = "SELECT e FROM Empleado e WHERE e.administrador = :administrador"),
    @NamedQuery(name = "Empleado.findByEmpUsuario", query = "SELECT e FROM Empleado e WHERE e.usuario = :usuario"),
    @NamedQuery(name = "Empleado.findByEmpClave", query = "SELECT e FROM Empleado e WHERE e.clave = :clave"),
    @NamedQuery(name = "Empleado.findByEmpFingreso", query = "SELECT e FROM Empleado e WHERE e.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Empleado.findByEmpFsalida", query = "SELECT e FROM Empleado e WHERE e.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "Empleado.findByEmpEstado", query = "SELECT e FROM Empleado e WHERE e.estado = :estado"),
    @NamedQuery(name = "Empleado.findByEmpVersion", query = "SELECT e FROM Empleado e WHERE e.version = :version")*/})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PLAM_EMPLEADOS_EMP_ID_GENERATOR", sequenceName = "una.PLAM_EMPLEADOS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_EMPLEADOS_EMP_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "EMP_NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "EMP_PAPELLIDO")
    private String primerApellido;
    @Basic(optional = false)
    @Column(name = "EMP_SAPELLIDO")
    private String segundoApellido;
    @Basic(optional = false)
    @Column(name = "EMP_CEDULA")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "EMP_GENERO")
    private String genero;
    @Column(name = "EMP_CORREO")
    private String correo;
    @Basic(optional = false)
    @Column(name = "EMP_ADMINISTRADOR")
    private String administrador;
    @Column(name = "EMP_USUARIO")
    private String usuario;
    @Column(name = "EMP_CLAVE")
    private String clave;
    @Basic(optional = false)
    @Column(name = "EMP_FINGRESO")
    //@Temporal(TemporalType.DATE)
    private LocalDate fechaIngreso;
    @Column(name = "EMP_FSALIDA")
    //@Temporal(TemporalType.DATE)
    private LocalDate fechaSalida;
    @Basic(optional = false)
    @Column(name = "EMP_ESTADO")
    private String estado;
    @Version
    @Basic(optional = false)
    @Column(name = "EMP_VERSION")
    private Long version;
    @ManyToMany(mappedBy = "empleados", fetch = FetchType.LAZY)
    private List<TipoPlanilla> tiposPlanilla;

    public Empleado() {
    }

    public Empleado(Long empId) {
        this.id = empId;
    }

    public Empleado(Long empId, String empNombre, String empPapellido, String empSapellido, String empCedula, String empGenero, String empAdministrador, LocalDate empFingreso, String empEstado) {
        this.id = empId;
        this.nombre = empNombre;
        this.primerApellido = empPapellido;
        this.segundoApellido = empSapellido;
        this.cedula = empCedula;
        this.genero = empGenero;
        this.administrador = empAdministrador;
        this.fechaIngreso = empFingreso;
        this.estado = empEstado;
    }
    
    public Empleado(EmpleadoDto empleadoDto){
        this.id = empleadoDto.getId();
        actualizarEmpleado(empleadoDto);
    }
    
    public void actualizarEmpleado(EmpleadoDto empleadoDto){
        this.nombre = empleadoDto.getNombre();
        this.primerApellido = empleadoDto.getpApellido();
        this.segundoApellido = empleadoDto.getsApellido();
        this.cedula = empleadoDto.getCedula();
        this.genero = empleadoDto.getGenero();
        this.administrador = empleadoDto.getAdministrador();
        this.fechaIngreso = empleadoDto.getfIngreso();
        this.fechaSalida = empleadoDto.getfSalida();
        this.correo = empleadoDto.getCorreo();
        this.usuario = empleadoDto.getUsuario();
        this.clave = empleadoDto.getClave();
        this.estado = empleadoDto.getEstado();
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @XmlTransient
    public List<TipoPlanilla> getTiposPlanilla() {
        return tiposPlanilla;
    }

    public void setTiposPlanilla(List<TipoPlanilla> tiposPlanilla) {
        this.tiposPlanilla = tiposPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla3.model.Empleado[ id=" + id + " ]";
    }
    
}
