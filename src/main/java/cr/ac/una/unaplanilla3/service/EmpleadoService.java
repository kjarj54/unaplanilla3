/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla3.service;

import cr.ac.una.unaplanilla3.model.EmpleadoDto;
import cr.ac.una.unaplanilla3.util.EntityManagerHelper;
import cr.ac.una.unaplanilla3.util.Respuesta;
import cr.ac.una.unaplanilla3.model.Empleado;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class EmpleadoService {
    
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getUsuario(String usuario, String clave) {
        try {      
            Query query = em.createNamedQuery("Empleado.findByUsuClave",Empleado.class);
            query.setParameter("usuario", usuario);
            query.setParameter("clave", clave);
            EmpleadoDto empleado = new EmpleadoDto((Empleado)query.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleado);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con las credenciales ingresadas.", "getUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el usuario.", "getUsuario NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta getEmpleado(Long id) {
        try {
            Query query = em.createNamedQuery("Empleado.findByEmpId",Empleado.class);
            query.setParameter("id", id);
            EmpleadoDto empleado = new EmpleadoDto((Empleado)query.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleado);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un empleado con el código ingresado.", "getEmpleado NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el empleado.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el empleado.", "getEmpleado NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el empleado.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el empleado.", "getEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta getEmpleados(String cedula, String nombre, String pApellido, String sApellido) {
        try {
            Query query = em.createNamedQuery("Empleado.findByCedulaNombreApellidos",Empleado.class);
            query.setParameter("nombre", nombre);
            query.setParameter("cedula", cedula);
            query.setParameter("pApellido", pApellido);
            query.setParameter("sApellido", sApellido);
            List<Empleado> empleados = (List<Empleado>) query.getResultList();
            List<EmpleadoDto> empleadosDto = new ArrayList<>();
            for (Empleado emp : empleados) {
                empleadosDto.add(new EmpleadoDto(emp));
            }
            return new Respuesta(true, "", "", "Empleados", empleadosDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existen empleados con los criterios ingresados.", "getEmpleados NoResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo empleados.", ex);
            return new Respuesta(false, "Error obteniendo empleados.", "getEmpleados " + ex.getMessage());
        }
    }
    
    public Respuesta guardarEmpleado(EmpleadoDto empleadoDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Empleado empleado;
            if (empleadoDto.getId() != null && empleadoDto.getId() > 0){
                empleado = em.find(Empleado.class, empleadoDto.getId());
                if (empleado == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el empleado a modificar.", "guardarEmpleado NoResultException");
                }
                empleado.actualizarEmpleado(empleadoDto);
                empleado = em.merge(empleado);
            } else{
                empleado = new Empleado(empleadoDto);
                em.persist(empleado);
            }            
            et.commit();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDto(empleado));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            return new Respuesta(false, "Error guardando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarEmpleado(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            Empleado empleado;
            if (id != null && id > 0){
                empleado = em.find(Empleado.class, id);
                if (empleado == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el empleado a eliminar.", "eliminarEmpleado NoResultException");
                }
                em.remove(empleado);
            } else {
                et.rollback();
                return new Respuesta(false, "Debe cargar el empleado a eliminar.", "eliminarEmpleado NoResultException");
            }
            et.commit();
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            et.rollback();
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, "No se puede eliminar el empleado porque tiene relaciones con otros registros.", "eliminarEmpleado " + ex.getMessage());
            }
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            return new Respuesta(false, "Error eliminando el empleado.", "eliminarEmpleado " + ex.getMessage());
        }
    }
}
