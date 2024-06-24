 package bo.com.titodev.Dato;

import java.sql.Date;

public class estudianteDato extends personaDato{
    private Date fecha_nacimiento;
    public estudianteDato() {
    }

    public estudianteDato(String ci, String nombre, String apellido_pat, String apellido_mat, String telefono, char sexo, Date fecha_nacimiento, int usuario_id) {
        super(ci, nombre, apellido_pat, apellido_mat, telefono, sexo, fecha_nacimiento, usuario_id);
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    

    

    

}