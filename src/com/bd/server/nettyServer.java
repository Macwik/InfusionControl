package com.bd.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 定长解码 服务器端
 * 
 *
 */
public class nettyServer {

	public static final ScheduledExecutorService ss = Executors.newScheduledThreadPool(4);

	public void start(final ChannelHandler handler) throws Exception {
		ss.submit(new Runnable() {

			@Override
			public void run() {
				try {
					innerStart(handler);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		});
	}

	private void innerStart(ChannelHandler handler) {
		int port = 7000;

		// 接收客户端连接用
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 处理网络读写事件
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 配置服务器启动类
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))// 配置日志输出
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("#".getBytes());

							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(300, delimiter));
							// 长度设置为30
							ch.pipeline().addLast(new StringDecoder());// 设置字符串解码器
							ch.pipeline().addLast(new StringEncoder());// 设置字符串解码器

							ch.pipeline().addLast("idleState", new IdleStateHandler(9999999, 1, 3));

							ch.pipeline().addLast(handler);// 处理网络IO
							// 处理器
						}
					});
			// 绑定端口 等待绑定成功
			ChannelFuture f = null;
			try {
				f = b.bind(port).sync();
			} catch (InterruptedException e) {
			}
			// // 等待服务器退出
			try {
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
			}
		} finally {
			// 释放线程资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
