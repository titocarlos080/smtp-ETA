package bo.com.titodev.Controllers;

import java.util.LinkedList;

import bo.com.titodev.Models.*;
import bo.com.titodev.Utils.*;

public class estudianteProgramaController {

    private estudianteProgramaModel estudiantePrograma;
    private String respuesta;
    private programaController programa;
    private estudianteController estudiante;

    public estudianteProgramaController() {
        estudiantePrograma = new estudianteProgramaModel();
        programa = new programaController();
        estudiante = new estudianteController();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        estudiantePrograma = new estudianteProgramaModel(0, params.get(0),
                Integer.parseInt(params.get(1)), Integer.parseInt(params.get(2)));
        if (estudiantePrograma.create()) {
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
        estudiantePrograma = new estudianteProgramaModel(Integer.parseInt(params.get(0)), params.get(1),
                Integer.parseInt(params.get(2)),
                Integer.parseInt(params.get(3)));
        if (estudiantePrograma.update()) {
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
        estudiantePrograma.setId(id);
        if (estudiantePrograma.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return estudiantePrograma.getAll(params);
    }

    private void validateCreate(LinkedList<String> params) {
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El fecha debe ser un string y no debe estar vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "El ID del estudiante debe ser un numero";
            return;
        }
        if (!estudiante.exist(Integer.parseInt(params.get(1)))) {
            this.respuesta = "El ID del estudiante no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID del programa debe ser un numero";
            return;
        }
        if (!programa.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El ID del programa no existe.";
        }
    }

    private void validateUpdate(LinkedList<String> params) {
        estudiantePrograma = new estudianteProgramaModel();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El id debe ser un numero";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El fecha debe ser un string y no debe estar vacio";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID del estudiante debe ser un numero";
            return;
        }
        if (!estudiante.exist(Integer.parseInt(params.get(2)))) {
            this.respuesta = "El ID del estudiante no existe.";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "El ID del programa debe ser un numero";
            return;
        }
        if (!programa.exist(Integer.parseInt(params.get(3)))) {
            this.respuesta = "El ID del programa no existe.";
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
