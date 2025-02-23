import java.util.*;

// Main class for the Hospital Management System
public class HospitalManagementSystem {

    // Entry point of the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital("City Care Hospital");

        int choice;
        do {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. View Electronic Health Records (EHR)");
            System.out.println("4. Billing and Invoicing");
            System.out.println("5. Manage Medical Inventory");
            System.out.println("6. Staff Management");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    hospital.registerPatient(scanner);
                    break;
                case 2:
                    hospital.scheduleAppointment(scanner);
                    break;
                case 3:
                    hospital.viewEHR(scanner);
                    break;
                case 4:
                    hospital.generateBill(scanner);
                    break;
                case 5:
                    hospital.manageInventory(scanner);
                    break;
                case 6:
                    hospital.manageStaff(scanner);
                    break;
                case 7:
                    System.out.println("Exiting system. Stay healthy!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 7);

        scanner.close();
    }
}

class Hospital {
    private String name;
    private List<Patient> patients;
    private List<Staff> staffMembers;
    private List<Appointment> appointments;
    private Map<String, Integer> medicalInventory;

    public Hospital(String name) {
        this.name = name;
        this.patients = new ArrayList<>();
        this.staffMembers = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.medicalInventory = new HashMap<>();
    }

    public void registerPatient(Scanner scanner) {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter patient condition: ");
        String condition = scanner.nextLine();

        Patient patient = new Patient(name, age, condition);
        patients.add(patient);

        System.out.println("Patient registered successfully. Patient ID: " + patient.getId());
    }

    public void scheduleAppointment(Scanner scanner) {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        Optional<Patient> patient = patients.stream().filter(p -> p.getId() == patientId).findFirst();
        if (patient.isPresent()) {
            Appointment appointment = new Appointment(patient.get(), date);
            appointments.add(appointment);
            System.out.println("Appointment scheduled successfully for " + date);
        } else {
            System.out.println("Patient not found.");
        }
    }

    public void viewEHR(Scanner scanner) {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        patients.stream()
                .filter(p -> p.getId() == patientId)
                .findFirst()
                .ifPresentOrElse(
                        p -> System.out.println("EHR for " + p.getName() + ": " + p.getCondition()),
                        () -> System.out.println("Patient not found."));
    }

    public void generateBill(Scanner scanner) {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        patients.stream()
                .filter(p -> p.getId() == patientId)
                .findFirst()
                .ifPresentOrElse(
                        p -> System.out.println("Bill generated for " + p.getName() + ": $" + amount),
                        () -> System.out.println("Patient not found."));
    }

    public void manageInventory(Scanner scanner) {
        System.out.print("Enter medical supply name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        medicalInventory.put(itemName, medicalInventory.getOrDefault(itemName, 0) + quantity);
        System.out.println("Inventory updated. " + itemName + ": " + medicalInventory.get(itemName));
    }

    public void manageStaff(Scanner scanner) {
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();

        Staff staff = new Staff(name, role);
        staffMembers.add(staff);
        System.out.println("Staff member added successfully. Staff ID: " + staff.getId());
    }
}

class Patient {
    private static int counter = 1;
    private int id;
    private String name;
    private int age;
    private String condition;

    public Patient(String name, int age, String condition) {
        this.id = counter++;
        this.name = name;
        this.age = age;
        this.condition = condition;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCondition() { return condition; }
}

class Staff {
    private static int counter = 1;
    private int id;
    private String name;
    private String role;

    public Staff(String name, String role) {
        this.id = counter++;
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

class Appointment {
    private Patient patient;
    private String date;

    public Appointment(Patient patient, String date) {
        this.patient = patient;
        this.date = date;
    }
}
