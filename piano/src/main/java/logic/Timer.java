package logic;

import java.io.IOException;

public class Timer extends Thread {

    public Timer() {
        long start = System.currentTimeMillis();// 程序启动时间的毫秒值

        while (true) {
            long now = System.currentTimeMillis();// 获取一秒之后的毫秒值
            long time = now - start;// 两个时间相减的到毫秒差
            System.out.println(time);
            System.out.format("%02d:%02d\n",
                    time / (1000 * 60) % 60/* 分 */,
                    time / 1000 % 60/* 秒 */);// 格式化字符串输出
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 控制台清屏
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void cls() throws IOException, InterruptedException {
//        新建一个 ProcessBuilder，其要执行的命令是 cmd.exe，参数是 /c 和 cls
        new ProcessBuilder("cmd", "/c", "cls")
                //将 ProcessBuilder 对象的输出管道和 Java 的进程进行关联，这个函数的返回值也是一个 ProcessBuilder
                .inheritIO()
                //开始执行 ProcessBuilder 中的命令
                .start()
                //等待 ProcessBuilder 中的清屏命令执行完毕
                //如果不等待则会出现清屏代码后面的输出被清掉的情况
                .waitFor(); // 清屏命令
    }
}
