package com.aplikasi.binarfudv2.service.impl;

import com.aplikasi.binarfudv2.entity.OrderDetail;
import com.aplikasi.binarfudv2.repo.OrderDetailRepo;
import com.aplikasi.binarfudv2.service.OrderDetailService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private TemplateResponse response;

    @Override
    public Map addOrderDetail(OrderDetail orderDetail) {
        try {
            log.info("add order detail");
            return response.success(orderDetailRepo.save(orderDetail));
        } catch (Exception e){
            log.error("add order detail error: " + e.getMessage());
            return response.Error("add order detail = " + e.getMessage());
        }
    }

    @Override
    public Map updateOrderDetail(OrderDetail orderDetail) {
        try {
            log.info("update order detail");
            if (orderDetail.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<OrderDetail> checkDataDBOrderDetail = orderDetailRepo.findById(orderDetail.getId());
            if (!checkDataDBOrderDetail.isPresent()) {
                return response.Error(Config.ORDER_DETAIL_NOT_FOUND);
            }

            checkDataDBOrderDetail.get().setQuantity(orderDetail.getQuantity());
            checkDataDBOrderDetail.get().setTotal_price(orderDetail.getTotal_price());
            checkDataDBOrderDetail.get().setOrder(orderDetail.getOrder());
            checkDataDBOrderDetail.get().setProduct(orderDetail.getProduct());
            checkDataDBOrderDetail.get().setUpdated_date(new Date());

            return response.success(orderDetailRepo.save(checkDataDBOrderDetail.get()));
        } catch (Exception e){
            log.error("Update order detail error: " + e.getMessage());
            return response.Error("Update order detail = " + e.getMessage());
        }
    }

    @Override
    public Map deleteOrderDetail(OrderDetail orderDetail) {
        try {
            log.info("delete order Detail");
            if (orderDetail.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<OrderDetail> checkDataDBOrderDetail = orderDetailRepo.findById(orderDetail.getId());
            if (!checkDataDBOrderDetail.isPresent()) {
                return response.Error(Config.ORDER_DETAIL_NOT_FOUND);
            }
            checkDataDBOrderDetail.get().setDeleted_date(new Date());
            orderDetailRepo.save(checkDataDBOrderDetail.get());
            return response.success(Config.SUCCESS);
        } catch (Exception e){
            log.error("delete order detail error: " + e.getMessage());
            return response.Error("delete order detail = " + e.getMessage());
        }
    }

    @Override
    public Map getByID(Long orderDetail) {
        Optional<OrderDetail> getBaseOptional = orderDetailRepo.findById(orderDetail);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }
}