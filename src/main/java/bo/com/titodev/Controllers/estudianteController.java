package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class estudianteController {

    private estudianteModel estudiante;
    private String respuesta;

    public estudianteController() {
        estudiante = new estudianteModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        estudiante = new estudianteModel(0, params.get(0), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7), params.get(8), params.get(9),
                params.get(10), params.get(11), params.get(12));
        if (estudiante.create()) {
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
        estudiante = new estudianteModel(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7), params.get(8), params.get(9),
                params.get(10), params.get(11), params.get(12), params.get(13));
        if (estudiante.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        estudiante.setId(id);
        if (estudiante.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return estudiante.getAll(params);
    }

    public boolean exist(int id) {
        return estudiante.exist(id);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 13) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El honorifico debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El apellido debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La CI no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La CI expedicicion no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El telefono no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "El correo no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateEmail(params.get(6))) {
            this.respuesta = "El correo no es valido.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "La carrera no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "La universidad no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(9))) {
            this.respuesta = "El estado no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(10))) {
            this.respuesta = "La fecha de inactividad no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(11))) {
            this.respuesta = "El sexo no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(12))) {
            this.respuesta = "La nacionalidad no debe estar vacio.";
            return;
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        estudiante = new estudianteModel();
        if (params.size() != 14) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!estudiante.exist(Integer.parseInt(params.get(0)))) {
            this.respuesta = "El estudiante no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El honorifico debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El nombre debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El apellido debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "La CI no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "La CI expedicicion no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "El telefono no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "El correo no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateEmail(params.get(7))) {
            this.respuesta = "El correo no es valido.";
            return;
        }
        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "La carrera no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(9))) {
            this.respuesta = "La universidad no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(10))) {
            this.respuesta = "El estado no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(11))) {
            this.respuesta = "La fecha de inactividad no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(12))) {
            this.respuesta = "El sexo no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(13))) {
            this.respuesta = "La nacionalidad no debe estar vacio.";
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
