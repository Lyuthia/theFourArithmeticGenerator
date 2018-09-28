import java.util.Stack;
import java.util.HashMap;

public class CheckAns {
    /**
     * 检查式子结果、生成逆波兰表达式
     * @param formula 为 式子
     * @return reversePolishNotation 为 当前式子 的 （改良）后缀表达式 && 结果 && 字符串形式 的 数组
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
     * 计算式子运算结果
     * @param m 为 操作数
     * @param n 为 操作数
     * @param operator 为 运算符
     * @return ansFormula 为 当前式子 的 计算结果，若ansFormula为null，则不符合条件
     */
    private String calculate(String m,String n,String operator) {
        String ansFormula = null;//计算结果
        char op = operator.charAt(0);//符号
        int[] indexFraction = {m.indexOf('\''), m.indexOf('/'), n.indexOf('\''), n.indexOf('/')};//分数 各部分 切割位置

        //处理 含分数 的 运算
        if (indexFraction[1] > 0 || indexFraction[3] > 0) {
            int[] denominator = new int[3];
            int[] molecule = new int[3];
            int[] integralPart = new int[3];

            //切割分数
            if (indexFraction[1] > 0) {
                for (int i = 0; i < m.length(); i++) {
                    if (i < indexFraction[0]) {
                        integralPart[0] = Integer.parseInt(integralPart[0] + String.valueOf(m.charAt(i) - '0'));
                    } else if (i > indexFraction[0] && i < indexFraction[1]) {
                        molecule[0] = Integer.parseInt(molecule[0] + String.valueOf(m.charAt(i) - '0'));
                    } else if (i > indexFraction[1]) {
                        denominator[0] = Integer.parseInt(denominator[0] + String.valueOf(m.charAt(i) - '0'));
                    }
                }
            } else {
                integralPart[0] = Integer.parseInt(m);
                denominator[0] = 1;
                molecule[0] = 0;
            }

            if (indexFraction[3] > 0) {
                for (int i = 0; i < n.length(); i++) {
                    if (i < indexFraction[2]) {
                        integralPart[1] = Integer.parseInt(integralPart[1] + String.valueOf(n.charAt(i) - '0'));
                    } else if (i > indexFraction[2] && i < indexFraction[3]) {
                        molecule[1] = Integer.parseInt(molecule[1] + String.valueOf(n.charAt(i) - '0'));
                    } else if (i > indexFraction[3]) {
                        denominator[1] = denominator[1] + n.charAt(i) - '0';
                    }
                }
            } else {
                integralPart[1] = Integer.parseInt(n);
                denominator[1] = 1;
                molecule[1] = 0;
            }

            //分数运算
            switch (op) {
                case '＋': {
                    denominator[2] = denominator[0] * denominator[1];
                    molecule[2] = integralPart[0] * denominator[2] + molecule[0] * denominator[1]
                            + integralPart[1] * denominator[2] + molecule[1] * denominator[0];
                    break;
                }
                case '－': {
                    denominator[2] = denominator[0] * denominator[1];
                    molecule[2] = integralPart[0] * denominator[2] + molecule[0] * denominator[1]
                            - integralPart[1] * denominator[2] - molecule[1] * denominator[0];
                    break;
                }
                default:
                    return null;
            }

            //提取整数部分
            if (molecule[2] >= denominator[2] && molecule[2]>0) {
                integralPart[2] = molecule[2] / denominator[2];
                molecule[2] = Math.abs(molecule[2] % denominator[2]);
            } else if (molecule[2]<0) {
                return null;
            }

            //化简分数
            if (molecule[2] != 0) {
                ansFormula = greatFraction(integralPart[2],molecule[2],denominator[2]);
            } else ansFormula = String.valueOf(integralPart[2]);

        } else { //处理整数运算
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
                    else
                        return null;
                    break;
                }
                case '×': {
                    ansFormula = String.valueOf(a * b);
                    break;
                }
                case '÷': {
                    if (b == 0) {
                        return null;
                    } else if (a % b != 0) {
                        ansFormula = a % b + "/" + b;
                        if (a / b > 0) ansFormula = a / b + "'" + ansFormula;
                    } else
                        ansFormula = String.valueOf(a / b);
                    break;
                }
            }
        }
        return ansFormula;
    }

    /**
     * 化简分数
     * @param integralPart 为 分数的整数部分
     * @param molecule 为 分数的分子部分
     * @param denominator 为 分数的分母部分
     * @return ansFormula 为 当前式子 的 最简分数表达形式
     */
    private String greatFraction (int integralPart,int molecule,int denominator) {
        String ansFormula;
        int commonFactor = 1;

        //求最大公约数
        Create create = new Create();
        commonFactor = create.commonFactor(denominator,molecule);

        //化简分数
        denominator /= commonFactor;
        molecule /= commonFactor;

        //带分数 表示
        if (integralPart == 0 && molecule > 0) {
            ansFormula = String.valueOf(molecule) + '/' + String.valueOf(denominator);
        } else if (molecule == 0)
            ansFormula = String.valueOf(integralPart);
        else {
            ansFormula = String.valueOf(integralPart) + "'" + String.valueOf(molecule) + '/' + String.valueOf(denominator);
        }

        //返回最简分数
        return ansFormula;
    }
}
