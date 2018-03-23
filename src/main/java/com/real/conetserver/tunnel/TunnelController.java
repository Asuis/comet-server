package com.real.conetserver.tunnel;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.tunnel.TunnelHandleOptions;
import com.qcloud.weapp.tunnel.TunnelService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author asuis
 */
@Controller
@Api("小程序连接信道接口")
@Slf4j
public class TunnelController {
    @Autowired
    TunnelHandler tunnelHandler;
    @RequestMapping("/wx/tunnel")
    public void tunnel(HttpServletRequest request, HttpServletResponse response) {
        TunnelService tunnelService = new TunnelService(request,response);
        TunnelHandleOptions options = new TunnelHandleOptions();
        options.setCheckLogin(true);
        try {
            tunnelService.handle(tunnelHandler,options);
        } catch (ConfigurationException e) {
            log.warn("tunnel config error",e.getMessage());
        }
    }
}