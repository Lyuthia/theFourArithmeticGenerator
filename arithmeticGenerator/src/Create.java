import java.util.*;

public class Create {
    /**
     * 式子生成器
     * totalOperator 为 当前式子 的 运算符 数组
     * totalNumber 为 当前式子 的 操作数 数组
     * totalFraction 为 当前式子 的 运算符 数组
     * @param r 为 操作数 的 范围
     * @return formula 为 当前式子 的 字符串形式
     */
    public String[] createFormula(int r){
        Random random = new Random();
        //String[] operator = {"+","-","*","/"};
        String[] operator = {"＋","－","×","÷","＝"};

        //运算符 && 操作数 && 式子
        String[] totalOperator = new String[1 + random.nextInt(2)];
        //Double[] totalNumber = new Double[totalOperator.length+1];
        String[] totalFraction = new String[totalOperator.length+1];
        String formula = new String();
        //是否包含分数
        Boolean hasFraction = false;

        //随机产生  操作数 && 运算符
        for (int i=0;i<totalFraction.length;i++) {

            //操作数：
            int fractionOrNot = random.nextInt(2);
            //System.out.println("fractionOrNot["+i+"]："+fractionOrNot);
            if (fractionOrNot == 0) {
                int integralPart = random.nextInt(r);
                //totalNumber[i] = (double)integralPart;
                totalFraction[i] = String.valueOf(integralPart);
            } else {
                int denominator = 1+random.nextInt(9);
                int molecule = random.nextInt(denominator);
                int integralPart = random.nextInt(r);
                //化简分数
                if (molecule!=0) {
                    int commonFactor = commonFactor(denominator, molecule);
                    denominator /= commonFactor;
                    molecule /= commonFactor;
                }

                //分数
                //totalNumber[i] = integralPart + (double)molecule / denominator;
                if (integralPart == 0 && molecule > 0) {
                    totalFraction[i] = molecule + "/" + denominator;
                    hasFraction = true;
                }
                else if (molecule == 0)
                    totalFraction[i] = String.valueOf(integralPart);
                else {
                    totalFraction[i] = integralPart + "'" + molecule + "/" + denominator;
                    hasFraction = true;
                }
            }
            //System.out.println("totalNumber："+Arrays.toString(totalNumber));
        }

        //运算符
        for (int i=0;i < totalOperator.length;i++) {
            if (hasFraction)
                totalOperator[i] = operator[random.nextInt(2)];
            else
                totalOperator[i] = operator[random.nextInt(4)];
        }

        //选择式子括号起始位置
        int choose = totalFraction.length;
        if (totalFraction.length != 2 )
            choose = random.nextInt(totalFraction.length);
        //生成式子
        for (int i=0;i<totalFraction.length;i++) {

            if (i == choose && choose<totalOperator.length) {
                formula = formula + "(" + totalFraction[i] + totalOperator[i] ;
            } else if (i == totalFraction.length - 1 && i == choose+1 && choose<totalOperator.length) {
                formula = formula + totalFraction[i] + ")" + "=";
            } else if (i == choose+1 && choose<totalOperator.length) {
                formula = formula + totalFraction[i] + ")" + totalOperator[i];
            } else if (i == totalFraction.length - 1) {
                formula = formula + totalFraction[i] + "=";
            } else {
                formula = formula + totalFraction[i] + totalOperator[i];
            }
        }

        //检查运算结果
        Check check = new Check();
        String[] ansFormula = check.checkout(formula,3*totalOperator.length+2+1);//2*totalOperator.length+3

        //System.out.println("ansFormula："+Arrays.toString(ansFormula));
        if (ansFormula!=null)
            return ansFormula;
        return null;
    }

    /**
     * 求最大公因数，以化简分数
     * @param x 为 操作数 的 分母
     * @param y 为 操作数 的 分子
     * @return formula 为 当前式子 的 字符串形式
     */
    public int commonFactor(int x,int y)
    {
        while(true)
        {
            if(x%y==0)return y;
            int temp=y;
            y=x%y;
            x=temp;
        }
    }

}
