package nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileChannelTest {

    @Test
    public void test2() {
        System.out.println("test2");
        // System.out.println(System.getProperty(“user.dir”));
        try {
            RandomAccessFile  aFile = new RandomAccessFile("F:\\mnt\\admin\\data\\0.txt", "rw");

            FileChannel inChannel = aFile.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(48);

            int byteRead = 0;
            try {
                byteRead = inChannel.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (byteRead != -1) {
                System.out.println("Read " + byteRead);
                buf.flip();
                byte[] bytes = new byte[byteRead];
                int index = 0;
                while (buf.hasRemaining()) {
                    bytes[index] = buf.get();
                    index++;
                }
                System.out.println(new String(bytes, "utf-8"));
                buf.clear();
                byteRead = inChannel.read(buf);
            }
            aFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 该程序步骤：
     * 1. 读取文件获取FileChannel；
     * 2. 将ByteBuffer分配大小，并得到ByteBuffer
     * 3. 将通道的数据读取到buffer（读取的数据是将数据一次性读到buffer，如果buffer太小，那么读到的文件数据就会缺失）；返回值为读取数，读完为-1；
     * 4. 将buffer中的limit定位到文件尾，也就是如果文件大小229，你设置buffer的limit为1000，他会将limit设为229；
     * 5. 如果读取位置和limit之间还有数据，打印byte数据
     * 6. 关闭buffer；
     * 7. 关闭channel；
     */
    @Test
    public void test3() {

        try {
            //创建一个capacity为48的ByteBuffer对象
            ByteBuffer bb = ByteBuffer.allocate(48);

            System.out.println("Read ---------- 0.txt");


            RandomAccessFile raf = new RandomAccessFile("F:\\mnt\\admin\\data\\0.txt", "rw");


            FileChannel channel = raf.getChannel();



            int read = channel.read(bb);

            while (read != -1) {

                System.out.println("Read " + bb);

                bb.flip();

                while (bb.hasRemaining()) {
                    System.out.print((char) bb.get());
                }

                bb.clear();
                read = channel.read(bb);

            }
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void test1 () throws IOException{
        RandomAccessFile aFile = new RandomAccessFile("F:\\mnt\\admin\\data\\0.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

//分配缓存区大小
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = 0;
        while((bytesRead = inChannel.read(buf)) != -1) {
            System.out.println("Read" + bytesRead);
            buf.flip();

            System.out.println("Position : " + inChannel.position());

            while(buf.hasRemaining()) {
                System.out.print((char)buf.get());
            }

            buf.clear();
            inChannel.read(buf);
        }
        aFile.close();
    }


    @Test
    public void test4(){
        List<String> list = new ArrayList<String>();
        list.add("h");
        list.add("e");
        list.add("l");
        list.add("l");
        list.add("o");

        List<String> z = list.stream().filter(str -> !str.equals("e")).map( str -> str+=" is not e").collect(Collectors.toList());

        System.out.println(z);
//        System.out.println(e);



    }




}
