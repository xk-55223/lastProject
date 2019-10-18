package com.stylefeng.guns.rest.modular.oss.config.propertise;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {
    private Access access;
    private Oss oss;

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Oss getOss() {
        return oss;
    }

    public void setOss(Oss oss) {
        this.oss = oss;
    }

    public static class Access {
        private Key key;

        public Key getKey() {
            return key;
        }

        public void setKey(Key key) {
            this.key = key;
        }

        public static class Key {
            private String id;
            private String secret;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }
        }
    }

    public static class Oss {
        private String endpoint;
        private Bucket bucket;
        private Img img;
        private Oss.Url url;
        private Upload upload;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public Bucket getBucket() {
            return bucket;
        }

        public void setBucket(Bucket bucket) {
            this.bucket = bucket;
        }

        public Img getImg() {
            return img;
        }

        public void setImg(Img img) {
            this.img = img;
        }

        public Url getUrl() {
            return url;
        }

        public void setUrl(Url url) {
            this.url = url;
        }

        public Upload getUpload() {
            return upload;
        }

        public void setUpload(Upload upload) {
            this.upload = upload;
        }

        public static class Bucket {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class Img {
            private String domain;

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }
        }

        public static class Url {
            //默认一年过期
            private Long duration;

            public Long getDuration() {
                return duration;
            }

            public void setDuration(Long duration) {
                this.duration = duration;
            }
        }

        public static class Upload {
            private String contentType;

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }
        }
    }
}
