package org.dee;

import com.baomidou.mybatisplus.annotation.TableField;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptedTest {
    public static void main(String[] args) {
        try {
            // 方式1：通过 KeyGenerator 生成标准 AES-256 密钥（推荐）

            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256, new SecureRandom()); // 👈 关键：指定 256 位 + SecureRandom

            SecretKey aesKey = aes.generateKey();
//            SecretKey aesKey = (SecretKey) key;
            // 强制指定 256 位（某些 JVM 需安装 JCE 无限制策略）
            // 若报错 "Illegal key size"，请升级 JDK 或安装 JCE Unlimited Strength Jurisdiction Policy Files
            // 替代方案见下方方式2

            byte[] keyBytes = aesKey.getEncoded(); // 通常为 32 字节（256位）

            // 确保是 32 字节
            if (keyBytes.length != 32) {
                throw new IllegalStateException("Generated key is not 256-bit: " + keyBytes.length * 8 + " bits");
            }

            // 输出格式1：Base64（紧凑，适合环境变量）
            String base64Key = Base64.getEncoder().encodeToString(keyBytes);
            System.out.println("✅ AES-256 Key (Base64, 32 bytes):");
            System.out.println(base64Key);
            System.out.println("(Length: " + base64Key.length() + " chars)\n");

            // 输出格式2：Hex（易读，适合配置文件）
            String hexKey = bytesToHex(keyBytes);
            System.out.println("✅ AES-256 Key (Hex, 64 chars):");
            System.out.println(hexKey);
            System.out.println();

            // 示例：如何在代码中使用（从环境变量读取）
            System.out.println("📌 Usage in Java:");
            System.out.println("String keyBase64 = System.getenv(\"DB_ENCRYPTION_KEY\");");
            System.out.println("byte[] keyBytes = Base64.getDecoder().decode(keyBase64);");
            System.out.println("SecretKey key = new SecretKeySpec(keyBytes, \"AES\");");

        } catch (NoSuchAlgorithmException e) {
            System.err.println("❌ AES KeyGenerator not available: " + e.getMessage());
            // 备用方案：直接用 SecureRandom 生成 32 字节
            generateRawRandomKey();
        }
    }

    // 备用：直接生成 32 字节随机数（不依赖 KeyGenerator）
    private static void generateRawRandomKey() {
        System.out.println("⚠️ Falling back to raw SecureRandom...");
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits
        secureRandom.nextBytes(key);

        String base64Key = Base64.getEncoder().encodeToString(key);
        String hexKey = bytesToHex(key);

        System.out.println("✅ Raw Random Key (Base64):");
        System.out.println(base64Key);
        System.out.println("✅ Raw Random Key (Hex):");
        System.out.println(hexKey);
    }

    // 工具：byte[] → Hex String
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }



    private static final String BASE64_KEY = "9U12bQ50eGO10h9/5Sg1xhCtMI8ogtRZW8/yk2SdYHc=";

    @Test
    public void test01(){
//        String s = encryptedPassword("mySecretPassword123!");
        String s = "JyBUyF+1ZGtaZhwLYUqkwugUiRAfu97ldyMFA9HHWjbDejk=";

        String s1 = decryptPassword(s);

        System.out.println(s);
        System.out.println(s1);

    }
    public static String encryptedPassword(String password) {
        if (password == null || password.isEmpty()) {
            return password;
        }
        try {

            byte[] keyBytes = Base64.getDecoder().decode(BASE64_KEY);

            SecretKey key = new SecretKeySpec(keyBytes, 0, 32, "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = new byte[12]; // GCM推荐IV长度为12字节
            new SecureRandom().nextBytes(iv);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv); // 128-bit tag

            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // 格式：Base64(IV + CIPHERTEXT)
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException("Password encryption failed", e);
        }
    }

    public static String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            return encryptedPassword;
        }
        try {
            // 同加密逻辑，密钥需一致

            byte[] keyBytes = Base64.getDecoder().decode(BASE64_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, 32, "AES");

            byte[] decoded = Base64.getDecoder().decode(encryptedPassword);
            if (decoded.length < 12) {
                throw new IllegalArgumentException("Invalid encrypted password format");
            }

            byte[] iv = new byte[12];
            byte[] ciphertext = new byte[decoded.length - 12];
            System.arraycopy(decoded, 0, iv, 0, 12);
            System.arraycopy(decoded, 12, ciphertext, 0, ciphertext.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Password decryption failed", e);
        }
    }

}
