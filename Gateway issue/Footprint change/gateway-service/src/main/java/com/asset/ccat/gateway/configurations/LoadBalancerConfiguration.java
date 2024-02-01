///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.asset.ccat.gateway.configurations;
//
//import com.asset.ccat.gateway.logger.CCATLogger;
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.DefaultServiceInstance;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import reactor.core.publisher.Flux;
//
///**
// *
// * @author Mahmoud Shehab
// */
//@Configuration
//public class LoadBalancerConfiguration {
//
//    @Autowired
//    Properties properties;
//
//    @Bean
//    @Primary
//    ServiceInstanceListSupplier serviceInstanceListSupplier() {
//        return new IMServiceInstanceListSuppler("user-management", properties);
//    }
//
//    class IMServiceInstanceListSuppler implements ServiceInstanceListSupplier {
//
//        private final String serviceId;
//
//        //@Value("#{'${api.endpoints}'.split(',')}")
//        //private List<String> endpoints;
//
//        private final Properties properties;
//
//        private List<ServiceInstance> instances;
//
//        IMServiceInstanceListSuppler(String serviceId, Properties properties) {
//            this.serviceId = serviceId;
//            this.properties = properties;
//        }
//
//        @PostConstruct
//        void buildServiceInstance() {
//            instances = new ArrayList<>();
//            Integer sid = 0;
//            String endpoints2[] = properties.getUserManagementUrls().split(",");
//            for (String url : endpoints2) {
//                sid++;
//                String[] hostAndPort = url.split(":");
//                Integer port = Integer.parseInt(hostAndPort[1]);
//                instances.add(new DefaultServiceInstance(serviceId + sid, serviceId, hostAndPort[0], port, false));
//                CCATLogger.info(" ----------------- Added Host --------------------   " + url);
//            }
//        }
//
//        @Override
//        public String getServiceId() {
//            return serviceId;
//        }
//
//        @Override
//        public Flux<List<ServiceInstance>> get() {
//            return Flux.just(instances);
//        }
//
//    }
//}
