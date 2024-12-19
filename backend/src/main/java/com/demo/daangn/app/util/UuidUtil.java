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
        String[] uuidArr = {
            "51a8de11-414b-4d64-b25f-82fe73870428",
            "656a5af1-60de-4f79-b949-b8cf019fdf92"
        };

        for(String uuidStr : uuidArr) {
            UUID uuid = UUID.fromString(uuidStr);
            System.out.println("Original UUID: " + uuid);

            String base62 = encrypt(uuid);
            System.out.println("Encoded UUID: " + base62);
            System.out.println("Decoded UUID: " + decrypt(base62));
            System.out.println();
        }
    }
}
