import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void displayInfo() {
        System.out.println(name + ": $" + price);
    }
}

class Appetizer extends MenuItem {
    public Appetizer(String name, double price) {
        super(name, price);
    }

    @Override
    public void displayInfo() {
        System.out.println("Appetizer: " + getName() + " - $" + getPrice());
    }
}

class MainCourse extends MenuItem {
    public MainCourse(String name, double price) {
        super(name, price);
    }

    @Override
    public void displayInfo() {
        System.out.println("Main Course: " + getName() + " - $" + getPrice());
    }
}

class Dessert extends MenuItem {
    public Dessert(String name, double price) {
        super(name, price);
    }

    @Override
    public void displayInfo() {
        System.out.println("Dessert: " + getName() + " - $" + getPrice());
    }
}

class Drink extends MenuItem {
    public Drink(String name, double price) {
        super(name, price);
    }

    @Override
    public void displayInfo() {
        System.out.println("Drink: " + getName() + " - $" + getPrice());
    }
}

class Table {
    private int tableId;
    private boolean isAvailable;

    public Table(int tableId) {
        this.tableId = tableId;
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getTableId() {
        return tableId;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Order {
    private int orderId;
    private int tableId;
    private List<MenuItem> orderItems;
    private double totalCost;

    public Order(int orderId, int tableId) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.orderItems = new ArrayList<>();
        this.totalCost = 0;
    }

    public void addItem(MenuItem item) {
        orderItems.add(item);
        totalCost += item.getPrice();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId + " | Table ID: " + tableId);
        for (MenuItem item : orderItems) {
            item.displayInfo();
        }
        System.out.println("Total: $" + totalCost);
    }
}

class Reservation {
    private int reservationId;
    private String customerName;
    private int tableId;
    private String time;

    public Reservation(int reservationId, String customerName, int tableId, String time) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.tableId = tableId;
        this.time = time;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getTableId() {
        return tableId;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId + " | Customer: " + customerName +
                " | Table: " + tableId + " | Time: " + time);
    }
}

class Restaurant {
    private List<MenuItem> menu = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private int nextOrderId = 1;
    private int nextReservationId = 1;

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void displayMenu() {
        for (MenuItem item : menu) {
            item.displayInfo();
        }
    }

    public MenuItem findMenuItemByName(String name) {
        for (MenuItem item : menu) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public int getNextOrderId() {
        return nextOrderId++;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void showTableStatus() {
        for (Table table : tables) {
            System.out.println("Table " + table.getTableId() + ": " + (table.isAvailable() ? "Available" : "Occupied"));
        }
    }

    public Table findTableById(int tableId) {
        for (Table table : tables) {
            if (table.getTableId() == tableId) {
                return table;
            }
        }
        return null;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void displayOrders() {
        for (Order order : orders) {
            order.displayOrder();
        }
    }

    public void addReservation(String customerName, int tableId, String time) {
        Table table = findTableById(tableId);
        if (table != null && table.isAvailable()) {
            table.setAvailable(false);
            reservations.add(new Reservation(nextReservationId++, customerName, tableId, time));
            System.out.println("Reservation added successfully!");
        } else {
            System.out.println("Table is not available or does not exist.");
        }
    }

    public void cancelReservation(int reservationId) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getReservationId() == reservationId) {
                Table table = findTableById(reservation.getTableId());
                if (table != null) {
                    table.setAvailable(true);
                }
                reservations.remove(i);
                System.out.println("Reservation canceled successfully!");
                return;
            }
        }
        System.out.println("Reservation not found.");
    }

    public void displayReservations() {
        for (Reservation reservation : reservations) {
            reservation.displayReservation();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Restaurant Management System!");

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a menu item");
            System.out.println("2. Display menu");
            System.out.println("3. Add a table");
            System.out.println("4. Show table status");
            System.out.println("5. Place an order");
            System.out.println("6. Display all orders");
            System.out.println("7. Make a reservation");
            System.out.println("8. Display reservations");
            System.out.println("9. Cancel a reservation");
            System.out.println("10. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Choose category:");
                    System.out.println("1. Appetizer");
                    System.out.println("2. Main Course");
                    System.out.println("3. Dessert");
                    System.out.println("4. Drink");
                    int categoryChoice = scanner.nextInt();
                    scanner.nextLine();

                    MenuItem item;
                    switch (categoryChoice) {
                        case 1 -> item = new Appetizer(name, price);
                        case 2 -> item = new MainCourse(name, price);
                        case 3 -> item = new Dessert(name, price);
                        case 4 -> item = new Drink(name, price);
                        default -> {
                            System.out.println("Invalid category choice. Item not added.");
                            continue;
                        }
                    }
                    restaurant.addMenuItem(item);
                    System.out.println("Menu item added successfully!");
                }
                case 2 -> restaurant.displayMenu();
                case 3 -> {
                    System.out.print("Enter table ID: ");
                    int tableId = scanner.nextInt();
                    restaurant.addTable(new Table(tableId));
                    System.out.println("Table added successfully!");
                }
                case 4 -> restaurant.showTableStatus();
                case 5 -> {
                    System.out.print("Enter table ID for the order: ");
                    int tableId = scanner.nextInt();
                    scanner.nextLine();
                    Table table = restaurant.findTableById(tableId);
                    if (table == null || !table.isAvailable()) {
                        System.out.println("Invalid or unavailable table ID.");
                        break;
                    }
                    table.setAvailable(false);
                    Order order = new Order(restaurant.getNextOrderId(), tableId);
                    boolean addingItems = true;
                    while (addingItems) {
                        System.out.print("Enter menu item name to add to order (or type 'done' to finish): ");
                        String itemName = scanner.nextLine();
                        if (itemName.equalsIgnoreCase("done")) {
                            addingItems = false;
                        } else {
                            MenuItem item = restaurant.findMenuItemByName(itemName);
                            if (item != null) {
                                order.addItem(item);
                                System.out.println("Added " + item.getName() + " to the order.");
                            } else {
                                System.out.println("Item not found.");
                            }
                        }
                    }
                    restaurant.addOrder(order);
                    System.out.println("Order placed successfully! Total: $" + order.getTotalCost());
                }
                case 6 -> restaurant.displayOrders();
                case 7 -> {
                    System.out.print("Enter customer name for reservation: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter table ID for reservation: ");
                    int tableId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter reservation time (e.g., '7:00'): ");
                    String time = scanner.nextLine();
                    restaurant.addReservation(customerName, tableId, time);
                }
                case 8 -> restaurant.displayReservations();
                case 9 -> {
                    System.out.print("Enter reservation ID to cancel: ");
                    int reservationId = scanner.nextInt();
                    restaurant.cancelReservation(reservationId);
                }
                case 10 -> {
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }
}
