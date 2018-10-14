package com.weikun.server.controller;

import com.weikun.server.model.Account;
import com.weikun.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * �����ߣ�weikun��YST��   ���ڣ�2018/10/14
 * ˵˵���ܣ�
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/acc"},name="�˻�����")
@Api(value = "acc",description = "�˻��������ݽӿ�")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @ApiOperation(value = "loginAccount",notes = "��¼�˻���Ϣ")
    @ApiResponses(value = {
            @ApiResponse(code=404,message = "���ش���"),
            @ApiResponse(code=200,message = "���سɹ�")
    })
    @PostMapping(value = {"/login"})
    public ResponseEntity<Account> login(@RequestBody Account account){
        System.out.println(account.getUsername());
        Map<String,String> map=new HashMap<String,String>();
        map.put("username",account.getUsername());
        map.put("password",account.getPassword());
        Account a=service.login(map);
        if(a==null){
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<Account>(a,HttpStatus.OK);
        }
    }

}
