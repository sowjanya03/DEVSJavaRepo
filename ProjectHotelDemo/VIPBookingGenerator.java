package ProjectHotelDemo;

import java.util.Random;

import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class VIPBookingGenerator extends ViewableAtomic{
	double generation_time = 8;
	int bookingID, priority;
	Random rand;
	
	public VIPBookingGenerator() {
		this("VIPBookingGenerator", 8, 1);
	}
	
	public VIPBookingGenerator(String _name,double gen_time, int _priority) {
		super(_name);
		generation_time = gen_time;
		priority = _priority;
		addOutport("request_vip_room");
	}
	
	public void initialize() {
		bookingID = 0;
		rand = new Random();
		holdIn("active", generation_time);
	}
	
	public void deltext(double e, message x) {
		Continue(e);
	}
	
	public void deltint() {
		bookingID++;
		double booking_time = rand.nextDouble()*generation_time;
		holdIn("active",booking_time);
	}
	
	public message out() {
		message m = new message();	
		double stay_duration = rand.nextDouble();		
		content con = makeContent("request_vip_room", new BookingEntity("VIPbookingID_"+bookingID,stay_duration, 1));
		m.add(con);
		return m;
	}
}