package com.demo.util;

import java.security.*;

public class PasswordUtil {

	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16]; // 128-bit salt
		random.nextBytes(salt);
		return bytesToHex(salt);
	}

	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String input = password + salt;
			byte[] hashed = md.digest(input.getBytes("UTF-8"));
			return bytesToHex(hashed);
		} catch (Exception e) {
			throw new RuntimeException("Fail to encrypt password", e);
		}
	}

	public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt) {
		String inputHash = hashPassword(inputPassword, storedSalt);
		return inputHash.equals(storedHash);
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
