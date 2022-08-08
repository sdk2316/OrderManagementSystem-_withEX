package com.durgesh.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durgesh.model.OrderDetails;
import com.durgesh.service.IOrderService;
import com.durgesh.view.OrderDetailsExcelExporter;
import com.durgesh.view.OrderDetailsPDFExporter;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderService iOrderService;

	// http://localhost:9090/order/saveOrder
	@PostMapping("/saveOrder")
	public ResponseEntity<String> saveOrder(@RequestBody OrderDetails orderDetails) {
		ResponseEntity<String> resp = null;
		try {

			Integer id = iOrderService.saveOrder(orderDetails);
			resp = new ResponseEntity<String>(new StringBuffer().append("order created with id ").append(id)
					// .append(" saved")
					.toString(), HttpStatus.CREATED);

		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to process save order", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;

	}

	// Pagination
	//http://localhost:8000/order/OrderDetails
	@GetMapping("/OrderDetails/{pageNo}/{pageSize}")
	public List<OrderDetails> getPaginatedOrderDetails(@PathVariable int pageNo, @PathVariable int pageSize) {

		return iOrderService.findPaginated(pageNo, pageSize);
	}

	// exception handing done
	// http://localhost:8000/order/search/NR
	@GetMapping("/search/{customerName}")
	public List<Object> getCusDetails(@PathVariable("customerName") String customerName) {
		System.out.println(iOrderService.getByCustomerName(customerName));

		return iOrderService.getByCustomerName(customerName);

	}

	// http://localhost:9090/order/getAllOrder
	@GetMapping("/getAllOrder")
	public ResponseEntity<List<OrderDetails>> getAllOrders() {

		ResponseEntity<List<OrderDetails>> resp = null;
		try {
			List<OrderDetails> list = iOrderService.getAllOrderDetails();
			resp = new ResponseEntity<List<OrderDetails>>(list, HttpStatus.OK);

		} catch (Exception e) {
			resp = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;

	}

	// excel export
	//
	@GetMapping(value = "/OrderDetails/export/excel", produces = { "application/excel" })
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=OrderDetails_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<OrderDetails> listOrderDetails = iOrderService.getAllOrderDetailsInExcelAndPdf();

		OrderDetailsExcelExporter excelExporter = new OrderDetailsExcelExporter(listOrderDetails);

		excelExporter.export(response);
	}
	// pdf export

	@GetMapping(value = "/OrderDetails/export/pdf", produces = { "application/pdf" })
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=OrderDetails_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<OrderDetails> listOrderDetails = iOrderService.getAllOrderDetailsInExcelAndPdf();

		OrderDetailsPDFExporter exporter = new OrderDetailsPDFExporter(listOrderDetails);
		exporter.export(response);

	}

	//// Get Data By Brand Name
	// exception handle
	@GetMapping("/getAllBrand/{brand}")
	public List<OrderDetails> getAllBrand(@PathVariable("brand") String brand) {

		List<OrderDetails> list = iOrderService.getBybrand(brand);

		return list;

	}

	// Get Data By Customer Name
	// exception handle
	@GetMapping("/getAllOrderName/{customerName}")
	public List<OrderDetails> getAllOrderName(@PathVariable("customerName") String customerName) {

		List<OrderDetails> list = iOrderService.getOrderNameByCustomerName(customerName);

		return list;
	}
	// http://localhost:9090/order/getOneOrder/2 exception handle

	@GetMapping("/getOneOrder/{id}")
	public OrderDetails getOneOrderDetails(@PathVariable Integer id) {

		OrderDetails od = iOrderService.getOneOrderDetails(id);

		return od;
	}

	// http://localhost:9090/order/deleteorder/2
	// exception handle
	@DeleteMapping("/deleteorder/{id}")
	public ResponseEntity<String> deleteOrderDetails(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		try {
			iOrderService.deleteOrderDetails(id);
			resp = ResponseEntity.ok("Order Deleted with " + id);
		} catch (Exception e) {
			resp = new ResponseEntity<String>(" Order not present with " + id, HttpStatus.NOT_FOUND);
			e.printStackTrace();
		}
		return resp;

	}

	// exception handle
	// http://localhost:9090/order/updateOrder/2
	@PutMapping("/updateOrder/{id}")
	public ResponseEntity<String> updateOrderDetails(@PathVariable Integer id, @RequestBody OrderDetails orderDetails) {
		ResponseEntity<String> resp = null;

		if (iOrderService.isOrderDetailsExist(id)) {
			orderDetails.setOrderId(id);
			iOrderService.updateOrderDetails(orderDetails);
			return ResponseEntity.ok(" Order Updated with id " + id);
		} else {

			resp = new ResponseEntity<String>(" Order record not exist with " + id, HttpStatus.NOT_FOUND);
		}
		return resp;
	}

	@PutMapping("/disableProduct/{id}")
	public String disableOrderDetails(@PathVariable Integer id) {
		
		Integer disAbleProductId = iOrderService.disAbleProduct(id);

		return "Product Id Number " + disAbleProductId + " is Disable";
	}

	@PutMapping("/enableProduct/{id}")
	public String enableOrderDetails(@PathVariable Integer id) {

		Integer enableProductId = iOrderService.enableAbleProduct(id);

		return "Product Id Number " + enableProductId + " is Enable";
	}

}
