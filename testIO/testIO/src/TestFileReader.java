import java.io.*;

public class TestFileReader {
    /**
     * 字符流、缓冲流的写入写出
     */
    public static  void testFileReaderAndFileWriter(){
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // 1、源
            // 1-1 创建源文件res对象
            File res = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello.txt");

            // 1-2 创建字符流读取对象
            FileReader fr = new FileReader(res);

            // 1-3 创建缓冲流读取对象
            br = new BufferedReader(fr);

            // 2、目标
            // 2-1 创建目标文件dest对象
            File dest = new File("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello2.txt");

            // 2-2 创建字符流写入对象
            FileWriter fw = new FileWriter(dest);

            // 2-3 创建缓冲流写入对象
            bw = new BufferedWriter(fw);

            // 3、读、写
            String string;
            while ((string = br.readLine()) != null){
                bw.write(string);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 4、关闭资源
        finally {
            if (bw != null){
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
        testFileReaderAndFileWriter(); 
    }
}
