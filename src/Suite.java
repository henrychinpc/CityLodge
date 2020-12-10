import util.DateTime;

public class Suite extends Room {
	public static final double DAILY_RATE = 999;
	private DateTime lastMaintenanceDate;

	public Suite(String roomId, String roomFeature, String status, DateTime lastMaintenanceDate) {
		super(roomId, roomFeature, "Suite", 6, status);
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	public DateTime getLastMaintenanceDate() {
		return this.lastMaintenanceDate;
	}

	public void setLastMaintenanceDate(DateTime lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	@Override
	public String getDetails() {
		if (hiringRecords.size() == 1) {
			return String.format("%n%-25s%s %n%-25s%d %n%-25s%s %n%-25s%s %n%-25s%s", "Room ID:", getRoomId(), "Number of beds: ", getNumOfBeds(), "Room type: ",
					getRoomType(), "Room status: ", getStatus(), "Features: ", getRoomFeature(), "RENTAL RECORD",
					hiringRecords.get(0).getDetails());

		} else if (hiringRecords.size() > 1) {
			String string = String.format("%n%-25s%s %n%-25s%d %n%-25s%s %n%-25s%s %n%-25s%s", "Room ID:", getRoomId(), "Number of beds: ", getNumOfBeds(), "Room type: ",
					getRoomType(), "Room status: ", getStatus(), "Features: ", getRoomFeature(), "RENTAL RECORD",
					hiringRecords.get(0).getDetails());
			int i = 1;
			while (i < hiringRecords.size()) {
				String line = String.format("%n---------------------------%n%s", hiringRecords.get(i).getDetails());
				string = string + line;
				i++;
			}
			return string;

		} else {
			return String.format("%n%-25s%s %n%-25s%d %n%-25s%s %n%-25s%s %n%-25s%s %n%-25s%s", "Room ID: ", getRoomId(), "Number of beds: ", getNumOfBeds(), "Room type: ",
					getRoomType(), "Room status: ", getStatus(), "Features: ", getRoomFeature(), "RENTAL RECORD",
					"Empty");
		}
	}

	@Override
	public String toString() {
		return (getRoomId() + ":" + getNumOfBeds() + ":" + getRoomType() + ":" + getStatus() + ":" + lastMaintenanceDate
				+ ":" + getRoomFeature());
	}

	@Override
	public boolean returnRoom(DateTime returnDate) {
		if (hiringRecords.size() == 0) {
			System.out.println("No room to return");
		} else if (hiringRecords.size() > 0) {
			HiringRecord hire = hiringRecords.get(hiringRecords.size() - 1);
			if (DateTime.diffDays(hire.getRentDate(), returnDate) <= 0 || roomStatus == "Available") {
				hire.setActualReturnDate(returnDate);
				roomStatus = "Available";

				double rentalFee;
				rentalFee = DAILY_RATE * (DateTime.diffDays(returnDate, hire.getRentDate()));
				hire.setRentalFee(rentalFee);
				
				double lateFee = 0;
				if (DateTime.diffDays(hire.getEstimatedReturnDate(), returnDate) > 0) {
					hire.setLateFee(lateFee);
				} else if(DateTime.diffDays(hire.getEstimatedReturnDate(), returnDate) < 0) {
					lateFee = 1099 * (DateTime.diffDays(returnDate, hire.getRentDate()));
					hire.setLateFee(lateFee);
				}
				System.out.println(getRoomId() + "successfully returned!");
				return true;
			} else {
				System.out.println(getRoomId() + "cannot be returned.");
				return false;
			}
		}
		return false;
	}

	// TODO create any instance variables required for the
	// Suite type

	// TODO create constructors

	// TODO implements methods and / abstract methods

	// TODO might need to override the toString() method

	// same as ROOM class
}
