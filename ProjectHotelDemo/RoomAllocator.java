package ProjectHotelDemo;

import java.util.Random;

import GenCol.DEVSQueue;
import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class RoomAllocator extends ViewableAtomic{
	
	double room_allocation_time;
	entity job, currentJob;
	DEVSQueue allocator_queue, temp_queue;
	int totalRooms, roomId, requestedRooms, availableRooms, aR;
	Random r;
	
	public RoomAllocator() {
		this("roomAllocator", 3);
	}
	
	public RoomAllocator(String _name, double processTime) {
		super(_name);
		room_allocation_time = processTime;
		addInport("assign_in");
		addOutport("assigned_out");
		job = new entity("NullJob");
		
		addTestInput("assign_in", new BookingEntity("testBooking", 5, 0));
	}
	
	public void initialize() {
		allocator_queue = new DEVSQueue();
		temp_queue = new DEVSQueue();
		totalRooms = 5;
		aR = 5;
		requestedRooms = 0;
		currentJob = null;
		roomId = 0;
		r = new Random();
		passivateIn("wait");		
	}
	
	public void deltext(double e, message x) {
		Continue(e);
		aR--;
		if(aR >= 0) {
				for(int i=0; i<x.getLength(); i++) {
					if(messageOnPort(x, "assign_in", i)) {
						job = x.getValOnPort("assign_in", i);
						currentJob = job;
						room_allocation_time = ((BookingEntity)job).getDuration()/10;
						holdIn("busy", room_allocation_time);
					}
				}
			}
		else {
			phase = "all_occupied";
			for(int i=0; i<x.getLength(); i++) {
				if(messageOnPort(x, "assign_in", i)) {
					job = x.getValOnPort("assign_in", i);
					allocator_queue.add(job);
				}
			}
		}
		
	}
	
	public void deltint() {
		roomId++;
		aR++;
			if(allocator_queue.size() > 0) {
				job = (entity) allocator_queue.pop();
				currentJob = job;
				room_allocation_time =  ((BookingEntity)job).getDuration()/10;				
				holdIn("busy", room_allocation_time);
			}
			else
				passivateIn("wait");
			
	}
	
	public double ta() {
		return sigma;
	}
	
	public message  out( )
	{
	   message  m = new message();
	   double prTime = 3 + r.nextDouble();
	   content con = makeContent("assigned_out", new RoomAllocEntity("serviceID"+roomId, prTime));
	   m.add(con);

	   return m;
	}

	public String getTooltipText(){
		if(currentJob!=null)
			return super.getTooltipText()+"\n Queue size: "+allocator_queue.size()+ 
					"\n Current Job "+currentJob.getName()+
					"\n Available Rooms: " +aR;
		else
			return "Init State";
	}

}
