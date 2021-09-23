package blehblu.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {
TextView score;
TextView life;
TextView time;
TextView question;
EditText answer;
Button okay;
Button next;
Random random= new Random();
int num1;
int num2;
int userAns;
int finalAns;
int userScore=0;
int userLife=3;
CountDownTimer timer;
private static final long TIMER =30000;
Boolean timerRun;
long time_left=TIMER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        score= findViewById(R.id.score);
        life=findViewById(R.id.life);
        time=findViewById(R.id.time);
        question=findViewById(R.id.question);
        answer=findViewById(R.id.ans);
        okay=findViewById(R.id.okay);
        next=findViewById(R.id.next);
        gameContinue();
        final Handler handler= new Handler();
        final Handler handler1= new Handler();
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
              okay.setEnabled(false);
            }
        };
        Runnable runnable1= new Runnable() {
            @Override
            public void run() {
                next.setEnabled(false);
            }
        };

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler1.removeCallbacks(runnable1);
                pauseTimer();
                next.setEnabled(true);
                handler.postDelayed(runnable,0);
                userAns=Integer.valueOf(answer.getText().toString());

                if(userAns==finalAns){
                    userScore= userScore+ 10;
                    score.setText(""+userScore);
                    question.setText("Congratulations, your answer is right!");
                }
                else {
                     userLife=userLife-1;
                     life.setText(""+userLife);
                     question.setText("Sorry, That is wrong");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                handler1.postDelayed(runnable1,0);
                okay.setEnabled(true);
                resetTimer();
                answer.setText("");
                if(userLife<=0){
                    Toast.makeText(getApplicationContext(),"GAME OVER",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Game.this,Result.class);
                    intent.putExtra("score",userScore);
                    startActivity(intent);
                    finish();
                }

               else{
                    gameContinue();
                }
            }
        });


    }

    public void gameContinue(){
     num1=random.nextInt(200);
     num2= random.nextInt(200);
     question.setText(num1 + " + " + num2);
     finalAns=num1+num2;
     startTimer();


    }
    public void startTimer(){
        timer = new CountDownTimer(time_left,1000) {
            @Override
            public void onTick(long l) {
            time_left=l;
            updateText();
           // answer.addTextChangedListener(answatch);
            }

            @Override
            public void onFinish() {
            timerRun=false;
            pauseTimer();
            resetTimer();
            updateText();
            userLife=userLife-1;
            life.setText(""+userLife);
            question.setText("Oops time up!");

            }
        }.start();
        timerRun=true;
    }
    public void updateText(){
        int sec= (int) (time_left/1000)%60;
        String timeLeft=String.format(Locale.getDefault(),"%02d",sec);
        time.setText(timeLeft);
    }
    public void pauseTimer(){
    timer.cancel();
    timerRun=false;
    }
    public void resetTimer(){
         time_left=TIMER;
         updateText();
    }
//    public TextWatcher answatch= new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        String input2=answer.getText().toString();
//        okay.setEnabled(!input2.isEmpty());
////        next.setEnabled(!input2.isEmpty());
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//          String input=answer.getText().toString();
//          okay.setEnabled(true);
////          next.setEnabled(!input.isEmpty());
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            String input=answer.getText().toString();
//           //next.setEnabled(input.isEmpty());
//
//        }
//    };
}
