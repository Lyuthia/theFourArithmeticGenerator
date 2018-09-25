import java.util.Random;

public class Create {
    public String createFormula(int r){
        Random random = new Random();
        String[] operator = {"+","-","*","/"};//{"＋","－","×","÷","＝"}

        //运算符 && 操作数 && 式子
        String[] totalOperator = new String[1 + random.nextInt(2)];
        int[] totalNumber = new int[totalOperator.length+1];
        String formula = new String();

        //随机产生  操作数 && 运算符
        for (int i=0;i<totalOperator.length;i++) {

            //操作数：
            int fractionOrNot = random.nextInt(2);
            System.out.println("fractionOrNot："+fractionOrNot);
            if (fractionOrNot == 0) {
                totalNumber[i] = random.nextInt(r);
            } else {
                int denominator = random.nextInt(11);
                int molecule = random.nextInt(denominator);
                int integralPart = random.nextInt(r);

                totalNumber[i] = integralPart + (molecule / denominator);//分数
                System.out.println("totalNumber：["+i+"]"+totalNumber[i]);
            }

            //运算符
            if (i < totalOperator.length) {
                totalOperator[i] = operator[random.nextInt(3)];
            }
        }

        //选择式子括号起始位置
        int choose = random.nextInt(totalNumber.length);
        //生成式子
        for (int i=0;i<totalNumber.length;i++) {


            if (i == choose && choose<totalOperator.length) {
                formula = formula + "(" + totalNumber[i] + totalOperator[i] ;
            } else if (i == totalNumber.length - 1 && i == choose+1 && choose<totalOperator.length) {
                formula = formula + totalNumber[i] + ")" + "=";
            } else if (i == choose+1 && choose<totalOperator.length) {
                formula = formula + totalNumber[i] + ")" + totalOperator[i];
            } else if (i == totalNumber.length - 1) {
                formula = formula + totalNumber[i] + "=";
            } else {
                formula = formula + totalNumber[i] + totalOperator[i];
            }
        }

        System.out.println("formula："+formula);
        return formula;
    }
}
