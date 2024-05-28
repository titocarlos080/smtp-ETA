package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;
public class prospectoController {
    private prospectoModel prospecto;
    private String respuesta;

    public prospectoController() {
        prospecto = new prospectoModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null)
            return this.respuesta;
        prospecto = new prospectoModel(0, params.get(0), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6));
        if (prospecto.create()) {
            respuesta = "Creado exitosamente.";
        } else {
            respuesta = "No se pudo crear.";
        }
        return respuesta;
    }

    public String update(LinkedList<String> params) {
        validateUpdate(params);
        if (this.respuesta != null)
            return this.respuesta;
        prospecto = new prospectoModel(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7));
        if (prospecto.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        prospecto.setId(id);
        if (prospecto.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return prospecto.getAll(params);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 7) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El nombre debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El telefono debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El correo debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateEmail(params.get(2))) {
            this.respuesta = "El correo debe ser un email valido.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El interes debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La carrera debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El estado debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "Los detalles deben ser un string y no puede estar vacio.";
            return;
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 8) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un entero.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El telefono debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El correo debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateEmail(params.get(3))) {
            this.respuesta = "El correo debe ser un email valido.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "El interes debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "La carrera debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "El estado debe ser un string y no puede estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "Los detalles deben ser un string y no puede estar vacio.";
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
