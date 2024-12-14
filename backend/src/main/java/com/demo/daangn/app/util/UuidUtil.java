package com.demo.daangn.app.util;

import java.util.UUID;

import org.unbrokendome.base62.Base62;


public class UuidUtil {

    public static String encrypt(UUID uuid) {
        return Base62.encodeUUID(uuid);
    }

    public static UUID decrypt(String fromUuid) {
        return Base62.decodeUUID(fromUuid);
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println("Original UUID: " + uuid);

        String base62 = encrypt(uuid);
        System.out.println("Encoded UUID: " + base62);
        System.out.println("Decoded UUID: " + decrypt(base62));
    }
}
