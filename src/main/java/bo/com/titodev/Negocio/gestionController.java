package bo.com.titodev.Negocio;

import bo.com.titodev.Dato.gestionModel;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;
import java.sql.Date;

public class gestionController {

    private gestionModel gModel;
    private String respuesta;

    public gestionController() {
        this.gModel = new gestionModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        gModel = new gestionModel(0, params.get(0), Date.valueOf(params.get(1)), Date.valueOf(params.get(2)));
        if (gModel.create()) {
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
        gModel = new gestionModel(Integer.parseInt( params.get(0)), params.get(1), Date.valueOf(params.get(2)), Date.valueOf(params.get(3)));
        if (gModel.update()) {
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
        gModel.setCodigo(id);
        if (gModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return gModel.getAll(params);
    }

    public boolean exist(int id) {
        return gModel.exist(id);
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

    }

    private void validateUpdate(LinkedList<String> params) {

    }

}
