package com.backend.test;

import com.backend.user.Paciente;

import java.sql.*;

public class Test {
    private static final String SQL_TABLE_CREATE = "DROP TABLE IF EXISTS PACIENTE; CREATE TABLE PACIENTE"
            +"("
            +"ID INT PRIMARY KEY,"
            +"NOMBRE varchar(100) NOT NULL,"
            +"APELLIDO varchar(100) NOT NULL,"
            +"DOMICILIO varchar(100) NOT NULL,"
            +"DNI varchar(100) NOT NULL,"
            +"FECHADEALTA varchar(100) NOT NULL,"
            +"USUARIO varchar(100) NOT NULL,"
            +"PASSWORD varchar(100) NOT NULL"
            +")";

    private static final String SQL_INSERT = "INSERT INTO PACIENTE(ID, NOMBRE, APELLIDO, DOMICILIO, DNI, FECHADEALTA, USUARIO, PASSWORD) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE PACIENTE SET PASSWORD=? WHERE USUARIO=?";

    public Test() throws Exception {
    }

    public static void main(String[] args) throws Exception {

        Paciente paciente = new Paciente(1,"Jaimito", "Perez", "Siempre viva 666", "33344567","10/01/2020", "gremlin", "holaChau");

        Connection connection = null;
        try{

            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_TABLE_CREATE);

            PreparedStatement preparedInsert = connection.prepareStatement(SQL_INSERT);

            preparedInsert.setInt(1, paciente.getId());
            preparedInsert.setString(2, paciente.getNombre());
            preparedInsert.setString(3, paciente.getApellido());
            preparedInsert.setString(4, paciente.getDomicilio());
            preparedInsert.setString(5, paciente.getDni());
            preparedInsert.setString(6, paciente.getFechaAlta());
            preparedInsert.setString(7, paciente.getUsuario());
            preparedInsert.setString(8, paciente.getPassword());

            preparedInsert.execute();

            connection.setAutoCommit(false);

            PreparedStatement preparedUpdate = connection.prepareStatement(SQL_UPDATE);

            preparedUpdate.setString(1, paciente.setPassword("chauHola"));
            preparedUpdate.setString(2, paciente.getUsuario());

            preparedUpdate.execute();

            int error = 1 / 0;

            connection.commit();
            connection.setAutoCommit(true);

            String sql = "SELECT * FROM PACIENTE";
            Statement stmt = connection.createStatement();
            ResultSet rd = stmt.executeQuery(sql);
            while(rd.next()){
                System.out.println(rd.getInt(1)
                        + rd.getString(2)
                        + rd.getString(3)
                        + rd.getString(4)
                        + rd.getString(5)
                        + rd.getString(6)
                        + rd.getString(7)
                        + rd.getString(8));
            }






        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
        Connection connection1 = getConnection();
        String sql = "SELECT * FROM PACIENTE";
        Statement stmt = connection1.createStatement();
        ResultSet rd = stmt.executeQuery(sql);
        while(rd.next()){
            System.out.println(rd.getInt(1)
                    + rd.getString(2)
                    + rd.getString(3)
                    + rd.getString(4)
                    + rd.getString(5)
                    + rd.getString(6)
                    + rd.getString(7)
                    + rd.getString(8));
        }

    }

    public static Connection getConnection() throws Exception{
        Class.forName("org.h2.Driver").newInstance();
        return DriverManager.getConnection("jdbc:h2:"+"./Datavase/my"+"root"+"myPassword");
    }

}
