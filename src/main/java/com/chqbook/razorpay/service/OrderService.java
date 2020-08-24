package com.chqbook.razorpay.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chqbook.razorpay.dto.CreateOrderDto;
import com.chqbook.razorpay.dto.RazorPayDto;
import com.chqbook.razorpay.entity.Order;
import com.chqbook.razorpay.entity.Product;
import com.chqbook.razorpay.exception.CustomException;
import com.chqbook.razorpay.repository.ApplicationUserRespository;
import com.chqbook.razorpay.repository.OrderRepository;
import com.chqbook.razorpay.repository.ProductRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ApplicationUserRespository applicationUserRespository;

	@Value("${razorpay.id}")
	private String keyId;

	@Value("${razorpay.secret}")
	private String secretKey;

	public RazorPayDto CreateOrder(CreateOrderDto createOrderDto) throws CustomException {

		Optional<Product> optionalProduct = productRepository.findById(createOrderDto.getProductId());
		if (optionalProduct != null) {
			Product product = optionalProduct.get();
			double price = product.getPrice();
			int quantity = 1;
			if (createOrderDto.getQuantity() > 0)
				quantity = createOrderDto.getQuantity();

			DecimalFormat df = new DecimalFormat("#.##");
			double totalAmount = Double.valueOf(df.format(price * quantity));

			String razorpayOrderId = createRazorPayOrder(totalAmount);

			Order order = new Order();
			order.setRazorpayOrderId(razorpayOrderId);
			order.setAmount(totalAmount);
			order.setQuantity(quantity);
			order.setCreatedAt(ZonedDateTime.now());
			order.setProduct(product);

			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = userDetails.getUsername();
			long createdBy = applicationUserRespository.findByUsername(username).getId();

			order.setCreatedBy(createdBy);
			orderRepository.save(order);
			return getRazorPay(razorpayOrderId,totalAmount, username);
			
		} else {
			throw new CustomException("The product id is not found please try again with different id.", HttpStatus.NOT_FOUND);
		}
	}

	private String createRazorPayOrder(double amount) throws CustomException {

		JSONObject options = new JSONObject();
		options.put("amount", convertRupeeToPaise(amount));
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
		try {
			RazorpayClient client = new RazorpayClient(keyId, secretKey);
			return client.Orders.create(options).get("id").toString();
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private long convertRupeeToPaise(double amount) {
		return (long) (amount * 100);
	}
	
	private RazorPayDto getRazorPay(String orderId, double amount, String username) {
		RazorPayDto razorPay = new RazorPayDto();
		razorPay.setAmount(convertRupeeToPaise(amount));
		razorPay.setCustomerName(username);
		razorPay.setMerchantName("Test");
		razorPay.setPurchaseDescription("TEST PURCHASES");
		razorPay.setRazorpayOrderId(orderId);
		razorPay.setSecretKey(keyId);
		razorPay.setNotes("notes"+orderId);
		
		return razorPay;
	}
}
