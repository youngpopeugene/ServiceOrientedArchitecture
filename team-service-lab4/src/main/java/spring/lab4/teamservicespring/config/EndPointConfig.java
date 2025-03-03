package spring.lab4.teamservicespring.config;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import spring.lab4.teamservicespring.service.TeamServiceImpl;

@Configuration
public class EndPointConfig extends WsConfigurerAdapter {
    private final Bus bus;

    @Autowired
    public EndPointConfig(Bus bus) {
        this.bus = bus;
    }

    @Bean
    public Endpoint endpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, new TeamServiceImpl());
        endpoint.publish("/team");
        endpoint.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
        return endpoint;
    }
}
