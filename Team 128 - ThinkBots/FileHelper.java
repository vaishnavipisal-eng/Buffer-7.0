package aircraft_management;

import java.io.*;
import java.util.ArrayList;

public class FileHelper {
    private static final String FILE_NAME = "fleet.dat";

    public static void saveFleet(ArrayList<Aircraft> fleet) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(fleet);
            System.out.println("Data saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Aircraft> loadFleet() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Aircraft>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found, starting fresh.");
            return new ArrayList<>();
        }
    }
}