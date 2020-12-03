package ProjectHotelDemo;

import GenCol.entity;

public class RoomAllocEntity extends entity{
	int priority = 0;
	String name = "Room Alloc";
	double room_process_time = 10;
	
	public RoomAllocEntity() {
		this("room_alloc", 10);
	}
	
	
	public RoomAllocEntity(String _name, double booking_duration) {
		super(_name);
		name = _name;
		room_process_time = booking_duration;
	}
	
	public String getName() {
		return name;
	}
	
	public double getProcessTime() {
		return room_process_time;
	}
	
	public String toString() {
		return name+"_ProcessTime_"+(double)((int)(room_process_time*100))/100;
	//(double)((int)(room_process_time*100))/100;
		//Math.ceil((double)((int)(room_process_time*100))/10);
	}
}
