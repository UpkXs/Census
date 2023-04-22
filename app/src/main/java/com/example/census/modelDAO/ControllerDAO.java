package com.example.census.modelDAO;

public class ControllerDAO {
    private int controller_id;
    private String controller_username;
    private int region_id;

    public ControllerDAO() {
    }

    public ControllerDAO(int controller_id, String controller_username, int region_id) {
        this.controller_id = controller_id;
        this.controller_username = controller_username;
        this.region_id = region_id;
    }

    public int getController_id() {
        return controller_id;
    }

    public void setController_id(int controller_id) {
        this.controller_id = controller_id;
    }

    public String getController_username() {
        return controller_username;
    }

    public void setController_username(String controller_username) {
        this.controller_username = controller_username;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
