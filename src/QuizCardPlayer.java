import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            if (isSowAnswer) {
                // показываем ответ, т. к. вопрос был показан ранее
                display.setText(currentCard.getAnswer());
                nextButton.setText("Next card");
                isSowAnswer = false ;
            } else {
                // показываем следующий вопрос
                if (currentCardIndex < cardList.size()) {
                    showNextCard() ;
                } else {
                    // больше карточек нет
                    display.setText("That was last card");
                    nextButton.setEnabled(false) ;
                }
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
    
}