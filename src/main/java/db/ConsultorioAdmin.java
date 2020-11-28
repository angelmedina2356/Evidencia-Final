package db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ConsultorioAdmin {

    static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        int seleccion;
        String user = "";
        String password = "";
        BD persist = new BD("consultorio.db");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese su usuario y contraseña para iniciar");
            System.out.println("Usuario:");
            user = scanner.nextLine();
            System.out.println("Contraseña:");
            password = scanner.nextLine();
            List<Usuario> usuario = persist.getUsuarioByName(user, password);
            if (!usuario.isEmpty()) {
                while (true) {
                    System.out.println("(1) Dar de alta doctores.");
                    System.out.println("(2) Dar de alta pacientes.");
                    System.out.println("(3) Crear una cita con fecha y hora.");
                    System.out.println("(4) Relacionar una cita con un doctor y un paciente");
                    System.out.println("(5) Listar doctores");
                    System.out.println("(6) Listar horarios");
                    System.out.println("(7) Listar pacientes");
                    System.out.println("(8) Ver citas agendadas");
                    System.out.println("(0) Salir");
                    System.out.println("\nPor favor ingrese una opción: ");
                    // Fin de Menu
                    // Try Anidado
                    try {
                        // Asigna token Integer parseado
                        seleccion = scanner.nextInt();
                        switch (seleccion) {
                            case 0:
                                System.out.println("Saliendo..");
                                logger.info("Saliendo...");
                                return;
                            case 1:
                                Scanner nom = new Scanner(System.in);
                                Scanner app = new Scanner(System.in);
                                Scanner apm = new Scanner(System.in);
                                Scanner esp = new Scanner(System.in);
                                System.out.println("Ingras el nombre del nuevo doctor");
                                String name = nom.next();
                                System.out.println("Ingrsa el apellido paterno del nuevo doctor");
                                String appe = app.next();
                                System.out.println("Ingrsa el apellido materno del nuevo doctor");
                                String apme = apm.next();
                                System.out.println("Ingrsa la especilidad del nuevo doctor");
                                String espe = esp.next();
                                persist.añadirDoctor(name, appe, apme, espe);
                                break;
                            case 2:
                                Scanner pnom = new Scanner(System.in);
                                Scanner pap = new Scanner(System.in);
                                Scanner pam = new Scanner(System.in);
                                System.out.println("Ingresa el nombre del nuevo paciente");
                                String namem = pnom.next();
                                System.out.println("Ingresa el apellido paterno del nuevo paciente");
                                String appp = pap.next();
                                System.out.println("Ingresa el apelliido materno del nuevo paciente");
                                String apmm = pam.next();
                                persist.añadirPaciente(namem, appp, apmm);
                                break;
                            case 3:
                                Scanner doctor = new Scanner(System.in);
                                Scanner paciente = new Scanner(System.in);
                                Scanner horario = new Scanner(System.in);
                                persist.verDoctores();
                                System.out.println("Escribe el ID del doctor");
                                Integer doc = doctor.nextInt();
                                persist.verPacientes();
                                System.out.println("Escribe ID del paciente");
                                Integer pac = paciente.nextInt();
                                persist.verHorariosDeCitas();
                                System.out.println("Escribre el ID del horario que deseas asistir");
                                Integer hor = horario.nextInt();
                                persist.crearUnaCita(doc, pac, hor);

                                break;
                            case 4:

                                break;
                            case 5:
                                persist.verDoctores();
                                break;
                            case 6:
                                persist.verHorariosDeCitas();
                                break;
                            case 7:
                                persist.verPacientes();
                                break;
                            case 8:
                                persist.verCitasAgendadas();
                            default:
                                System.err.println("Opción inválida.");
                                logger.error("Opción inválida: {}", seleccion);
                                break;
                        }

                    } catch (Exception ex) {
                        logger.error("{}: {}", ex.getClass(), ex.getMessage());
                        System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("No tiene autorización");
            }
        } catch (Exception ex) {
            logger.error("{}: {}", ex.getClass(), ex.getMessage());
            System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
        } finally {
            persist.getConnection().close();
        }
    }
}
