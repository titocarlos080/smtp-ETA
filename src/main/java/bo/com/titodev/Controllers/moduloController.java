package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;
public class moduloController {

    private moduloModel modulo;
    private String respuesta;
    private docenteController docente;
    private programaController programa;

    public moduloController() {
        modulo = new moduloModel();
        docente = new docenteController();
        programa = new programaController();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        modulo = new moduloModel(0,
                params.get(0), params.get(1), params.get(2), params.get(3), params.get(4), params.get(5), params.get(6),
                params.get(7), Integer.parseInt(params.get(8)), Integer.parseInt(params.get(9)));
        if (modulo.create()) {
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
        modulo = new moduloModel(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7), params.get(8),
                Integer.parseInt(params.get(9)),
                Integer.parseInt(params.get(10)));
        if (modulo.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        modulo.setId(id);
        if (modulo.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return modulo.getAll(params);
    }

    public boolean exist(int id) {
        return modulo.exist(id);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 10) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El codigo de modulo debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La sigla debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La version debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La edicion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "La modalidad debe ser un string y no debe estar vacio.";
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
        if (!validatorUtils.validateNumber(params.get(8))) {
            this.respuesta = "El ID del programa debe ser un numero.";
            return;
        }
        if (!programa.exist(Integer.parseInt(params.get(8)))) {
            this.respuesta = "EL ID del programa no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(9))) {
            this.respuesta = "El ID del docente debe ser un numero.";
            return;
        }
        if (!docente.exist(Integer.parseInt(params.get(9)))) {
            this.respuesta = "EL ID del docente no existe.";
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 11) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El ID debe ser un numero.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El codigo de modulo debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El nombre debe ser un string y no debe estar vacio.";
            return;
        }

        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La sigla debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La version debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "La edicion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "La modalidad debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "La fecha de inicio debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "La fecha de finalizacion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(9))) {
            this.respuesta = "El ID programa debe ser un numero.";
            return;
        }
        if (!programa.exist(Integer.parseInt(params.get(9)))) {
            this.respuesta = "EL ID del programa no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(10))) {
            this.respuesta = "El ID del docente debe ser un numero.";
            return;
        }
        if (!docente.exist(Integer.parseInt(params.get(10)))) {
            this.respuesta = "EL ID del docente no existe.";
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
