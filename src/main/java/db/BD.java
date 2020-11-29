package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BD {

    private String database;
    private Connection connection;
    private Statement statement;

    public BD(String db) throws ClassNotFoundException, SQLException {
        this.database = db;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        this.statement = connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Usuario> getUsuarioByName(String nombre, String password) throws SQLException {
        ResultSet rs = this.statement.executeQuery("select * from usuario where upper(nombre)='" + nombre.toUpperCase() + "' and password='" + password.toUpperCase() + "'");
        List<Usuario> usuario = new ArrayList();
        while (rs.next()) {
            Usuario temp = new Usuario();
            temp.setIdUsuario(rs.getInt("id_usuario"));
            temp.setIdUsuario(rs.getInt("nombre"));
            temp.setIdUsuario(rs.getInt("password"));
            temp.setIdUsuario(rs.getInt("rol"));
            usuario.add(temp);
        }
        return usuario;
    }

    //Ver todos los doctores//
    public void verDoctores() throws SQLException {
        String sql = "SELECT id_doctores, nombre, apellidop, apellidom, especialidad FROM doctores";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getInt("id_doctores") +  "\t" +
                        rs.getString("nombre") + "\t" +
                        rs.getString("apellidop") + "\t" +
                        rs.getString("apellidom") + "\t" +
                        rs.getString("especialidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Ver todos los pacientes//
    public void verPacientes() throws SQLException {
        String sql = "SELECT id_paciente, nombre, apellidop, apellidom FROM paciente";
        try (Statement stmt = this.connection.createStatement();
             ResultSet sr    = stmt.executeQuery(sql)){

            while (sr.next()) {
                System.out.println(sr.getInt("id_paciente") +  "\t" +
                        sr.getString("nombre") + "\t" +
                        sr.getString("apellidop") + "\t" +
                        sr.getString("apellidom"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Ver todos los horarios para citas//
    public void verHorariosDeCitas() throws SQLException {
        String sql = "SELECT id_horarios, fecha, hora, motivo_de_la_cita FROM horarios";
        try (Statement stmt = this.connection.createStatement();
             ResultSet ab    = stmt.executeQuery(sql)){

            while (ab.next()) {
                System.out.println(ab.getInt("id_horarios") +  "\t" +
                        ab.getString("fecha") + "\t" +
                        ab.getString("hora") + "\t" +
                        ab.getString("motivo_de_la_cita"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Añadir doctor a la base de datos//
    public boolean añadirDoctor(String nombre, String apellidop, String apellidom, String especialidad) throws SQLException {
        String sql = "insert into doctores(nombre, apellidop, apellidom, especialidad) "
                + "values (?,?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        prepStmt.setString(2, apellidop);
        prepStmt.setString(3, apellidop);
        prepStmt.setString(4, especialidad);
        return prepStmt.execute();
    }

    //Añadir paciente a la base de datos//
    public boolean añadirPaciente(String nombre, String apellidop, String apellidom) throws SQLException {
        String sql = "insert into paciente(nombre, apellidop, apellidom)"
                + "values (?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        prepStmt.setString(2, apellidop);
        prepStmt.setString(3, apellidop);
        return prepStmt.execute();
    }

    //Crear una cita//
    public boolean crearUnaCita(Integer id_doctores, Integer id_paciente, Integer id_horarios) throws SQLException{

        String sql = "insert into citas(id_doctores, id_paciente, id_horarios)" + "values(?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, id_doctores);
        prepStmt.setInt(2, id_paciente);
        prepStmt.setInt(3, id_horarios);
        return prepStmt.execute();
    }

    //Ver las citas que se agendaron//
    public void verCitasAgendadas() throws SQLException {
        String sql = "SELECT id_cita, id_doctores, id_paciente, id_horarios FROM citas";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getInt("id_cita") +  "\t" +
                        rs.getInt("id_doctores") + "\t" +
                        rs.getInt("id_paciente") + "\t" +
                        rs.getInt("id_horarios"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Ver doctor con paciente//
    public void verDoctorconPaciente() throws SQLException {
        String sql = "SELECT id_doctores, id_paciente FROM citas";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(
                        rs.getString("id_doctores") + "\t" +
                        rs.getString("id_paciente"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void verPacienteConDoctorCita() throws SQLException {
        String sql = "SELECT id_paciente, id_doctores, id_horarios FROM citas";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(
                        rs.getString("id_paciente") + "\t" +
                                rs.getString("id_doctores")+"\t"+
                        rs.getString("id_horarios"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}









