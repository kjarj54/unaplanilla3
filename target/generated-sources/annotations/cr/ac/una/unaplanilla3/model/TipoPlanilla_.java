package cr.ac.una.unaplanilla3.model;

import cr.ac.una.unaplanilla3.model.Empleado;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.8.v20201217-rNA", date="2021-06-14T19:12:11")
@StaticMetamodel(TipoPlanilla.class)
public class TipoPlanilla_ { 

    public static volatile SingularAttribute<TipoPlanilla, String> descripcion;
    public static volatile SingularAttribute<TipoPlanilla, String> codigo;
    public static volatile SingularAttribute<TipoPlanilla, Integer> numUltPla;
    public static volatile SingularAttribute<TipoPlanilla, String> estado;
    public static volatile SingularAttribute<TipoPlanilla, Integer> mesUltPla;
    public static volatile SingularAttribute<TipoPlanilla, Integer> planillasMes;
    public static volatile ListAttribute<TipoPlanilla, Empleado> empleados;
    public static volatile SingularAttribute<TipoPlanilla, Long> id;
    public static volatile SingularAttribute<TipoPlanilla, Integer> anoUltPla;
    public static volatile SingularAttribute<TipoPlanilla, Integer> version;

}