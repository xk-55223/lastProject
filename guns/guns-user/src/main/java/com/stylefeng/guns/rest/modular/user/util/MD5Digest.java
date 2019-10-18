package com.stylefeng.guns.rest.modular.user.util;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import org.jboss.netty.util.internal.StringUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Digest {
    private final static Log log = LogFactory.getLog(StringUtil.class);
    private static MessageDigest digest = null;

    public static String encode(String password) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                log.error("Failed to load the MD5 MessageDigest, We will be unable to function normally.");
                e.printStackTrace();
            }
        }
        digest.update(password.getBytes());
        return encodeHex(digest.digest());
    }

    private static String encodeHex(byte[] digest) {
        StringBuffer stringBuffer = new StringBuffer(digest.length * 2);
        for (int i = 0; i < digest.length; i++) {
            if ((digest[i] & 0Xff) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(digest[i] & 0xff, 16));
        }
        return stringBuffer.toString().toUpperCase();
    }
}
