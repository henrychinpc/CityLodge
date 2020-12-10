import util.DateTime;

public class HiringRecord {
	private String recordId;
	private DateTime rentDate = new DateTime();
	private DateTime estimatedReturnDate = new DateTime();
	private DateTime actualReturnDate = new DateTime(00, 00, 0000);
	private double rentalFee;
	private double lateFee;

	public String getRecordId() {
		return recordId;
	}

	public DateTime getRentDate() {
		return rentDate;
	}

	public DateTime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}

	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}

	public void setEstimatedReturnDate(int numOfDays) {
		estimatedReturnDate = new DateTime(rentDate, numOfDays);
	}

	public void setRecordId(String roomId, String customerId) {
		recordId = String.format(roomId, customerId, getRentDate().getEightDigitDate());
	}

	@Override
	public String toString() {
		return (recordId + ":" + rentDate + ":" + estimatedReturnDate + ":" + actualReturnDate + ":" + rentalFee + ":"
				+ lateFee);
	}

	public String getDetails() {
		if (!actualReturnDate.getEightDigitDate().equals("00000000")) {
			return String.format("%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%f%n%-25s%f", "Record ID: ", recordId,
					"Rent date: ", rentDate.toString(), "Estimated return date: ", estimatedReturnDate.toString(),
					"Actual return date: ", actualReturnDate.toString(), "Rental fee: ", rentalFee, "Late fee: ",
					lateFee);
		} else {
			return String.format("%n%-25s%s%n%-25s%s%n%-25s%s", "Record ID: ", recordId, "Rent date: ",
					rentDate.toString(), "Estimated return date: ", estimatedReturnDate.toString());
		}
	}
}
