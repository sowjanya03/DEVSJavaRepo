package ProjectHotelDemo;

import java.util.Random;

import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class BookingGenerator extends ViewableAtomic{
	
	double generation_time = 5;
	int bookingID;
	Random rand;
	
	public BookingGenerator() {
		this("BookingGenerator", 5);
	}
	
	public BookingGenerator(String _name,double gen_time) {
		super(_name);
		generation_time = gen_time;
		addOutport("request_gen_room");
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
		content con = makeContent("request_gen_room", new BookingEntity("bookingID_"+bookingID,stay_duration, 0));
		m.add(con);
		return m;
	}
}
