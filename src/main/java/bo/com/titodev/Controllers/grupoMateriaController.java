package bo.com.titodev.Controllers;

import bo.com.titodev.Models.grupoMateriaModel;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;

public class grupoMateriaController {

    private grupoMateriaModel mModel;
    private String respuesta;

    public grupoMateriaController() {
        this.mModel = new grupoMateriaModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
       //String sigla, String descripcion, String materia_sigla, String carrera_sigla, int docente_id 
        mModel = new grupoMateriaModel(params.get(0),params.get(1),params.get(2),params.get(3),Integer.valueOf(4));
        
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
        mModel = new grupoMateriaModel(params.get(0),params.get(1),params.get(2),params.get(3),Integer.valueOf(4));
 
        if (mModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String sigla) {
        if (!validatorUtils.validateString(String.valueOf(sigla))) {
            return "La sigla de grupo no es valida";
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
        //String sigla, String descripcion, String materia_sigla, String carrera_sigla, int docente_id 
        mModel = new grupoMateriaModel();
        if (params.size() != 5) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La sigla de la del grupo no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La  descripcion debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La  sigla_materia debe se un texto";
            return;
        }if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La  sigla_carrera debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(4))) {
            this.respuesta = "el Id de la materia de la materia debe ser Numero";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        mModel = new grupoMateriaModel();
        mModel = new grupoMateriaModel();
        if (params.size() != 5) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La sigla de la del grupo no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La  descripcion debe se un texto";
            return;
        } if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La  sigla_materia debe se un texto";
            return;
        }if (!validatorUtils.validateString(params.get(3))) {
            this.respuesta = "La  sigla_carrera debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(4))) {
            this.respuesta = "el Id de la materia de la materia debe ser Numero";
            return;
        }
    }

}
