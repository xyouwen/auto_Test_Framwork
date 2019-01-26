import java.io.*;

public class TestFileInputStream {

    /**
     * 测试输入流
     */
    public static void testFileInputStream(){
        FileInputStream fis = null;
        try {
            // 1、创建一个File类的对象 (注意：输出流的文件路径一定要存在，否则会报FileNotFoundException)
            File file = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello.txt");

            // 2、创建一个FileInputStream类的对象（输入流对象）
            fis = new FileInputStream(file);

            // 3、调用read方法，实现文件的读取

            /*
            //3-1 数组容器：实例化一个数组容器，这个数组的长度是5，每次读取的数据就装入到数组（就比如：用瓢在桶里取水，瓢的大小是5升，每次取的水都装在瓢里）
            byte[] bytes = new byte[5];  // 5：数组容器的大小视具体文件大小而定

            // 3-2 初始化：读取到容器b中的字节数（比如：一桶水有101升， 一次取5升，那前20次len的值就是5，第21次len的值就是1，第22次len的值就是-1）
            int len = fis.read(bytes);
            while (len != -1){
                System.out.println("读取到的字符的长度为：" + len);

                // 3-3 把每次读取的内容，打印出来
                for (int i = 0; i < len; i++) {
                    System.out.print((char) bytes[i]);
                }
                // 3-4 每取一次，重新计算len的值（比如：每取一次水，就查看一下瓢里装了多少水）
                len = fis.read(b);
                System.out.println();

                简易写法如下：
            }
            */

            byte [] bytes = new byte[5];
            int len;
            while ((len = fis.read(bytes)) != -1){
                System.out.println("读取到的字符的长度为：" + len);
                String str = new String(bytes,0,len);
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    // 4、关闭输入字节流（程序中打开的文件IO资源不属于内存里的资源，垃圾回收机制无法回收该资源，所以应该显式关闭文件 IO 资源）
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试输出流
     */
    public  static  void testFileOutputStream(){
        FileOutputStream fos = null;
        try {
            // 1、创建一个File类的对象（注意：输出流时，文件路径可以不存在； 当不存在时，会自动创建该路径；当存时，会覆盖该路径）
            File file = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello1.txt");

            // 2、创建一个FileOutputStream类的对象（输出流对象）
            fos = new FileOutputStream(file);

            // 3、调用输出流对象的writer方法
            fos.write(new String("I love China, I love world").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    // 4、关闭输出流
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试输入输出流：复制
     */
    public static void testFileInputStreamAndFileOutputStream(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            // 源:
            File res = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/14M.jpg");
            fis = new FileInputStream(res);

            // 目标：
            File dest = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/14M-2.jpg");
            fos = new FileOutputStream(dest);

            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1){
                fos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 注意：先关闭输出流，再关闭输入流
        finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试缓冲输入流、缓冲输出流
     */
    public static void testBufferedInputStreamAndBufferedOutputStream(){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            // 源:
            File res = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/14M.jpg");
            FileInputStream fis = new FileInputStream(res);
            bis = new BufferedInputStream(fis);


            // 目标：
            File dest = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/14M-3.jpg");
            FileOutputStream fos = new FileOutputStream(dest);
            bos = new BufferedOutputStream(fos);

            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1){
                bos.write(bytes,0,len);
                bos.flush(); // 这里最好要刷新一下
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 注意：先关闭输出流，再关闭输入流
        finally {
            if (bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
//        testFileInputStream();
//        testFileOutputStream();
        long start = System.currentTimeMillis();
        testFileInputStreamAndFileOutputStream();
//        testBufferedInputStreamAndBufferedOutputStream();
        long end = System.currentTimeMillis();
        System.out.println("花费的时间 = " + (end - start));
    }


}
