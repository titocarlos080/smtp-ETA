package bo.com.titodev.Negocio;

import bo.com.titodev.Dato.carreraModel;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;

public class carreraController {

    private carreraModel cModel;
    private String respuesta;

    public carreraController() {
        this.cModel = new carreraModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        cModel = new carreraModel(params.get(0).toString(), params.get(1).toString(),
                Integer.valueOf(params.get(2).toString()));
        if (cModel.create()) {
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
        cModel = new carreraModel(params.get(0).toString(), params.get(1).toString(),
                Integer.valueOf(params.get(2).toString()));

        if (cModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String sigla) {
        if (!validatorUtils.validateString(String.valueOf(sigla))) {
            return "La sigla no valida de la carrera";
        }
        cModel.setSigla(sigla);
        ;
        if (cModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return cModel.getAll(params);
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
        cModel = new carreraModel();
        if (params.size() != 3) {
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
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID de la gestion no es valida";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        cModel = new carreraModel();
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La sigla de la carrera no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La  descripcion debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "El ID de la gestion no es valida";
            return;
        }
    }

}
