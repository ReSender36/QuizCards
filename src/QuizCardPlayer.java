import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mdi on 09.10.2016.
 */
public class QuizCardPlayer {
    private JTextArea display ;
    private JTextArea answer ;
    private ArrayList<QuizCard> cardList ;
    private QuizCard currentCard ;
    private int currentCardIndex ;
    private JFrame frame ;
    private JButton nextButton ;
    private boolean isSowAnswer ;

    public static void main(String[] args){
        QuizCardPlayer reader = new QuizCardPlayer() ;
        reader.go() ;
    }

    public void go() {
        // формируем GUI

        frame = new JFrame("Quiz Card Player") ;
        JPanel mainPanel = new JPanel() ;
        Font bigFont = new Font("sansserif", Font.BOLD, 24) ;

        display = new JTextArea(10,20) ;
        display.setFont(bigFont) ;

        display.setLineWrap(true) ;
        display.setEditable(false) ;

        JScrollPane qScroller = new JScrollPane(display) ;
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        nextButton = new JButton("Show question") ;
        mainPanel.add(qScroller) ;
        mainPanel.add(nextButton) ;
        nextButton.addActionListener(new QuizCardBuilder.NextCardListener());
        
    }
}
