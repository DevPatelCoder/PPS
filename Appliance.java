package ASSIGNMENTS;
import java.util.Scanner;


// -----------------------------------------------------
// Assignment (#1)
// Question: (Part 1 and Part 2)
// Written by: (Dev Patel (40305577) and Pranay Patel (40307223) )
// -----------------------------------------------------

/*
    This program is an **Appliance Store Inventory System** that allows users to manage a collection of appliances by adding, updating, and searching
    for them based on different criteria. It maintains details like the appliance type, brand, serial number, and price.
    Users can interact with a menu-driven interface to add new appliances, modify existing ones (if authenticated with a password),
    search by brand, or filter appliances by price. The program also ensures data validation, such as checking for valid appliance
    types and preventing negative prices. It provides a structured way to organize and track appliances efficiently in a store's inventory.
 */
/**
 * Dev Patel (40305577) and Pranay Patel (40307223)
 * comp6481
 * Assignment #1
 * Friday, January 31, 2025
 * .
 * .
 * .
 * Represents an Appliance with attributes like type, brand, serial number, and price.
 * This class provides constructors to create Appliance objects and methods to manipulate and retrieve appliance details.
 * It also includes a main driver program to manage appliance inventory.
 *
 * @author Dev Patel (40305577) and Pranay Patel (40307223)
 */

public class Appliance {
    private String type;
    private String brand;
    private final long serialNum;
    private static long serialCounter = 1000000;
    private double price;

    private static int appliancecounter = 0;


    /**
     * Default constructor initializes the appliance with default values.
     */
    //Default constructor
    public Appliance(){
        type = "Fridge";
        brand = "Samsung";
        serialNum = serialCounter;
        price = 15000;
        serialCounter++;
        appliancecounter++;
    }
    /**
     * Parameterized constructor to initialize an appliance with specific attributes.
     *
     * @param type  the type of the appliance
     * @param brand the brand of the appliance
     * @param price the price of the appliance (must be greater than 1)
     * @throws IllegalArgumentException if price is less than 1 or type is invalid
     */
    //Parameterized Constructor
    public Appliance(String type, String brand, double price) {
        if (price < 1) {
            throw new IllegalArgumentException("Price cannot be less than $1.");
        }
        if (!isValidType(type)) {
            throw new IllegalArgumentException("Invalid appliance type.");
        }
        this.type = type;
        this.brand = brand;
        serialNum = serialCounter;
        this.price = price;
        serialCounter++;
        appliancecounter++;
    }
    /**
     * Copy constructor creates a new Appliance object as a copy of another.
     *
     * @param app the Appliance object to copy
     */
    public Appliance(Appliance app){
        this.type = app.type;
        this.brand = app.brand;
        serialNum = serialCounter;
        this.price = app.price;
        serialCounter++;
        appliancecounter++;
    }

    // Getter methods
    /**
     * @return the type of the appliance
     */
    public String getType() {
        return type;
    }
    /**
     * @return the brand of the appliance
     */
    public String getBrand() {
        return brand;
    }
    /**
     * @return the price of the appliance
     */
    public double getPrice() {
        return price;
    }
    /**
     * @return the serial number of the appliance
     */
    public long getSerialNum() {
        return serialNum;
    }

    // Setter methods
    /**
     * Sets the appliance type if valid.
     *
     * @param type the new type of the appliance
     */
    public void setType(String type) {
        if (!isValidType(type)) {
            System.out.println("Invalid appliance type.");
        }
        else
            this.type = type;
    }
    /**
     * Sets the appliance brand.
     *
     * @param brand the new brand of the appliance
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /**
     * Sets the price of the appliance if valid.
     *
     * @param price the new price of the appliance
     */
    public void setPrice(double price) {
        if (price < 1) {
            System.out.println("Please enter a price greater than $1.");
        } else {
            this.price = price;
        }
    }

    /**
     * Validates the appliance type.
     *
     * @param type the type to check
     * @return true if valid, false otherwise
     */
    //Check Validity of type
    private boolean isValidType(String type) {
        String[] validTypes = {"Fridge", "Air Conditioner", "Washer", "Dryer", "Freezer", "Stove", "Dishwasher", "Water Heaters", "Microwave"};
        for (String validType : validTypes) {
            if (validType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return String representation of the appliance
     */
    // Returns appliance details
    public String toString() {
        return "Appliance Serial # " + serialNum + "\n"
                + "Brand: " + brand + "\n"
                + "Type: " + type + "\n"
                + "Price: $" + price;
    }

    /**
     * @return the total number of appliances created
     */
    // Returns the total number of appliances created
    public static int findNumberOfCreatedAppliances() {
        return appliancecounter;
    }

    /**
     * Compares this appliance with another based on type, brand, and price.
     *
     * @param app the Appliance object to compare
     * @return true if equal, false otherwise
     */
    // Compares two appliances based on type, brand, and price
    public boolean equals(Appliance app) {
        return type.equals(app.type) && brand.equals(app.brand) && price == app.price;
    }
    /**
     * Authenticates the user with a password.
     * @param scanner Scanner for user input.
     * @param password The correct password.
     * @param maxAttempts Maximum number of attempts allowed.
     * @return True if authentication is successful, otherwise false.
     */
    //check authentication
    private static boolean authenticate(Scanner scanner, String password, int maxAttempts) {
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("Enter password: ");
            String input = scanner.next();

            if (input.equals(password)) {
                return true;
            } else
            {
                attempts++;
                System.out.println("Incorrect password. Attempts left: " + (maxAttempts - attempts));
            }
        }
        return false;
    }

    /**
     * Finds appliances by brand.
     * @param inventory The inventory array.
     * @param brand The brand name to search for.
     */
    // Find by brand name
    private static void findAppliancesByBrand(Appliance[] inventory, String brand) {
        boolean found = false;
        for (Appliance appliance : inventory) {
            if (appliance != null && appliance.getBrand().equalsIgnoreCase(brand)) {
                System.out.println(appliance);
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appliances found with the brand: " + brand);
        }
    }

    /**
     * Filters and displays appliances that are cheaper than a specified price.
     * @param inventory The inventory array containing appliances.
     * @param price The price threshold to filter appliances.
     */
    // Filter to find cheaper than
    private static void findCheaperThan(Appliance[] inventory, double price) {
        boolean found = false;
        for (Appliance appliance : inventory) {
            if (appliance != null && appliance.getPrice() < price) {
                System.out.println(appliance);
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appliances found cheaper than: $" + price);
        }
    }


    // Driver Program
    /**
     * The main method acts as the driver program for the Appliance Store Inventory System.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        final String PASSWORD = "c6481";
        int maxAttempts = 3;
        int invalidAttempts = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Appliance Store Inventory System! ");
        System.out.println("Developed by: Dev Patel (40305577) & Pranay Patel(40307223) ");

        System.out.print("Enter the maximum number of appliances your store can contain: ");
        int maxAppliances = scanner.nextInt();
        Appliance[] inventory = new Appliance[maxAppliances];
        int currentCount = 0;

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add new appliances (password required)");
            System.out.println("2. Update appliance information (password required)");
            System.out.println("3. Find appliances by brand");
            System.out.println("4. Find appliances cheaper than a price");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Add new appliances
                    if (authenticate(scanner, PASSWORD, maxAttempts))
                    {
                        System.out.print("How many appliances do you want to add? ");
                        int count = scanner.nextInt();

                        if (currentCount + count <= maxAppliances) {
                            for (int i = 0; i < count; i++) {
                                System.out.println("Enter information for appliance " + (i+1));
                                System.out.print("Enter appliance type: ");
                                String type = scanner.next();
                                System.out.print("Enter appliance brand: ");
                                String brand = scanner.next();
                                System.out.print("Enter appliance price: ");
                                double price = scanner.nextDouble();
                                System.out.println();
                                inventory[currentCount++] = new Appliance(type, brand, price);
                            }
                        } else {
                            System.out.println("Not enough space in the inventory. Remaining capacity: " + (maxAppliances - currentCount));
                        }
                    }
                    else
                    {
                        invalidAttempts++;
                        if (invalidAttempts == 4) {
                            System.out.println("Program detected suspicious activities and will terminate immediately!");
                            System.exit(0);
                        }
                    }
                    break;

                case 2: // Update appliance information
                    if (authenticate(scanner, PASSWORD, maxAttempts))
                    {
                        System.out.print("Enter the serial number of the appliance to update: ");
                        long serial = scanner.nextLong();
                        Appliance appliance = findApplianceBySerial(inventory, serial);

                        if (appliance == null) {
                            while(true){
                                System.out.println("No appliance found with the given serial number.");
                                System.out.println("Do You want to");
                                System.out.println("1. Enter another serial number ");
                                System.out.println("2. Go back to the main menu" );
                                System.out.print("Enter your choice: ");
                                int ch = scanner.nextInt();
                                if(ch == 1){
                                    System.out.print("Enter the serial number of the appliance to update: ");
                                    long serialnew = scanner.nextLong();
                                    Appliance app1 = findApplianceBySerial(inventory, serialnew);

                                    if (app1 != null) {
                                        System.out.println("Appliance found: ");
                                        System.out.println(app1);
                                        updateAppliance(scanner, app1);
                                        break;
                                    }
                                }
                                else{
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Appliance found: ");
                            System.out.println(appliance);
                            updateAppliance(scanner, appliance);
                        }
                    }
                    break;

                case 3: // Find appliances by brand
                    System.out.print("Enter the brand name: ");
                    String brand = scanner.next();
                    findAppliancesByBrand(inventory, brand);
                    break;

                case 4: // Find appliances cheaper than a price
                    System.out.print("Enter the price: ");
                    double price = scanner.nextDouble();
                    findCheaperThan(inventory, price);
                    break;

                case 5: // Exit
                    System.out.println("Thank you for using the Appliance Store Inventory System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    /**
     * Finds an appliance by serial number.
     * @param inventory The inventory array.
     * @param serial The serial number to search for.
     * @return The appliance if found, otherwise null.
     */
    // Find Appliance by Serial number
    private static Appliance findApplianceBySerial(Appliance[] inventory, long serial) {
        for (Appliance appliance : inventory) {
            if (appliance != null && appliance.getSerialNum() == serial) {
                return appliance;
            }
        }
        return null;
    }

    //Update the Appliance
    private static void updateAppliance(Scanner scanner, Appliance appliance) {
        while (true) {
            System.out.println("\nWhat information would you like to change?");
            System.out.println("1. Brand");
            System.out.println("2. Type");
            System.out.println("3. Price");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    // change in brand
                    System.out.print("Enter new brand: ");
                    String brand = scanner.next();
                    appliance.setBrand(brand);
                    break;

                case 2:
                    // change in type
                    System.out.print("Enter new type: ");
                    String type = scanner.next();
                    appliance.setType(type);
                    break;

                case 3:
                    // change in price
                    System.out.print("Enter new price: ");
                    double price = scanner.nextDouble();
                    appliance.setPrice(price);
                    break;

                case 4:
                    // exit
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println("Updated appliance: ");
            System.out.println(appliance);
        }
    }
}