import java.io.*;

public class TestInputStreamReader {

    public  static  void  testInputStreamReaderAndOutputStreamReader(){
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // 1、解码
            // 1-1 创建字节输入流
            FileInputStream fis = new FileInputStream(new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello.txt"));

            // 1-2 解码：以指定的字符集GBK，将字节数组解码为字符串
            InputStreamReader isr = new InputStreamReader(fis,"GBK");

            // 1-3 缓冲流包装
            br = new BufferedReader(isr);

            // 2、编码
            // 2-1 创建字节输出流对象
            FileOutputStream fos = new FileOutputStream(new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello3.txt"));

            // 2-2 编码：以指定的字符集GBK，将字符串编码为字节数组
            OutputStreamWriter osw =  new OutputStreamWriter(fos);

            // 2-3 缓冲流包装
            bw = new BufferedWriter(osw);

            // 3、转换
            String string;
            while ((string = br.readLine()) != null){
                bw.write(string);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args){
        testInputStreamReaderAndOutputStreamReader(); 
    }
}
