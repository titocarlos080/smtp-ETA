package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class procesoRealizadoController {

    private procesoRealizadoModel procesoRealizado;
    private String respuesta;
    private procesoModuloController proceso;
    private moduloController modulo;

    public procesoRealizadoController() {
        procesoRealizado = new procesoRealizadoModel();
        proceso = new procesoModuloController();
        modulo = new moduloController();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        procesoRealizado = new procesoRealizadoModel(0, params.get(0), Integer.parseInt(params.get(1)),
                Integer.parseInt(params.get(2)));
        if (procesoRealizado.create()) {
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
        procesoRealizado = new procesoRealizadoModel(Integer.parseInt(params.get(0)), params.get(1),
                Integer.parseInt(params.get(2)),
                Integer.parseInt(params.get(3)));
        if (procesoRealizado.update()) {
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
        procesoRealizado.setId(id);
        if (procesoRealizado.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return procesoRealizado.getAll(params);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La fecha no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "El ID del proceso no puede ser vacio";
            return;
        }
        if (!proceso.exist(Integer.parseInt(params.get(1)))) {
            this.respuesta = "El ID del proceso no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID del modulo no puede ser vacio";
            return;
        }
        if (!modulo.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El ID del modulo no existe.";
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        procesoRealizado = new procesoRealizadoModel();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El ID no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La fecha no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID del proceso no puede ser vacio";
            return;
        }
        if (!proceso.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El ID del proceso no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "El ID del modulo no puede ser vacio";
            return;
        }
        if (!modulo.exist(Integer.parseInt(params.get(3)))) {
            this.respuesta = "El ID del modulo no existe.";
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
