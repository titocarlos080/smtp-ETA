package bo.com.titodev.Negocio;

import java.util.LinkedList;

import bo.com.titodev.Dato.reporteDato;

public class reporteNegocio {

    private reporteDato reporte;

    public reporteNegocio() {
        this.reporte = new reporteDato();
    }

    public String getEgresosPorGestion(LinkedList<String> paramsList) {

        return reporte.getEgresosPorGestion();
    }

    public String getIngresosPorGestion(LinkedList<String> paramsList) {
        return reporte.getIngresosPorGestion();
    }

    public String getEstudiantesPorCarrera(LinkedList<String> paramsList) {
        return reporte.getEstudiantesPorCarrera();
    }

    public LinkedList<String> createList(String[] params) {
        LinkedList<String> list = new LinkedList<>();
        for (String param : params) {
            param = param.trim();
            list.add(param);
        }
        return list;
    }
}
