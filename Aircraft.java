package aircraft_management;

import java.io.Serializable;
import java.util.ArrayList;

class Part implements Serializable {
    String name;
    int status;
    String nextRepairDate;

    Part(String name, int status, String nextRepairDate) {
        this.name = name;
        this.status = status;
        this.nextRepairDate = nextRepairDate;
    }

    @Override
    public String toString() {
        String s = (status == 1) ? "CRITICAL" : (status == 2) ? "MODERATE" : "LOW";
        return name + " | Status: " + s + " | Next Repair: " + nextRepairDate;
    }
}

public class Aircraft implements Serializable {
    String id;
    String model;
    String airline;
    int flyingHours;
    int fuelLevel;
    int engineTemperature;
    int totalLandings;
    boolean engineFault;
    int lastMaintenanceDaysAgo;

    ArrayList<Part> parts = new ArrayList<>();

    Aircraft(String id, String model, String airline, int flyingHours, int fuelLevel,
             int engineTemperature, int totalLandings, boolean engineFault, int lastMaintenanceDaysAgo) {
        this.id = id;
        this.model = model;
        this.airline = airline;
        this.flyingHours = flyingHours;
        this.fuelLevel = fuelLevel;
        this.engineTemperature = engineTemperature;
        this.totalLandings = totalLandings;
        this.engineFault = engineFault;
        this.lastMaintenanceDaysAgo = lastMaintenanceDaysAgo;
    }

    void addPart(String pname, String date) {
        int status;

        boolean critical = fuelLevel < 20
                        || flyingHours > 20000
                        || engineTemperature > 900
                        || totalLandings > 15000
                        || engineFault
                        || lastMaintenanceDaysAgo > 365;

        boolean moderate = fuelLevel < 40
                        || flyingHours > 10000
                        || engineTemperature > 700
                        || totalLandings > 8000
                        || lastMaintenanceDaysAgo > 180;

        if (critical) status = 1;
        else if (moderate) status = 2;
        else status = 3;

        parts.add(new Part(pname, status, date));
    }

    void showParts() {
        for (Part p : parts) {
            System.out.println("   " + p);
        }
    }

    @Override
    public String toString() {
        return id + " | " + model + " | " + airline +
               " | Hours: " + flyingHours +
               " | Fuel: " + fuelLevel + "%" +
               " | Eng Temp: " + engineTemperature + "°C" +
               " | Landings: " + totalLandings +
               " | Fault: " + (engineFault ? "YES" : "NO") +
               " | Last Maintenance: " + lastMaintenanceDaysAgo + " days ago";
    }
}