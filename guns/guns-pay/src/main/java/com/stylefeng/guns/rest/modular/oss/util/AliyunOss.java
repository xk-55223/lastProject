package com.stylefeng.guns.rest.modular.oss.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.stylefeng.guns.rest.modular.oss.config.propertise.AliyunProperties;
import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

@Component
public class AliyunOss {
    @Autowired
    private OSSClient ossClient;

    @Autowired
    private AliyunProperties aliyunProperties;


    public String upload(InputStream is, String path, String contentType) throws URISyntaxException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        ossClient.putObject(aliyunProperties.getOss().getBucket().getName(), path, is, metadata);
        URL url = generateUrl(path);
        URI uri = url.toURI();
        HttpHost httpHost = new HttpHost(aliyunProperties.getOss().getImg().getDomain());
        URI mappingURI = URIUtils.rewriteURI(uri, httpHost);
        return mappingURI.toString();
    }

    private URL generateUrl(String path) {
        /*url过期时间*/
        Long duration = aliyunProperties.getOss().getUrl().getDuration();
        long time = System.currentTimeMillis() + duration;
        Date expiration = new Date(time);
        return ossClient.generatePresignedUrl(aliyunProperties.getOss().getBucket().getName(), path, expiration);
    }
}
