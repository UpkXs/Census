package com.example.census.database;

import com.example.census.model.Citizen;
import com.example.census.model.CitizenLogin;
import com.example.census.model.Controller;
import com.example.census.model.Region;
import com.example.census.model.Stationary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
    Statement statement = null;
    private boolean status = false;

    public int insertInto(Connection connection, String sql) {
        System.out.println("sql = " + sql);
        System.out.println("coon = " + connection);
        int rows = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            rows = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            rows = 0;
        }

        return rows;
    }
    public List<String> selectFromRegion(Connection connection, String sql) {
        System.out.println("connection" + connection);
        System.out.println("sql" + sql);

        List<String> regionList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement = " + statement);
                    status = true;
                    System.out.println("status = " + status);
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        String region = rs.getString("region_name");
                        regionList.add(region);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }

        return regionList;
    }

    public Region selectRegionIdFromRegion(Connection connection, String sql) {
        System.out.println("connection" + connection);
        System.out.println("sql" + sql);

        Region region = new Region();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement = " + statement);
                    status = true;
                    System.out.println("status = " + status);
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        region.setRegion_id(rs.getInt("region_id"));
                        region.setRegion_name(rs.getString("region_name"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }

        return region;
    }

    public CitizenLogin selectFromCitizenLogin(Connection connection, String sql) {
        System.out.println("connection" + connection);
        System.out.println("sql" + sql);

        CitizenLogin citizenLogin = new CitizenLogin();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement = " + statement);
                    status = true;
                    System.out.println("status = " + status);
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        citizenLogin.setUsername_id(rs.getInt("username_id"));
                        citizenLogin.setUsername(rs.getString("username"));
                        citizenLogin.setPassword(rs.getString("password"));
                        citizenLogin.setFinger_print(rs.getString("finger_print"));
                        citizenLogin.setFacial_print(rs.getString("facial_print"));
                        citizenLogin.setApi_key(rs.getInt("api_key"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }

        return citizenLogin;
    }

    public Citizen selectFromCitizen(Connection connection, String sql) {
        System.out.println("connection" + connection);
        System.out.println("sql" + sql);

        Citizen citizen = new Citizen();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement = " + statement);
                    status = true;
                    System.out.println("status = " + status);
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        citizen.setCitizen_tin(rs.getInt("citizen_tin"));
                        citizen.setCitizen_fullName(rs.getString("citizen_fullName"));
                        citizen.setUsername_id(rs.getInt("username_id"));
                        citizen.setRegion_id(rs.getInt("region_id"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }

        return citizen;
    }

    public Stationary selectFromStationary(Connection connection, String sql) {
        System.out.println("connection" + connection);
        System.out.println("sql" + sql);

        Stationary stationary = new Stationary();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement = " + statement);
                    status = true;
                    System.out.println("status = " + status);
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        stationary.setStationary_id(rs.getInt("stationary_id"));
                        stationary.setStationary_username(rs.getString("stationary_username"));
                        stationary.setStationary_password(rs.getString("stationary_password"));
                        stationary.setStationary_apikey(rs.getInt("stationary_apikey"));
                        stationary.setRegion_id(rs.getInt("region_id"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }

        return stationary;
    }

    public Controller selectFromController(Connection connection, String sql) {
        System.out.println("connection" + connection);
        System.out.println("sql" + sql);

        Controller controller = new Controller();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement = " + statement);
                    status = true;
                    System.out.println("status = " + status);
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        controller.setController_id(rs.getInt("controller_id"));
                        controller.setController_name(rs.getString("controller_name"));
                        controller.setController_username(rs.getString("controller_username"));
                        controller.setController_password(rs.getString("controller_password"));
                        controller.setController_apikey(rs.getInt("controller_apikey"));
                        controller.setRegion_id(rs.getInt("region_id"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }

        return controller;
    }
}
