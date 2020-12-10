import java.util.HashMap;
import java.util.Scanner;
import util.DateTime;

public class CityLodge {
	Scanner keyboard = new Scanner(System.in);
	private String roomId;
	private String roomType;
	private String roomFeature;
	private int numOfBeds;
	private HashMap<String, Room> rooms = new HashMap<String, Room>();

	public void addStandardRoom() {
		do {
			System.out.println("Please enter a Standard Room ID: ");
			roomId = keyboard.next();
			if (!roomId.contains("R_")) {
				System.out.println("Invalid input. Please input ID starting with R_");
			} else if (rooms.containsKey(roomId)) {
				System.out.println("Invalid input. Room already exists!");
			} else
				break;
		} while (true);

		do {
			System.out.println("Please enter number of beds: ");
			numOfBeds = keyboard.nextInt();
			if (numOfBeds != 1 && numOfBeds != 2 && numOfBeds != 4) {
				System.out.println("Invalid input. Number of beds has to be either 1, 2 or 4.");
			}
			break;
		} while (true);

		System.out.println("Please enter features: ");
		keyboard.nextLine();
		roomFeature = keyboard.nextLine();
		roomType = "Standard";
		StandardRoom standardRoom = new StandardRoom(roomId, roomFeature, roomType, numOfBeds, "Available");
		rooms.put(roomId, standardRoom);
	}

	public void addSuiteRoom() {
		do {
			System.out.println("Please enter a Suite Room ID: ");
			roomId = keyboard.next();
			if (!roomId.contains("S_")) {
				System.out.println("Invalid input. Please input ID starting with S_");
			} else if (rooms.containsKey(roomId)) {
				System.out.println("Invalid input. Room already exists!");
			} else
				break;
		} while (true);

		System.out.println("Please enter features: ");
		keyboard.nextLine();
		roomFeature = keyboard.nextLine();
		roomType = "Suite";
		System.out.println("Please enter last maintenance date: (dd/mm/yyyy)");
		String maintenanceDate = keyboard.next();
		int day = Integer.parseInt(maintenanceDate.substring(0, 2));
		int month = Integer.parseInt(maintenanceDate.substring(3, 5));
		int year = Integer.parseInt(maintenanceDate.substring(6, 10));
		DateTime dateTime = new DateTime(day, month, year);
		Suite suite = new Suite(roomId, roomFeature, "Available", dateTime);
		rooms.put(roomId, suite);
	}

	public void rentStandardRoom() {
		System.out.println("Please select which standard room to rent: ");
		roomId = keyboard.next();
		String customerId;
		StandardRoom standardRoom = (StandardRoom) rooms.get(roomId);

		do {
			if (!roomId.contains("R_")) {
				System.out.println("Invalid input. Room ID has to start with R_");
			} else if (!rooms.containsKey(roomId)) {
				System.out.println("Invalid input. The room does not exists.");
			} else if (standardRoom.getStatus().contains("Rented")
					|| standardRoom.getStatus().contains("Maintenance")) {
				System.out.println("Sorry. The room " + roomId + "is not available.");
			} else {
				System.out.println("Please enter customer ID: ");
				customerId = keyboard.next();
				break;
			}
		} while (true);

		System.out.println("Please input rent date: (dd/mm/yyyy)");
		String rentDate = keyboard.next();
		int day = Integer.parseInt(rentDate.substring(0, 2));
		int month = Integer.parseInt(rentDate.substring(3, 5));
		int year = Integer.parseInt(rentDate.substring(6, 10));
		DateTime dateTime = new DateTime(day, month, year);
		do {
			System.out.println("Please input rent days: ");
			int numOfRentDays = keyboard.nextInt();
			String days = dateTime.getNameOfDay();
			if (numOfRentDays < 2 || numOfRentDays > 10) {
				System.out.println("Invalid input. Minimum rent days must be more than 2 and less than 10.");
			} else if (numOfRentDays < 3 && (days.equals("Saturday") || days.equals("Sunday"))) {
				System.out.println("Invalid input. Minimum rent days during the weekends must be more than 3.");
			} else {
				standardRoom.rentRoom(customerId, dateTime, numOfRentDays);
				break;
			}
		} while (true);
	}

	public void rentSuiteRoom() {
		String customerId;
		Suite suite = (Suite) rooms.get(roomId);

		do {
			System.out.println("Please select which suite to rent: ");
			roomId = keyboard.next();
			if (!roomId.contains("S_")) {
				System.out.println("Invalid input. Room ID has to start with S_");
			} else if (!rooms.containsKey(roomId)) {
				System.out.println("Invalid input. The room does not exists.");
			} else if (suite.getStatus().contains("Rented") || suite.getStatus().contains("Maintenance")) {
				System.out.println("Sorry. The room " + roomId + "is not available.");
			} else {
				System.out.println("Please enter customer ID: ");
				customerId = keyboard.next();
				break;
			}
		} while (true);

		do {
			System.out.println("Please input rent date: (dd/mm/yyyy)");
			String rentDate = keyboard.next();
			int day = Integer.parseInt(rentDate.substring(0, 2));
			int month = Integer.parseInt(rentDate.substring(3, 5));
			int year = Integer.parseInt(rentDate.substring(6, 10));
			DateTime dateTime = new DateTime(day, month, year);
			System.out.println("Please input rent days: ");
			int numOfRentDays = keyboard.nextInt();
			DateTime estimatedReturnDate;
			estimatedReturnDate = new DateTime(dateTime, numOfRentDays);
			if (DateTime.diffDays(suite.getLastMaintenanceDate(), estimatedReturnDate) >= 10) {
				System.out.println(
						"Sorry. The suite room cannot be rented as it clashes with upcoming maintenance date.");
			} else {
				suite.rentRoom(customerId, dateTime, numOfRentDays);
				break;
			}
		} while (true);
	}

	public void roomMaintenance() {
		Room room = (Room) rooms.get(roomId);
		do {
			System.out.println("Please enter Room ID: ");
			roomId = keyboard.next();
			if (!rooms.containsKey(roomId)) {
				System.out.println("Invalid input. The room does not exists.");
			} else {

				if (room.getStatus().contains("Rented")) {
					System.out.println("Sorry. The room is currently rented.");
				} else if (room.getStatus().contains("Maintenance")) {
					System.out.println("Sorry. The room is currently under maintenance.");
				} else {
					room.roomMaintenance();
					if (roomId.contains("R_")) {
						System.out.println("Room " + roomId + "is now under maintenance");
					} else if (roomId.contains("S_")) {
						System.out.println("Suite " + roomId + "is now under maintenance");
					}
				}
			}
			break;
		} while (true);

	}

	public void completeMaintenance() {
		Room room = (Room) rooms.get(roomId);
		do {
			System.out.println("Please enter Room ID: ");
			roomId = keyboard.next();
			if (!rooms.containsKey(roomId)) {
				System.out.println("Invalid input. The room does not exists.");
			} else {

				if (!room.getStatus().contains("Maintenance")) {
					System.out.println("The room is not under maintenance");
				} else {
					System.out.println("Please enter maintenance completion date: (dd/mm/yyy)");
					String rentDate = keyboard.next();
					int day = Integer.parseInt(rentDate.substring(0, 2));
					int month = Integer.parseInt(rentDate.substring(3, 5));
					int year = Integer.parseInt(rentDate.substring(6, 10));
					DateTime dateTime = new DateTime(day, month, year);
					room.completeMaintenance(dateTime);

					if (roomId.contains("R_")) {
						System.out.println("Room " + roomId + "has completed maintenance");
					} else if (roomId.contains("S_")) {
						System.out.println("Room " + roomId + "has completed maintenanace");
					}
				}
			}
		} while (true);
	}

	public void returnRoom() {
		do {
			System.out.println("Please enter Room ID: ");
			roomId = keyboard.next();
			if (!rooms.containsKey(roomId)) {
				System.out.println("Invalid input. The room does not exists.");
			} else {
				Room room = (Room) rooms.get(roomId);

				if (!room.getStatus().contains("Rented")) {
					System.out.println("The room is not currently being rented");
				} else {
					System.out.println("Please enter return date: (dd/mm/yyyy) ");
					String rentDate = keyboard.next();
					int day = Integer.parseInt(rentDate.substring(0, 2));
					int month = Integer.parseInt(rentDate.substring(3, 5));
					int year = Integer.parseInt(rentDate.substring(6, 10));
					DateTime dateTime = new DateTime(day, month, year);
					room.returnRoom(dateTime);
					break;
				}
			}
		} while (true);
	}

	public void displayAllRooms() {
		System.out.println("\nAll rooms: \n");

		for (String key : rooms.keySet()) {
			Room room = rooms.get(key);
			System.out.println(room.getDetails());
		}
	}

	public void runApp() {

		int option = 0;

		while (true) {

			String menu[] = { "**** CITYLODGE MAIN MENU ****", "\n\nAdd Room:			1", "Rent room:			2",
					"Return room:			3", "Room Maintenance:		4", "Complete Maintenance:		5",
					"Display All Rooms:		6", "Exit Program:			7", "Enter your choice: " };

			do {
				for (int i = 0; i < menu.length; i++)
					System.out.println(menu[i]);
				while (!keyboard.hasNextInt()) {
					System.out.println("Please only enter integer values");
					keyboard.next();
				}

				option = keyboard.nextInt();

				if (option == 1) {
					int addChoice = 0;
					String add[] = { "\nAdd standard room:		1", "Add suite:			2", "Enter your choice: " };
					do {
						for (int i = 0; i < add.length; i++)
							System.out.println(add[i]);
						addChoice = keyboard.nextInt();
						if (addChoice == 1) {
							addStandardRoom();
						} else if (addChoice == 2) {
							addSuiteRoom();
						}
						break;
					} while (true);

				} else if (option == 2) {
					int rentChoice = 0;
					String rent[] = { "\nRent standard room:		1", "Rent suite:			2",
							"Enter your choice: " };
					do {
						for (int i = 0; i < rent.length; i++)
							System.out.println(rent[i]);
						rentChoice = keyboard.nextInt();
						if (rentChoice == 1) {
							rentStandardRoom();
						} else if (rentChoice == 2) {
							rentSuiteRoom();
						}
						break;
					} while (true);

				} else if (option == 3) {
					returnRoom();

				} else if (option == 4) {
					roomMaintenance();

				} else if (option == 5) {
					completeMaintenance();

				} else if (option == 6) {
					displayAllRooms();

				} else if (option == 7) {
					System.out.println("Thank you for using CityLodge Systems. Goodbye!");
					System.exit(0);
				}

			} while (option < 0 || option > 6);
		}

	}
}
