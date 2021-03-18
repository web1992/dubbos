
import java.util.HashMap;
import java.util.Map;

/**
 * @author web1992
 * @date 2021/1/19  下午7:17
 */
public class UserContextUtils {

    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    @SuppressWarnings("unchecked")
    public static <V> V get(String key) {
        // get
        return (V) threadLocal.get().get(key);
    }

    public static void set(Map<String, Object> map) {
        // clear
        threadLocal.get().clear();
        // put
        threadLocal.get().putAll(map);
    }


    public static int getAppId() {
        return get(CommonParameter.applicationId);
    }

    public static int getDomainId() {
        return get(CommonParameter.domainId);

    }

    public static long getDeviceId() {
        return get(CommonParameter.deviceId);
    }

    public static long getOrgId() {
        return get(CommonParameter.orgId);
    }

    public static long getUserId() {
        return get(CommonParameter.userId);
    }

    public static long getTenantId() {
        return get(CommonParameter.tenantId);
    }


}
