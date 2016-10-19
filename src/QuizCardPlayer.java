import com.sun.xml.internal.ws.dump.MessageDumping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
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
    private boolean isShowAnswer;

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
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        nextButton = new JButton("Show question") ;
        mainPanel.add(qScroller) ;
        mainPanel.add(nextButton) ;
        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar() ;
        JMenu fileMenu = new JMenu("File") ;
        JMenuItem loadMenuItem = new JMenuItem("Load card set") ;
        loadMenuItem.addActionListener(new OpenMenuListener()) ;
        fileMenu.add(loadMenuItem) ;
        menuBar.add(fileMenu) ;
        frame.setJMenuBar(menuBar) ;
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel) ;
        frame.setSize(640,500);
        frame.setVisible(true);
    } // закрываем метод GO
    public class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(null != cardList) {
                if (isShowAnswer) {
                    // показываем ответ, т. к. вопрос был показан ранее
                    display.setText(currentCard.getAnswer());
                    nextButton.setText("Next card");
                    isShowAnswer = false;
                } else {
                    // показываем следующий вопрос
                    if (currentCardIndex < cardList.size()) {
                        showNextCard();
                    } else {
                        // больше карточек нет
                        display.setText("That was last card");
                        nextButton.setEnabled(false);
                    }
                }
            }
            else{
/*                JFileChooser fileOpen = new JFileChooser() ;
                fileOpen.showOpenDialog(frame) ;
                loadFile(fileOpen.getSelectedFile()) ;
                */

                System.out.println("Не загружен набор карточек") ;
            }
        }
    }

    public class OpenMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser() ;
            fileOpen.showOpenDialog(frame) ;
            loadFile(fileOpen.getSelectedFile()) ;
        }
    }

    private void loadFile(File file) {
        cardList = new ArrayList<QuizCard>() ;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file)) ;
            String line = null ;
            while ((line = reader.readLine()) != null) {
                makeCard(line) ;
            }
            reader.close() ;
        } catch (Exception ex) {
            System.out.println("couldn't read the card file") ;
            ex.printStackTrace();
        }
        // пришло время показать первую карточку
        showNextCard() ;
    }
    private void makeCard(String lineToParse) {
        String[] result = lineToParse.split("/") ;
        QuizCard card = new QuizCard(result[0],result[1]) ;
        cardList.add(card) ;
        System.out.println("made a card");
    }
    private void showNextCard() {
        currentCard = cardList.get(currentCardIndex) ;
        currentCardIndex++ ;
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show answer") ;
        isShowAnswer = true ;
    }
}