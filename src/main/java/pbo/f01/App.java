package pbo.f01;

import pbo.f01.model.ParkingArea;
import pbo.f01.model.Vehicle;

import javax.persistence.*;
import java.util.*;

public class App {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("park-it-pu");
        em = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("#");
            String command = parts[0];

            switch (command) {
                case "area-add":
                    handleAreaAdd(parts);
                    break;
                case "vehicle-add":
                    handleVehicleAdd(parts);
                    break;
                case "park":
                    handlePark(parts);
                    break;
                case "display-all":
                    handleDisplayAll();
                    break;
                default:
                    // unknown command, ignore
                    break;
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }

    // area-add#<name>#<capacity>#<allowed_type>
    private static void handleAreaAdd(String[] parts) {
        if (parts.length < 4) return;
        String name = parts[1];
        int capacity;
        try {
            capacity = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return;
        }
        String allowedType = parts[3];

        // Check if area already exists
        ParkingArea existing = em.find(ParkingArea.class, name);
        if (existing != null) return;

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        ParkingArea area = new ParkingArea(name, capacity, allowedType);
        em.persist(area);
        tx.commit();
    }

    // vehicle-add#<plate_number>#<owner>#<type>
    private static void handleVehicleAdd(String[] parts) {
        if (parts.length < 4) return;
        String plateNumber = parts[1];
        String owner = parts[2];
        String type = parts[3];

        // Check if vehicle already exists
        Vehicle existing = em.find(Vehicle.class, plateNumber);
        if (existing != null) return;

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Vehicle vehicle = new Vehicle(plateNumber, owner, type);
        em.persist(vehicle);
        tx.commit();
    }

    // park#<plate_number>#<area_name>
    private static void handlePark(String[] parts) {
        if (parts.length < 3) return;
        String plateNumber = parts[1];
        String areaName = parts[2];

        Vehicle vehicle = em.find(Vehicle.class, plateNumber);
        if (vehicle == null) return; // vehicle not registered

        ParkingArea area = em.find(ParkingArea.class, areaName);
        if (area == null) return; // area not found

        // Validate type match
        if (!vehicle.getType().equalsIgnoreCase(area.getAllowedType())) return;

        // Refresh area to get current occupancy count
        em.refresh(area);

        // Validate capacity
        long occupied = countVehiclesInArea(areaName);
        if (occupied >= area.getCapacity()) return;

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        vehicle = em.find(Vehicle.class, plateNumber);
        area = em.find(ParkingArea.class, areaName);
        vehicle.setParkingArea(area);
        em.merge(vehicle);
        tx.commit();
    }

    private static long countVehiclesInArea(String areaName) {
        TypedQuery<Long> q = em.createQuery(
            "SELECT COUNT(v) FROM Vehicle v WHERE v.parkingArea.name = :areaName", Long.class);
        q.setParameter("areaName", areaName);
        return q.getSingleResult();
    }

    // display-all: areas sorted by name ASC, then vehicles in each area sorted by plate ASC
    private static void handleDisplayAll() {
        TypedQuery<ParkingArea> areaQuery = em.createQuery(
            "SELECT a FROM ParkingArea a ORDER BY a.name ASC", ParkingArea.class);
        List<ParkingArea> areas = areaQuery.getResultList();

        for (ParkingArea area : areas) {
            long occupied = countVehiclesInArea(area.getName());
            System.out.println(area.getName() + " " + area.getAllowedType()
                + " " + area.getCapacity() + "|" + occupied);

            TypedQuery<Vehicle> vehicleQuery = em.createQuery(
                "SELECT v FROM Vehicle v WHERE v.parkingArea.name = :areaName ORDER BY v.plateNumber ASC",
                Vehicle.class);
            vehicleQuery.setParameter("areaName", area.getName());
            List<Vehicle> vehicles = vehicleQuery.getResultList();

            for (Vehicle v : vehicles) {
                System.out.println(v.getPlateNumber() + " " + v.getOwner() + " " + v.getType());
            }
        }
    }
}
