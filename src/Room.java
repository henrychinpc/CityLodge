import java.util.ArrayList;
import util.DateTime;

public abstract class Room {
	private String roomId;
	private String roomFeature;
	private String roomType;
	protected int numOfBeds;
	protected String roomStatus;
	protected ArrayList<HiringRecord> hiringRecords = new ArrayList<HiringRecord>();

	public Room(String roomId, String roomFeature, String roomType, int numOfBeds, String roomStatus) {
		this.roomId = roomId;
		this.numOfBeds = numOfBeds;
		this.roomFeature = roomFeature;
		this.roomType = roomType;
		this.roomStatus = roomStatus;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public String getRoomFeature() {
		return this.roomFeature;
	}

	public String getRoomType() {
		return this.roomType;
	}

	public int getNumOfBeds() {
		return this.numOfBeds;
	}

	public String getStatus() {
		return this.roomStatus;
	}

	public ArrayList<HiringRecord> getHiringRecords() {
		return hiringRecords;
	}

	public void setHireRecord(HiringRecord hiringRecord) {
		hiringRecords.add(hiringRecord);
	}

	public abstract boolean returnRoom(DateTime returnDate);

	public boolean roomMaintenance() {
		if (roomStatus == "Rented") {
			return false;
		} else {
			roomStatus = "Maintenance";
			return true;
		}
	}

	public boolean completeMaintenance(DateTime completionDate) {
		if (roomStatus == "Maintenance") {
			roomStatus = "Available";
			return true;
		} else {
			return false;
		}
	}

	public boolean rentRoom(String customerId, DateTime rentDate, int numOfRentDays) {
		if (roomStatus == "Available") {
			HiringRecord hire = new HiringRecord();
			hire.setRentDate(rentDate);
			hire.setRecordId(roomId, customerId);
			hire.setEstimatedReturnDate(numOfRentDays);
			roomStatus = "Rented";
			setHireRecord(hire);
			return true;

		} else
			return false;
	}

	@Override
	public String toString() {
		return (roomId + ":" + numOfBeds + ":" + roomType + ":" + roomStatus + ":" + roomFeature);
	}

	public String getDetails() {
		if (hiringRecords.size() == 1) {
			return String.format("%n%-25s%s %n%-25s%d %n%-25s%s %n%-25s%s %n%-25s%s", "Room ID:", roomId,
					"Number of beds: ", numOfBeds, "Room type: ", roomType, "Room status: ", roomStatus, "Features: ",
					roomFeature, "Rental record: ", hiringRecords.get(0).getDetails());

		} else if (hiringRecords.size() > 1) {
			String string = String.format("%n%-25s%s %n%-25s%d %n%-25s%s %n%-25s%s %n%-25s%s", "Room ID:", roomId,
					"Number of beds: ", numOfBeds, "Room type: ", roomType, "Room status: ", roomStatus, "Features: ",
					roomFeature, "Rental record:", hiringRecords.get(0).getDetails());
			int i = 1;
			while (i < hiringRecords.size()) {
				String line = String.format("%n---------------------------%n%s", hiringRecords.get(i).getDetails());
				string = string + line;
				i++;
			}
			return string;

		} else {
			return String.format("%n%-25s%s %n%-25s%d %n%-25s%s %n%-25s%s %n%-25s%s %n%-25s%s", "Room ID: ", roomId,
					"Number of beds: ", numOfBeds, "Room type: ", roomType, "Room status: ", roomStatus, "Features: ",
					roomFeature, "RENTAL RECORD", "EMPTY");
		}
	}

}