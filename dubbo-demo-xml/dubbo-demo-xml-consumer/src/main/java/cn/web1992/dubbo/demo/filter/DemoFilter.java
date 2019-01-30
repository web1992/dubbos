package cn.web1992.dubbo.demo.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.*;

/**
 * desc: 文件注释
 * <p>
 * Version		1.0.0
 *
 * @author web1992
 * <p>
 * Date	      2019/1/30 17:18
 */
// group = "consumer" 是必须的，否则就不会被加载，这个用来表示，这个 Filter 对客户端的请求进行过滤
// 如果 group = "provider"，这个 Filter 只有在服务端才会被使用
@Activate(group = "consumer")
public class DemoFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("DemoFilter#invoke before filter ...");
        Result result = invoker.invoke(invocation);
        logger.info("DemoFilter#invoke after filter ...");
        return result;
    }
}
