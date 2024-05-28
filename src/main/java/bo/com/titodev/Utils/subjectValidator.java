package bo.com.titodev.Utils;

import java.util.LinkedList;

import bo.com.titodev.Controllers.*;
import bo.com.titodev.Services.smtpService;
 

public class subjectValidator {

    private String subject;
    private String emailEmisor;
    private smtpService smtp;
    private usuarioController userValidate;

    public subjectValidator(String subject, String emailEmisor) {
        this.subject = subject;
        this.emailEmisor = emailEmisor;
        this.userValidate = new usuarioController();
        smtp = new smtpService();

    }

    public void ValidateSuject() {
        usuarioController usuario = new usuarioController();
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
        if (parentesis1 > parentesis2) {
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
        int firstSpace = subject.indexOf(" ");
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
        String command = subject.substring(0, firstSpace);

        String[] opcionArray = command.split("-"); 
        if (opcionArray.length != 2) {
            smtp.sendEmail(emailEmisor,
                    "No se reconoce el formato indicado. Verifique que exista un '-' en el comando.");
            return;
        }

        OpcionList();
    }

    private void OpcionList() {
        String response = "";
        int firstSpace = subject.indexOf(" ");
        String command = subject.substring(0, firstSpace);
        String params = subject.substring(firstSpace + 1, subject.length());
        String[] opcionArray = command.split("-");
        params = params.replace("[", "");
        params = params.replace("]", "");
        String opcion = opcionArray[1];
        String[] parametros;
        if (params.length() >= 1) {
            parametros = params.split(","); 
        } else {
            parametros = new String[0];
        }

        if (opcion.toLowerCase().equals("usuario")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Administrador")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            usuarioController usuario = new usuarioController();
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

        if (opcion.toLowerCase().equals("rol")) {
            if (!this.userValidate.validateRol(this.emailEmisor, "Administrador")) {
                smtp.sendEmail(this.emailEmisor, "No tienes permisos para realizar esta accion.");
                return;
            }
            rolController rol = new rolController();
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

        if (opcion.toLowerCase().equals("estudiante")) {
            estudianteController estudiante = new estudianteController();
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
                    response = estudiante.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("prospecto")) {
            prospectoController prospecto = new prospectoController();
            LinkedList<String> paramsList = prospecto.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = prospecto.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = prospecto.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = prospecto.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = prospecto.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("programa")) {
            programaController programa = new programaController();
            LinkedList<String> paramsList = programa.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = programa.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = programa.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = programa.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = programa.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("oferta")) {
            programaController programa = new programaController();
            LinkedList<String> paramsList = programa.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = programa.getOferta(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("modulo")) {
            moduloController modulo = new moduloController();
            LinkedList<String> paramsList = modulo.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = modulo.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = modulo.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = modulo.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = modulo.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("docente")) {
            docenteController docente = new docenteController();
            LinkedList<String> paramsList = docente.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = docente.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = docente.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = docente.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = docente.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("calendario")) {
            calendarioAcademicoController calendarioAcademico = new calendarioAcademicoController();
            LinkedList<String> paramsList = calendarioAcademico.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = calendarioAcademico.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = calendarioAcademico.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = calendarioAcademico.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = calendarioAcademico.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("contrato")) {
            contratoController contrato = new contratoController();
            LinkedList<String> paramsList = contrato.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = contrato.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = contrato.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = contrato.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = contrato.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("proceso")) {
            procesoModuloController procesoModulo = new procesoModuloController();
            LinkedList<String> paramsList = procesoModulo.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = procesoModulo.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = procesoModulo.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = procesoModulo.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = procesoModulo.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("procesorealizado")) {
            procesoRealizadoController procesoRealizado = new procesoRealizadoController();
            LinkedList<String> paramsList = procesoRealizado.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = procesoRealizado.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = procesoRealizado.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = procesoRealizado.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = procesoRealizado.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("notas")) {
            estudianteNotaController estudianteNota = new estudianteNotaController();
            LinkedList<String> paramsList = estudianteNota.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = estudianteNota.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = estudianteNota.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = estudianteNota.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = estudianteNota.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Get, Create, Update, Delete.");
                    break;
            }
            return;
        }

        if (opcion.toLowerCase().equals("inscripcion")) {
            estudianteProgramaController inscripcion = new estudianteProgramaController();
            LinkedList<String> paramsList = inscripcion.createList(parametros);
            switch (opcionArray[0].toLowerCase()) {
                case "list":
                    response = inscripcion.getAll(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "create":
                    response = inscripcion.create(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "update":
                    response = inscripcion.update(paramsList);
                    smtp.sendEmail(emailEmisor, response);
                    break;
                case "delete":
                    response = inscripcion.delete(Integer.parseInt(paramsList.get(0)));
                    smtp.sendEmail(emailEmisor, response);
                    break;
                default:
                    smtp.sendEmail(emailEmisor,
                            "No se reconoce el formato indicado. Verifique que sea una de estas opciones List, Create, Update, Delete.");
                    break;
            }
            return;
        }

        smtp.sendEmail(emailEmisor, "Comando incorrecto, Verifique que este enviando bien los comandos");
    }

    private String AllComand() {
        return "<h1>ESCUELA DE INGENIERIA - COMANDOS</h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black; padding: 8px;\"> \n \n"
                + "<tr> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #1C7293; color: white; border: 1px solid black;\"> FUNCIONALIDAD </th> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #1C7293; color: white; border: 1px solid black;\"> COMANDO </th> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar rol</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-ROL [NOMBRE, DESCRIPCION]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar rol</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-ROL [NOMBRE, DESCRIPCION]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar rol</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-ROL [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar roles</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-ROL [] || LIST-ROL [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-USUARIO [NOMBRE, CORREO, CONTRASEÑA, AREA, ROL_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-USUARIO [ID, NOMBRE, CORREO, CONTRASEÑA, AREA, ROL_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-USUARIO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar usuarios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-USUARIO [] || LIST-USUARIO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar estudiante</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-ESTUDIANTE [HONORIFICO, NOMBRE, APELLIDO, CI, CI EXPEDICION, TELEFONO, CORREO, CARRERA, UNIVERSIDAD, ESTADO, FECHA INACTIVIDAD, SEXO, NACIONALIDAD]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar estudiante</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-ESTUDIANTE [ID, HONORIFICO, NOMBRE, APELLIDO, CI, CI EXPEDICION, TELEFONO, CORREO, CARRERA, UNIVERSIDAD, ESTADO, FECHA INACTIVIDAD, SEXO, NACIONALIDAD]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar estudiante</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-ESTUDIANTE [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar estudiantes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-ESTUDIANTE [] || LIST-ESTUDIANTE [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar prospecto</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-PROSPECTO [NOMBRE, TELEFONO, CORREO, INTERES, CARRERA, ESTADO, DETALLES]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar prospecto</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-PROSPECTO [ID, NOMBRE, TELEFONO, CORREO, INTERES, CARRERA, ESTADO, DETALLES]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar prospecto</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-PROSPECTO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar prospecto</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-PROSPECTO [] || LIST-PROSPECTO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar programa</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-PROGRAMA [CODIGO PROGRAMA, NOMBRE, SIGLA, EDICION, VERSION, FECHA INICIO, FECHA FINAL, COSTO, TIPO, MODALIDAD, HRS ACADEMICAS]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar programa</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-PROGRAMA [ID, CODIGO PROGRAMA, NOMBRE, SIGLA, EDICION, VERSION, FECHA INICIO, FECHA FINAL, COSTO, TIPO, MODALIDAD, HRS ACADEMICAS]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar programa</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-PROGRAMA [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar programa</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-PROGRAMA [] || LIST-PROGRAMA [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar oferta academica</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-OFERTA [] || LIST-OFERTA [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar modulo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-MODULO [CODIGO MODULO, NOMBRE, SIGLA, VERSION, EDICION, MODALIDAD, FECHA INICIO, FECHA FINAL, PROGRAMA_ID, DOCENTE_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar modulo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-MODULO [ID, CODIGO MODULO, NOMBRE, SIGLA, VERSION, EDICION, MODALIDAD, FECHA INICIO, FECHA FINAL, PROGRAMA_ID, DOCENTE_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar modulo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-MODULO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar modulo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-MODULO [] || LIST-MODULO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar docente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-DOCENTE [HONORIFICO, NOMBRE, APELLIDO, CORREO, TELEFONO, CI, CI EXPEDICION, FACTURACION]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar docente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-DOCENTE [ID, HONORIFICO, NOMBRE, APELLIDO, CORREO, TELEFONO, CI, CI EXPEDICION, FACTURACION]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar docente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-DOCENTE [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar docente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-DOCENTE [] || LIST-DOCENTE [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar evento en el calendario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-CALENDARIO [NOMBRE, FECHA INICIO, FECHA FINAL, TIPO EVENTO, TIPO DE FECHA, LUGAR, HORA, ENCARGADO]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar evento en el calendario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-CALENDARIO [ID, NOMBRE, FECHA INICIO, FECHA FINAL, TIPO EVENTO, TIPO DE FECHA, LUGAR, HORA, ENCARGADO]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar evento en el calendario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-CALENDARIO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar evento en el calendario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-CALENDARIO [] || LIST-CALENDARIO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar contrato</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-CONTRATO [HONORARIO, FECHA INICIO, FECHA FINAL, HORARIO, PAGADO, NRO PREVENTIVA, ESTADO, MODULO_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar contrato</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-CONTRATO [ID, HONORARIO, FECHA INICIO, FECHA FINAL, HORARIO, PAGADO, NRO PREVENTIVA, ESTADO, MODULO_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar contrato</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-CONTRATO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar contrato</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-CONTRATO [] || LIST-CONTRATO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar proceso</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-PROCESO [NOMBRE, ORDEN]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar proceso</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-PROCESO [ID, NOMBRE, ORDEN ]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar proceso</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-PROCESO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar proceso</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-PROCESO [] || LIST-PROCESO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar proceso realizado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-PROCESOREALIZADO [FECHA, PROCESO_ID, MODULO_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar proceso realizado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-PROCESOREALIZADO [ID, FECHA, PROCESO_ID, MODULO_ID ]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar proceso realizado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-PROCESOREALIZADO [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar proceso realizado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-PROCESOREALIZADO [] || LIST-PROCESOREALIZADO [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar notas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-NOTA [ NOTA, DETALLES, ESTUDIANTE_ID, PROGRAMA_ID, MODULO_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar notas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-NOTA [ID, NOTA, DETALLES, ESTUDIANTE_ID, PROGRAMA_ID, MODULO_ID  ]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar notas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-NOTA [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar notas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-NOTA [] || LIST-NOTA [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Registrar inscripcion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">CREATE-INSCRIPCION [FECHA INSCRIPCION, ESTUDIANTE_ID, PROGRAMA_ID]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Actualizar inscripcion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">UPDATE-INSCRIPCION [ID, FECHA INSCRIPCION, ESTUDIANTE_ID, PROGRAMA_ID  ]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Eliminar inscripcion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">DELETE-INSCRIPCION [id]</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">Listar inscripcion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">LIST-INSCRIPCION [] || LIST-INSCRIPCION [KEY, VALOR]</td> \n \n"
                + "</tr> \n \n"
                + "</table>";

    }
}
