package org.bytemark.bytewheel.constants;

public enum BookingStatus {

	BOOKED,			// Booked, mail not sent 		
	CONFIRMED, 		// Booked and mail is sent to the customer
	CANCELLED		// Cancelled previous confirmed/unconfirmed booking
	
}
