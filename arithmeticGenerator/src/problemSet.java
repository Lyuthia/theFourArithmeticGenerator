import java.io.*;
import java.util.ArrayList;

public class problemSet {
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
    //File gradeTXT = new File("../Grade.txt");

    public void createEXEFile(ArrayList txtList){
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
                    System.out.println(txtList.get(i));
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

    public void createAnsFile(ArrayList ansList){
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

                for(int i=0;i<ansList.size();i++){
                    System.out.print(">");
                    p.println(i+1 + ". " +ansList.get(i));
                    //System.out.println(ansList.get(i));
                }
                /*Create create = new Create();
                for(int i=0;i<n;i++){
                    p.println(i+1 + ". " +create.createFormula(r));
                }*/
                ansFile.close();
                p.close();
                System.out.println("Answer.txt 创建成功！");
            }

        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
