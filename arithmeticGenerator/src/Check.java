import java.util.*;

public class Check {
    /**
     * 检查式子
     * @param formula 为 式子
     * @return ansFormula 为 当前式子 的 计算结果，若ansFormula为null，则不符合条件
     */
    public String[] checkout(String formula,int length){
        // 操作数 && 操作符 && 逆波兰表达式
        Stack<String> stackNumber = new Stack<>();
        Stack<String> stackOperator = new Stack<>();
        String[] reversePolishNotation = new String[length];
        // 哈希表 存放运算符优先级
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("(", 0);
        hashmap.put("＋", 1);
        hashmap.put("－", 1);
        hashmap.put("×", 2);
        hashmap.put("÷", 2);

        for (int i=0,j=0; i < formula.length();) {
            //StringBuffer类中的方法主要偏重于对于字符串的变化，例如追加、插入和删除等，这个也是StringBuffer和String类的主要区别。
            StringBuilder digit = new StringBuilder();
            //将 式子 切割为 c字符
            char c = formula.charAt(i);
            //若 c字符 为10进制数字,将 c字符 加入digit（可以将多位数一起储存为一个数）
            while (Character.isDigit(c)||c=='/'||c=='\'') {
                digit.append(c);
                i++;
                c = formula.charAt(i);
            }

            if (digit.length() == 0){ //当前digit里面已经无数字，即当前处理符号
                switch (c) {
                    //如果是“(”转化为字符串压入字符栈
                    case '(': {
                        stackOperator.push(String.valueOf(c));
                        break;
                    }
                    //遇到“)”了，则进行计算，因为“(”的优先级最高
                    case ')': {
                        //将 stackOperator 栈顶元素取到 operator
                        String operator = stackOperator.pop();
                        //当前符号栈里面还有 ＋ － × ÷时，取 操作数 并 运算
                        while (!stackOperator.isEmpty() && !operator.equals("(")) {
                            //取操作数a,b
                            String a = stackNumber.pop();
                            String b = stackNumber.pop();
                            //后缀表达式变形
                            reversePolishNotation[j++] = a;
                            reversePolishNotation[j++] = b;
                            reversePolishNotation[j++] = operator;
                            //计算
                            String ansString = calculate(b, a, operator);
                            //如果 结果 不满足 要求 则 return -1，该式子不满足条件
                            if(ansString == null)
                                return  null;
                            //将结果压入栈
                            stackNumber.push(ansString);
                            //符号指向下一个计算符号
                            operator = stackOperator.pop();
                        }
                        break;
                    }
                    //遇到了“=”，则计算最终结果
                    case '=': {
                        String operator;
                        //当前符号栈里面还有 ＋ － × ÷时，即还没有算完，取 操作数 并 运算
                        while (!stackOperator.isEmpty()) {
                            //取值 && 取操作数
                            operator = stackOperator.pop();
                            String a = stackNumber.pop();
                            String b = stackNumber.pop();
                            //后缀表达式变形
                            reversePolishNotation[j++] = a;
                            reversePolishNotation[j++] = b;
                            reversePolishNotation[j++] = operator;
                            //计算
                            String ansString = calculate(b, a, operator);
                            if(ansString == null)
                                return null;
                            stackNumber.push(ansString);
                        }
                        break;
                    }
                    //不满足之前的任何情况
                    default: {
                        String operator;
                        //当前符号栈里面还有 ＋ － × ÷时，取 操作数 并 运算
                        while (!stackOperator.isEmpty()) {
                            //当前符号栈，栈顶元素
                            operator = stackOperator.pop();
                            if (hashmap.get(operator) >= hashmap.get(String.valueOf(c))) { //比较优先级
                                //取值
                                String a = stackNumber.pop();
                                String b = stackNumber.pop();
                                //后缀表达式变形
                                reversePolishNotation[j++] = a;
                                reversePolishNotation[j++] = b;
                                reversePolishNotation[j++] = operator;
                                //计算
                                String ansString =calculate(b, a, operator);
                                if(ansString == null)
                                    return  null;
                                stackNumber.push(ansString);
                            }
                            else {
                                stackOperator.push(operator);
                                break;
                            }

                        }
                        stackOperator.push(String.valueOf(c));  //将符号压入符号栈
                        break;
                    }
                }
            }
            //处理数字，直接压栈
            else {
                stackNumber.push(digit.toString());
                //reversePolishNotation[j++] = digit.toString();
                continue; //结束本次循环，回到for语句进行下一次循环，即不执行i++(因为此时i已经指向符号了)
            }
            i++;
        }
        //获取 栈顶数字 即 等式的最终答案
        reversePolishNotation[length-3] = "=";
        reversePolishNotation[length-2] = stackNumber.peek();
        reversePolishNotation[length-1] = formula;
        return reversePolishNotation;
    }

    /**
     * 计算整数运算结果
     * @param m 为 操作数
     * @param n 为 操作数
     * @param operator 为 运算符
     * @return ansFormula 为 当前式子 的 计算结果，若ansFormula为null，则不符合条件
     */
    private String calculate(String m,String n,String operator) {
        String ansFormula = null;//计算结果
        char op = operator.charAt(0);//符号
        //System.out.println(m + operator + n);

        //处理分数运算
        int[] indexFraction = new int[4];
        indexFraction[0] = m.indexOf('\'');
        indexFraction[1] = m.indexOf('/');
        indexFraction[2] = n.indexOf('\'');
        indexFraction[3] = n.indexOf('/');

        if (indexFraction[1] > 0 && indexFraction[3] > 0) {
            int[] denominator = new int[3];
            int[] molecule = new int[3];
            int[] integralPart = new int[3];

            for (int i = 0; i < m.length(); i++) {
                if (i < indexFraction[0]) {
                    integralPart[0] = integralPart[0] + m.charAt(i) - '0';
                }else if (i > indexFraction[0] && i < indexFraction[1]) {
                    molecule[0] = molecule[0] + m.charAt(i) - '0';
                }else if (i > indexFraction[1]){
                    denominator[0] = denominator[0] + m.charAt(i) - '0';
                }
            }

            for (int i = 0; i < n.length(); i++) {
                if (i < indexFraction[2]) {
                    integralPart[1] = integralPart[1] + n.charAt(i) - '0';
                } else if (i > indexFraction[2] && i < indexFraction[3]){
                    molecule[1] = molecule[1] + n.charAt(i) - '0';
                }else if (i > indexFraction[3]) {
                    denominator[1] = denominator[1] + n.charAt(i) - '0';
                }
            }

            switch (op) {
                case '＋': {
                    integralPart[2] = integralPart[0] + integralPart[1];
                    molecule[2] = molecule[0] * denominator[1] + molecule[1] * denominator[0];
                    denominator[2] = denominator[0] * denominator[1];

                    if (molecule[2] >= denominator[2]) {
                        integralPart[2] = integralPart[2] + molecule[2] / denominator[2];
                        molecule[2] = molecule[2] % denominator[2];
                    }
                    ansFormula = String.valueOf(integralPart[2]) + "'" + String.valueOf(molecule[2]) + '/' + String.valueOf(denominator[2]);
                    break;
                }
                case '－': {
                    integralPart[2] = integralPart[0] - integralPart[1];
                    molecule[2] = molecule[0] * denominator[1] - molecule[1] * denominator[0];
                    denominator[2] = denominator[0] * denominator[1];

                    if (molecule[2] < 0) {
                        integralPart[2]--;
                        molecule[2] = denominator[2] - molecule[2];
                    }
                    //System.out.println(integralPart[2]+","+molecule[2] +"," + denominator[2]);
                    //分数显示
                    if (integralPart[2] == 0 && molecule[2] > 0) {
                        ansFormula = String.valueOf(molecule[2]) + '/' + String.valueOf(denominator[2]);
                    } else if (molecule[2] == 0)
                        ansFormula = String.valueOf(integralPart[2]);
                    else {
                        ansFormula = String.valueOf(integralPart[2]) + "'" + String.valueOf(molecule[2]) + '/' + String.valueOf(denominator[2]);
                    }

                    if (integralPart[2] < 0) {
                        System.out.println("fraCal1:"+ansFormula);
                        return null;
                    }
                    break;
                }
            }
            //System.out.println("fraCal1:"+ansFormula);
            return ansFormula;

        } else if (indexFraction[1] > 0 || indexFraction[3] > 0) {
            int denominator = 0;
            int molecule = 0;
            int[] integralPart = new int[3];

            if (indexFraction[1] > 0) {
                for (int i = 0; i < m.length(); i++) {
                    if (i < indexFraction[0]) {
                        integralPart[0] = integralPart[0] + m.charAt(i) - '0';
                    }else if (i > indexFraction[0] && i < indexFraction[1]) {
                        molecule = molecule + m.charAt(i) - '0';
                    }else if (i > indexFraction[1]) {
                        denominator = denominator + m.charAt(i) - '0';
                    }
                }
                integralPart[1] = Integer.parseInt(n);
            } else if (indexFraction[3] > 0) {
                for (int i = 0; i < n.length(); i++) {
                    if (i < indexFraction[2]) {
                        integralPart[1] = integralPart[1] + n.charAt(i) - '0';
                    }else if (i > indexFraction[2] && i < indexFraction[3]) {
                        molecule = molecule + n.charAt(i) - '0';
                    }else if (i > indexFraction[3]) {
                        denominator = denominator + n.charAt(i) - '0';
                    }
                }
                integralPart[0] = Integer.parseInt(m);
            }

            switch (op) {
                case '＋': {
                    integralPart[2] = integralPart[0] + integralPart[1];

                    ansFormula = String.valueOf(integralPart[2]) + "'" + String.valueOf(molecule) + '/' + String.valueOf(denominator);
                    break;
                }
                case '－': {
                    //System.out.println(integralPart[0] + ","+ integralPart[1]+","+molecule +"," + denominator);
                    integralPart[2] = integralPart[0] - integralPart[1];

                    if (indexFraction[3] > 0) {
                        if (integralPart[2]>0) {
                            integralPart[2]--;
                            molecule = denominator - molecule;
                        } else if (integralPart[2]==0){
                            ansFormula = "-";
                        }
                    }

                    //System.out.println(integralPart[2]+","+molecule +"," + denominator);
                    //分数显示
                    if (integralPart[2] == 0 && molecule > 0)
                        ansFormula = String.valueOf(molecule) + '/' + String.valueOf(denominator);
                    else if (molecule == 0)
                        ansFormula = String.valueOf(integralPart[2]);
                    else
                        ansFormula = String.valueOf(integralPart[2]) + "'" + String.valueOf(molecule) + '/' + String.valueOf(denominator);

                    if (integralPart[2] < 0) {
                        //System.out.println("fraCal2:" + ansFormula);
                        return null;
                    }
                    break;
                }
            }
            //System.out.println("fraCal2:"+ansFormula);
            return ansFormula;
        } else {

            //处理整数运算
            int a = Integer.parseInt(m);
            int b = Integer.parseInt(n);

            switch (op) {
                case '＋': {
                    ansFormula = String.valueOf(a + b);
                    break;
                }
                case '－': {
                    if (a - b >= 0)
                        ansFormula = String.valueOf(a - b);
                    else {
                        //System.out.println("intCal:"+ansFormula);
                        return null;
                    }
                    break;
                }
                case '×': {
                    ansFormula = String.valueOf(a * b);
                    break;
                }
                case '÷': {
                    if (b == 0) {
                        //System.out.println("intCal:"+ansFormula);
                        return null;
                    }else if (a % b != 0)
                        ansFormula = a / b + "'" + a % b + "/" + b;
                    else
                        ansFormula = String.valueOf(a / b);
                    break;
                }
            }
            //System.out.println("intCal:"+ansFormula);
            return ansFormula;
        }
    }

//    /**
//     * 分割分数，以进行计算
//     * @param x 为 操作数 的 分母
//     * @param y 为 操作数 的 分子
//     * @return formula 为 当前式子 的 字符串形式
//     */
//    public String greatFactor(String m,int[] indexFraction,int[] integralPart,int molecule,int denominator)
//    {
//        for (int i = 0; i < m.length(); i++) {
//            if (i < indexFraction[0]) {
//                integralPart[0] = integralPart[0] + m.charAt(i) - '0';
//            }else if (i > indexFraction[0] && i < indexFraction[1]) {
//                molecule = molecule + m.charAt(i) - '0';
//            }else if (i > indexFraction[1]){
//                denominator = denominator + m.charAt(i) - '0';
//            }
//        }
//    }

    /**
     * 化简分数
     * @param x 为 操作数 的 分母
     * @param y 为 操作数 的 分子
     * @return formula 为 当前式子 的 字符串形式
     */
    public String greatFactor(int x,int y)
    {

        Create create = new Create();
        if (y!=0) {
            int commonFactor = create.commonFactor(x,y);
            x /= commonFactor;
            y /= commonFactor;
        }
        return x+"/"+y;
    }
}
