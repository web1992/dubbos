package cn.web1992.spring.boot.consumer.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.dubbo.rpc.RpcContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ApiClazzRegisterUtils {

    public static final String Underscore = "_";
    public static final String COLON = ":";
    public static final String SERVICE_NAME = "mit";
    public static final String SERVICE_IP = "10.0.44.26";
    public static final int SERVICE_PORT = 8848;

    public static final String SERVICE_API_CLASS_INFO = "service_api_class_info";

    public static final double DEFAULT_WEIGHT = 1.0;

    public static RegisterClazzInfo getRegisterClazzInfo(String name) throws Exception {
        NamingService naming = getNamingService();
        List<Instance> instanceList = naming.selectInstances(name, getServiceGroupName(), false);
//        if (null == instanceList || instanceList.isEmpty()) {
//            return null;
//        }
        Instance instance = naming.selectOneHealthyInstance(name, getServiceGroupName());

        Map<String, String> metadata = instance.getMetadata();

        if (metadata.isEmpty()) {
            return null;
        }

        String s = metadata.get(SERVICE_API_CLASS_INFO);

        return JSON.parseObject(s, RegisterClazzInfo.class);
    }

    public static void registerApiInfo(Class<?> clazz) throws Exception {


        RegisterClazzInfo clazzInfo = RegisterUtils.genClazzInfo(clazz);

        NamingService naming = getNamingService();
        String serviceName = getServiceName(clazzInfo);
        String groupName = getServiceGroupName();
        naming.registerInstance(serviceName, groupName, buildInstance(clazzInfo));

    }

    public static void unregisterApiInfo(Class<?> clazz) throws NacosException {

        RegisterClazzInfo clazzInfo = RegisterUtils.genClazzInfo(clazz);
        NamingService naming = getNamingService();
        naming.deregisterInstance(getServiceName(clazzInfo), buildInstance(clazzInfo));

    }

    private static Instance buildInstance(RegisterClazzInfo clazzInfo) {
        String localAddressString = RpcContext.getServerContext().getLocalAddressString();
        int localPort = RpcContext.getServerContext().getLocalPort();

        Instance instance = new Instance();
        instance.setEphemeral(false);
        instance.setIp(localAddressString);
        instance.setPort(localPort);
        instance.setHealthy(true);
        instance.setWeight(DEFAULT_WEIGHT);
        instance.setServiceName(getServiceName(clazzInfo));
        Map<String, String> instanceMeta = new HashMap<>();
        instanceMeta.put(SERVICE_API_CLASS_INFO, JSON.toJSONString(clazzInfo));
        instance.setMetadata(instanceMeta);
        instance.setInstanceId(getInstanceId());

        return instance;
    }

    public static String getInstanceId() {
        String localAddressString = RpcContext.getServerContext().getLocalAddressString();
        int localPort = RpcContext.getServerContext().getLocalPort();
        return localAddressString + COLON
                + localPort;
    }

    private static String getServiceName(RegisterClazzInfo clazzInfo) {
        return clazzInfo.getClassName();
    }

    private static String getServiceGroupName() {
        return "demo111";
    }

    public static NamingService getNamingService() throws NacosException {
        return NamingFactory.createNamingService(getServeAddr());
    }

    private static String getServeAddr() {
        return SERVICE_IP + COLON + SERVICE_PORT;
    }

}
