package com.aplikasi.binarfudv2.controller;

import com.aplikasi.binarfudv2.entity.OrderDetail;
import com.aplikasi.binarfudv2.repo.OrderDetailRepo;
import com.aplikasi.binarfudv2.service.OrderDetailService;
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
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    public TemplateResponse response;

    @Autowired
    public OrderDetailRepo orderDetailRepo;

    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @PostMapping(value ={"/add","/add/"})
    public ResponseEntity<Map> addOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            return new ResponseEntity<Map>(orderDetailService.addOrderDetail(orderDetail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(value ={"/update","/update/"})
    public ResponseEntity<Map> updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            return new ResponseEntity<Map>(orderDetailService.updateOrderDetail(orderDetail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @DeleteMapping(value ={"/delete","/delete/"})
    public ResponseEntity<Map> deleteOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            return new ResponseEntity<Map>(orderDetailService.deleteOrderDetail(orderDetail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
    @GetMapping(value={"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Map>(orderDetailService.getByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
    @GetMapping(value = {"/listOrderDetail", "/listOrderDetail/"})
    public ResponseEntity<Map> list(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String total_price,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        try {
            Pageable show_data = simpleStringUtil.getShort(orderby, ordertype, page, size);

            Specification<OrderDetail> spec =
                    ((root, query, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (quantity != null && !quantity.isEmpty()) {
                            predicates.add(criteriaBuilder.equal(root.get("quantity"), quantity));
                        }
                        if (total_price != null && !total_price.isEmpty()) {
                            predicates.add(criteriaBuilder.equal(root.get("total_price"), total_price));
                        }
                        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                    });

            Page<OrderDetail> list = orderDetailRepo.findAll(spec, show_data);

            Map map = new HashMap();
            map.put("data",list);
            return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}