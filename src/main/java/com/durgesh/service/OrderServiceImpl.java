package com.durgesh.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.durgesh.exception.CustomerNameNotFoundExdeption;
import com.durgesh.exception.IdNotExistException;
import com.durgesh.exception.NoBrandExistExdeption;
import com.durgesh.exception.OrderNotFoundException;
import com.durgesh.model.OrderDetails;
import com.durgesh.repository.OrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	// save order
	
	@Override
	public Integer saveOrder(OrderDetails order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order).getOrderId();
	}
	
	
	
	//// Pagination
	@Override
    public List<OrderDetails> findPaginated(int pageNo, int pageSize) {

        PageRequest paging =  PageRequest.of(pageNo, pageSize);
        Page<OrderDetails> pagedResult = orderRepository.findAll(paging);
       
        return pagedResult.toList();
    }
	
	//exception handing done
		// http://localhost:8000/order/search/NR
		public List<Object> getByCustomerName(String customerName){
			
			  List<Object> existCustomerName = orderRepository.findByCustomerName(customerName);
			  if(existCustomerName.size()==0) {
				  throw new CustomerNameNotFoundExdeption("No Name Found");
			  }
			
			  return existCustomerName;
			
		}

	

	@Override
	public List<OrderDetails> getAllOrderDetails() {
		// TODO Auto-generated method stub
		List<OrderDetails> allProduct = (List<OrderDetails>) orderRepository.findAll();
		List<OrderDetails> aP = new ArrayList<>();
		for (OrderDetails productList : allProduct) {
			
			if (productList.getIsActive() == true) {
				aP.add(productList);
			}

		}
		return aP;
	}
	
	// get excel
	@Override
	public List<OrderDetails> getAllOrderDetailsInExcelAndPdf() {
		// TODO Auto-generated method stub
		return (List<OrderDetails>) orderRepository.findAll();
	}
	
	// get by brand name 
	//Get Data By Brand Name logic
		@Override
		public List<OrderDetails> getBybrand(String brand) {
		
			 List<OrderDetails> findBybrand = orderRepository.findBybrand(brand);
			 if(findBybrand.size()==0) {
				 throw new NoBrandExistExdeption("No Brand Found");
			 }
			 return findBybrand;
		}
		
		
		//Get Data By Customer Name logic
		@Override
		public List<OrderDetails> getOrderNameByCustomerName(String customerName) {
			// TODO Auto-generated method stub
			 List<OrderDetails> existCustomerName = orderRepository.findOrderNameByCustomerName(customerName);
			 
			 if(existCustomerName.size()==0) {
				 throw new CustomerNameNotFoundExdeption("No Name Found");
			 }
			 return existCustomerName;
		}

	

	
	//exception handle
	@Override
	public OrderDetails getOneOrderDetails(Integer id) {
		// TODO Auto-generated method stub
		/*
		 * Optional<OrderDetails> opt = orderRepository.findById(id);
		 * if(opt.isPresent()) { OrderDetails od = opt.get(); return od; }else { throw
		 * new OrderNotFoundException("Order Details "+id+"  Not Exist"); }
		 * 
		 */
		return orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order Details " + id + "  Not Exist"));
	}

	// exception handle
	@Override
	public void deleteOrderDetails(Integer id) {
		orderRepository.delete(orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order Details " + id + "  Not Exist")));

	}

	
	//// exception handle
	@Override
	public void updateOrderDetails(OrderDetails orderDetails) {
		orderRepository.save(orderDetails);

	}

	@Override
	public boolean isOrderDetailsExist(Integer id) {
		// TODO Auto-generated method stub
		return orderRepository.existsById(id);
	}

	@Override
	public Integer disAbleProduct(Integer id) {
		// TODO Auto-generated method stub
			OrderDetails existId = orderRepository.findById(id).orElseThrow(()->new IdNotExistException("Id Not Exist Exception"));
	
			existId.setIsActive(false);
			return orderRepository.save(existId).getOrderId();
		
	}

	@Override
	public Integer enableAbleProduct(Integer id) {
		// TODO Auto-generated method stub
		OrderDetails existId = orderRepository.findById(id).orElseThrow(()->new IdNotExistException("Id Not Exist Exception"));
		existId.setIsActive(true);

		return orderRepository.save(existId).getOrderId();

	}
	

	
		

}
