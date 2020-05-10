package com.wemedia.config;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * MD5加密测试
 */
public class Test {

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 1;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
        System.out.println(obj);
    }
    /*public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 2;
        ByteSource credentialsSalt = ByteSource.Util.bytes("user");
        Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        System.out.println(obj);
    }*/
}
