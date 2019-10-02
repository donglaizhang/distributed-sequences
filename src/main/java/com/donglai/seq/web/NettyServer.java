package com.donglai.seq.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

    private int httpPort=90;
    public NettyServer(int httpPort) {
        this.httpPort = httpPort;
    }

    public void run() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(eventLoopGroup)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer())
                    .channel(NioServerSocketChannel.class);

            Channel ch = bootstrap.bind(httpPort).sync().channel();
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
    public static void main(String args[]) {
        NettyServer ns = new NettyServer(8080);
        ns.run();

    }
}
