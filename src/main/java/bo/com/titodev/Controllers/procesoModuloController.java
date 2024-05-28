package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class procesoModuloController {

    private procesoModuloModel procesoModulo;
    private String respuesta;

    public procesoModuloController() {
        procesoModulo = new procesoModuloModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        procesoModulo = new procesoModuloModel(0, params.get(0), params.get(1));
        if (procesoModulo.create()) {
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
        procesoModulo = new procesoModuloModel(Integer.parseInt(params.get(0)), params.get(1), params.get(2));
        if (procesoModulo.update()) {
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
        procesoModulo.setId(id);
        if (procesoModulo.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return procesoModulo.getAll(params);
    }

    public boolean exist(int id) {
        return procesoModulo.exist(id);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 2) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El nombre no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El orden no puede ser vacio";
            return;
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        procesoModulo = new procesoModuloModel();
        if (params.size() != 6) {
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
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El orden no puede ser vacio";
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
