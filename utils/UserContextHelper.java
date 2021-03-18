import com.alibaba.dubbo.rpc.Invocation;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author web1992
 * @date 2021/1/19  下午7:27
 */
public abstract class UserContextHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserContextHelper.class);

    public static void setUserContext(Invocation invocation) {

        Object[] arguments = invocation.getArguments();
        Class<?>[] argumentsClazz = invocation.getParameterTypes();

        Class<?> anInterface = invocation.getInvoker().getInterface();
        String methodName = invocation.getMethodName();

        Map<String, Object> paramMap = new HashMap<>();
        try {

            Method method = anInterface.getMethod(methodName, argumentsClazz);
            Parameter[] parameters = method.getParameters();

            HttpApi httpApi = method.getAnnotation(HttpApi.class);

            if (null == httpApi) {
                logger.info("UserContextHelper#param  httpApi is null");
                return;
            }

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                ApiAutowired apiAutowired = parameter.getAnnotation(ApiAutowired.class);

                if (null != apiAutowired) {
                    if (CommonParameter.applicationId.equals(apiAutowired.value())) {
                        paramMap.put(CommonParameter.applicationId, arguments[i]);
                        continue;
                    }

                    if (CommonParameter.domainId.equals(apiAutowired.value())) {
                        paramMap.put(CommonParameter.domainId, arguments[i]);
                        continue;
                    }

                    if (CommonParameter.deviceId.equals(apiAutowired.value())) {
                        paramMap.put(CommonParameter.deviceId, arguments[i]);
                        continue;
                    }

                    if (CommonParameter.orgId.equals(apiAutowired.value())) {
                        paramMap.put(CommonParameter.orgId, arguments[i]);
                        continue;
                    }

                    if (CommonParameter.userId.equals(apiAutowired.value())) {
                        paramMap.put(CommonParameter.userId, arguments[i]);
                        continue;
                    }

                    if (CommonParameter.tenantId.equals(apiAutowired.value())) {
                        paramMap.put(CommonParameter.tenantId, arguments[i]);
                        continue;
                    }
                }

            }

            UserContextUtils.set(paramMap);

            logger.info("UserContextHelper#param {}", paramMap);

        } catch (Exception e) {
            logger.error("UserContextHelper#put ex", e);
        }
    }

}
