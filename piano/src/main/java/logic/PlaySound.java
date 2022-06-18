package logic;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的线程
public class PlaySound extends Thread {
    private String filename;

    public PlaySound(String wavfile) {

        filename = wavfile;
    }

    public void run() {
        File soundFile = new File(filename);
        AudioInputStream audioInputStream = null;
        try {
            //获得音频输入流
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }
        //指定声音流中特定数据安排
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        try {
            //从混频器获得源数据行
            auline = (SourceDataLine) AudioSystem.getLine(info);
            //打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //允许数据行执行数据 I/O
        auline.start();
        int nBytesRead = 0;
        // 这是缓冲
        byte[] abData = new byte[512];
        try {
            while (nBytesRead != -1) {
                //从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    //通过此源数据行将音频数据写入混频器
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }
}

