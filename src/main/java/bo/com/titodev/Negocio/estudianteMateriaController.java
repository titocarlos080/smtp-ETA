package bo.com.titodev.Negocio;

import bo.com.titodev.Dato.estudianteMateria;
import bo.com.titodev.Utils.validatorUtils;
import java.util.LinkedList;
import java.sql.Date;
 
public class estudianteMateriaController {

    private estudianteMateria emModel;
    private String respuesta;

    public estudianteMateriaController() {
        this.emModel = new estudianteMateria();
    }

    public String create(LinkedList<String> params) {
        this.validateCreate(params);
        if (this.respuesta != null) {
            return this.respuesta;
        }
        emModel = new estudianteMateria(0, Date.valueOf( params.get(0)), params.get(1),  params.get(2));
        if (emModel.create()) {
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
        emModel = new estudianteMateria(Integer.parseInt(params.get(0)), Date.valueOf( params.get(1)), params.get(2),  params.get(3));
        if (emModel.update()) {
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
        emModel.setId(id);
        if (emModel.delete()) {
            respuesta = "Eliminado exitosamente.";
        } else {
            respuesta = "No se pudo eliminar.";
        }
        return respuesta;
    }

    public String getAll(LinkedList<String> params) {
        return emModel.getAll(params);
    }

    public boolean exist(int id) {
        return emModel.exist(id);
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
