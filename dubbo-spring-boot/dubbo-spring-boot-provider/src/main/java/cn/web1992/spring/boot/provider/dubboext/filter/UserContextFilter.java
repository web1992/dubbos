package cn.web1992.spring.boot.provider.dubboext.filter;


import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * desc: 文件注释
 * <p>
 * Version		1.0.0
 *
 * @author erbao.wang
 * <p>
 * Date	      2021/1/19 17:18
 */
// group = "consumer" 是必须的，否则就不会被加载，这个用来表示，这个 Filter 对客户端的请求进行过滤
// 如果 group = "provider"，这个 Filter 只有在服务端才会被使用
@Activate(group = {"provider", "consumer"})
public class UserContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);


    public UserContextFilter() {
        logger.info("UserContextFilter init");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        System.out.println("UserContextFilter");
        return invoker.invoke(invocation);
    }


}


