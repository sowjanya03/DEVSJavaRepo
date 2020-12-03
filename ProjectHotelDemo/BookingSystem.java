package ProjectHotelDemo;

import java.awt.Dimension;
import java.awt.Point;

import simView.ViewableAtomic;
import simView.ViewableComponent;
import simView.ViewableDigraph;

public class BookingSystem extends ViewableDigraph{

	public BookingSystem() {
		this("Booking System");
	}
	
	public BookingSystem(String name) {
		super(name);
		generateSystem();
	}
	
	public void generateSystem() {
		ViewableAtomic book_gen = new BookingGenerator("General Booking Generator", 5);
		add(book_gen);
		ViewableAtomic vip_book_gen = new VIPBookingGenerator("VIP Booking Generator", 8, 1);
		add(vip_book_gen);
		ViewableAtomic book_manager = new BookingManager("Booking Manager");
		add(book_manager);
		ViewableAtomic room_allocator = new RoomAllocator("Room Allocator", 3);
		add(room_allocator);
		ViewableAtomic book_controller = new BookingController("Booking Controller", 500);
		add(book_controller);
		ViewableAtomic checkout_manager = new CheckoutManager("Checkout Manager", 3);
		add(checkout_manager);
		
		addCoupling(book_gen,"request_gen_room", book_manager, "book_in");
		addCoupling(vip_book_gen,"request_vip_room", book_manager, "book_in");
		addCoupling(book_manager, "book_out", room_allocator, "assign_in");
		addCoupling(book_manager, "book_out", book_controller, "book_in");
		addCoupling(room_allocator, "assigned_out", book_controller, "room_in");
		addCoupling(room_allocator,"assigned_out", checkout_manager, "check_in");
		//addCoupling(book_controller,"out", checkout_manager, "pay_in");
		addCoupling(checkout_manager,"check_out", this, "out");
	}


    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1218, 497);
        if((ViewableComponent)withName("Room Allocator")!=null)
             ((ViewableComponent)withName("Room Allocator")).setPreferredLocation(new Point(515, 134));
        if((ViewableComponent)withName("VIP Booking Generator")!=null)
             ((ViewableComponent)withName("VIP Booking Generator")).setPreferredLocation(new Point(18, 303));
        if((ViewableComponent)withName("Booking Manager")!=null)
             ((ViewableComponent)withName("Booking Manager")).setPreferredLocation(new Point(246, 200));
        if((ViewableComponent)withName("Checkout Manager")!=null)
             ((ViewableComponent)withName("Checkout Manager")).setPreferredLocation(new Point(796, 138));
        if((ViewableComponent)withName("General Booking Generator")!=null)
             ((ViewableComponent)withName("General Booking Generator")).setPreferredLocation(new Point(17, 135));
        if((ViewableComponent)withName("Booking Controller")!=null)
             ((ViewableComponent)withName("Booking Controller")).setPreferredLocation(new Point(656, 306));
    }
}