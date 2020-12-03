package ProjectHotelDemo;

import GenCol.DEVSQueue;
import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class BookingManager extends ViewableAtomic{

	double gen_process_time = 2;
	//double vip_process_time =25;
	entity job,currentJob = null;
	protected DEVSQueue q;

	public BookingManager() {this("Booking Manager");}

	public BookingManager(String name){
	    super(name);
	    addInport("book_in");
	    //addInport("vip_in");
	    addOutport("book_out");

	    addTestInput("book_in",new BookingEntity("VIPBook",5,1));
	    //addTestInput("vip_in",new BookingEntity("VIPBook",8,1));
	}

	public void initialize(){
	     q = new DEVSQueue();
	     passivateIn("wait");
	}


	public void  deltext(double e,message x)
	{
	Continue(e);
	if(phaseIs("wait")){
	   for (int i=0; i< x.getLength();i++){
	     if (messageOnPort(x, "book_in", i)) {
	       job = x.getValOnPort("book_in", i);
	       currentJob = job;
	       //holdIn("busy", ((BookingEntity)currentJob).getDuration());
	       holdIn("busy", gen_process_time);
	     }
	   }
	}
	else if(phaseIs("busy")){
	    for (int i=0; i< x.getLength();i++){
	      if (messageOnPort(x, "book_in", i)) {
	        job = x.getValOnPort("book_in", i);
	        if(((BookingEntity)job).getPriority() == 1)
	        	q.addFirst(job);
	        else
	        	q.addLast(job);
	      }
	    }
	 }
	}


	public void  deltint( )
	{
	if(phaseIs("busy")){
	   if(!q.isEmpty()) {
	     currentJob = (entity)q.pop();
	     holdIn("busy", gen_process_time);
	   }
	   else
	          passivateIn("wait");
	 }
	}

	public message out( )
	{
	   message  m = new message();
	   content con = makeContent("book_out",
	            new BookingEntity(currentJob.getName() ,((BookingEntity)currentJob).getDuration()*10,0));
	   m.add(con);
	  return m;
	}

	public String getTooltipText(){
		if(currentJob!=null)
		return super.getTooltipText()+"\n number in queue:"+q.size()+
		"\n current job :" + currentJob.toString();
		else return "initial value";
	}
}
