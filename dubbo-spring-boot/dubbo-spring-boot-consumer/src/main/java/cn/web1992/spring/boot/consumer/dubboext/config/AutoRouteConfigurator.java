package cn.web1992.spring.boot.consumer.dubboext.config;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.cluster.configurator.AbstractConfigurator;

/**
 * Configurator 2.7.x 扩展无法加载，可能是Bug
 */
public class AutoRouteConfigurator extends AbstractConfigurator {

    public static final String VERSION_KEY = "version";
    public static final String REVERSION_KEY = "revision";
    public static final String TAG_KEY = "tag";
    public static final String TAG_LOCAL = "local";

    public AutoRouteConfigurator(URL url) {
        super(url);
    }

    @Override
    public URL doConfigure(URL currentUrl, URL configUrl) {
        currentUrl = currentUrl.removeParameter(TAG_KEY);
        currentUrl = currentUrl.addParameter(TAG_KEY, "testxxxx");
        return currentUrl;
    }
}
