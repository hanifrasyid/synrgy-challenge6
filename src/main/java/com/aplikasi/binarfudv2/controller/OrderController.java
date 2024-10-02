package com.aplikasi.binarfudv2.controller;

import com.aplikasi.binarfudv2.entity.Customer;
import com.aplikasi.binarfudv2.entity.Merchant;
import com.aplikasi.binarfudv2.entity.Order;
import com.aplikasi.binarfudv2.repo.OrderRepo;
import com.aplikasi.binarfudv2.service.InvoiceService;
import com.aplikasi.binarfudv2.service.OrderService;
import com.aplikasi.binarfudv2.util.SimpleStringUtil;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @Autowired
    public OrderRepo orderRepo;

    @Autowired
    public TemplateResponse response;

    @PostMapping(value ={"/createOrder","/createOrder/"})
    public ResponseEntity<Map> createOrder(@RequestBody Order order) {
        try {
            return new ResponseEntity<Map>(orderService.createOrder(order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value ={"/getOrder","/getOrder/"})
    public List<Order> getOrder() {
        return orderService.getOrder();
    }
    @GetMapping(value={"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Map>(orderService.getByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value={"/{CustomerId}", "/{CustomerId}/"})
    public ResponseEntity<Map> getByCustomerId(@PathVariable("CustomerId") Long CustomerId) {
        try {
            return new ResponseEntity<Map>(orderService.getByCustomerId(CustomerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = {"/listOrder", "/listOrder/"})
    public ResponseEntity<Map> list(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String destination_address,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        try {
            Pageable show_data = simpleStringUtil.getShort(orderby, ordertype, page, size);

            Specification<Order> spec =
                    ((root, query, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (destination_address != null && !destination_address.isEmpty()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("destination_address")), "%" + destination_address.toLowerCase() + "%"));
                        }
                        if (completed != null) {
                            predicates.add(criteriaBuilder.equal(root.get("completed"), completed));
                        }
                        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                    });
            Page<Order> list = orderRepo.findAll(spec, show_data);

            Map map = new HashMap();
            map.put("data",list);
            return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    //invoice service
    @PostMapping(value ={"/generateInvoice","/generateInvoice/"})
    public ResponseEntity<Map> generateInvoice(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<Map>(invoiceService.generateInvoice(customer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping(value ={"/generateReport","/generateReport/"})
    public ResponseEntity<Map> generateReport(@RequestBody Merchant merchant) {
        try {
            return new ResponseEntity<Map>(invoiceService.generateReport(merchant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}