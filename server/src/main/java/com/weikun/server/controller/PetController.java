package com.weikun.server.controller;

import com.weikun.server.model.Account;
import com.weikun.server.model.Category;
import com.weikun.server.service.PetServiceImpl;
import com.weikun.server.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * �����ߣ�weikun��YST��   ���ڣ�2018/10/21
 * ˵˵���ܣ�
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/pet"},name="�������")
@Api(value = "pet",description = "����������ݽӿ�")
public class PetController {

    @Autowired
    private PetServiceImpl service;

    @ApiResponses(value = {
            @ApiResponse(code=404,message = "��ѯ����"),
            @ApiResponse(code=200,message = "��ѯ�ɹ�")
    })
    @GetMapping(value = {"/qc"})
    public ResponseEntity<List> qc(){
        List<Category> list=service.queryCategory();
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }


}
