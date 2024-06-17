package bo.com.titodev.Negocio;

import bo.com.titodev.Dato.egresoModel;
import bo.com.titodev.Utils.validatorUtils;
 
import java.sql.Date;
import java.util.LinkedList;

public class egresoController {

    private egresoModel eModel;
    private String respuesta;

    public egresoController() {
        this.eModel = new egresoModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        eModel = new egresoModel(0,Double.parseDouble(params.get(0)),Date.valueOf(params.get(1)),params.get(2),Integer.valueOf(params.get(3)));
        if (eModel.create()) {
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
        eModel = new egresoModel(0,Double.parseDouble(params.get(0)),Date.valueOf(params.get(1)),params.get(2),Integer.valueOf(params.get(3)));

        if (eModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
         
        eModel.setId(id);
        ;
        if (eModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return eModel.getAll(params);
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
        eModel = new egresoModel();
        if (params.size() != 4) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El monto no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La  Fecha debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "El concepto debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(3))) {
            this.respuesta = "Los Codigo de la gestion no valida";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        eModel = new egresoModel();
        if (params.size() != 5) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El Id del egreso  debe ser un Numero !!!.";
            return;
        } if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "El monto no es valido, Debe ser un dato numerico !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La  Fecha debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "El concepto debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(4))) {
            this.respuesta = "Los Codigo de la gestion no valida";
            return;
        }

    }

}
