package com.luispiquinrey.KnotCommerce.Servlet;

import org.junit.Test;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.junit.jupiter.api.Assertions;

public class ServletTest {
    @Test
    void starterServlet() throws Exception {
        AbstractServletWebServerFactory factory = getFactory();
        factory.setPort(8080);
        var webServer = factory.getWebServer();
        webServer.start();

        var tomcat = ((org.springframework.boot.web.embedded.tomcat.TomcatWebServer) webServer).getTomcat();
        var host = tomcat.getHost();
        Assertions.assertNotNull(host, "El host de Tomcat no debe ser nulo");
        webServer.stop();
    }

    private AbstractServletWebServerFactory getFactory() {
        return new org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory();
    }
}
