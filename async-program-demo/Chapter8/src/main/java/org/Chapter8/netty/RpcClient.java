package org.Chapter8.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 帧格式 消息内容:请求id|
 *
 * @author luxu.zlx
 *
 */
public class RpcClient {

	// 连接通道
	private volatile Channel channel;
	// 请求id生成器
	private static final AtomicLong INVOKE_ID = new AtomicLong(0);
	// 启动器
	private Bootstrap b;

	public RpcClient() {
		// 1. 配置客户端.
		EventLoopGroup group = new NioEventLoopGroup();
		NettyClientHandler clientHandler = new NettyClientHandler();
		try {
			b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							// 1.1设置帧分隔符解码器
							ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
							p.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));
							// 1.2设置消息内容自动转换为String的解码器到管线
							p.addLast(new StringDecoder());
							// 1.3设置字符串消息自动进行编码的编码器到管线
							p.addLast(new StringEncoder());
							// 1.4添加业务Handler到管线
							p.addLast(clientHandler);

						}
					});
			// 2.发起链接请求，并同步等待链接完成
			ChannelFuture f = b.connect("127.0.0.1", 12800).sync();
			if (f.isDone() && f.isSuccess()) {
				this.channel = f.channel();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMsg(String msg) {
		channel.writeAndFlush(msg);
	}

	public void close() {

		if (null != b) {
            b.config().group().shutdownGracefully();
		}
		if (null != channel) {
			channel.close();
		}
	}

	// 根据消息内容和请求id，拼接消息帧
	private String generatorFrame(String msg, String reqId) {
		return msg + ":" + reqId + "|";
	}

	// 异步调用
	public CompletableFuture<String> rpcAsyncCall(String msg) {
		System.out.println(Thread.currentThread().getName());

		// 1. 创建future
		CompletableFuture<String> future = new CompletableFuture<>();

		// 2.创建消息id
		String reqId = INVOKE_ID.getAndIncrement() + "";

		// 3.根据消息，请求id创建协议帧
		msg = generatorFrame(msg, reqId);

		// 4.nio异步发起网络请求，马上返回
		try {
			this.sendMsg(msg);
		} catch (Exception e) {
			future.cancel(true);
			throw e;
		}

		// 5.保存future对象
		FutureMapUtil.put(reqId, future);

		return future;
	}

	// 异步转反应式
	public Flowable<String> rpcAsyncCallFlowable(String msg) {
		return Flowable.fromFuture(rpcAsyncCall(msg)).subscribeOn(Schedulers.io());
	}

	// 异步转反应式
	public Flowable<String> rpcAsyncCallFlowable3(String msg) {

		final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
		CompletableFuture<String> future = rpcAsyncCall(msg);
		future.whenComplete((v, t) -> {
			if (t != null) {
				flowable.onError(t);
			} else {
				flowable.onNext(v);
				flowable.onComplete();
			}
		});

		return flowable;
	}

	// 异步转反应式
	public Flowable<String> rpcAsyncCallFlowable4(String msg) {
		// 1.1 使用defer操作，当订阅时候在执行rpc调用
		return Flowable.defer(() -> {
			// 1.2创建含有一个元素的流
			final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
			// 1.3具体执行RPC调用
			CompletableFuture<String> future = rpcAsyncCall(msg);
			// 1.4等rpc结果返回后设置结果到流对象
			future.whenComplete((v, t) -> {
				if (t != null) {// 1.4.1结果异常则发射错误信息
					flowable.onError(t);
				} else {// 1.4.2结果OK，则发射出rpc返回结果
					flowable.onNext(v);
					// 1.4.3结束流
					flowable.onComplete();
				}
			});
			return flowable;
		});
	}

	// 异步转反应式,订阅时候切换到其他线程
	public Flowable<String> rpcAsyncCallFlowable5(String msg) {
		return Flowable.defer(() -> {

			final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
			CompletableFuture<String> future = rpcAsyncCall(msg);
			future.whenComplete((v, t) -> {
				if (t != null) {
					flowable.onError(t);
				} else {
					flowable.onNext(v);
					flowable.onComplete();
				}
			});
			return flowable;
		}).observeOn(Schedulers.io());

	}

	// 同步转反应式
	public Flowable<String> rpcAsyncCallFlowable2(String msg) {
		return Flowable.fromCallable(() -> rpcSyncCall(msg)).subscribeOn(Schedulers.io());
	}

	// 同步调用
	public String rpcSyncCall(String msg) throws InterruptedException, ExecutionException {

		System.out.println(Thread.currentThread().getName());
		// 1. 创建future
		CompletableFuture<String> future = new CompletableFuture<>();

		// 2.创建消息id
		String reqId = INVOKE_ID.getAndIncrement() + "";

		// 3.消息体后追加消息id和帧分隔符
		msg = generatorFrame(msg, reqId);

		// 4.nio异步发起网络请求，马上返回
		try {
			this.sendMsg(msg);
		} catch (Exception e) {
			future.cancel(true);
			throw e;
		}

		// 5.保存future
		FutureMapUtil.put(reqId, future);

		// 6.同步等待结果
        return future.get();
	}
}
