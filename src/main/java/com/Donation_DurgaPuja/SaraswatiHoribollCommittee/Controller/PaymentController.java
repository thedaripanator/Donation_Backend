package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;


import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Booking;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.BookingRepository;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service.RazorpayService;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.dto.BookingRequest;
import com.razorpay.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping
    public String health() {

        return "OK";
    }
    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create-order")
    public String createOrder(
            @RequestBody BookingRequest request
    ) throws Exception {

        int amount =
                request.getQuantity() * 201;

        Order order =
                razorpayService.createOrder(amount);

        JSONObject jsonObject =
                new JSONObject(order.toString());

        Booking booking = new Booking();

        booking.setDonorName(
                request.getDonorName());

        booking.setPhone(
                request.getPhone());

        booking.setQuantity(
                request.getQuantity());

        booking.setTotalAmount(amount);

        booking.setRazorpayOrderId(
                jsonObject.getString("id"));

        booking.setPaymentStatus("CREATED");

        bookingRepository.save(booking);

        return order.toString();
    }
}
