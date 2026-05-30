package pbo.f01;
import pbo.f01.model.ParkingArea;
import pbo.f01.model.Vehicle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class utama
 * Nama: [Geralda Natali Gultom]
 * Nim: [12S24051]
 */

public class App {

    static {
        System.setProperty("org.jboss.logging.provider", "jdk");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
    }

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pbo-f01-pu");

    public static void main(String[] args) {

        
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] tokens = input.split("#");
            String command = tokens[0];

            switch (command) {
                case "area-add":
                    if (tokens.length == 4) {
                        String name = tokens[1];
                        int capacity = Integer.parseInt(tokens[2]);
                        String allowedType = tokens[3];

                        em.getTransaction().begin();
                        ParkingArea area = em.find(ParkingArea.class, name);
                        if (area == null) {
                            area = new ParkingArea(name, capacity, allowedType);
                            em.persist(area);
                        }
                        em.getTransaction().commit();
                    }
                    break;

                case "vehicle-add":
                    if (tokens.length == 4) {
                        String plateNumber = tokens[1];
                        String owner = tokens[2];
                        String type = tokens[3];

                        em.getTransaction().begin();
                        Vehicle vehicle = em.find(Vehicle.class, plateNumber);
                        if (vehicle == null) {
                            vehicle = new Vehicle(plateNumber, owner, type);
                            em.persist(vehicle);
                        }
                        em.getTransaction().commit();
                    }
                    break;

                case "park":
                    if (tokens.length == 3) {
                        String plateNumber = tokens[1];
                        String areaName = tokens[2];

                        em.getTransaction().begin();
                        Vehicle vehicle = em.find(Vehicle.class, plateNumber);
                        ParkingArea area = em.find(ParkingArea.class, areaName);

                        if (vehicle != null && area != null && area.canPark(vehicle)) {
                            area.addVehicle(vehicle);
                            em.merge(area);
                        }
                        em.getTransaction().commit();
                    }
                    break;

                case "display-all":
                    List<ParkingArea> areas = em.createQuery("SELECT a FROM ParkingArea a", ParkingArea.class)
                                                .getResultList();
                    
                    Collections.sort(areas);

                    for (ParkingArea area : areas) {
                        System.out.println(area.toString());
                        
                        List<Vehicle> parkedVehicles = area.getVehicles();
                        Collections.sort(parkedVehicles);

                        for (Vehicle vehicle : parkedVehicles) {
                            System.out.println(vehicle.toString());
                        }
                    }

                    em.close();
                    emf.close();
                    scanner.close();
                    return;

                default:
                    break;
            }
        }

        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
        scanner.close();
    }
}