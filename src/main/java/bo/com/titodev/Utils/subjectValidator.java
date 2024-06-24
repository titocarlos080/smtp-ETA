package bo.com.titodev.Utils;

import java.util.LinkedList;

import bo.com.titodev.Negocio.*;
import bo.com.titodev.Services.smtpService;

public class subjectValidator {

    private String subject;
    private String emailEmisor;
    private smtpService smtp;
    private usuarioNegocio userValidate;

    public subjectValidator(String subject, String emailEmisor) {
        this.subject = subject;
        this.emailEmisor = emailEmisor;
        this.userValidate = new usuarioNegocio();
        smtp = new smtpService();

    }

    public void ValidateSuject() {
        usuarioNegocio usuario = new usuarioNegocio();
        if (!usuario.auth(emailEmisor)) {
            smtp.sendEmail(emailEmisor, "No tienes permisos para realizar esta acción.");
            return;
        }

        String subjectAux = subject.toLowerCase();
        subjectAux = subjectAux.trim();
        if (subjectAux.equals("help")) {
            smtp.sendEmail(emailEmisor, AllComand());
            return;
        }

        int parentesis1 = subject.indexOf("[");
        int parentesis2 = subject.indexOf("]");
        int espacio = subject.indexOf(" ");
        if (parentesis1 == -1 && parentesis2 == -1) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que está utilizando los corchetes( [] ) para realizar la petición.");
            return;
        }
        if (parentesis1 > parentesis2) { // ][
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que está utilizando los corchetes( [] ) de forma ordenada.");
            return;
        }
        if (parentesis1 < 0) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que está enviando los datos dentro de un encabezado.");
            return;
        }
        if (espacio == -1) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que exista un espacio entre el comando y los parametros.");
            return;
        }

        subject = subject.trim();
        int firstSpace = subject.indexOf(" "); // LIST-ROLES []
        int parentesis1Aux = subject.indexOf("[");
        if (firstSpace == -1 || parentesis1Aux == -1) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que exista un espacio entre el comando y los parametros.");
            return;
        }
        if (parentesis1Aux < firstSpace) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que exista un espacio entre el comando y los parametros.");
            return;
        }
        String command = subject.substring(0, firstSpace);// LIST-ROLES

        String[] opcionArray = command.split("-");// LIST,ROLES
        if (opcionArray.length != 2) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que exista un '-' en el comando.");
            return;
        }

        OpcionList();
    }

    private void OpcionList() {
        String response = "";
        int firstSpace = subject.indexOf(" "); // LIST-ROLES []
        String command = subject.substring(0, firstSpace);// LIST-ROLES
        String params = subject.substring(firstSpace + 1, subject.length());// []
        String[] opcionArray = command.split("-");// LIST,ROLES
        params = params.replace("[", ""); //
        params = params.replace("]", "");
        String opcion = opcionArray[1]; // ROLES
        String[] parametros;

        if (params.length() >= 1) {
            parametros = params.split(",");
        } else {
            parametros = new String[0];
        }

        if (opcion.toLowerCase().equals("usuarios")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            usuarioNegocio usuario = new usuarioNegocio();
            LinkedList<String> paramsList = usuario.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = usuario.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = usuario.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = usuario.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = usuario.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("roles")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            rolNegocio rol = new rolNegocio();
            LinkedList<String> paramsList = rol.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = rol.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = rol.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = rol.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = rol.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }
       
        if (opcion.toLowerCase().equals("permisos")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            permisoNegocio permiso = new permisoNegocio();
            LinkedList<String> paramsList = permiso.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = permiso.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = permiso.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = permiso.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = permiso.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("gestiones")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            gestionNegocio gestion = new gestionNegocio();
            LinkedList<String> paramsList = gestion.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = gestion.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = gestion.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = gestion.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = gestion.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("carreras")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            carreraNegocio carrera = new carreraNegocio();
            LinkedList<String> paramsList = carrera.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = carrera.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = carrera.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = carrera.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = carrera.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("niveles")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            nivelNegocio niveles = new nivelNegocio();
            LinkedList<String> paramsList = niveles.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = niveles.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = niveles.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = niveles.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = niveles.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "<html><body style='background-color:red;'>No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.</body></html>");
                    break;
            }
            return;
        }
    
        if (opcion.toLowerCase().equals("dias")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            diaNegocio dias = new diaNegocio();
            LinkedList<String> paramsList = dias.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = dias.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = dias.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = dias.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = dias.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "<html><body style='background-color:red;'>No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.</body></html>");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("materias")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            materiaNegocio materias = new materiaNegocio();
            LinkedList<String> paramsList = materias.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = materias.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = materias.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = materias.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = materias.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }
      
        if (opcion.toLowerCase().equals("grupos_materias")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            grupoMateriaNegocio grupoMateria = new grupoMateriaNegocio();
            LinkedList<String> paramsList = grupoMateria.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = grupoMateria.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = grupoMateria.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = grupoMateria.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = grupoMateria.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }
      
        if (opcion.toLowerCase().equals("gupo_materia_horarios")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            grupoMateriaHorarioNegocio grupoMateriaHorario = new grupoMateriaHorarioNegocio();
            LinkedList<String> paramsList = grupoMateriaHorario.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = grupoMateriaHorario.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = grupoMateriaHorario.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = grupoMateriaHorario.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = grupoMateriaHorario.delete(paramsList.get(0),Integer.parseInt(paramsList.get(1)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("horarios")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            materiaNegocio materias = new materiaNegocio();
            LinkedList<String> paramsList = materias.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = materias.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = materias.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = materias.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = materias.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("estudiante_materias")) {

            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            materiaNegocio materias = new materiaNegocio();
            LinkedList<String> paramsList = materias.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = materias.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = materias.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = materias.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = materias.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        smtp.sendEmail(emailEmisor, "Comando incorrecto, Verifique que este enviando bien los comandos");
    }

    private String AllComand() {

        return "<h1>ESCUELA ETA</h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black; padding: 8px;\"> \n \n"
                + "<tr> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #1C7293; color: white; border: 1px solid black;\"> FUNCIONALIDAD </th> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #1C7293; color: white; border: 1px solid black;\"> COMANDO </th> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar rol</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-ROLES [NOMBRE]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar rol</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-ROLES [ID,NOMBRE]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar rol</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-ROLES [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar roles</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-ROLES [] || LIST-ROLES [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar usuarios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-USUARIOS [NOMBRE, CORREO, CONTRASEÑA,  ROL_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-USUARIOS [ID, NOMBRE, CORREO, CONTRASEÑA,  ROL_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-USUARIOS [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar usuarios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-USUARIOS [] || LIST-USUARIO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar carreras</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-CARRERAS [COD,DESCRIPCION,COD_GESTION]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar carreras</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-CARRERAS [COD, DESCRIPCION,COD_GESTION]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar carreras</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-CARRERAS [COD]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar carreras</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-CARRERAS [] || LIST-CARRERAS [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar  materias </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-MATERIAS [SIGLA,DESCRIPCION,OBSERVACION,CREDITOS]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar  materias </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-MATERIAS [SIGLA,DESCRIPCION,OBSERVACION,CREDITOS]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar  materias </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-MATERIAS [SIGLA]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar materias </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-MATERIAS [] || LIST-MATERIAS [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"


                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar  niveles </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-NIVELES [NOMBRE]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar  niveles </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-NIVELES [ID,NOMBRE]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar  niveles </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-NIVELES [ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar niveles </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-NIVELES [] || LIST-NIVELES [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                + "</table>";

    }

}
