package ProjectHotelDemo;

import GenCol.entity;

public class BookingEntity extends entity{
	
	int priority = 0;
	String name = "Booking Name";
	double duration_of_stay = 10;
	
	public BookingEntity() {
		this("booking_name", 10, 1);
	}
	
	
	public BookingEntity(String _name, double booking_duration, int _priority) {
		super(_name);
		name = _name;
		duration_of_stay = booking_duration;
		priority =_priority;
	}
	
	public String getName() {
		return name;
	}
	
	public double getDuration() {
		return duration_of_stay;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public String toString() {
		return name+"_Duration_"+ (double)((int)(duration_of_stay*100))/100;
	//Math.ceil((double)((int)(duration_of_stay*100))/10);
	}

}
