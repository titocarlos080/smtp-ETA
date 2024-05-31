 package bo.com.titodev.Models;

import java.sql.Date;

class docenteModel extends personaModel{

    public docenteModel() {
    }

    public docenteModel(String ci, String nombre, String apellido_pat, String apellido_mat, String telefono, char sexo, Date fecha_nacimiento, int usuario_id) {
        super(ci, nombre, apellido_pat, apellido_mat, telefono, sexo, fecha_nacimiento, usuario_id);
    }
    
    
    
}