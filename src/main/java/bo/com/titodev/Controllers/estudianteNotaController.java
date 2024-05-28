package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class estudianteNotaController {

    private estudianteNotaModel nota;
    private String respuesta;
    private moduloController modulo;
    private programaController programa;
    private estudianteController estudiante;

    public estudianteNotaController() {
        nota = new estudianteNotaModel();
        modulo = new moduloController();
        programa = new programaController();
        estudiante = new estudianteController();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        nota = new estudianteNotaModel(0, params.get(0), params.get(1), Integer.parseInt(params.get(2)),
                Integer.parseInt(params.get(3)), Integer.parseInt(params.get(4)));
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
        nota = new estudianteNotaModel(Integer.parseInt(params.get(0)), params.get(1), params.get(2),
                Integer.parseInt(params.get(3)),
                Integer.parseInt(params.get(4)), Integer.parseInt(params.get(5)));
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

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 5) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "La nota debe ser un numero";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El detalle no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID del estudiante debe ser un numero.";
            return;
        }
        if (!estudiante.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El ID del estudiante no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "El ID del programa debe ser un numero.";
            return;
        }
        if (!programa.exist(Integer.parseInt(params.get(3)))) {
            this.respuesta = "El ID del programa no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(4))) {
            this.respuesta = "El ID del modulo debe ser un numero.";
            return;
        }
        if (!modulo.exist(Integer.parseInt(params.get(4)))) {
            this.respuesta = "El ID del modulo no existe.";
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        if (params.size() != 6) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "La nota debe ser un numero";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El detalle no puede ser vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "El ID del estudiante debe ser un numero.";
            return;
        }
        if (!estudiante.exist(Integer.parseInt(params.get(3)))) {
            this.respuesta = "El ID del estudiante no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(4))) {
            this.respuesta = "El ID del programa debe ser un numero.";
            return;
        }
        if (!programa.exist(Integer.parseInt(params.get(4)))) {
            this.respuesta = "El ID del programa no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(5))) {
            this.respuesta = "El ID del modulo debe ser un numero.";
            return;
        }
        if (!modulo.exist(Integer.parseInt(params.get(5)))) {
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
