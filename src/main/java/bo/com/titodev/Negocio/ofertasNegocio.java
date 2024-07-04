package bo.com.titodev.Negocio;

import java.util.LinkedList;

import bo.com.titodev.Dato.ofertaDato;

public class ofertasNegocio {

    private ofertaDato oferta;

    public ofertasNegocio() {
        oferta = new ofertaDato();
    }

    public String getAll(LinkedList<String> params) {
        return oferta.getAll(params);
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
