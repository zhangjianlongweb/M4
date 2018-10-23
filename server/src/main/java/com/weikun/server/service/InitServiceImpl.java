package com.weikun.server.service;

import com.weikun.server.mapper.*;
import com.weikun.server.model.*;
import com.weikun.server.redis.dao.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * �����ߣ�weikun��YST��   ���ڣ�2018/10/21
 * ˵˵���ܣ�
 */
@Service
public class InitServiceImpl {
    @Autowired
    private RedisMapper rdao;

    @Autowired
    private CategoryMapper cdao;

    @Autowired
    private AccountMapper adao;

    @Autowired
    private ProfileMapper pdao;

    @Autowired
    private ProductMapper prdao;

    @Autowired
    private ItemMapper idao;

    public void flushdb(){
        List list=new ArrayList();
        rdao.executeRedisByLua(list,"flushDB.lua");
    }
    public void init(){
        flushdb();
        this.initAccount();
        this.initProfile();
        this.initCategory();
        this.initProduct();
        this.initItem();


    }
    private void initAccount(){
        AccountExample e=new AccountExample();
        e.createCriteria().andUseridIsNotNull();

        List<Account> list=adao.selectByExample(e);

        list.forEach(c->rdao.setHashTable("account",c.getUsername(),c));

    }
    private void initItem(){
        ItemExample item=new ItemExample();
        item.createCriteria().andItemidIsNotNull();
        List<Item> list=idao.selectByExample(item);

        list.forEach(c->rdao.setSet("item:"+c.getProductid()+":"+c.getItemid(),c));;//productid:itemid
    }
    private void initProduct(){
        ProductExample pro=new ProductExample();
        pro.createCriteria().andProductidIsNotNull();
        List<Product> list=prdao.selectByExample(pro);

        list.forEach(c->rdao.setSet("product:"+c.getCatid()+":"+c.getProductid(),c));//product:catid:productid
    }
    private void initCategory(){

        CategoryExample ca=new CategoryExample();
        ca.createCriteria().andCatidIsNotNull();

        List<Category> list=cdao.selectByExample(ca);
        list.forEach(c->rdao.setHashTable("category",c.getCatid().toString(),c));


    }
    private void initProfile(){
        ProfileExample e=new ProfileExample();
        e.createCriteria().andUseridIsNotNull();

        List<Profile> list=pdao.selectByExample(e);

        list.forEach(c->rdao.setHashTable("profile",c.getUserid().toString(),c));

    }

}
