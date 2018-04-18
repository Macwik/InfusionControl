package com.bd.Control.Util;

import static com.bd.Control.SystemConfig.dbProp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @description：
 * 
 * @author: zhangc @date：2018/3/5 21:14
 */
public class Mp3Utils implements Runnable {

	private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	private static Player player;
	private static File music;
	private static volatile AtomicInteger warningNum = new AtomicInteger(0);
	private static Mp3Utils mp3Utils;
	private static int timeInterval = 4;
	// private static volatile AtomicInteger quitNum = new AtomicInteger(0);
	private static volatile boolean systemVoice = true;

	public static void setSystemVoiceOpen() {
		Mp3Utils.systemVoice = true;
	}

	public static void setSystemVoiceClosed() {
		Mp3Utils.systemVoice = false;
	}

	static {
		music = new File(new File("").getAbsoluteFile(),
				"resource" + File.separator + "music" + File.separator + "warm.mp3");
		mp3Utils = new Mp3Utils();
		timeInterval = Integer.parseInt(dbProp.getProperty("timeInterval"));
		scheduledExecutorService.scheduleAtFixedRate(mp3Utils, 0, timeInterval, TimeUnit.SECONDS);
		// System.out.println("-----------------");
	}

	@Override
	public void run() {
		try {
			if (warningNum.get() > 0 && systemVoice) {
				play();
			}
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
	}

	void play() throws FileNotFoundException, JavaLayerException {

		BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
		player = new Player(buffer);
		player.play();
	}

	public static void playWarmMp3() {
		warningNum.incrementAndGet();
	}

	public static void removePlayWarmMp3() {
		warningNum.decrementAndGet();
	}

	// public static void addQuit() {
	// quitNum.incrementAndGet();
	// }
	//
	// public static void removeQuit() {
	// quitNum.decrementAndGet();
	// }

	public static void main(String[] args) throws InterruptedException {
		playWarmMp3();
		Thread.sleep(20000);
		removePlayWarmMp3();
	}
}
