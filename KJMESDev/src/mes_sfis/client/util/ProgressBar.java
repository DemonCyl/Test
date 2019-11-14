package mes_sfis.client.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Efil_Ding on 2018/10/19.
 */
public class ProgressBar {
    Timer timer;
    JProgressBar jpbFileLoading;
    private  int loadingValue;

    public ProgressBar() {
        JFrame jf = new JFrame("?度???");
        /**
         * ?建一?常?模式的?度?,其默??水平方向,最小值?0,最大值?100,初始值?0
         */
        jpbFileLoading = new JProgressBar();
        jpbFileLoading.setStringPainted(true);  //?置?度?呈??度字符串,默??false
        jpbFileLoading.setBorderPainted(false); //不?制?框,默??true
        jpbFileLoading.setPreferredSize(new Dimension(100, 40)); //?置首?大小
        timer = new Timer(50, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                schedule();
            }
        });
        timer.start();
        /**
         * ?建一?不确定模式的?度?
         */
        JProgressBar jpbFileLoadingIndeterminate = new JProgressBar();
        jpbFileLoadingIndeterminate.setIndeterminate(true); //?置?度??不确定模式,默??确定模式
        jpbFileLoadingIndeterminate.setStringPainted(true);
        jpbFileLoadingIndeterminate.setString("加載中......");
        /**
         * ??种?度?放到主面板里
         */
        jf.add(jpbFileLoading, BorderLayout.NORTH);
        jf.add(new JLabel(getChickenSoup(), SwingConstants.CENTER), BorderLayout.CENTER);
        jf.add(jpbFileLoadingIndeterminate, BorderLayout.SOUTH);
        jf.setSize(300, 150);
        jf.setLocationRelativeTo(null); //居中?示
        jf.setUndecorated(true);        //禁用此窗体的??
        jf.getRootPane().setWindowDecorationStyle(JRootPane.NONE); //采用指定的窗体???格
        jf.setVisible(true);
        /**
         * 不确定模式的?度??理
         */
        ctrlRule();
        jpbFileLoadingIndeterminate.setIndeterminate(false); //?置?度??确定模式,即常?模式,否?那????走?走去
        jpbFileLoadingIndeterminate.setString("加載完畢..");
        try {
            Thread.sleep(800);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * ??窗体
         */
        jf.setVisible(false); //?藏窗体
        jf.dispose();         //?放?源,??窗体
        jf = null;            //若不再使用了就??
    }
    //進程進行控制
    public void schedule() {
        loadingValue = jpbFileLoading.getValue();
        if (loadingValue < 100){
            jpbFileLoading.setValue(++loadingValue);
        }else {
            timer.stop();
        }

    }
    //終止規則
    public void ctrlRule(){
        while(loadingValue<100) {
//            System.out.println(loadingValue);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public String getChickenSoup(){
        ArrayList list=new ArrayList();
        list.add("大部分成功靠得既不是厚積薄發的努力，也不是戲居化的機遇，而是早就定好的出身和天賦。<br>");
        list.add("失戀的時候，許多年輕人以為整個世界都拋棄了自己，別傻了，世界根本就沒需要過妳。<br>");
        list.add("妳說對生活絕望，完全失去了熱情和動力，這種情況說到底，就是妳比較懶。<br>");
        list.add("常聽到別人說：我希望他/她有什麼話當面說，不要在別後中傷人。<br>壹些人信以為真，而實際上，我的實踐證明，當面說別人壞話，別人會非常憤怒，難堪。<br>所以中傷別人壹定要在背後。<br>");
        list.add("每天顯得無聊或寂寞了，去找朋友壹起吃飯和逛，只不過是為了滿足自己與人交往的需求，算不上是社交。<br>");
        list.add("有些人感慨：自己歲數不小了，還沒有成熟起來。<br>其實妳們已經成熟起來了，妳們成熟起來就這樣。<br>");
        list.add("那些大方捨得為妳花錢的人，並不是多麼富有，誰的錢都不是大風刮來的，而是他覺得妳們的關系比錢重要，因為重要才捨得。<br>");
        list.add("許多事不要害怕做錯，即使錯了，也不必懊惱，人生就是對對錯錯，回頭看來，對錯已經無所謂了。<br>");
        list.add("直到三十歲才知道，和不同的人說不同的話，表現出不壹樣的態度，是壹種非常可貴的能力，而不是虛偽。<br>");
        list.add("是虛榮、驕傲、嫉妒與報復，支撐妳到今天。<br>妳的成長是依賴這些負的能量，而非天生的善良。<br>");
        list.add("回首往事，我發現自己失去了很多寶貴的東西。<br>但我並不難過，因為我知道，以後會失去的更多。<br>");
        list.add("所謂的女漢子，只不過是因為長得醜而已，但凡有些爺們氣質的漂亮姑娘，都被稱為女王大人。<br>");
        list.add("很多時候妳如果不逼自己壹把，妳就不知道妳還有把事情搞砸的本事。<br>");
        list.add("別人的精彩只是因為早有老天的安排，只不過老天卻對妳有了另外安排。<br>");
        list.add("有些事不是努力就可以改變的，五十塊的人民幣設計的再好看，也沒有壹百塊的招人喜歡。<br>");
        list.add("在逝去的青春?，很多寶貴的東西從我身邊溜走。<br>但我知道，以後還會失去更多，所以我並不難過。<br>");
        list.add("我發現很多混得不好的人看得都很開。<br>也不知道他們是因為看得透徹而不屑於世俗的成功，還是因為不成功而不得不看得開。<br>");
        list.add("有時候我們需要的不是壹碗雞湯，而是壹個巴掌！有些人出現在妳的生命?，就是為了告訴妳：妳真好騙。<br>");
        list.add("很羨慕妳們能和妳們喜歡的人在壹起，不像我，周圍都是喜歡我的人。<br>");
        list.add("愛笑的女生運氣不會太差，說實話，如果壹個女的運氣壹直不好，我不知道她怎麼笑得出來");


        int a = (int) (Math.random() * 20);
        return  "<html><body>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(a).toString()+"<body></html>";
    }


    public static void main(String[] args) {

        new ProgressBar();
    }
}

