package cr.ac.una.unaplanilla3.model;

import cr.ac.una.unaplanilla3.model.TipoPlanilla;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.8.v20201217-rNA", date="2021-06-14T19:12:11")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, String> administrador;
    public static volatile SingularAttribute<Empleado, String> clave;
    public static volatile SingularAttribute<Empleado, String> estado;
    public static volatile SingularAttribute<Empleado, String> primerApellido;
    public static volatile ListAttribute<Empleado, TipoPlanilla> tiposPlanilla;
    public static volatile SingularAttribute<Empleado, String> cedula;
    public static volatile SingularAttribute<Empleado, String> segundoApellido;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile SingularAttribute<Empleado, Long> version;
    public static volatile SingularAttribute<Empleado, LocalDate> fechaIngreso;
    public static volatile SingularAttribute<Empleado, String> genero;
    public static volatile SingularAttribute<Empleado, String> correo;
    public static volatile SingularAttribute<Empleado, LocalDate> fechaSalida;
    public static volatile SingularAttribute<Empleado, String> usuario;
    public static volatile SingularAttribute<Empleado, Long> id;

}