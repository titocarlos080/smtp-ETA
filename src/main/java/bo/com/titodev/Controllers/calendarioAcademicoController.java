package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class calendarioAcademicoController {

    private calendarioAcademicoModel calendarioAcademico;
    private String respuesta;

    public calendarioAcademicoController() {
        calendarioAcademico = new calendarioAcademicoModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        calendarioAcademico = new calendarioAcademicoModel(0, params.get(0), params.get(1), params.get(2),
                params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7));
        if (calendarioAcademico.create()) {
            respuesta = "Creado exitosamente.";
        } else {
            respuesta = "No se pudo crear.";
        }
        return respuesta;
    }

    public String update(LinkedList<String> params) {
        this.validateUpdate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        calendarioAcademico = new calendarioAcademicoModel(Integer.parseInt(params.get(0)), params.get(1),
                params.get(2), params.get(3), params.get(4), params.get(5), params.get(6), params.get(7),
                params.get(8));
        if (calendarioAcademico.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        calendarioAcademico.setId(id);
        if (calendarioAcademico.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return calendarioAcademico.getAll(params);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 8) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }

        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La fecha de inicio debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La fecha de finalizacion debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El tipo debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "El tipo de fecha debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El lugar debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "La hora debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "El encargado debe ser un string y no debe estar vacio.";
            return;
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 9) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }

        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero.";
            return;
        }

        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La fecha de inicio debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La fecha de finalizacion debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "El tipo debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El tipo de fecha debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "El lugar debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "La hora debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "El encargado debe ser un string y no debe estar vacio.";
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
