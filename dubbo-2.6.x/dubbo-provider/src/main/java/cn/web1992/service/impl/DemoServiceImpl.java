package cn.web1992.service.impl;

import cn.web1992.api.DemoService;
import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String name) {
        System.out.println("hello from " + RpcContext.getContext().getRemoteHost());
        return name + " hello. from " + RpcContext.getContext().getLocalHost();
    }
}
