package bo.com.titodev.Negocio;

import java.util.LinkedList;

import bo.com.titodev.Dato.docenteDato;
import bo.com.titodev.Utils.validatorUtils;
 
public class docenteNegocio {
       private docenteDato docente;
    private String respuesta;

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }

        docente = new docenteDato(
                params.get(0), params.get(1), params.get(2),
                params.get(3), params.get(4),   params.get(5),
                params.get(6),Integer.parseInt(params.get(7)));
        if (docente.create()) {
            respuesta = "Creado exitosamente.";
        } else {
            respuesta = "No se pudo crear.";
        }
        return respuesta;
    }

    public String update(LinkedList<String> params) {
        validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
     
        docente = new docenteDato(
                params.get(0), params.get(1), params.get(2),
                params.get(3), params.get(4),   params.get(5),
                params.get(6),Integer.parseInt(params.get(7))); 
            
            if (docente.update(params.get(0))) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String ci) {
        if (!validatorUtils.validateString(ci)) {
            return "El id debe ser un numero";
        }
        docente.setCi(ci);
        if (docente.delete(ci)) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return docente.getAll(params);
    }

    public boolean exist(String ci) {
        return docente.exist(ci);
    }
    private void validateCreate(LinkedList<String> params) {
        int expectedParamCount = 8; // Número de parámetros esperados
    
        // Validar la cantidad de parámetros
        if (params.size() != expectedParamCount) {
            this.respuesta = "La cantidad de parámetros es incorrecta";
            return;
        }
    
        // Validar CI
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El CI no puede ser vacío";
            return;
        }
    
        // Validar nombre
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre no puede ser vacío";
            return;
        }
    
        // Validar apellido paterno
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El apellido paterno no puede ser vacío";
            return;
        }
    
        // Validar apellido materno
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El apellido materno no puede ser vacío";
            return;
        }
    
        // Validar kardex
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "El kardex no puede ser vacío";
            return;
        }
    
        // Validar curriculum
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El curriculum no puede ser vacío";
            return;
        }
    
        // Validar email
        if (!validatorUtils.validateEmail(params.get(6))) {
            this.respuesta = "El email no es válido";
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
