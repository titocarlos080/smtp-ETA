package bo.com.titodev.Negocio;

import bo.com.titodev.Dato.grupoMateriaHorarioModel;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;

public class grupoMateriaHorarioController {

    private grupoMateriaHorarioModel gmhModel;
    private String respuesta;

    public grupoMateriaHorarioController() {
        this.gmhModel = new grupoMateriaHorarioModel();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        gmhModel = new grupoMateriaHorarioModel(params.get(0), Integer.parseInt(params.get(1)),
                Integer.parseInt(params.get(2)));

        if (gmhModel.create()) {
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
        gmhModel = new grupoMateriaHorarioModel(params.get(0), Integer.parseInt(params.get(1)),
                Integer.parseInt(params.get(2)));
        if (gmhModel.update()) {
            respuesta = "Actualizado exitosamente.";
        } else {
            respuesta = "No se pudo actualizar.";
        }
        return respuesta;
    }

    public String delete(String sigla, int horarioId) {
        if (!validatorUtils.validateString(String.valueOf(sigla))) {
            return "La sigla de grupo no es valida";
        }
        gmhModel.setGrupoSigla(sigla);
        gmhModel.setHorarioId(horarioId);

        if (gmhModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return gmhModel.getAll(params);
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
        // String sigla, String descripcion, String materia_sigla, String carrera_sigla,
        // int docente_id
        gmhModel = new grupoMateriaHorarioModel();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }

        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La  sigla debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "La  Horario ID debe se un numero";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "La  Dia Id debe ser un numero";
            return;
        }

    }

    private void validateUpdate(LinkedList<String> params) {
        gmhModel = new grupoMateriaHorarioModel();
        if (params.size() != 3) {
            this.respuesta = "La cantidad de parametros es incorrecta";
            return;
        }
        if (!validatorUtils.validateString(params.get(0))) {
            this.respuesta = "La  sigla debe se un texto";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(1))) {
            this.respuesta = "La  Horario ID debe se un numero";
            return;
        }
        if (!validatorUtils.validateNumber(params.get(2))) {
            this.respuesta = "La  Dia Id debe ser un numero";
            return;
        }

    }

}
