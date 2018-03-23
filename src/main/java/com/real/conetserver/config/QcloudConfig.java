package com.real.conetserver.config;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author asuis
 * wafer-java-sdk配置类
 */
@Configuration
@Order(1)
public class QcloudConfig implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(QcloudConfig.class);

    @Value("${qcloud.serverHost}")
    private String serverHost;
    @Value("${qcloud.authServerUrl}")
    private String authServerUrl;
    @Value("${qcloud.tunnelServerUrl}")
    private String tunnelServerUrl;
    @Value("${qcloud.tunnelSignatureKey}")
    private String tunnelSignatureKey;
    @Value("${qcloud.networkTimeout}")
    private Integer networkTimeout;

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>>初始化加载sdk配置<<<<<<<<<<<<<");
        logger.debug(serverHost);
        com.qcloud.weapp.Configuration configuration = new com.qcloud.weapp.Configuration();
        // 业务服务器访问域名
        configuration.setServerHost(serverHost);
        // 鉴权服务地址
        configuration.setAuthServerUrl(authServerUrl);
        // 信道服务地址
        configuration.setTunnelServerUrl(tunnelServerUrl);
        // 信道服务签名 key
        configuration.setTunnelSignatureKey(tunnelSignatureKey);
        // 网络请求超时设置，单位为秒
        configuration.setNetworkTimeout(networkTimeout);

        try {
            ConfigurationManager.setup(configuration);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
