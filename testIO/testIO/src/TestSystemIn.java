import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestSystemIn {

    /*
    题目：从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，直至当输入“e”或者“exit”时，退出程序。
     */
    public  static void testSystemIn(){
        BufferedReader br = null;
        try {
            // 1、从键盘输入
            InputStream is = System.in;

            // 2、将字节流转换成字符流
            InputStreamReader isr = new InputStreamReader(is);

            // 3、缓冲流包装
            br = new BufferedReader(isr);

            // 4、编写需求内容
            String str;
            while (true){
                System.out.println("请输入英文字符串:");
                str = br.readLine();
                if (str.equalsIgnoreCase("e") || str.equalsIgnoreCase("exit")){
                    break;
                }
                String newStr = str.toUpperCase();
                System.out.println(newStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 5、关闭资源
        finally {
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
        testSystemIn();
    }
}
