/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @className HeaderRestTemplateAutoConfiguration
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/10/18 14:39
 * @version v1.0
 */
public class HeaderRestTemplateAutoConfiguration {

    @Bean
    public HeaderRestTemplateInterceptor headerRestTemplateInterceptor() {
        return new HeaderRestTemplateInterceptor();
    }

    @Autowired(required = false)
    private Collection<RestTemplate> restTemplates;

    @Autowired
    private HeaderRestTemplateInterceptor headerRestTemplateInterceptor;

    @PostConstruct
    public void init() {
        if (this.restTemplates != null) {
            for (RestTemplate restTemplate : restTemplates) {
                List<ClientHttpRequestInterceptor> interceptors =
                        new ArrayList<ClientHttpRequestInterceptor>(restTemplate.getInterceptors());
                interceptors.add(this.headerRestTemplateInterceptor);
                restTemplate.setInterceptors(interceptors);
            }
        }
    }

}
