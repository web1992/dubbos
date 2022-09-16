package cn.web1992.spring.boot.provider.dubboext.listenter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate
public class TestExporterListener implements ExporterListener {

    private static final Logger log = LoggerFactory.getLogger(TestExporterListener.class);


    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        System.out.println("TestExporterListener");
    }

    @Override
    public void unexported(Exporter<?> exporter) {

    }


}
