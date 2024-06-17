package bo.com.titodev.Controllers;

 import bo.com.titodev.Models.horarioModel;
import bo.com.titodev.Utils.validatorUtils;
 
import java.sql.Time;
import java.util.LinkedList;

public class horarioController {

    private horarioModel hModel;
    private String respuesta;

    public horarioController() {
        this.hModel = new horarioModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        hModel = new horarioModel(0,Time.valueOf(params.get(0)),Time.valueOf(params.get(1)));
        if (hModel.create()) {
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
        hModel = new horarioModel(Integer.parseInt(params.get(0)),Time.valueOf(params.get(1)),Time.valueOf(params.get(2)));

        if (hModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(int id) {
         
        hModel.setId(id);
        ;
        if (hModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return hModel.getAll(params);
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
        hModel = new horarioModel();
        if (params.size() != 2) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
       
        
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La  Hora_inicio  debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "El Hora_fin debe se un texto";
            return;
        }
     

    }

    private void validateUpdate(LinkedList<String> params) {
        hModel = new horarioModel();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El Id del horario  debe ser un Numero !!!.";
            return;
        } if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La Hora_inicio debe ser un formato HORA/MIN !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La  Hora_fin debe se un texto  HORA/MIN ";
            return;
        } 
         

    }

}
