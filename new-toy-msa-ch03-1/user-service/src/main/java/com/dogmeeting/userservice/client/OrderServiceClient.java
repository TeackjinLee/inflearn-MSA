package com.dogmeeting.userservice.client;

import com.dogmeeting.userservice.error.FeignErrorDecoder;
import com.dogmeeting.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service", configuration = FeignErrorDecoder.class)
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders-ng")
    List<ResponseOrder> getOrders(@PathVariable String userId);


}
