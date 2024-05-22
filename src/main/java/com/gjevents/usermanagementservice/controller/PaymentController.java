package com.gjevents.usermanagementservice.controller;

import com.stripe.model.Coupon;
import com.gjevents.usermanagementservice.service.StripeService;
import com.gjevents.usermanagementservice.utils.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaymentController {

	@Value("${stripe.key.public}")
	private String API_PUBLIC_KEY;

	private StripeService stripeService;

	public PaymentController(StripeService stripeService) {
		this.stripeService = stripeService;
	}

	@GetMapping("/")
	public String homepage() {
		return "homepage";
	}

	@GetMapping("/subscription")
	public String subscriptionPage(Model model) {
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "subscription";
	}

	@GetMapping("/charge")
	public String chargePage(@RequestParam("total") String total, @RequestParam("eventId") String eventId, Model model) {

		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		model.addAttribute("total", total);
		model.addAttribute("eventId", eventId);

		return "charge";
	}

	@PostMapping("/create-subscription")
	public @ResponseBody Response createSubscription(String email, String token, String plan, String coupon) {

		if (token == null || plan.isEmpty()) {
			return new Response(false, "Stripe payment token is missing. Please try again later.");
		}

		String customerId = stripeService.createCustomer(email, token);

		if (customerId == null) {
			return new Response(false, "An error accurred while trying to create customer");
		}

		String subscriptionId = stripeService.createSubscription(customerId, plan, coupon);

		if (subscriptionId == null) {
			return new Response(false, "An error accurred while trying to create subscription");
		}

		return new Response(true, "Success! your subscription id is " + subscriptionId);
	}

	@PostMapping("/cancel-subscription")
	public @ResponseBody Response cancelSubscription(String subscriptionId) {

		boolean subscriptionStatus = stripeService.cancelSubscription(subscriptionId);

		if (!subscriptionStatus) {
			return new Response(false, "Faild to cancel subscription. Please try again later");
		}

		return new Response(true, "Subscription cancelled successfully");
	}

	@PostMapping("/coupon-validator")
	public @ResponseBody Response couponValidator(String code) {

		Coupon coupon = stripeService.retriveCoupon(code);

		if (coupon != null && coupon.getValid()) {
			String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
					: coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
			return new Response(true, details);
		}
		return new Response(false, "This coupon code is not available. This may be because it has expired or has "
				+ "already been applied to your account.");
	}

//	@PostMapping("/create-charge")
//	public @ResponseBody Response createCharge(String email, String token) {
//
//		if (token == null) {
//			return new Response(false, "Stripe payment token is missing. please try again later.");
//		}
//
//		String chargeId = stripeService.createCharge(email, token, 999);// 9.99 usd
//
//		if (chargeId == null) {
//			return new Response(false, "An error accurred while trying to charge.");
//		}
//
//		// You may want to store charge id along with order information
//
//		return new Response(true, "Success your charge id is " + chargeId);
//	}





	@PostMapping("/create-charge")
	public ResponseEntity<Response> createCharge(String email, String token, String total, String eventId) {
		Response response = new Response();

		if (token == null) {
			response.setStatus(false);
			response.setDetails("Token is missing. Please try again.");
			return ResponseEntity.badRequest().body(response);
		}

		String chargeId = stripeService.createCharge(email, token, Integer.parseInt(total)); // 9.99 usd

		if (chargeId == null) {
			response.setStatus(false);
			response.setDetails("An error occurred while trying to charge.");
			return ResponseEntity.badRequest().body(response);
		}
		stripeService.sendEmail(email, eventId, total, chargeId);

		response.setStatus(true);
		response.setDetails("http://localhost:3000/organizer/event/" + eventId);
		return ResponseEntity.ok(response);
	}


}
