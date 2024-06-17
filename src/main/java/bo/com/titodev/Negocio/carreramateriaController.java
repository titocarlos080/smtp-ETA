package bo.com.titodev.Negocio;

import bo.com.titodev.Dato.carreraMateriaModel;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;

public class carreramateriaController {

    private carreraMateriaModel cModel;
    private String respuesta;

    public carreramateriaController() {
        this.cModel = new carreraMateriaModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        cModel = new carreraMateriaModel(Integer.valueOf(params.get(0)),params.get(1),params.get(2) );
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
        cModel = new carreraMateriaModel(Integer.valueOf(params.get(0)),params.get(1),params.get(2) );


        if (cModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String carreraSigla,String materiaSigla ) {
        if (!(validatorUtils.validateString(String.valueOf(carreraSigla))
            && validatorUtils.validateString(String.valueOf(materiaSigla)))
        ) {
            return "La sigla no valida de la carrera";
        }
        cModel.setCarrera_sigla(carreraSigla);
        cModel.setMateria_sigla(materiaSigla );
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
        cModel = new carreraMateriaModel();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El Id del nivel no es valido !!!.";
            return;
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La materia sigla debe ser un texto";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La materia sigla debe ser un texto";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        cModel = new carreraMateriaModel();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(0))) {
            this.respuesta = "El Id del nivel no es valido !!!.";
            return; 
        }
        if (!validatorUtils.validateString(params.get(1))) {
            this.respuesta = "La materia sigla debe ser un texto";
            return;
        }
        if (!validatorUtils.validateString(params.get(2))) {
            this.respuesta = "La materia sigla debe ser un texto";
            return;
        }
    }

}
