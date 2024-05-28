package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class programaController {

    private programaModel programa;
    private String respuesta;

    public programaController() {
        programa = new programaModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        programa = new programaModel(0,
                params.get(0), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7), params.get(8), params.get(9),
                params.get(10));

        if (programa.create()) {
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
        programa = new programaModel(Integer.parseInt(params.get(0)),
                params.get(1), params.get(2), params.get(3), params.get(4),
                params.get(5), params.get(6), params.get(7), params.get(8), params.get(9), params.get(10),
                params.get(11));
        if (programa.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        programa.setId(id);
        if (programa.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return programa.getAll(params);
    }

    public String getOferta(LinkedList<String> params) {
        return programa.getOferta(params);
    }

    public boolean exist(int id) {
        return programa.exist(id);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 11) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El Codigo debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El Nombre debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La sigla debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La edicion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La version debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "La fecha de inicio debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "La fecha de finalizacion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "El costo debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "El tipo debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(9))) {
            this.respuesta = "La modalidad debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(10))) {
            this.respuesta = "Las horas academicas deben ser un string y no debe estar vacio.";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 12) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El ID debe ser un numero.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El Codigo debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El Nombre debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La sigla debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La edicion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "La version debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "La fecha de inicio debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "La fecha de finalizacion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "El costo debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(9))) {
            this.respuesta = "El tipo debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(10))) {
            this.respuesta = "La modalidad debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(11))) {
            this.respuesta = "Las horas academicas deben ser un string y no debe estar vacio.";
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
