import util.DateTime;

public class StandardRoom extends Room {
	public static final double SINGLE_BED_DAILY_RATE = 59;
	public static final double DOUBLE_BED_DAILY_RATE = 99;
	public static final double FOUR_BED_DAILY_RATE = 199;

	public StandardRoom(String roomId, String roomFeature, String roomType, int numOfBeds, String status) {
		super(roomId, roomFeature, roomType, numOfBeds, status);
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
				if (numOfBeds == 1) {
					rentalFee = StandardRoom.SINGLE_BED_DAILY_RATE
							* (DateTime.diffDays(returnDate, hire.getRentDate()));
					hire.setRentalFee(rentalFee);
				}
				if (numOfBeds == 2) {
					rentalFee = StandardRoom.DOUBLE_BED_DAILY_RATE
							* (DateTime.diffDays(returnDate, hire.getRentDate()));
					hire.setRentalFee(rentalFee);
				}
				if (numOfBeds == 4) {
					rentalFee = StandardRoom.FOUR_BED_DAILY_RATE * (DateTime.diffDays(returnDate, hire.getRentDate()));
					hire.setRentalFee(rentalFee);
				}

				double lateFee = 0;
				if (DateTime.diffDays(hire.getEstimatedReturnDate(), returnDate) > 0) {
					hire.setLateFee(lateFee);
					if (numOfBeds == 1) {
						lateFee = (135 / 11) * StandardRoom.SINGLE_BED_DAILY_RATE
								* (DateTime.diffDays(returnDate, hire.getRentDate()));
						hire.setLateFee(lateFee);
					}
					if (numOfBeds == 2) {
						lateFee = (135 / 11) * StandardRoom.DOUBLE_BED_DAILY_RATE
								* (DateTime.diffDays(returnDate, hire.getRentDate()));
						hire.setLateFee(lateFee);
					}
					if (numOfBeds == 4) {
						lateFee = (135 / 11) * StandardRoom.FOUR_BED_DAILY_RATE
								* (DateTime.diffDays(returnDate, hire.getRentDate()));
						hire.setLateFee(lateFee);
					}
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
}

