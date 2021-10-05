package com.manager.password.storage.diskmap;

import com.manager.password.storage.diskmap.utils.DefaultObjectConverter;
import com.manager.password.storage.diskmap.utils.ObjectConverter;

import java.io.Serializable;

public class ConversionUtils {

    public static final ConversionUtils instance = new ConversionUtils();
    private ObjectConverter os;

    public ConversionUtils() {
        try {
            os = new DefaultObjectConverter();
        } catch (Exception e) {
            System.err.println("Unable to create hessian object convertor, using the default convertor.");
            os = new DefaultObjectConverter();
        }
    }

    public byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[3 - i] = (byte) (n >>> (i * 8));
        }
        return b;
    }

    public byte[] longToBytes(long n) {
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[b.length - 1 - i] = (byte) (n >>> (i * 8));
        }
        return b;
    }

    public int byteToInt(byte[] b) {
        return byteToInt(b, 0);
    }

    public int byteToInt(byte[] b, int offset) {
        int n = 0;
        for (int i = offset; i < offset + 4; i++) {
            n <<= 8;
            n ^= (int) b[i] & 0xFF;
        }
        return n;
    }

    public long byteToLong(byte[] b) {
        long n = 0;
        for (int i = 0; i < 8; i++) {
            n <<= 8;
            n ^= (long) b[i] & 0xFF;
        }
        return n;
    }

    public byte[] serialize(Serializable object) throws Exception {
        return os.serialize(object);
    }

    public <T> T deserialize(byte[] buffer) {
        return (T) os.deserialize(buffer);
    }

    public byte[] shortToBytes(int n) {
        return shortToBytes((short) n);
    }

    public byte[] shortToBytes(short n) {
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[1 - i] = (byte) (n >>> (i * 8));
        }
        return b;
    }

    public short byteToShort(byte[] b) {
        return byteToShort(b, 0);
    }

    public short byteToShort(byte[] b, int offset) {
        short n = 0;
        for (int i = offset; i < offset + 2; i++) {
            n <<= 8;
            n ^= (int) b[i] & 0xFF;
        }
        return n;
    }


}
