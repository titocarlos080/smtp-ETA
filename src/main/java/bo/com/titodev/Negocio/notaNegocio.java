package bo.com.titodev.Negocio;

import java.util.LinkedList;

import bo.com.titodev.Dato.*;
import bo.com.titodev.Utils.*;

public class notaNegocio {

    private notaDato nota;
    private String respuesta;
    
    public notaNegocio() {
        nota = new notaDato();
    }
    
    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        nota = new notaDato(0, Integer.getInteger(params.get(0)),Double.parseDouble(params.get(1)) );
        if (nota.create()) {
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
        nota = new notaDato(Integer.parseInt(params.get(0)),Integer.parseInt(params.get(1)),Double.parseDouble(params.get(2)));
        if (nota.update()) {
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
        nota.setId(id);
        if (nota.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }
    
    public String getAll(LinkedList<String> params) {
        return nota.getAll(params);
    }
    
    public boolean exist(int id) {
        return nota.exist(id);
    }
    
    private void validateCreate(LinkedList<String> params) {
        nota = new notaDato();
        if (params.size() != 2) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser numerico";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "La Nota debe ser numero";
            return;
        }
    }
    
    private void validateUpdate(LinkedList<String> params) {
        nota = new notaDato();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "El id_mate no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "La nota debe ser numero";
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
