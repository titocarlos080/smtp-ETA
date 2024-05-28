package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class docenteController {

    private docenteModel docente;
    private String respuesta;

    public docenteController() {
        docente = new docenteModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        docente = new docenteModel(0, params.get(0), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7));
        if (docente.create()) {
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
        docente = new docenteModel(Integer.parseInt(params.get(0)), params.get(1), params.get(2), params.get(3),
                params.get(4), params.get(5), params.get(6), params.get(7), params.get(8));
        if (docente.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
        docente.setId(id);
        if (docente.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return docente.getAll(params);
    }

    public boolean exist(int id) {
        return docente.exist(id);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 8) {
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
        if (!validatorUtils.validateEmail(params.get(3))) {
            this.respuesta = "El correo no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateEmail(params.get(3))) {
            this.respuesta = "El correo es invalido.";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "El telefono debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El ci debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "El ci_expedicion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "El facturacion debe ser un string y no debe estar vacio.";
            return;
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 9) {
            this.respuesta = "Numero de parametros incorrecto.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un entero y no debe estar vacio.";
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
        if (!validatorUtils.validateEmail(params.get(4))) {
            this.respuesta = "El correo no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateEmail(params.get(4))) {
            this.respuesta = "El correo es invalido.";
            return;
        }
        if (!validatorUtils.validateString(params.get(5))) {
            this.respuesta = "El telefono debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(6))) {
            this.respuesta = "El ci debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(7))) {
            this.respuesta = "El ci_expedicion debe ser un string y no debe estar vacio.";
            return;
        }
        if (!validatorUtils.validateString(params.get(8))) {
            this.respuesta = "El facturacion debe ser un string y no debe estar vacio.";
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
