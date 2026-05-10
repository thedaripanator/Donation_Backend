package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {
    @Value("${razorpay.key.id}")
    private String key;

    @Value("${razorpay.key.secret}")
    private String secret;

    public Order createOrder(Integer amount)
            throws Exception {

        RazorpayClient client =
                new RazorpayClient(key, secret);

        JSONObject object = new JSONObject();

        object.put("amount", amount * 100);

        object.put("currency", "INR");

        object.put("receipt", "txn_123");

        return client.orders.create(object);
    }
}
