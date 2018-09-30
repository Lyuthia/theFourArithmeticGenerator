# 四则运算生成器（Java实现） #
The four arithmetic generator


#### github项目传送门：[https://github.com/Lyuthia/theFourArithmeticGenerator.git](https://github.com/Lyuthia/theFourArithmeticGenerator.git) ####

<br/>
> #### 使用说明 ####
> 1. 使用 “-n” 参数控制生成题目的个数
> 2. 使用 “-r” 参数控制题目中数值的范围
> 3. 使用 “-e & -a 组合命令” 给定 题目解答文件 和 正确答案文件，判定答案对错并进行数量统计
> 4. 命令不做顺序要求，可以单独使用一个命令，也可以同时使用多个命令
> 
> #### 举例说明 ####
> 1. `-n 20 -r 10 -e <exercisefile>.txt -a <answerfile>.txt`
> 2. `-n 10`
> 3. `-r 20`
> 4. `-e <exercisefile>.txt -a <answerfile>.txt`
> 5. `-n 663 -r 1`



<br/>
## 一、项目相关要求 ##
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;两人组队实现一个自动生成小学四则运算题目的命令行程序，具体要求见下面所述。<br/>

### 1. 基本功能：生成题集 && 答案集 ###

> #### 具体要求 ####
> - 生成的题目中计算过程不能产生负数，也就是说算术表达式中如果存在形如e1 − e2的子表达式，那么e1 ≥ e2。
> - 生成的题目中如果存在形如e1 ÷ e2的子表达式，那么其结果应是真分数。
> - 每道题目中出现的运算符个数不超过3个。
> - 程序一次运行生成的题目不能重复，即任何两道题目不能通过有限次交换+和×左右的算术表达式变换为同一道题目。
> - 程序应能支持一万道题目的生成。

#### 命令行： ####
- Myapp.exe -n [number] &nbsp;&nbsp;&nbsp;&nbsp;// 使用 -n 控制生成题目的个数
- Myapp.exe -r [number] &nbsp;&nbsp;&nbsp;&nbsp;// 使用 -r 控制题目中数值（自然数、真分数和真分数分母）的范围 

#### 生成结果： ####

- 生成的题集存入执行程序同级目录下的Exercises.txt文件，格式如下：

	    1. [四则运算题目1]
    	2. [四则运算题目2]
    	……

- 生成题集的同时也生成答案集，一并存入执行程序同级目录下的Answers.txt文件，格式如下：

	    1. [答案1]
    	2. [答案2]
    	……


### 2. 拓展功能：评判答题情况 ###

> #### 具体要求 ####
> - 给定解题文件和答案文件，判定答案中的对错并进行数量统计

#### 命令行： ####
- Myapp.exe -e \<exercisefile>.txt -a \<answerfile>.txt &nbsp;&nbsp;&nbsp;&nbsp;// 使用 -e 给出 解答文件的路径， 使用 -a 给出 答案文件的路径

#### 生成结果： ####
- 生成的成绩文件存入执行程序同级目录下的Grade.txt文件，格式如下：
- 其中“Correct: ”或“Wrong: ”后面的数字5表示对/错的题目的数量，括号内的数字是则对/错题目的编号。
- 为简单起见，假设输入的题目都是按照顺序编号的符合规范的题目。

	    Correct: 5 (1, 3, 5, 7, 9)
    
    	Wrong: 5 (2, 4, 6, 8, 10)



<br/>
## 二、遇到的困难及解决方法 ##
- 困难描述：由于使用编程语言的不同，在找队员的时候比较麻烦，组队开发项目时两人之间也需要一定时间的磨合期
- 做过哪些尝试：分布式版本控制系统开发
- 是否解决：是
- 有何收获：git是个很强大很好用的工具，能很好地提高团队合作效率



<br/>
## 三、设计程序流程 ##

#### 解题思路 ####
具体实现思路见关键代码
![程序设计图](https://i.imgur.com/rVpQ9ov.png)

#### 设计实现过程 ####
![流程图](https://i.imgur.com/OLKeK3y.png)



<br/>
## 四、关键代码 ##

#### 项目目录： ####
![项目目录](https://i.imgur.com/ZzUh6V3.png)
### 主函数代码： ###
	public static void main(String[] args) {
        while(true) {
            int n = 10;
            int r = 10;
            String submitPath = null;
            String answersPath = null;

            try {
                System.out.println("Please enter the command：");
                Scanner command = new Scanner(System.in);
                String arr[] = command.nextLine().split("\\s");

                //获取指令的相应参数
                if (arr.length > 1) {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        switch (arr[i]) {
                            case "-n":
                                n = Integer.parseInt(arr[i + 1]);
                                if (n > 10000 || n < 1) {
                                    System.out.println("对不起，只允许输入1-10000的数字！");
                                    return;
                                }
                                break;
                            case "-r":
                                r = Integer.parseInt(arr[i + 1]);
                                if (r < 1) {
                                    System.out.println("对不起，只允许大于等于1的自然数！");
                                    return;
                                }
                                break;
                            case "-e":
                                submitPath = arr[i + 1];
                                if (submitPath == null) {
                                    System.out.println("对不起，没有输入相应文件路径，请重新输入");
                                    return;
                                }
                                break;
                            case "-a":
                                answersPath = arr[i + 1];
                                if (answersPath == null) {
                                    System.out.println("对不起，没有输入相应文件路径，请重新输入");
                                    return;
                                }
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
            problemSet makefile = new problemSet();
            if (submitPath != null && answersPath != null)
                makefile.createGradeFile(submitPath,answersPath);
            else
                makefile.createProblemSet(n,r);
        }
    }
#### 随机生成表达式： ####
	/**
     * 式子生成器
     * totalOperator 为 当前式子 的 运算符 数组
     * formula 为 当前式子 的 字符串形式
     * totalFraction 为 当前式子 的 操作数 数组
     * @param r 为 操作数 的 范围
     * @return ansFormula 为 当前式子 的 逆波兰表达式 && 结果 && 字符串形式 的 数组
     */
    public String[] createFormula(int r){
        Random random = new Random();
        String[] operator = {"＋","－","×","÷","＝"};

        String[] totalOperator = new String[1 + random.nextInt(3)];
        String[] totalFraction = new String[totalOperator.length+1];
        String formula = new String();
        Boolean hasFraction = false;

        //操作数
        for (int i=0;i<totalFraction.length;i++) {

            int fractionOrNot = random.nextInt(2);
            if (fractionOrNot == 0) {
                int integralPart = random.nextInt(r+1);
                totalFraction[i] = String.valueOf(integralPart);
            } else {
                int denominator = 1+random.nextInt(r);
                int molecule = random.nextInt(denominator);
                int integralPart = random.nextInt(r+1);

                if (molecule!=0) {
                    int commonFactor = commonFactor(denominator, molecule);
                    denominator /= commonFactor;
                    molecule /= commonFactor;
                }

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
        }

        //运算符
        for (int i=0;i < totalOperator.length;i++) {
            if (hasFraction)
                totalOperator[i] = operator[random.nextInt(2)];
            else
                totalOperator[i] = operator[random.nextInt(4)];
        }

        int choose = totalFraction.length;
        if (totalFraction.length != 2 )
            choose = random.nextInt(totalFraction.length);

        //式子
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
        CheckAns checkAns = new CheckAns();
        String[] ansFormula = checkAns.checkout(formula,3*totalOperator.length+3);

        if (ansFormula!=null)
            return ansFormula;
        return null;
    }

	/**
     * 求最大公因数，以化简分数
     * @param x 为 操作数 的 分母
     * @param y 为 操作数 的 分子
     * @return y 为 最大公因数
     */
    public int commonFactor(int x,int y) {
        while(true)
        {
            if(x%y==0)return y;
            int temp=y;
            y=x%y;
            x=temp;
        }
    }

#### 式子运算、逆波兰表达式生成： ####
    /**
     * 检查式子结果、生成逆波兰表达式
     * @param formula 为 式子
     * @return reversePolishNotation 为 当前式子 的 （改良）后缀表达式 && 结果 && 字符串形式 的 数组
     */
    public String[] checkout(String formula,int length){
        // 操作数 && 操作符 && 逆波兰表达式 && 优先级
        Stack<String> stackNumber = new Stack<>();
        Stack<String> stackOperator = new Stack<>();
        String[] reversePolishNotation = new String[length];
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("(", 0);
        hashmap.put("＋", 1);
        hashmap.put("－", 1);
        hashmap.put("×", 2);
        hashmap.put("÷", 2);

		//判断运算符OR操作数，运算并得到逆波兰表达式
        for (int i=0,j=0; i < formula.length();) {
            StringBuilder digit = new StringBuilder();
            char c = formula.charAt(i);
            while (Character.isDigit(c)||c=='/'||c=='\'') {
                digit.append(c);
                i++;
                c = formula.charAt(i);
            }

            if (digit.length() == 0){
                switch (c) {
                    case '(': {
                        stackOperator.push(String.valueOf(c));
                        break;
                    }
                    case ')': {
                        String operator = stackOperator.pop();
                        while (!stackOperator.isEmpty() && !operator.equals("(")) {
                            
                            String a = stackNumber.pop();
                            String b = stackNumber.pop();
                            
                            reversePolishNotation[j++] = a;
                            reversePolishNotation[j++] = b;
                            reversePolishNotation[j++] = operator;
                            
                            String ansString = calculate(b, a, operator);
                            if(ansString == null)
                                return  null;
                            stackNumber.push(ansString);
                            operator = stackOperator.pop();
                        }
                        break;
                    }
                    case '=': {
                        String operator;
                        
                        while (!stackOperator.isEmpty()) {//取 操作数 并 运算
                            
                            operator = stackOperator.pop();
                            String a = stackNumber.pop();
                            String b = stackNumber.pop();
                            
                            reversePolishNotation[j++] = a;
                            reversePolishNotation[j++] = b;
                            reversePolishNotation[j++] = operator;
                            
                            String ansString = calculate(b, a, operator);
                            if(ansString == null)
                                return null;
                            stackNumber.push(ansString);
                        }

                        break;
                    }
                    default: {
                        String operator;
                        
                        while (!stackOperator.isEmpty()) {//取 操作数 并 运算
                            
                            operator = stackOperator.pop();
                            if (hashmap.get(operator) >= hashmap.get(String.valueOf(c))) { //比较优先级
                                String a = stackNumber.pop();
                                String b = stackNumber.pop();

                                reversePolishNotation[j++] = a;
                                reversePolishNotation[j++] = b;
                                reversePolishNotation[j++] = operator;

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

                        stackOperator.push(String.valueOf(c));
                        break;
                    }
                }
            }
            else {
                stackNumber.push(digit.toString());
                continue;
            }
            i++;
        }
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
		//计算结果 && 符号 && 分数切割位置
        String ansFormula = null;
        char op = operator.charAt(0);
        int[] indexFraction = {m.indexOf('\''), m.indexOf('/'), n.indexOf('\''), n.indexOf('/')};

        //处理 含分数 的 运算
        if (indexFraction[1] > 0 || indexFraction[3] > 0) {
            int[] denominator = new int[3];
            int[] molecule = new int[3];
            int[] integralPart = new int[3];

            //切割
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

            //运算
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

            //化简
            if (molecule[2] >= denominator[2] && molecule[2]>0) {
                integralPart[2] = molecule[2] / denominator[2];
                molecule[2] = Math.abs(molecule[2] % denominator[2]);
            } else if (molecule[2]<0) {
                return null;
            }

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

        Create create = new Create();
        commonFactor = create.commonFactor(denominator,molecule);
        denominator /= commonFactor;
        molecule /= commonFactor;

        if (integralPart == 0 && molecule > 0) {
            ansFormula = String.valueOf(molecule) + '/' + String.valueOf(denominator);
        } else if (molecule == 0)
            ansFormula = String.valueOf(integralPart);
        else {
            ansFormula = String.valueOf(integralPart) + "'" + String.valueOf(molecule) + '/' + String.valueOf(denominator);
        }

        return ansFormula;
    }

#### 表达式查重： ####
    /**
     * 生成暂存题集、答案集
     * @param n 为 需要的式子总数
     * @param r 为 式子中操作数的范围
     * @return returnList 为 题集&答案集
     */
    public ArrayList generate(int n,int r) {
        Create create = new Create();
        //生成n条不重复的式子
        for(int i=0;i<n;){
            String[] ansFormula = create.createFormula(r);
            if (ansFormula!=null)
                if (!ifRepeat(ansFormula)) i++;
        }

        //把式子及运算结果添加到returnList
        for (int i =0; i<2*n;i++) {
            if(i<n) {
                returnList.add(txtList.get(i));
            } else {
                returnList.add(ansList.get(i - n));
            }
        }
        return returnList;
    }

    /**
     * 判断式子是否重复
     * @param ansFormula 为 后缀表达式、运算结果、式子 的 数组
     * @return ifRepeat 表示当前式子是否重复
     */
    private boolean ifRepeat(String[] ansFormula) {
        String formula = ansFormula[ansFormula.length-1];
        String[] rPNotation = new String[ansFormula.length-1];
        System.arraycopy(ansFormula, 0, rPNotation, 0, ansFormula.length-1);
        boolean ifRepeat = false;

		for (String[] ansFo: ansFoList) {
            if (Arrays.equals(ansFo,rPNotation)) { //直接一一对应比较
                ifRepeat = true;
            } else if (ansFo.length == rPNotation.length && ansFo[ansFo.length-1].equals(rPNotation[rPNotation.length-1])){//若运算结果及长度一致，则式子可能重复，进一步比较
                int j=0;
                for (j=0;j<rPNotation.length-2;) {
                    boolean opRight = ansFo[j+2].equals("＋")||ansFo[j+2].equals("×");
                    boolean exRight = ansFo[j].equals(rPNotation[j + 1]) && ansFo[j + 1].equals(rPNotation[j]) && ansFo[j + 2].equals(rPNotation[j + 2]);
                    boolean copRight = ansFo[j].equals(rPNotation[j]) && ansFo[j + 1].equals(rPNotation[j + 1]) && ansFo[j + 2].equals(rPNotation[j + 2]);
                    //运算符前后两个操作数交换比较
                    if (exRight&&opRight) {
                        j = j + 3;
                    } else if (copRight) {
                        j = j + 3;
                    } else {
                        break;
                    }
                }
                if (j == rPNotation.length-2) {
                    ifRepeat = true;
                    break;
                }
            }
        }

        if (!ifRepeat) {
            this.txtList.add(formula);
            this.ansList.add(rPNotation[rPNotation.length-1]);
            this.ansFoList.add(rPNotation);
        }
        return ifRepeat;
    }

#### 生成并输出 Exercises.txt、Answers.txt： ####
    /**
     * 生成并输出Exercises.txt、Answer.txt
     * @param n 为 需要的式子总数
     * @param r 为 式子中操作数的范围
     */
    public void createProblemSet(int n,int r){
        checkRepeat temporarySet = new checkRepeat();
        ArrayList returnList = temporarySet.generate(n,r);
        ArrayList<String> txtList = new ArrayList<>();
        ArrayList<String> ansList = new ArrayList<>();

        for (int i =0;i<2*n;i++) {
            if(i<n) txtList.add(returnList.get(i).toString());
            else ansList.add(returnList.get(i).toString());
        }

        createEXEFile(txtList);
        createAnsFile(ansList);
    }

    /**
     * 生成并输出Exercises.txt
     * @param txtList 为 所得题集的 式子字符串
     */
    private void createEXEFile(ArrayList txtList){
        try{
            File exTXT = new File("../Exercises.txt");

            if (exTXT.exists()) {
                exTXT.delete();
            }
            if(exTXT.createNewFile()){
                System.out.println("创建Exercises.txt:");
                FileOutputStream txtFile = new FileOutputStream(exTXT);
                PrintStream q = new PrintStream(exTXT);
                q.println("学号：3216005168    姓名：Lyuthia    成绩：\n");

                for(int i=0;i<txtList.size();i++){
                    System.out.print(">");
                    q.println(i+1 + ". " +txtList.get(i));
                    System.out.println(i+1 + ". " +txtList.get(i));
                }

                txtFile.close();
                q.close();
                System.out.println("Exercises.txt 创建成功！");
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 生成并输出Answer.txt
     * @param ansList 为 所得答案集的 答案字符串
     */
    private void createAnsFile(ArrayList ansList){
        try{
            File ansTXT = new File("../Answer.txt");

            if (ansTXT.exists()) {
                ansTXT.delete();
            }
            if(ansTXT.createNewFile()){
                System.out.print("创建Answer.txt:");
                FileOutputStream ansFile = new FileOutputStream(ansTXT);
                PrintStream p = new PrintStream(ansTXT);
                p.println("答案：\n");

                for(int i=0;i<ansList.size();i++){
                    System.out.print(">");
                    p.println(i+1 + ". " +ansList.get(i));
                }
                ansFile.close();
                p.close();
                System.out.println("Answer.txt 创建成功！");
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

#### 生成并输出 Grade.txt： ####
    /**
     * 生成并输出Grade.txt
     * @param submitPath 为 解题文件 的 路径
     * @param answersPath 为 答案文件 的 路径
     */
    public void createGradeFile(String submitPath, String answersPath) {
        try {
            /* **** 获取指定文件的内容 **** */
            ArrayList<String> submitList = obtainAnswer(submitPath);
            ArrayList<String> answersList = obtainAnswer(answersPath);

            /* **** 评判成绩 **** */
            ArrayList<String> trueQuesNum = new ArrayList<>();
            ArrayList<String> falseQuesNum = new ArrayList<>();

            for (int i = 0; i < submitList.size(); i++) {
                if (submitList.get(i).equals(answersList.get(i)))
                    trueQuesNum.add(String.valueOf(i+1));
                else
                    falseQuesNum.add(String.valueOf(i+1));
            }

            /* **** 生成并输出文件 **** */
            File gradeTXT = new File("../Grade.txt");

            if (gradeTXT.exists()) {
                gradeTXT.delete();
            }
            if (gradeTXT.createNewFile()) {
                System.out.print("创建Grade.txt:");
                FileOutputStream gradeFile = new FileOutputStream(gradeTXT);
                PrintStream p = new PrintStream(gradeTXT);
                p.println("成绩：\n");

                p.print("Correct:");
                output(p, trueQuesNum);
                p.print("Wrong:");
                output(p, falseQuesNum);

                gradeFile.close();
                p.close();
                System.out.println("Grade.txt 创建成功！");
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 输出 成绩
     * @param quesNum 为 Correct/Wrong的 题目序号集
     */
    private void output(PrintStream p,ArrayList quesNum) {
        p.print(quesNum.size() +"(");
        for(int i=0;i<quesNum.size();i++){
            System.out.print(">");
            if (i<quesNum.size()-1)
                p.print(" " + quesNum.get(i) + "，");
            else
                p.print(" " + quesNum.get(i));
        }
        p.print(" )\n");
    }

    /**
     * 获取相应文件的 正确答案 或 答题答案
     * @param path 为 文件 路径
     */
    private ArrayList<String> obtainAnswer(String path) throws IOException {
        ArrayList<String> answerList = new ArrayList<>();
        BufferedReader answerFile = new BufferedReader(new FileReader(path));
        String answerLine = null;

        while ((answerLine = answerFile.readLine()) != null) {
            answerLine = answerLine.replace(" ", "");
            //int index = answerLine.indexOf('=') > answerLine.indexOf('.') ? answerLine.indexOf('=') : answerLine.indexOf('.');
            if (answerLine.indexOf('.') >= 0) {//index >= 0
                if (answerLine.length() > 2)
                    answerList.add(answerLine);
            }
        }
        return answerList;
    }


<br/>
## 五、测试运行 ##

#### 生成并输出 Exercises.txt、Answer.txt ####
![EATXT](https://i.imgur.com/VPy3Fi0.png)

#### 解答文件 ####
![SATXT](https://i.imgur.com/splizHy.png)

#### 生成并输出 Grade.txt ####
![GTXT](https://i.imgur.com/7hT0UAO.png)

#### 代码覆盖率 ####

- 生成题集的代码覆盖率

![生成题集的代码覆盖率](https://i.imgur.com/00w9qaB.png)

- 生成成绩的代码覆盖率

![生成成绩的代码覆盖率](https://i.imgur.com/ZhwcBRs.png)

**附**

[1万道 r=2 的 题目](https://github.com/Lyuthia/theFourArithmeticGenerator/blob/master/1wEX---r%3D2.txt "1万道 r=2 的 题目")

[1万道 r=2 的 题目 对应的 答案](https://github.com/Lyuthia/theFourArithmeticGenerator/blob/master/1wAN---r%3D2.txt "对应的 答案")



<br/>
## 六、PSP ##

| PSP2.1| Personal Software Process Stages| 预估耗时（分钟） | 实际耗时（分钟） |
|-----------------|----------------------|-----------------|----------------|
| Planning        | 计划                     |45              |45            |
| · Estimate        | · 估计这个任务需要多少时间    |· 45        |· 45          |
| Development     | 开发                      |960             |1095         |
| · Analysis        | · 需求分析 (包括学习新技术)   |· 120       |· 150        |
| · Design Spec     | · 生成设计文档               |· 45        |· 60         |
| · Design Review   | · 设计复审 (和同事审核设计文档)|· 45        |· 45         |
| · Coding Standard | · 代码规范 (为目前的开发制定合适的规范)|· 30 |· 30         |
| · Design          | · 具体设计              |· 120            |· 180        |
| · Coding          | · 具体编码              |· 480            |· 480        |
| · Code Review     | · 代码复审              |· 60             |· 60         |
| · Test            | · 测试（自我测试，修改代码，提交修改）|· 60  |· 90         |
| Reporting         | 报告                   |105               |120          |
| · Test Report     | · 测试报告              |· 45             |· 60         |
| · Size Measurement| · 计算工作量            |· 30              |· 30        |
| · Postmortem & Process Improvement Plan|· 事后总结, 并提出过程改进计划|· 30|· 30|
| 合计              |                        |1110               |1260         |



<br/>
## 七、项目小结 ##
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
这次项目的解决方案不唯一，二叉树及逆波兰表达式都是常用的解决方案，这与第一次的个人项目实现的较为单一的实现方法不同，这也让我们明白，了解一个功能的多种解决方案是必须的，然后才能根据自己的实际情况加以抉择，做出最优决策；这次项目也是自己第一次在项目实践中使用数据结构的相关知识，也明白了自己在数据结构这一块基础的薄弱，以前的项目主要是做前端开发这一块，没有用到过数据结构，所以对数据结构的确是疏忽了，这次项目使我明白了数据结构的重要性，今后自己一定会加强数据结构的学习。
