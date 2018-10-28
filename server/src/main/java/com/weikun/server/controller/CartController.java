package com.weikun.server.controller;

import com.weikun.server.model.Cart;
import com.weikun.server.service.CartServiceImpl;
import com.weikun.server.service.PetServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * �����ߣ�weikun��YST��   ���ڣ�2018/10/28
 * ˵˵���ܣ�
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/cart"},name="���ﳵ����")
@Api(value = "cart",description = "���ﳵ�������ݽӿ�")
public class CartController {

    @Autowired
    private CartServiceImpl service;

    @ApiResponses(value = {
            @ApiResponse(code=404,message = "���Ӵ���"),
            @ApiResponse(code=200,message = "���ӳɹ�")
    })
    @PostMapping(value = {"/add"})
    public ResponseEntity<List> addCart(@RequestBody Cart cart){
        service.addCart(cart);

        return null;

    }

}
