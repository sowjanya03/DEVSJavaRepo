package ProjectHotelDemo;

import java.util.Random;

import GenCol.DEVSQueue;
import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class CheckoutManager extends ViewableAtomic{
	
	int checkout_counters;
	entity job, currentJob;
	double process_time, checkoutID;
	DEVSQueue checkout_queue = new DEVSQueue();
	//Random r = new Random();
	
	 public CheckoutManager(String  name, double pTime){
		  super(name);
		  addOutport("check_out");
		  addInport("check_in");
		  //addInport("pay_in");
		  process_time = pTime;
		  addTestInput("check_in",new RoomAllocEntity("Checkout", 2));
		 }

	 public CheckoutManager() {this("checkout", 3);}

	 public void initialize(){
		 job = null;
		 currentJob = null;
		 checkout_counters = 2;
		 checkoutID = 0;
		 passivateIn("wait");
	 }
	 
	 public void deltext(double e, message x) {
			Continue(e);
			checkout_counters--;
			if(checkout_counters >= 0) {
					for(int i=0; i<x.getLength(); i++) {
						if(messageOnPort(x, "check_in", i)) {
							job = x.getValOnPort("check_in", i);
							currentJob = job;
							process_time = ((RoomAllocEntity)job).getProcessTime();
							holdIn("wait", process_time);
						}
					}
				}
			else {
				phase = "busy";
				for(int i=0; i<x.getLength(); i++) {
					if(messageOnPort(x, "check_in", i)) {
						job = x.getValOnPort("check_in", i);
						checkout_queue.add(job);
					}
				}
			}
			
		}
		
		public void deltint() {
			checkoutID++;
			checkout_counters++;
				if(checkout_queue.size() > 0) {
					job = (RoomAllocEntity)checkout_queue.pop();
					currentJob = job;
					process_time =  ((RoomAllocEntity)job).getProcessTime();
					if(checkout_counters <= 0)
						holdIn("busy", process_time);
					else
						holdIn("wait", process_time);
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
		   content con = makeContent("check_out", new entity("CheckoutID"+checkoutID));
		   m.add(con);

		   return m;
		}

		public String getTooltipText(){
			if(currentJob!=null)
				return super.getTooltipText()+"\n Queue size: "+checkout_queue.size()+ 
						"\n Current Job "+currentJob.getName()+
						"\n Available Counters: " +checkout_counters;
			else
				return "Init State";
		}
		 
	

}
