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
                // case "create":
                // response = usuario.create(paramsList);
                // smtp.sendEmail(emailEmisor, response);
                // break;
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

        if (opcion.toLowerCase().equals("menus")) {
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

        if (opcion.toLowerCase().equals("administrativos")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            administrativoNegocio administrativo = new administrativoNegocio();
            LinkedList<String> paramsList = administrativo.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = administrativo.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = administrativo.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = administrativo.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = administrativo.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("docentes")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            docenteNegocio docentes = new docenteNegocio();
            LinkedList<String> paramsList = docentes.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = docentes.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = docentes.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = docentes.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = docentes.delete(paramsList.get(0));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("estudiantes")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Admin")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            estudianteNegocio estudiante = new estudianteNegocio();
            LinkedList<String> paramsList = estudiante.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = estudiante.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = estudiante.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = estudiante.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = estudiante.delete(paramsList.get(0));
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
                    response = grupoMateriaHorario.delete(paramsList.get(0), Integer.parseInt(paramsList.get(1)));
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

            horarioNegocio horarios = new horarioNegocio();
            LinkedList<String> paramsList = horarios.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = horarios.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = horarios.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = horarios.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = horarios.delete(Integer.parseInt(paramsList.get(0)));
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

        if (opcion.toLowerCase().equals("pagos")) {

            pagoNegocio pagos = new pagoNegocio();
            LinkedList<String> paramsList = pagos.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = pagos.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;

                case "efectivo":
                    response = pagos.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);

                    break;

                case "create":

                    // Crear el formulario de pago y enviarlo por correo
                    String pago = "<form method=\"post\" id=\"FormPagoFacil\" action=\"https://checkout.pagofacil.com.bo/es/pay\" enctype=\"multipart/form-data\" class=\"form\">"
                            +
                            "<input name=\"tcDatosCheckout\" id=\"tcDatosCheckout\" type=\"hidden\" value=\"NTEyNDdmYWUyODBjMjA0MTA4MjQ5NzdiMDc4MTQ1M2RmNTlmYWQ1YjIzYmYyYTBkMTRlODg0NDgyZjkxZT"
                            +
                            "A5MDc4ZGJlNTk2NmUwYjk3MGJhNjk2ZWM0Y2FmOWFhNSA2NjE4MDI5MzVmODY3MTdjNDgxZjE2NzBlNjNmMzVkNTA0MDBmZTJmYTRiYTI2YmQxMTFhNDFjMmNjNTcwZTM4OGVjZmIxYTA1ZThkNDU1MWY5NDljMjUyNmI4NjAyMCAwNmNhODA"
                            +
                            "wMTNmMjY4OTAzNDFmMTY2ZTQ2NDE2ZGZkYWExZDJiNzJjODMzZDFlMDkxOGNiNzE2YWIwYzU5YzUwZTY5fHBydWViYUBwcnVlYmEuY29tfDc3Nzc3Nzc3fDYgNjZ8MTIwfDJ8aHR0cHM6Ly9taWRvbWluaW8uY29tL2NhbGxiYWNrfGh0dHBzOi8vbWlkb21pbmlvLmNvbS9yZXR1cm58W3tcIlNlcmlhbFwiOjEsXCJQcm9kdWN0b1wiOlwiUFJPRFVDVE8gMVwiLFwiTGlua1BhZ29cIjowLFwiQ2FudGlkYWRcIjoyLFwiUHJlY2lvXCI6MTAsXCJEZXNjdWVudG9cIjowLFwiVG90YWxcIjoyMH0se1wiU2VyaWFsXCI6MixcIlByb2R1Y3RvXCI6XCJQUk9EIFVDVE8yXCIsXCJMaW5rUGFnb1wiOjAsXCJDYW50aWRhZFwiOjUsXCJQcmVjaW9cIjoyMCxcIkRlc2N1ZW50b1wiOjAsXCJUb3RhbFwiOjEwMH1dfDEx\">"
                            +
                            "<input name=\"tcCommerceID\" id=\"tcCommerceID\" type=\"hidden\" value=\"d029fa3a95e174a19934857f535eb9427d967218a36ea014b70ad704bc6c8d1c\">"
                            +
                            "<input type=\"submit\" class=\"btn btn-primary\" id=\"btnpagar\" value=\"pagar\">" +
                            "</form>";

                    smtp.sendEmail(emailEmisor, pago);
                    break;

                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("ofertas")) {

            ofertasNegocio ofertas = new ofertasNegocio();
            LinkedList<String> paramsList = ofertas.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = ofertas.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;

                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("reportes")) {

             
            reporteNegocio reportes = new reporteNegocio();
            LinkedList<String> paramsList = reportes.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "egresos":
                    response = reportes.getEgresosPorGestion(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "ingresos":
                    response = reportes.getIngresosPorGestion(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;

                case "estudiantes":
                    response = reportes.getEstudiantesPorCarrera(paramsList);
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

                // + "<td style=\"text-align: left; padding: 8px; border: 1px solid
                // black;\">Registrar usuarios</td> \n \n"
                // + "<td style=\"text-align: left; padding: 8px; border: 1px solid
                // black;\">CREATE-USUARIOS [NOMBRE, CORREO, CONTRASEÑA, ROL_ID]</td> \n \n"
                // + "</tr> \n \n"
                // + "<tr> \n \n"
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
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar Estudiantes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-ESTUDIANTES [CI,NOMBRE,APELLIDO_PAT,APELLIDO_MAT,TELEFONO,SEXO,FECHA NACIMIENTO,USER_ID=0,EMAIL]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar Estudiantes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-ESTUDIANTES [CI,NOMBRE,APELLIDO_PAT,APELLIDO_MAT,TELEFONO,SEXO,FECHA NACIMIENTO,USER_ID,EMAIL]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar Estudiantes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-ESTUDIANTES [CI]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Estudiantes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-ESTUDIANTES [] || LIST-ESTUDIANTES [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar Docentes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-DOCENTES [CI,NOMBRE,APELLIDO_PAT,APELLIDO_MAT,KARDEX,CURRICULUM,EMAIL,USER_ID=0]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar Docentes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-DOCENTES [CI,NOMBRE,APELLIDO_PAT,APELLIDO_MAT,KARDEX,CURRICULUM,EMAIL,USER_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar Docentes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-DOCENTES [CI]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Docentes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-DOCENTES [] || LIST-DOCENTES [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar Administrativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-ADMINISTRATIVOS [CI,NOMBRE,APELLIDO_PAT,APELLIDO_MAT,TELEFONO,SEXO,FECHA NACIMIENTO,USER_ID=0,EMAIL]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar Administrativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-ADMINISTRATIVOS [CI,NOMBRE,APELLIDO_PAT,APELLIDO_MAT,TELEFONO,SEXO,FECHA NACIMIENTO,USER_ID,EMAIL]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar Administrativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-ADMINISTRATIVOS [CI]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Administrativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-ADMINISTRATIVOS [] || LIST-ADMINISTRATIVOS [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                // int codigo, String descripcion, Date fecha_inicio, Date fecha_fin
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar Gestiones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-GESTIONES [DESCRIPCION, FECHA_INI,FECHA_FIN]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar Gestiones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-GESTIONES [COD,DESCRIPCION, FECHA_INI,FECHA_FIN]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar Gestiones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-GESTIONES [COD]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Gestiones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-GESTIONES [] || LIST-GESTIONES [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
//carreras
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
// materias
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
                // NIVELES DE LAS CARRERAS
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
                // INSCRIPCION A CARRERAS DE LOS ESTUDIANTES
                // (int id, String fechaInscripcion, String estudianteCi, String carreraSigla)
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar  Inscripcion Carrera </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-INSCARRERA [FECHA,EST_CI,CARRERASIGLA]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar  Inscripcion Carrera </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-INSCARRERA [ID,FECHA,EST_CI,CARRERASIGLA]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar  Inscripcion Carrera </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-INSCARRERA [ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Inscripcion Carrera </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-INSCARRERA [] || LIST-INSCARRERA [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                // int id, Date fecha, String grupoMateriaSigla, String estudiante_ci
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar  Inscripcion Materia </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-INSCMAT [FECHA,GRUPOSIGLA,EST_CI]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar  Inscripcion Materia </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-INSCMAT [ID,FECHA,GRUPOSIGLA,EST_CI]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar  Inscripcion Materia </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-INSCMAT [ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Inscripcion Materia </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-INSCMAT [] || LIST-INSCMAT [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                // NOTAS DE ALUMNOS POR MATERIAS
                // (int id, int estudianteMateriaId, double notaFinal)
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar Notas </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-NOTAS [EST_MAT_ID,NOTA]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar Notas </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-NOTAS [ID,EST_MAT_ID,NOTA]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar Notas </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-NOTAS [ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">ListarNotas </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-NOTAS [] || LIST-NOTAS [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                // Pagos efectivo
                // (int id, double monto, Date fecha, String concepto, int estudianteMateria
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar  Pagos </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-PAGOS [MONTO,FECHA,CONCEPTO,EST_MAT_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar  Pagos </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">EFECTIVO-PAGOS []</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar  Pagos </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-PAGOS [ID,MONTO,FECHA,CONCEPTO,EST_MAT_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar  Pagos </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-PAGOS [ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Pagos </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-PAGOS [] || LIST-PAGOS [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                // OFERTAS

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar Ofertas </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-OFERTAS [] || LIST-OFERTAS [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"

                // REPORTES

                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">EGRESOS POR GESTION </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">EGRESOS-REPORTES []</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">INGRESOS POR GESTION </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">INGRESOS-REPORTES [] </td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">ESTUDIANTES POR CARRERA </td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">ESTUDIANTES-REPORTES [] </td> \n \n"
                + "</tr> \n \n"

                + "</table>";

    }

}
