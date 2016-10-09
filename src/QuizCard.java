/**
 * Created by mdi on 09.10.2016.
 */
public class QuizCard {
    public QuizCard(String q, String a) {
        this.answer = a ;
        this.question = q ;
    }
    private String question ;
    private String answer ;

    public String getQuestion(){
        return question ;
    }
    public String getAnswer(){
        return answer ;
    }
}
