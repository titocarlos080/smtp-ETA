package bo.com.titodev.Controllers;

import bo.com.titodev.Models.materiaModel;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;

public class materiaController {

    private materiaModel mModel;
    private String respuesta;

    public materiaController() {
        this.mModel = new materiaModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        mModel = new materiaModel(params.get(0),params.get(1),params.get(2),Integer.valueOf(params.get(3)));
        if (mModel.create()) {
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
        mModel = new materiaModel(params.get(0),params.get(1),params.get(2),Integer.valueOf(params.get(3)));

        if (mModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String sigla) {
        if (!validatorUtils.validateString(String.valueOf(sigla))) {
            return "La sigla de la materia no es valida";
        }
        mModel.setSigla(sigla);
        ;
        if (mModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return mModel.getAll(params);
    }

    public LinkedList<String> createList(String[] params) {
        LinkedList<String> list = new LinkedList<>();
        for (String param : params) {
            param = param.trim();
            list.add(param);
        }
        return list;
    }

    private void validateCreate(LinkedList<String> params) {
        mModel = new materiaModel();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La sigla de la carrera no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La  descripcion debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La  Observacion debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "Los creditos de la materia debe ser numeros";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        mModel = new materiaModel();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La sigla de la carrera no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La  descripcion debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La  Observacion debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "Los creditos de la materia debe ser numeros";
            return;
        }
    }

}