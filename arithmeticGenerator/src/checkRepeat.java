import java.util.ArrayList;

public class checkRepeat {
    ArrayList<String> returnList = new ArrayList<>();
    ArrayList<String> txtList = new ArrayList<>();
    ArrayList<String> ansList = new ArrayList<>();
    ArrayList<String[]> ansFoList = new ArrayList<>();

    public ArrayList generate(int n,int r) {
        Create create = new Create();
        for(int i=0;i<n;){
            String[] ansFormula = create.createFormula(r);
            if (ansFormula!=null)
                if (!ifRepeat(ansFormula)) i++;
        }

        for (int i =0; i<2*n;i++) {
            if(i<n) {
                returnList.add(txtList.get(i));
                //System.out.println("txtList["+i+"]："+txtList.get(i));
            } else {
                returnList.add(ansList.get(i - n));
                //System.out.println("ansList["+(i-n)+"]："+ansList.get(i - n));
            }
        }
        return returnList;
    }

    public boolean ifRepeat(String[] ansFormula) {
        String formula = ansFormula[ansFormula.length-1];
        String[] rPNotation = new String[ansFormula.length-1];
        System.arraycopy(ansFormula, 0, rPNotation, 0, ansFormula.length-1);
        boolean ifRepeat = false;

        for(int i=0;i<this.ansFoList.size();i++){
            if (this.ansFoList.contains(rPNotation)) ifRepeat =true;
        }

        if (!ifRepeat) {
            this.txtList.add(formula);
            this.ansList.add(rPNotation[rPNotation.length-1]);
            this.ansFoList.add(rPNotation);
        }
        return ifRepeat;
    }
}
