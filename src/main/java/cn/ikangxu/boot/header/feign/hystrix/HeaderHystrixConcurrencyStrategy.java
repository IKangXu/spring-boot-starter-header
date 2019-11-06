/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.feign.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.RootHeader;
import cn.ikangxu.boot.header.util.CookieUtils;
import cn.ikangxu.boot.header.util.WebUtils;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @className HeaderHystrixConcurrencyStrategy
 * @description 
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/6/28 8:32
 * @version v1.0
 */
public class HeaderHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy delegate;

    public HeaderHystrixConcurrencyStrategy() {
        this.delegate = HystrixPlugins.getInstance().getConcurrencyStrategy();
        HystrixPlugins.reset();
        HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
    }

    @Override
    public <K> Callable<K> wrapCallable(Callable<K> c) {
        if (c instanceof HeaderContextCallable) {
            return c;
        }

        Callable<K> wrappedCallable;
        if (this.delegate != null) {
            wrappedCallable = this.delegate.wrapCallable(c);
        } else {
            wrappedCallable = c;
        }
        if (wrappedCallable instanceof HeaderContextCallable) {
            return wrappedCallable;
        }

        return new HeaderContextCallable<>(wrappedCallable);
    }

    private static class HeaderContextCallable<K> implements Callable<K> {

        private final Callable<K> actual;

        HeaderContextCallable(Callable<K> actual) {
            this.actual = actual;
        }

        @Override
        public K call() throws Exception {
            try {

                String[] keys = RootAttribute.list();
                if(null == keys || keys.length == 0) {
                    return actual.call();
                }

                HttpServletRequest servletRequest = WebUtils.getRequest();

                for(int i= 0; i < keys.length; i++) {
                    String key = keys[i];

                    String val = RootHeader.get(key);

                    if(!StringUtils.isEmpty(val)) {
                        RootHeader.bind(key, val);
                    }
                }

                return actual.call();
            } finally {

            }
        }

    }
}
