//package cn.hi028.android.highcommunity.utils.updateutil;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
///**
// * Created by Lee_yting on 2016/10/5.
// */
//public class MD5Utils {
//    protected static MessageDigest messagedigest = null;
//    static{
//        try{
//            messagedigest = MessageDigest.getInstance("MD5");
//        }catch(NoSuchAlgorithmException nsaex){
//            nsaex.printStackTrace();
//        }
//    }
//    public static String getFileMD5String(File file) throws IOException {
//        FileInputStream in = new FileInputStream(file);
//        FileChannel ch = in.getChannel();
//        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
//        messagedigest.update(byteBuffer);
//        return bufferToHex(messagedigest.digest());
//    }
//    private static String bufferToHex(byte bytes[]) {
//        return bufferToHex(bytes, 0, bytes.length);
//    }
//    private static String bufferToHex(byte bytes[], int m, int n) {
//        StringBuffer stringbuffer = new StringBuffer(2 * n);
//        int k = m + n;
//        for (int l = m; l < k; l++) {
//            appendHexPair(bytes[l], stringbuffer);
//        }
//        return stringbuffer.toString();
//    }
//    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
//        char c0 = hexDigits[(bt & 0xf0) >> 4];
//        char c1 = hexDigits[bt & 0xf];
//        stringbuffer.append(c0);
//        stringbuffer.append(c1);
//    }
//    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
//
//}
