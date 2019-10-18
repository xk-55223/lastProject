package com.stylefeng.guns.rest.modular.pay.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {
    private Qr qr;

    public Qr getQr() {
        return qr;
    }

    public void setQr(Qr qr) {
        this.qr = qr;
    }

    public static class Qr {
        private String localAddress;
        private String address;

        public String getLocalAddress() {
            return localAddress;
        }

        public void setLocalAddress(String localAddress) {
            this.localAddress = localAddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
