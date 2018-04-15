package com.bd.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.bd.Control.BedPatientBound;
import com.bd.Control.DeviceBedBound;
import com.bd.Control.DeviceBound;
import com.bd.Control.PhoneBedBound;
import com.bd.Control.SystemConfig;
import com.bd.Control.InterfaceAndEnum.EBedPhoneStatus;
import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;
import com.bd.Control.Util.ArrayMap;
import com.bd.Control.Util.StringUtil;
import com.bd.Manager.AboutPhone;
import com.bd.Manager.DevicetoBed;
import com.bd.Manager.SetDevice;
import com.bd.objects.DeviceInfo;
import com.bd.objects.InfusionEvent;
import com.bd.objects.PatientInfo;
import com.bd.objects.PhoneInfo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import logfile.log;

/**
 * 服务器handler
 *
 * @author
 */
public class nettyHandler extends SimpleChannelHandlerAdapter {
	// 报文头部和回复头部
	public static final String head_dvic = "*DVIC,SVER";
	public static final String reply_dvic = "*SVER,DVIC";
	// 回复手机报文头部
	public static final String head_phone = "*PHON,SVER";
	public static final String reply_phone = "*SVER,PHON";

	private static final String _open = "门打开";
	private static final String _close = "门关闭";
	private static final String _work = "工作";
	private static final String _reply = "护士已响应";
	private static final String _bang = "已绑定";
	/**
	 * TIME：2018年4月8号 Author：zhangchao 滴速异常 改 异常 无液滴速异常 改 无液异常 滴速异常低电压 改 异常低电压
	 */
	private static final String _alarm1 = "无液报警";
	private static final String _alarm2 = "异常";
	private static final String _alarm3 = "无液异常";
	private static final String _alarm4 = "低电压报警";
	private static final String _alarm5 = "无液低电压";
	private static final String _alarm6 = "异常低电压";
	private static final String _alarm7 = "无液异常低电压";
	long current_pausetime = 0;// 记录当前开门时间
	long base_pausetime = 0;// 记录当前基准时间
	long current_closetime = 0;
	long base_closetime = 0;
	long current_close = 0;
	long base_close = 0;
	long current_worktime = 0;
	long base_worktime = 0;
	long base_alarmtime = 0;
	// 定义设备号和ctx的Map
	private static ArrayMap<String, ChannelHandlerContext> ctxs = new ArrayMap<String, ChannelHandlerContext>();
	// 定义终端设备号和Ip的Map
	private static Map<String, String> deviceid_ip = new HashMap<String, String>();
	// 定义手机编号和IP的Map
	private static ArrayMap<String, ChannelHandlerContext> phone_ctxs = new ArrayMap<String, ChannelHandlerContext>();
	// 定义手机编号和IP的Map
	private static Map<String, String> phoneid_ip = new HashMap<String, String>();
	// 报警时间的Map
	private static Map<String, Long> alarm_time = new HashMap<String, Long>();
	// 设备状态的Map
	private static ConcurrentHashMap<String, DeviceStatus> status = new ConcurrentHashMap<String, DeviceStatus>();
	// 容量和滴速的Map
	public static ConcurrentHashMap<String, VolStatus> vol_status = new ConcurrentHashMap<String, VolStatus>();
	// 存储要发送的报文的信息
	private static ArrayMap<String, String> devid_msg = new ArrayMap<String, String>();
	// 存储要发送的NAME的信息
	private static HashMap<String, ByteBuf> devid_name = new HashMap<String, ByteBuf>();
	// 手机和中控空闲3s没有发报文就计数一次
	private static HashMap<String, Integer> phone_idle_count = new HashMap<String, Integer>();
	// 终端设备和中控空闲3s没有发报文就计数一次
	private static HashMap<String, Integer> device_idle_count = new HashMap<String, Integer>();
	// 握手报文的空闲计数
	private static HashMap<String, Integer> device_hand_count = new HashMap<String, Integer>();
	public static final char delimeter = ',';
	public static final int maxRepeatTime = 10000;
	public static final long waitTime = 3000;
	private static final StringBuilder sbTime = new StringBuilder();
	private static final int repeatTime = 2;
	private static final int setSpdTime = 1000;
	int Counter = 1;

	// public static final ScheduledExecutorService scheduler = Executors
	// .newScheduledThreadPool(1);

	// 发送报文函数
	public static void write_word(ChannelHandlerContext ctx, String content) {
		// Date date = new Date();
		// DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		// String rece_time = format2.format(date);
		synchronized (ctx) {
			ctx.writeAndFlush(content);
			// System.out.println(rece_time + "[" + getIpPort(ctx) + "]" +
			// content);
		}

	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelRegistered();
	}

	// 获得链接ctx的IP
	public static String getIpPort(ChannelHandlerContext ctx) {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIP = insocket.getAddress().getHostAddress();
		return clientIP;
	}

	// 设置基准滴速和剩余容量
	public static boolean SetSpd(String deviceid, String spd) {

		String sp = null;
		boolean succeed = false;
		spd = StringUtil.getTransVol(spd);
		try {
			for (int i = 0; i < repeatTime; i++) {
				sp = DeviceBedBound.getBaseSpd(deviceid);
				if (!spd.equals(sp)) {
					ChannelHandlerContext ctx = ctxs.get(deviceid);
					if (ctx != null) {
						StringBuilder sb = new StringBuilder();
						sb.append(reply_dvic).append(',').append(deviceid).append(',').append("SPSET,")
								.append(DeviceBedBound.getBedNum(deviceid)).append(',').append(spd).append(',')
								.append(StringUtil.getTransVol("" + DeviceBedBound.getVol(deviceid) / 20)).append('#');
						ctx.writeAndFlush(sb.toString());
						Thread.sleep(setSpdTime);
					}
				} else {
					succeed = true;
					break;
				}
			}
		} catch (InterruptedException e) {

		}
		if (!succeed) {
			succeed = spd.equals(sp = DeviceBedBound.getBaseSpd(deviceid));
		}
		return succeed;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		if (!alarm_time.containsKey(getIpPort(ctx))) {
			alarm_time.put(getIpPort(ctx), (long) 0);
		}
		super.channelActive(ctx);
	}

	// 中控和终端3S之间没有通信，或者中控和手机3s之间没有通信，触发这个函数
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
		if (ctxs.containsValue(ctx)) {// 判断是否是终端的连接还是手机的连接
			String dev_id = ctxs.getKeyForValue(ctx);// 从Map中取到设备的ID
			IdleStateEvent e = (IdleStateEvent) evt;//
			switch (e.state()) {// 判断空闲的类型，是读空闲，写空闲，还是读写都空闲
			case WRITER_IDLE:
				Integer hand_count = device_hand_count.get(dev_id);// 获取Map中的设备没有读取到报文空闲的次数
				if (hand_count == null) {// 如果次数为null，则插入1
					device_hand_count.put(dev_id, 1);
				} else if (hand_count < 30) {// 如果次数小于30，则对应的计数加1
					device_hand_count.put(dev_id, hand_count + 1);

				} else {// 每1s空闲一次，当次数累计到30的时候，发送握手报文给中终端
					sbTime.setLength(0);
					sbTime.append(reply_dvic).append(',').append(dev_id).append(",HAND#");
					write_word(ctx, sbTime.toString());// 发送握手报文
					device_hand_count.remove(dev_id);
				}
				break;
			case ALL_IDLE:

				DeviceStatus ds = status.get(ctxs.getKeyForValue(ctx));// 获取终端设备当前对应的状态
				// String dev_id = ctxs.getKeyForValue(ctx);
				String msg = devid_msg.get(dev_id);// 需要重发的报文的信息
				ByteBuf namemsg = devid_name.get(dev_id);// 需要重发的NAME报文的信息
				if (dev_id == null) {// 判断取到的设备ID是否为空
					if (namemsg != null) {
						namemsg.release();
					}
				} else if (msg != null || namemsg != null) {// 当存储的报文消息不为空时，才重发
					if (ds == null) {
						devid_msg.remove(dev_id);// 从map中删除设备id对应的信息
						devid_name.remove(dev_id);
						checkDeviceOffline(ctx);
					} else {
						switch (ds) {
						case INFOR:
						case INFOR_RECONNECT:
						case SPEED_RECONNECT:
							// message to send
							write_word(ctx, msg);// 3s发送一次对应状态下的报文的信息
							break;
						case NAME:
						case NAME_RECONNECT:
							// message to send
							namemsg.retain(1);
							ctx.writeAndFlush(namemsg);// 3s发送一次name报文的信息
							break;
						default:
							// clear message
							if (namemsg != null) {
								namemsg.release();
							}
							devid_msg.remove(dev_id);
							devid_name.remove(dev_id);
							checkDeviceOffline(ctx);
							break;
						}
					}
				} else {
					checkDeviceOffline(ctx);
				}
				break;
			default:
				break;
			}
		} else if (phone_ctxs.containsValue(ctx)) {// 如果空闲的是手机连接
			String phone_id = phoneid_ip.get(getIpPort(ctx));// 先从Map中取到手机的编号
			IdleStateEvent e1 = (IdleStateEvent) evt;
			switch (e1.state()) {
			case ALL_IDLE:
				if (phone_id != null) {
					Integer count = phone_idle_count.get(phone_id);
					if (count == null) {
						phone_idle_count.put(phone_id, 1);
					} else if (count < 60) {
						phone_idle_count.put(phone_id, count + 1);
						// System.out.println("手机count:" + count);
					} else {
						// offline
						try {
							// AboutPhone.setPhoneOff(phone_id);
							AboutPhone.setPhoneColor(phone_id, false);
							phone_ctxs.remove(phone_ctxs.getKeyForValue(ctx));
							ctx.close();
						} catch (RuntimeException e) {
							log.SqlSetException("三分钟掉线，异常" + e);
						}
					}
				} else {
					ctx.close();
				}
				break;
			default:
				break;
			}

		} else {
			return;
		}
	}

	// 当手机空闲3s之后也没有发送信息，则触发这个计数函数，每3s空闲count计数一次
	private void checkDeviceOffline(ChannelHandlerContext ctx) {

		if (ctx == null) {// 如果连接为空，返回
			return;
		}
		String device_id = deviceid_ip.get(getIpPort(ctx));
		if (device_id == null || !SetDevice.isDeviceRegi(device_id)) {
			ctx.close();
			return;
		}
		Integer count = device_idle_count.get(device_id);
		if (count == null) {
			device_idle_count.put(device_id, 1);
		} else if (count < 60) {// 如果count计数小于60,则一直加
			// System.out.println("终端count:" + count);
			device_idle_count.put(device_id, count + 1);
		} else {
			// 计数60次以后，也就是3s*60=180s时,触发离线事件
			try {
				SetDevice.setDeviceStatusColor(device_id, EDeviceStatusColorEnum.WAIT);// 清除中控床位上
				// 的颜色信息
				SetDevice.removeDeviceInfo(device_id);// 清除设备和床位的绑定信息
				ctxs.remove(ctxs.getKeyForValue(ctx));//
				ctx.close();
			} catch (RuntimeException e) {
				log.SqlSetException("三分钟掉线，异常" + e);
			}
		}
	}

	// 当连接关闭的时候触发这个函数
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		if (!(ctxs.containsValue(ctx) || phone_ctxs.containsValue(ctx))) {
			return;
		}
		String device_id = ctxs.getKeyForValue(ctx);
		if (device_id != null) {
			ctxs.remove(device_id);
			SetDevice.unRegisterDevice(device_id);// 先把设备ID对应的标志位设置为离线,3分钟之后看是否终端有报文进来，否则就当做掉线3分钟处理
			nettyServer.ss.schedule(new Runnable() {
				@Override
				public void run() {
					if (!SetDevice.isDeviceRegi(device_id)) {// 当3分钟之后设备对应的标志还是离线，掉线
						SetDevice.setDeviceStatusColor(device_id, EDeviceStatusColorEnum.WAIT);// 清除面板上的颜色
						SetDevice.removeDeviceInfo(device_id);// 清除终端的绑定信息。
					}
				}
			}, 180, TimeUnit.SECONDS);
		} else {// 处理手机连接关闭程序
			String phone_id = phone_ctxs.getKeyForValue(ctx);
			if (phone_id != null) {
				phone_ctxs.remove(phone_id);
				AboutPhone.setPhoneOff(phone_id);
				nettyServer.ss.schedule(new Runnable() {
					@Override
					public void run() {
						if (!AboutPhone.isPhoneReg(phone_id)) {
							AboutPhone.setPhoneColor(phone_id, false);// 清除面板上的手机颜色
						}
					}
				}, 180, TimeUnit.SECONDS);
			}

		}

	}

	// 客户端发过来的所有信息都是在这里处理
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		Date date = new Date();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		String rece_date = format1.format(date);
		String rece_time = format2.format(date);
		String content = msg.toString();
		StringBuilder sb = new StringBuilder();
		StringBuilder tmp = new StringBuilder();
		// System.out.println(rece_time + "[" + getIpPort(ctx) + "]" + msg);
		final String[] args = new String[9];
		if (content.startsWith(head_dvic)) {// 判断字符串开头字符串是否是终端发来的报文
			if (content.charAt(11) == ' ') {// 如果终端编号是空，则停止
				return;
			}
			StringUtil.split2ArrayDirect(content, delimeter, args);// 把报文以逗号分隔开来，存储在args数组中
			if (ctxs.containsKey(args[2])) {// 判断这个Map里面是否有这个终端连接,如果存储了旧的连接，则把旧的连接关闭，重新存储新的连接
				if (!(ctxs.get(args[2]).equals(ctx))) {
					ctxs.get(args[2]).close();
					ctxs.put(args[2], ctx);// 把终端设备和ctx添加到Map
				}
			} else {
				ctxs.put(args[2], ctx);// 把终端设备和ctx添加到Map
			}

			deviceid_ip.put(getIpPort(ctx), args[2]);// 把终端的IP和设备号添加到Map
			// clear device idle count
			Integer count = device_idle_count.get(args[2]);
			if (count != null) {
				device_idle_count.remove(args[2]);// 只要对应的终端设备有报文进来,则把相应的空闲count计数清零
			}

			sb.append(reply_dvic).append(',').append(args[2]).append(',');
			SetDevice.registerDevice(args[2]);// 设置终端在线状态
			PatientInfo patientInfo;
			if (args[3].equals("ON")) {// 终端开机注册报文如果是ON
				if (DeviceBound.containDevice(args[2])) {// 判断中控的面板上是否已经添加了这个终端ID
					if (DeviceBedBound.notContainBD(args[2])) {// 判断这个终端是否有床位绑定
						tmp.append(sb).append("REGI,0#");
						write_word(ctx, tmp.toString());// 回复报文给终端
						status.put(args[2], DeviceStatus.NONE);// 把对应的状态位置为NONE

					} else {// 处理三分钟之内断线重连
						status.put(args[2], DeviceStatus.NONE);// 把对应的状态位置为NONE
						SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.WAIT);// 先把颜色变为灰色
						SetDevice.removeDevInfoDisplay(args[2]);// 清除面板上的信息
						tmp.setLength(0);
						tmp.append(sb).append("REGI,1#");
						write_word(ctx, tmp.toString());// 重连发送报文REGI,1给终端

						patientInfo = BedPatientBound.getPatientByBedNo(DeviceBedBound.getBedNum(args[2]));
						String zyh = StringUtil.getTransPatientID(patientInfo.getPatientId());// 获取病人住院号
						String sex = StringUtil.getTransSextoInt(patientInfo.getPatientGender());// 获取病人性别
						String age = StringUtil.getTransAge(patientInfo.getPatientAge());// 获取病人年龄
						/**/
						Thread.sleep(500);
						/**/

						status.put(args[2], DeviceStatus.INFOR_RECONNECT);// 把标志位置为重连时候的INFO
						tmp.setLength(0);
						tmp.append(sb).append("INFO,").append(zyh).append(',').append(sex).append(',').append(age)
								.append('#');
						write_word(ctx, tmp.toString());
						devid_msg.put(args[2], tmp.toString());// 把需要重复发送给终端的INFO报文存储到devid_msg中，以便3s一次重新发送
					}

				} else {
					tmp.setLength(0);
					tmp.append(sb).append("REGI,2#");
					ctx.writeAndFlush(tmp.toString());// 面板上没有添加这个设备，发送REGI,2给终端
				}
			} else if (args[3].equals("CLOSE")) {// 处理门关闭程序
				SetDevice.setDeviceStatus(args[2], _close);// 面板上把显示的字设为门关闭

			} else {
				switch (args[3]) {
				case "BASE":
					if (!DeviceBedBound.containsBed(args[4])) {
						DeviceBedBound.addDeviceBedBound(args[4], args[2]);
					}
					SetDevice.setDeviceCurrentBase(args[2], args[5]);// 保存当前基准滴速

					switch (args[7]) {
					case "PAUSE":
						// 遍历床位号所对应的手机号，然后发送报警解除报文
						Iterator<PhoneInfo> pause_iter = AboutPhone.getPhoneByBed(args[4]).iterator();
						while (pause_iter.hasNext()) {
							String phone_id2 = pause_iter.next().getPhoneId();
							if (phone_id2 != null) {
								if (phone_ctxs.containsKey(phone_id2)) {
									write_word(phone_ctxs.get(phone_id2),
											reply_phone + ",88" + phone_id2 + ",NORMAL," + args[4] + "#");
								}
							}
						}
						AboutPhone.setBedPhoneStatus(args[4], EBedPhoneStatus.NORMAL);// 手机标志设置为normal
						/* 存储一次开门的报文信息 */
						current_pausetime = System.currentTimeMillis();
						String paientid_pause = DevicetoBed.getPatientIDFromDevice(args[2]);
						if (current_pausetime - base_pausetime > 20000) {
							SystemConfig.infusionEventDao
									.addInfusionEvent(new InfusionEvent(paientid_pause, rece_date, rece_time, _open));
						}
						base_pausetime = current_pausetime;

						SetDevice.setDeviceCurrentSpd(args[2], "0");// 存储当前滴数
						SetDevice.setDeviceRemainTime(args[2], args[8]);// 存储剩余时间
						SetDevice.setDeviceStatus(args[2], _open);// 面板上面设置为门打开
						SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.NOTWORK);// 床位上的颜色设置为蓝色
						PhoneBedBound.removeBedAlarm(args[4]);// 删除报警的床位信息
						break;

					case "WORK":
						// 遍历床位号所对应的手机号，然后发送报警解除报文
						Iterator<PhoneInfo> work_iter = AboutPhone.getPhoneByBed(args[4]).iterator();
						while (work_iter.hasNext()) {
							String phone_id3 = work_iter.next().getPhoneId();
							if (phone_id3 != null) {
								if (phone_ctxs.containsKey(phone_id3)) {
									write_word(phone_ctxs.get(phone_id3),
											reply_phone + ",88" + phone_id3 + ",NORMAL," + args[4] + "#");
								}
							}
						}
						AboutPhone.setBedPhoneStatus(args[4], EBedPhoneStatus.NORMAL);// 面板上的床位颜色变为绿色，表示正常工作状态
						/* 存储一次工作报文信息 */
						current_worktime = System.currentTimeMillis();
						String paientid_work = DevicetoBed.getPatientIDFromDevice(args[2]);
						if (current_worktime - base_worktime > 20000) {
							SystemConfig.infusionEventDao
									.addInfusionEvent(new InfusionEvent(paientid_work, rece_date, rece_time, _work));
						}
						base_worktime = current_worktime;

						if (StringUtil.isLessNine(args[8])) {// 判断剩余时间是否小于9分钟
							SetDevice.setDeviceCurrentSpd(args[2], args[6]);// 存储当前的滴数信息
							SetDevice.setDeviceStatus(args[2], _work);// 把面板上床位显示信息设置为工作
							SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.EARLYWARNING);// 床位上的颜色变为黄色，表示预警状态
							if (Integer.parseInt(args[8]) <= 999) {// 剩余时间最大是999
								SetDevice.setDeviceRemainTime(args[2], args[8]);// 存储剩余时间
							}
						} else {
							SetDevice.setDeviceStatus(args[2], _work);
							SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.WORK);// 床位上的显示信息为工作
							SetDevice.setDeviceCurrentSpd(args[2], args[6]);// 存储当前的滴数信息
							if (Integer.parseInt(args[8]) <= 999) {
								SetDevice.setDeviceRemainTime(args[2], args[8]);// 存储当前的剩余时间
							}
						}
						break;
					case "STOP":
						break;
					case "CLOSE":
						AboutPhone.setBedPhoneStatus(args[4], EBedPhoneStatus.NORMAL);// 设置手机的状态位normal
						SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.NOTWORK);// 床位颜色设置为蓝色
						SetDevice.setDeviceStatus(args[2], _close);// 床位上的字设置为门关闭
						/* 存储一个门关闭的信息 */
						String paientid_close = DevicetoBed.getPatientIDFromDevice(args[2]);
						current_closetime = System.currentTimeMillis();
						if (current_closetime - base_closetime > 20000) {
							SystemConfig.infusionEventDao
									.addInfusionEvent(new InfusionEvent(paientid_close, rece_date, rece_time, _close));
						}
						base_closetime = current_closetime;

						break;
					case "UNKNOW":
						break;
					case "NOTWORK":
						break;
					case "SET_OVER":
						SetDevice.setDeviceCurrentSpd(args[2], args[6]);// 存储滴数信息
						SetDevice.setDeviceRemainTime(args[2], args[8]);// 存储剩余时间
						break;
					case "DROP_TEST":
						break;
					case "ALARM_CONFIRM":
						SetDevice.setDeviceCurrentSpd(args[2], args[6]);
						SetDevice.setDeviceStatus(args[2], _reply);
						SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.NOTWORK);
						break;
					default:
						break;
					}
					;
					break;
				case "LOC":// LOC报文，申请床位
					String bednum = args[4];// 床位号
					if (BedPatientBound.contain(bednum)) {// 病人信息是否在床位上已经输入,床位和病人有没有绑定
						if (!(DeviceBedBound.containsBed(bednum))) {// 判断是否有设备和床位绑定
							tmp.setLength(0);
							tmp.append(sb).append(args[3]).append(',').append(bednum).append('#');
							ctx.writeAndFlush(tmp.toString());

							patientInfo = BedPatientBound.getPatientByBedNo(bednum);
							String zyh = StringUtil.getTransPatientID(patientInfo.getPatientId());// 获取病人住院号
							String sex = StringUtil.getTransSextoInt(patientInfo.getPatientGender());// 获取病人性别
							String age = StringUtil.getTransAge(patientInfo.getPatientAge());// 获取病人年龄
							// 设置一个定时器，每一秒发送病人的INFO信息给终端，如果回复则停止发送
							Thread.sleep(500);// 2016.1.13号修改，加上休眠
							status.put(args[2], DeviceStatus.INFOR);// 设置终端的标志位INFOR

							tmp.setLength(0);
							tmp.append(sb).append("INFO").append(',').append(zyh).append(',').append(sex).append(',')
									.append(age).append('#');

							write_word(ctx, tmp.toString());// 发送INFO信息给终端
							devid_msg.put(args[2], tmp.toString());// 存储INFO的报文信息

						} else {
							if (DeviceBedBound.getDeviceInfoByBedNo(bednum).getDeviceID().equals(args[2])) {
								tmp.setLength(0);
								tmp.append(sb).append(args[3]).append(',').append(bednum);// 如果有终端和床位绑定，而且ID号也相同
								write_word(ctx, tmp.toString());
							} else {
								tmp.setLength(0);
								tmp.append(sb).append(args[3]).append(",00#");
								write_word(ctx, tmp.toString());
							}
						}
					} else {
						tmp.setLength(0);
						tmp.append(sb).append(args[3]).append(",00#");
						write_word(ctx, tmp.toString());

					}
					break;
				case "INFO":

					DeviceStatus st = status.get(args[2]);
					String patientName = SystemConfig.patientDao.getPatientInfo(args[4]).getPatientName();
					if (st != null && st == DeviceStatus.INFOR_RECONNECT)// 判断是否是重连的INFO标志

					{
						status.put(args[2], DeviceStatus.NAME_RECONNECT);// 把终端的标志置为NAME重连
						// 设置一个定时器，每一秒发送病人的NAME信息给终端，如果回复则停止发送
						ByteBuf buffer = null;// 定义一个存储NAME信息的buffer
						buffer = ctx.alloc().buffer(256);
						buffer.retain(1);
						if (patientName != null) {
							byte[] send_buffer = new byte[128];
							try {
								Utf8ToMatrix.translated(patientName, send_buffer);
							} catch (IOException e) {
								e.printStackTrace();
							}
							tmp.setLength(0);
							tmp.append(sb).append("NAME,");
							byte[] bytes = new String(tmp).getBytes("UTF-8");
							buffer.writeBytes(bytes);
							buffer.writeBytes(send_buffer);
							buffer.writeBytes("#".getBytes("UTF-8"));
							ctx.writeAndFlush(buffer);
							devid_name.put(args[2], buffer);
							send_buffer = null;
						} else {
							break;
						}

					} else if (st != null && st == DeviceStatus.INFOR)// 判断终端是否是正常的INFO信息

					{

						status.put(args[2], DeviceStatus.NAME);// 把终端的标志置为NAME
						// 设置一个定时器，每3秒发送病人的NAME信息给终端，如果回复则停止发送

						ByteBuf buffer = null;
						buffer = ctx.alloc().buffer(256);
						buffer.retain(1);// 看不懂
						if (patientName != null) {
							byte[] send_buffer = new byte[128];
							try {
								Utf8ToMatrix.translated(patientName, send_buffer);
							} catch (IOException e) {
								e.printStackTrace();
							}
							tmp.setLength(0);
							tmp.append(sb).append("NAME,");
							byte[] bytes = new String(tmp).getBytes("UTF-8");
							buffer.writeBytes(bytes);
							buffer.writeBytes(send_buffer);
							buffer.writeBytes("#".getBytes("UTF-8"));
							devid_name.put(args[2], buffer);
							ctx.writeAndFlush(buffer);
							// TODO zhang新加
							buffer.release();
							send_buffer = null;
						}

					}

					break;
				case "NAME":

					st = status.get(args[2]);
					if (st != null && st == DeviceStatus.NAME_RECONNECT) {// 判断是否是重连的NAME确认信息
						status.put(args[2], DeviceStatus.SPEED_RECONNECT);// 标志位设置为发送容量和滴数的信息
						// 设置一个定时器，每一秒发送病人的容量和当前滴速信息给终端，如果回复则停止发送
						VolStatus vol_st = vol_status.get(args[2]);
						if (vol_st == VolStatus.Normal) {
							tmp.setLength(0);
							tmp.append(sb).append("SPSET,").append(DeviceBedBound.getBedNum(args[2])).append(',')
									.append(StringUtil.appendZero(DeviceBedBound.getBaseSpd(args[2]))).append(',')
									.append(StringUtil.appendZero("" + DeviceBedBound.getVol(args[2]) / 20))
									.append('#');
							write_word(ctx, tmp.toString());
							devid_msg.put(args[2], tmp.toString());

						} else if (vol_st == VolStatus.BigVol) {
							tmp.setLength(0);
							tmp.append(sb).append("SPSET,").append(DeviceBedBound.getBedNum(args[2])).append(',')
									.append(StringUtil.appendZero(DeviceBedBound.getBaseSpd(args[2]))).append(",800#");
							write_word(ctx, tmp.toString());
							devid_msg.put(args[2], tmp.toString());
						} else {
							tmp.setLength(0);
							tmp.append(sb).append("SPSET,").append(DeviceBedBound.getBedNum(args[2]))
									.append(",333,900#");
							write_word(ctx, tmp.toString());
							devid_msg.put(args[2], tmp.toString());
						}

					} else if (st != null && st == DeviceStatus.NAME)

					{
						status.remove(args[2]);// 如果是正常注册的NAME信息，则删除Map中对应的状态信息
					}
					break;
				case "SPSET":
					status.remove(args[2]);// 重练的时候，收到终端发回的滴数信息也删除Map中的状态信息
					DeviceBedBound.setDeviceCurrentBase(args[2], args[5]);// 存储当前的基准滴数
					break;
				case "BASESET":
					if (args[6] != null && args[5] != null && StringUtil.isSpeed(args[5])
							&& StringUtil.isSpeed(args[6]))

					{// 容量有三个不同的档数
						if (args[6].equals("800")) {
							vol_status.put(args[2], VolStatus.BigVol);
						} else if (args[5].equals("333") && args[6].equals("900")) {
							vol_status.put(args[2], VolStatus.HandOpera);
						} else {
							vol_status.put(args[2], VolStatus.Normal);
						}
					}

					if (DeviceBedBound.containsBed(args[4]))// 如果已经有设备绑定了这个床位,则回应终端REGI3

					{
						if (!(DeviceBedBound.getDeviceInfoByBedNo(args[4]).getDeviceID().equals(args[2]))) {
							tmp.setLength(0);
							tmp.append(sb).append("REGI,3#");
							ctx.writeAndFlush(tmp.toString());
							break;
						}
					}
					DeviceBedBound.addDeviceBedBound(args[4], args[2]);// 添加终端和床位的绑定
					SetDevice.setDeviceIp(args[2], getIpPort(ctx));
					SetDevice.setDeviceStatus(args[2], _bang);// 床位上的显示信息改为已绑定

					tmp.setLength(0);
					tmp.append(sb).append(args[3]).append(',').append(args[4]).append(',').append(args[5]).append(',')
							.append(args[6]).append('#');
					write_word(ctx, tmp.toString());// 回复终端同样的信息
					SetDevice.setDeviceCurrentBase(args[2], args[5]);// 保存当前基准滴速
					SetDevice.setDeviceCurrentVol(args[2], args[6]);// 保存当前剩余容量
					break;
				case "HAND":
					break;
				case "ALARM":
					if (args.length < 6) {// 如果长度不符合,则停止
						// wrong format
						return;
					}
					if (!DeviceBedBound.containsBed(args[4])) {
						DeviceBedBound.addDeviceBedBound(args[4], args[2]);// 如果终端突然发出一条报警信息,没有绑定的话,直接绑定
					}
					if (!AboutPhone.isConfirm(args[4])) {// 判断手机是否按了已响应
						SetDevice.setDeviceStatusColor(args[2], EDeviceStatusColorEnum.WARNING);// 床位颜色变为红色
						SetDevice.setDeviceCurrentSpd(args[2], "0");// 床位上的容量信息变为0
						String paientid_alarm = DevicetoBed.getPatientIDFromDevice(args[2]);
						long current_alarmtime;
						long _curralarm = System.currentTimeMillis();

						alarm_time.put(getIpPort(ctx), _curralarm);
						switch (args[5]) {
						case "1":
							SetDevice.setDeviceStatus(args[2], _alarm1);// 床位上的信息显示为无液报警
							/* 存储报警报文的信息 */
							current_alarmtime = System.currentTimeMillis();
							if (current_alarmtime - base_alarmtime > 20000) {
								SystemConfig.infusionEventDao.addInfusionEvent(
										new InfusionEvent(paientid_alarm, rece_date, rece_time, _alarm1));
							}
							base_alarmtime = current_alarmtime;
							break;
						case "2":
							SetDevice.setDeviceStatus(args[2], _alarm2);
							current_alarmtime = System.currentTimeMillis();

							// 滴速异常 改为 异常
							if (current_alarmtime - base_alarmtime > 20000) {
								SystemConfig.infusionEventDao.addInfusionEvent(
										new InfusionEvent(paientid_alarm, rece_date, rece_time, _alarm2));
							}

							base_alarmtime = current_alarmtime;
							break;
						case "3":
							current_alarmtime = System.currentTimeMillis();
							if (current_alarmtime - base_alarmtime > 20000) {
								SystemConfig.infusionEventDao.addInfusionEvent(
										new InfusionEvent(paientid_alarm, rece_date, rece_time, "无液滴速异常"));
							}
							base_alarmtime = current_alarmtime;
							SetDevice.setDeviceStatus(args[2], _alarm3);
							break;
						case "4":
							current_alarmtime = System.currentTimeMillis();
							if (current_alarmtime - base_alarmtime > 20000) {
								SystemConfig.infusionEventDao.addInfusionEvent(
										new InfusionEvent(paientid_alarm, rece_date, rece_time, "低电压报警"));
							}
							base_alarmtime = current_alarmtime;
							SetDevice.setDeviceStatus(args[2], _alarm4);
							break;
						case "5":
							current_alarmtime = System.currentTimeMillis();
							if (current_alarmtime - base_alarmtime > 20000) {
								SystemConfig.infusionEventDao.addInfusionEvent(
										new InfusionEvent(paientid_alarm, rece_date, rece_time, "无液低电压"));
							}
							base_alarmtime = current_alarmtime;
							SetDevice.setDeviceStatus(args[2], _alarm5);
							break;
						case "6":
							SetDevice.setDeviceStatus(args[2], _alarm6);
							break;
						case "7":
							SetDevice.setDeviceStatus(args[2], _alarm7);
							break;
						case "NURSEOK":
							break;
						default:
							break;
						}
						tmp.setLength(0);
						tmp.append(sb).append(args[3]).append(',').append(args[4]).append(',').append("SYSOK#");// 回应给终端的信息
						write_word(ctx, tmp.toString());
						// 遍历床位号所对应的手机号，然后向所有手机发送报警信息
						Iterator<PhoneInfo> phone_iter = AboutPhone.getPhoneByBed(args[4]).iterator();// 取到床位号所对应的所有List
						while (phone_iter.hasNext()) {// 判断List里面是否还有值
							String phone_Id = phone_iter.next().getPhoneId();// 获得List中的手机编号
							System.out.println("报警手机编号为:" + phone_Id);
							if (phone_Id == null) {
								return;
							}
							if (phone_ctxs.containsKey(phone_Id)) {// 判断Map中是否有这个手机ID
								if (DeviceBedBound.containsBed(args[4])) {
									tmp.setLength(0);
									tmp.append(reply_phone).append(",88").append(phone_Id).append(",ALARM,")
											.append(args[4]).append(',').append(args[5]).append('#');
									write_word(phone_ctxs.get(phone_Id), tmp.toString());// 发送报警的报文给所有手机
								}
							}

						}

					}
					break;
				// TODO 演示 增加一条滴速广播报文
				// case "DRIP":
				// if (args.length == 6) {
				// Collection<ChannelHandlerContext> values = ctxs.values();
				// content.replace("DVIC,SVER", "SVER,DVIC");
				// values.forEach(x -> {
				// write_word(x, content);
				// });
				// }
				// break;
				default:
					break;
				}
			}
		} else if (content.startsWith(head_phone)) {// 如果接收到的是手机报文

			String[] phone_array = new String[6];
			StringUtil.split2ArrayDirect(content, ',', phone_array);
			String phone_number = StringUtil.getStringTwo(phone_array[2]);// 把8801四位数变成只取2位01
			// clear phone idle count
			// 1月4号修改：之前存储进去8801，四位数，所以识别不了。应该往map中存01。
			/* 手机空闲的时候的count计数清零 */
			Integer count = phone_idle_count.get(phone_number);
			if (count != null) {
				phone_idle_count.remove(phone_number);
			}
			phoneid_ip.put(getIpPort(ctx), phone_number);// 存储手机的IP和编号信息

			if (phone_ctxs.containsKey(phone_number)) {// 判断Map中是否有这个连接
				if (!(phone_ctxs.get(phone_number).equals(ctx))) {
					phone_ctxs.get(phone_number).close();
					phone_ctxs.put(phone_number, ctx);// 把终端设备和ctx添加到Map
				}
			} else {
				phone_ctxs.put(phone_number, ctx);// 把终端设备和ctx添加到Map
			}
			if (!PhoneBedBound.getBoundBed(phone_number).isEmpty())
				AboutPhone.setPhoneColor(phone_number, true);// 手机框变成绿色
			switch (phone_array[3]) {
			case "STATUS":
				switch ((phone_array[4])) {
				case "ON":
					tmp.setLength(0);
					tmp.append(reply_phone).append(',').append(phone_array[2]).append(',').append(phone_array[3])
							.append(',').append(phone_array[4]).append('#');
					write_word(ctx, tmp.toString());// 回复手机一个ON报文
					break;
				case "CONTINUE":
					break;
				case "CONFIRM":
					String bedNum = phone_array[5];
					if (!SetDevice.isWarning(bedNum)) {// 判断床位当前的状态是否是报警状态
						tmp.setLength(0);
						tmp.append(reply_phone).append(",88").append(phone_number).append(",STATUS,CONFIRM,")
								.append(bedNum).append('#');
						write_word(ctx, tmp.toString());
						break;
					}

					if (StringUtil.isBedID(bedNum) && phone_number != null) {
						PhoneBedBound.addBedAlarm(bedNum, phone_number);// 增加床位和手机编号的绑定
					}
					// 手机报文发送CONFIRM过来之后，给所对应的终端发送NURSEOK
					Iterator<String> iter_device = ctxs.keySet().iterator();
					while (iter_device.hasNext()) {
						String _device = iter_device.next();
						String bednum_device = DeviceBedBound.getBedNum(_device);
						if (bednum_device != null) {
							if (bednum_device.equals(bedNum)) {
								tmp.setLength(0);
								tmp.append(reply_dvic).append(',').append(_device).append(",ALARM,")
										.append(bednum_device).append(',').append("NURSEOK#");
								write_word(ctxs.get(_device), tmp.toString());
							}
						}
					}
					// 把手机的状态变为confirm
					AboutPhone.setBedPhoneStatus(bedNum, EBedPhoneStatus.CONFIRM);
					/* 把中控上面的床位上面显示为护士已响应 */
					DeviceInfo device_bed = DeviceBedBound.getDeviceInfoByBedNo(bedNum);
					if (device_bed != null) {
						String device_Id = device_bed.getDeviceID();
						SetDevice.setDeviceStatus(device_Id, _reply);
						SetDevice.setDeviceStatusColor(device_Id, EDeviceStatusColorEnum.NOTWORK);
					}

					// 给所有的手机发送CONFIRM 报文。。解除警报
					Iterator<PhoneInfo> confirm_iter = AboutPhone.getPhoneByBed(bedNum).iterator();
					while (confirm_iter.hasNext()) {
						String phone_id1 = confirm_iter.next().getPhoneId();
						if (phone_id1 != null) {
							if (phone_ctxs.containsKey(phone_id1)) {
								tmp.setLength(0);
								tmp.append(reply_phone).append(",88").append(phone_id1).append(",STATUS,CONFIRM,")
										.append(bedNum).append('#');
								write_word(phone_ctxs.get(phone_id1), tmp.toString());
							}

						}

					}
					break;
				default:
					break;
				}
				break;
			case "ALARM":
				break;
			case "NORMAL":
				break;
			default:
				break;
			}

		} else

		{
			return;
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);

		System.err.println("channel is exception over. (SocketChannel)ctx.channel()=" + ctx.channel());

		cause.printStackTrace();
		System.err.println("channel is exception over. (SocketChannel)ctx.channel()=" + ctx.channel());

		ctx.close();
	}

}