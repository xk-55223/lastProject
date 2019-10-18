package com.stylefeng.guns.rest.modular.pay.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {
    private Qrcore qrcore;

    public Qrcore getQr() {
        return qrcore;
    }

    public void setQr(Qrcore qr) {
        this.qrcore = qr;
    }

    public static class Qrcore {
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
