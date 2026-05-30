package pbo.f01.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "parking_areas")
public class ParkingArea implements Comparable<ParkingArea> {
    
    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "allowed_type", nullable = false)
    private String allowedType;

    // Relasi One-to-Many ke Vehicle
    @OneToMany(mappedBy = "parkingArea", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();

    public ParkingArea() {}

    public ParkingArea(String name, int capacity, String allowedType) {
        this.name = name;
        this.capacity = capacity;
        this.allowedType = allowedType;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getAllowedType() { return allowedType; }
    public void setAllowedType(String allowedType) { this.allowedType = allowedType; }

    public List<Vehicle> getVehicles() { return vehicles; }
    public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles; }

    public boolean canPark(Vehicle vehicle) {
        if (!this.allowedType.equalsIgnoreCase(vehicle.getType())) {
            return false;
        }
        if (this.vehicles.size() >= this.capacity) {
            return false;
        }
        return true;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setParkingArea(this);
    }

    @Override
    public int compareTo(ParkingArea o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return this.name + " " + this.allowedType + " " + this.capacity + "|" + this.vehicles.size();
    }
}