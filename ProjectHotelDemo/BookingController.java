package ProjectHotelDemo;

import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class BookingController extends ViewableAtomic{

	 protected double observation_time;
	 entity job;
	 double gen_payment, vip_payment, overall_time, served_time;

	 public BookingController(String  name,double Observation_time){
	  super(name);
	  //addOutport("out");
	  addInport("room_in");
	  addInport("book_in");
	  observation_time = Observation_time;
	  addTestInput("room_in",new entity("valRoom"));
	  addTestInput("book_in",new entity("valBooked"));
	 }

	 public BookingController() {this("calculating", 500);}

	 public void initialize(){
		 job = null;
		 overall_time = 0;
		 gen_payment = 50;
		 vip_payment = 100;
		 holdIn("active", observation_time);
	 }

	 public void deltext(double e,message  x){
		 Continue(e);
		  for(int i=0; i< x.size();i++){
		    if(messageOnPort(x,"book_in",i)){
		       job = x.getValOnPort("book_in",i);
		       if(job.getName().startsWith("bookingID")){
		    	   overall_time = ((BookingEntity)job).getDuration();
		       	   System.out.println(job.getName()+"-- Overall_time: "+overall_time
		       			   +"estimated_total_payment: " +gen_payment*overall_time);
		       }
		       
		       else if(job.getName().startsWith("VIPbookingID")){
		    	   overall_time = ((BookingEntity)job).getDuration();
		       	   System.out.println(job.getName()+"-- Overall_time: "+overall_time
		       			   +"estimated_total_payment: " +vip_payment*overall_time);
		       }
		    }
		    
		 }
	 }
	 public void deltint(){
		 passivate();
	 }

	 public message out( ){
	  message  m = new message();
	  content  con = makeContent("out",new entity("Completed"));
	  m.add(con);
	  return m;
	 }

}
