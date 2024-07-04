package bo.com.titodev.Negocio;

import java.sql.Date;
import java.util.LinkedList;
import bo.com.titodev.Dato.administrativoDato;
import bo.com.titodev.Utils.validatorUtils;

public class administrativoNegocio {
    private administrativoDato administrativo;
    private String respuesta;

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }

        administrativo = new administrativoDato(
                params.get(0), params.get(1), params.get(2),
                params.get(3), params.get(4), params.get(5).charAt(0),
                params.get(6), Integer.parseInt(params.get(7)), params.getLast());
        if (administrativo.create()) {
            respuesta = "Creado exitosamente.";
        } else {
            respuesta = "No se pudo crear.";
        }
        return respuesta;
    }

    public String update(LinkedList<String> params) {
        validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        administrativo = new administrativoDato(
                params.get(0), params.get(1), params.get(2),
                params.get(3), params.get(4), params.get(5).charAt(0),
                 params.get(6), Integer.parseInt(params.get(7)), params.getLast());

        if (administrativo.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String ci) {
        if (!validatorUtils.validateString(ci)) {
            return "El id debe ser un numero";
        }
        administrativo.setCi(ci);
        if (administrativo.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return administrativo.getAll(params);
    }

    public boolean exist(String ci) {
        return administrativo.exist(ci);
    }

    private void validateCreate(LinkedList<String> params) {
        administrativo = new administrativoDato();
        int expectedParamCount = 9; // Número de parámetros esperados

        // Validar la cantidad de parámetros
        if (params.size() != expectedParamCount) {
            this.respuesta = "La cantidad de parámetros es incorrecta";
            return;
        }

        // Validar nombre
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "El CI no puede ser vacío";
            return;
        }

        // Validar otros parámetros (nombre, apellido_pat, apellido_mat, telefono, sexo,
        // fecha_nacimiento, email)
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El nombre no puede ser vacío";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El apellido paterno no puede ser vacío";
            return;
        }
        if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El apellido materno no puede ser vacío";
            return;
        }
        if (!validatorUtils.validateString(params.get(4))) {
            this.respuesta = "El teléfono no puede ser vacío";
            return;
        }
        if (params.get(5).length() != 1) {
            this.respuesta = "El sexo debe ser un solo carácter";
            return;
        }
        try {
            Date.valueOf(params.get(6));
        } catch (IllegalArgumentException e) {
            this.respuesta = "La fecha de nacimiento no es válida";
            return;
        }
        if (!validatorUtils.validateEmail(params.getLast())) {
            this.respuesta = "El email no es válido";
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
