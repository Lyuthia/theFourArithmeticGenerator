import java.io.*;
import java.util.ArrayList;

public class problemSet {
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

        //获取题集、答案集
        for (int i =0;i<2*n;i++) {
            if(i<n) txtList.add(returnList.get(i).toString());
            else ansList.add(returnList.get(i).toString());
        }
        //输出题集、、答案集
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

            //如果文件已存在，则删除文件
            if (exTXT.exists()) {
                exTXT.delete();
            }
            //创建文件成功？？
            if(exTXT.createNewFile()){
                System.out.println("创建Exercises.txt:");
                FileOutputStream txtFile = new FileOutputStream(exTXT);
                PrintStream q = new PrintStream(exTXT);
                q.println("学号：3216005168    姓名：Lyuthia    成绩：\n");

                for(int i=0;i<txtList.size();i++){
                    System.out.print(">");
                    q.println(i+1 + ". " +txtList.get(i));
                    //System.out.println(i+1 + ". " +txtList.get(i));
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

            //如果文件已存在，则删除文件
            if (ansTXT.exists()) {
                ansTXT.delete();
            }
            //创建文件成功？？
            if(ansTXT.createNewFile()){
                System.out.print("创建Answer.txt:");
                FileOutputStream ansFile = new FileOutputStream(ansTXT);
                PrintStream p = new PrintStream(ansTXT);
                p.println("答案：\n");

                for(int i=0;i<ansList.size();i++){//正常运行
                //for(int i=0;i<ansList.size()+1;i++){//测试代码覆盖率
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

    /**
     * 生成并输出Grade.txt
     * -e F:\5168-homework\theFourArithmeticGenerator\Answer.txt -a F:\5168-homework\theFourArithmeticGenerator\submit.txt
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

            //如果文件已存在，则删除文件
            if (gradeTXT.exists()) {
                gradeTXT.delete();
            }
            //创建文件成功？？
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
        for(int i=0;i<quesNum.size();i++){//正常运行
        //for(int i=0;i<quesNum.size()+1;i++){//测试代码覆盖率
            System.out.print(">");
            if (i<quesNum.size()-1)
                p.print(" " + quesNum.get(i) + "，");
            else
                p.print(" " + quesNum.get(i));
        }
        p.println(" )\n");
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

}
