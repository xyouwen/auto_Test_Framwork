import java.io.*;

public class TestObjectStream {

    /**
     * 对象的序列化过程：将内存中的对象通过objectOutputStream转换为二进制流，存储在硬盘文件中
     */
    public  static  void testObjectOutputStream(){
        ObjectOutputStream oos = null;
        try {
            // 1、对象
            Person p1 = new Person("小米", 23, new Pet("大黄")) ;
            Person p2 = new Person("红米", 24, new Pet("小七"));

            // 2、对象序列化：新建一个对象输出流对象
            oos = new ObjectOutputStream(new FileOutputStream("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello4.txt"));

            // 3、写入对象
            oos.writeObject(p1);
            oos.flush();

            oos.writeObject(p2);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 对象的反序列化：将硬盘中的文件，通过ObjectInputStream转换为相应的对象
     */
    public static void testObjectInputStream(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("/Users/xyouwen/git/auto_Test_Framwork/testIO/testIO/resources/hello4.txt"));

            Person p1 = (Person)ois.readObject();
            System.out.println(p1);

            Person p2 = (Person) ois.readObject();
            System.out.println(p2);
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
//        testObjectOutputStream();
        testObjectInputStream();
    }
}


/**
 * 要实现序列化类的要求：
 * 1、要求此类必须是可序列化的，就是实现了Serializable接口
 * 2、要求此类的属性同样要实现Serializable接口
 * 3、提供一个版本号 private  static  final  long serialVesionUID
 * 4、使用static或transient的修饰的属性，不可实现序列化
 */
class Person implements Serializable {
    // 凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态变量,serialVersionUID用来表明类的不同版本间的兼容性
    //如果类没有显示定义这个静态变量，它的值是Java运行时环境根据类的内部细节自动生成的。若类的源代码作了修改，serialVersionUID 可能发生变化。
    private  static  final  long serialVesionUID = 123456789;

    String name;
    Integer age;
    // 类的属性也要实现了Serializable接口
    Pet pet;

    public Person(String name, int age, Pet pet) {
        this.name = name;
        this.age = age;
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", pet=" + pet +
                '}';
    }
}


class Pet implements  Serializable{
    String name;

    public Pet(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                '}';
    }
}
