package com.weikun.server.service;

import com.weikun.server.mapper.CartMapper;
import com.weikun.server.mapper.CategoryMapper;
import com.weikun.server.mapper.OrdersMapper;
import com.weikun.server.model.Cart;
import com.weikun.server.model.Orders;
import com.weikun.server.redis.dao.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * �����ߣ�weikun��YST��   ���ڣ�2018/10/28
 * ˵˵���ܣ�
 */
@Service
public class CartServiceImpl {

    @Autowired
    private CartMapper cdao;

    @Autowired
    private OrdersMapper odao;
    @Autowired
    private RedisMapper rdao;

    public void addCart(Cart cart){
        boolean iu=true;//i true update false
        //ȡ��orderid
        List list=new ArrayList();
        list.add(cart.getUserid()+"");//1userid
        List result=rdao.executeRedisByLua(list,"getMaxOrderidByUserid.lua");

        //�洢mysql
        String sid=result.get(0).toString().substring(1);
        String flag=result.get(0).toString().substring(0,1);
        int id=Integer.parseInt(sid);

        if(flag.equals("0")){//���Լ�������
            cart.setOrderid(id);
        //��Ҫ���� ���û� ���� ��Ŀ��ͬ ��������
            // ������������
            //���� �û�id ����id itemid�����Ƿ����������

            list.clear();
            list.add("carts:"+cart.getUserid()+":"+cart.getOrderid()+":"+cart.getItemid());

            List l=rdao.executeRedisByLua(list,"getQuantity.lua");
            if(l.get(0)!=null){//����Ʒ ����
                cart.setQuantity(Integer.parseInt(l.get(0).toString())+cart.getQuantity());
                iu=false;
            }


        }else{
            ++id;
            cart.setOrderid(id);

            //����orders ������������orderid
            Orders os=new Orders();

            os.setOrderid(id);
            os.setUserid(cart.getUserid());
            odao.insert(os);
        }

        if(!iu){//��Ϊ�Ѿ��е�ǰ��Ʒ��
            cdao.updateByPrimaryKey(cart);
        }else{
            cdao.insert(cart);
        }

        list.clear();
        //�洢redis cart
        list.add(cart.getUserid()+"");//1userid
        list.add(cart.getOrderid()+"");//2orderid
        list.add(cart.getItemid()+"");//3itemid
        list.add(cart.getQuantity()+"");//4quantity �Ѿ���������
        //01 11
        String str= result.get(0).toString();
        String f=str.substring(1);//ȡ�������Ƿ��Ѿ��ύ�ı�־λ 0���������� 1���Ѿ���Ǯ��
        list.add(f+"");
        rdao.executeRedisByLua(list,"addCart.lua");

        //�洢redis orders
        if(flag.equals("1")){//�¶���
            list.clear();
            list.add(cart.getUserid()+"");//1userid
            list.add(cart.getOrderid()+"");//2orderid

            list.add(flag+id);//3 maxid�ı��
            list.add(flag+id);//4 maxid�ı��
            rdao.executeRedisByLua(list,"addOrders.lua");
        }

        //01 11






    }


}
