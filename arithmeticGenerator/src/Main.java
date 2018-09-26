import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while(true) {
        /* **** 输入指令 **** */
            int n = 10;
            int r = 10;
            try {
                // 获取输入指令
                System.out.println("Please enter the command：");
                Scanner command = new Scanner(System.in);
                String arr[] = command.nextLine().split("\\s");

                //System.out.println(arr.length);//-n 5 -r 6
                if (arr.length > 1) {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        switch (arr[i]) {
                            case "-n":
                                n = Integer.parseInt(arr[i + 1]);
                                if (n > 10000 || n < 1) {
                                    System.out.println("对不起，只允许输入1-10000的数字！");
                                    return; //结束运行
                                }
                                break;
                            case "-r":
                                r = Integer.parseInt(arr[i + 1]);
                                break;
                            case "-e":
                                break;
                            case "-a":
                                break;
                            case "-g":
                                break;
                            default:
                                System.out.println("指令输入错误!");
                                break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("您输入的指令有误，请重新输入");
            }

            /* **** 执行函数 **** */
            System.out.println("n: " + n + ", r: " + r);
            Create create = new Create();
            String formula = create.createFormula(r);
        }
    }
}
