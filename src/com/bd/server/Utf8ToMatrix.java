package com.bd.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * 
 * 将汉字转换为点阵数据输出
 * 
 * @author lzz
 *
 */
public class Utf8ToMatrix {
	static final String hzk = "Matrix.dat";

	public static void translated(String name, byte[] buffer) throws IOException {
		byte[] b = new byte[32];
		for (int i = 0; i < name.length(); i++) {
			b = read(hzk, name.substring(i, i + 1));
			System.arraycopy(b, 0, buffer, i * b.length, b.length);
		}
	}

	public static int[] getQw(String chinese) {
		byte[] bs;
		try {

			bs = chinese.getBytes("gb2312");
			int[] ret = new int[bs.length];
			for (int i = 0, k = bs.length; i < k; i++) {
				ret[i] = (((bs[i] - 0xA0) & 0xff));
			}
			return ret;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] read(String hzk, String hz) throws IOException {
		int[] res = getQw(hz);
		int qh = res[0];
		int wh = res[1];
		long location = (94 * (qh - 1) + (wh - 1)) * 32L;

		// 点阵缓冲 16x16 = 32x8
		byte[] bs = new byte[32];

		RandomAccessFile r = new RandomAccessFile(new File(hzk), "r");// 只读方式打开文件
		r.seek(location);// 指定下一次的开始位置
		r.read(bs);

		r.close();
		return bs;
	}
}