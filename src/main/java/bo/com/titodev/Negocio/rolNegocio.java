package bo.com.titodev.Negocio;

import java.util.LinkedList;

import bo.com.titodev.Dato.*;
import bo.com.titodev.Utils.*;

public class rolNegocio {

    private rolDato rol;
    private String respuesta;
    
    public rolNegocio() {
        rol = new rolDato();
    }
    
    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        rol = new rolDato(0, params.get(0) );
        if (rol.create()) {
            respuesta = "Creado exitosamente.";
        } else {
            respuesta = "No se pudo crear.";
        }
        return respuesta;
    }
    
    public String update(LinkedList<String> params) {
        validateUpdate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        rol = new rolDato(Integer.parseInt(params.get(0)), params.get(1));
        if (rol.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }
    
    public String delete(int id) {
        if (!validatorUtils.validateNumber(String.valueOf(id))) {
            return "El id debe ser un numero";
        }
        rol.setId(id);
        if (rol.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }
    
    public String getAll(LinkedList<String> params) {
        return rol.getAll(params);
    }
    
    public boolean exist(int id) {
        return rol.exist(id);
    }
    
    private void validateCreate(LinkedList<String> params) {
        rol = new rolDato();
        if (params.size() != 1) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El nombre no puede ser vacio";
            return;
        }
        
    }
    
    private void validateUpdate(LinkedList<String> params) {
        rol = new rolDato();
        if (params.size() != 2) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre no puede ser vacio";
            return;
        }
        
    }
    
    public LinkedList<String> createList(String[] params) {
        LinkedList<String> list = new LinkedList<>();
        for (String param : params) {
            param = param.trim();
            list.add(param);
        }
        return list;
    }
}
