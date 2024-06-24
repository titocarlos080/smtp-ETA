package bo.com.titodev.Dato;

import java.sql.Date;

class docenteDato extends personaDato {
    private String kardex;
    private String currilculum;

    public docenteDato() {
    }

    public docenteDato(String ci, String nombre, String apellido_pat, String apellido_mat, String telefono, char sexo,
            Date fecha_nacimiento, int usuario_id, String kardex, String curriculum) {
        super(ci, nombre, apellido_pat, apellido_mat, telefono, sexo, fecha_nacimiento, usuario_id);
        this.kardex = kardex;
        this.currilculum = curriculum;

    }
  
    public void setKardex(String kardex) {
        this.kardex = kardex;
    }

    public void setCurriculum(String curriculum) {
        this.currilculum = curriculum;
    }

    public String getKardex() {
        return this.kardex;
    }

    public String getCurriculum() {
        return this.currilculum;
    }

}
