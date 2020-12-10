package util;

public class DateTimeTest {

	public static void main(String[] args) {
		DateTime today = new DateTime();
		System.out.println(today); // 09/08/2019
		System.out.println(today.getNameOfDay()); // Friday
		
		DateTime threeDaysFromToday = new DateTime(today, 3);
		System.out.println(threeDaysFromToday); // 12/08/2019
		System.out.println(threeDaysFromToday.getNameOfDay()); 
		// Monday
		
		DateTime michaelBirthDay = new DateTime(4, 10, 1994);
		System.out.println(michaelBirthDay); // 04/10/1994

	}

}
