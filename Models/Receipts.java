package app.Emtech.Alesa.Models;

import org.json.JSONArray;

public class Receipts {

    private int id;
    private String driver, agent, type, status;
    private JSONArray items;

    public Receipts() {

     this.setId(0);
     this.setDriver("");
     this.setAgent("");
     this.setType("");
     this.setStatus("");

    }

    // cardView
    public Receipts(int id,String driver, String agent, String type, String status,JSONArray items) {
        setId(id);
        setDriver(driver);
        setAgent(agent);
        setType(type);
        setStatus(status);
        setItems(items);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {

        if (type.equals("Invoice")) {
            type = "Factura";
        }

        if (type.equals("PO")) {
            type = "Orden de compra";
        }

        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        if (status.equals("Total")) {
            status = "Total";
        }

        if (status.equals("Partial")) {
            status = "Parcial";
        }

        if (status.equals("Cancelled")) {
            status = "Cancelada";
        }

        this.status = status;
    }

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }
}
