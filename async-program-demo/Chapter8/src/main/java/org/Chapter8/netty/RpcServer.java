package org.Chapter8.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务端
 */
public final class RpcServer {

    /**
     * Sep 11, 2021 7:55:08 PM io.netty.handler.logging.LoggingHandler channelRegistered
     * INFO: [id: 0x13317764] REGISTERED
     * Sep 11, 2021 7:55:08 PM io.netty.handler.logging.LoggingHandler bind
     * INFO: [id: 0x13317764] BIND: 0.0.0.0/0.0.0.0:12800
     * Sep 11, 2021 7:55:08 PM io.netty.handler.logging.LoggingHandler channelActive
     * INFO: [id: 0x13317764, L:/0:0:0:0:0:0:0:0:12800] ACTIVE
     * Sep 11, 2021 7:55:31 PM io.netty.handler.logging.LoggingHandler channelRead
     * INFO: [id: 0x13317764, L:/0:0:0:0:0:0:0:0:12800] RECEIVED: [id: 0x22c4ed13, L:/127.0.0.1:12800 - R:/127.0.0.1:60589]
     * who are you:0
     * who are you:1
     * ------in active----
     * ------handlerRemoved----
     * Sep 11, 2021 7:56:53 PM io.netty.handler.logging.LoggingHandler channelRead
     * INFO: [id: 0x13317764, L:/0:0:0:0:0:0:0:0:12800] RECEIVED: [id: 0xa2cd162b, L:/127.0.0.1:12800 - R:/127.0.0.1:60621]
     * who are you:0
     * who are you:1
     * ------in active----
     * ------handlerRemoved----
     * Sep 11, 2021 7:57:42 PM io.netty.handler.logging.LoggingHandler channelRead
     * INFO: [id: 0x13317764, L:/0:0:0:0:0:0:0:0:12800] RECEIVED: [id: 0xc8eb1272, L:/127.0.0.1:12800 - R:/127.0.0.1:60637]
     * who are you:0
     * ------in active----
     * ------handlerRemoved----
     */
    public static void main(String[] args) throws Exception {
        // 0.配置创建两级线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);// boss
        EventLoopGroup workerGroup = new NioEventLoopGroup();// worker

        // 1.创建业务处理 handler
        NettyServerHandler serverHandler = new NettyServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(
                            NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(
                            new ChannelInitializer<SocketChannel>() {
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
                                    // 1.4添加业务handler  到管线
                                    p.addLast(serverHandler);
                                }
                            });

            // 2.启动服务，并且在12800端口监听
            ChannelFuture f = b.bind(12800).sync();

            // 3. 等待服务监听套接字关闭
            f.channel().closeFuture().sync();
        } finally {
            // 4.优雅关闭两级线程池，以便释放线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}

}
