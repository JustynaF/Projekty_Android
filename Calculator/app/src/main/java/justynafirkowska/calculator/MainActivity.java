package justynafirkowska.calculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Logger logger = LoggerManager.getLogger();
        Button one, two, three, four, five, six, seven, eight, nine, zero, add, sub, mul, div, cancel, equal, history;
        EditText disp;
        TextView wholeCalc;
        double op1 = 0.0;
        double op2;
        String optr;
        String calculation = "";
        Boolean equalsClicked = false;

        String FILENAME = "calcHistory";
        FileOutputStream fos = null;

        @Override protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            try {
                logger.d("Loading history file");
                fos = openFileOutput(FILENAME, Context.MODE_APPEND);
            } catch (Exception e) {
                logger.e("Could not load history file", e);
                throw new RuntimeException(e);
            }

            logger.i("Getting buttons from view");
            one = (Button) findViewById(R.id.one);
            two = (Button) findViewById(R.id.two);
            three = (Button) findViewById(R.id.three);
            four = (Button) findViewById(R.id.four);
            five = (Button) findViewById(R.id.five);
            six = (Button) findViewById(R.id.six);
            seven = (Button) findViewById(R.id.seven);
            eight = (Button) findViewById(R.id.eight);
            nine = (Button) findViewById(R.id.nine);
            zero = (Button) findViewById(R.id.zero);
            add = (Button) findViewById(R.id.add);
            sub = (Button) findViewById(R.id.sub);
            mul = (Button) findViewById(R.id.mul);
            div = (Button) findViewById(R.id.div);
            cancel = (Button) findViewById(R.id.cancel);
            equal = (Button) findViewById(R.id.equal);
            disp = (EditText) findViewById(R.id.display);
            wholeCalc = (TextView) findViewById(R.id.wholeCalc);
            try {
                logger.i("Setting onClickListeners for buttons");
                one.setOnClickListener(this);
                two.setOnClickListener(this);
                three.setOnClickListener(this);
                four.setOnClickListener(this);
                five.setOnClickListener(this);
                six.setOnClickListener(this);
                seven.setOnClickListener(this);
                eight.setOnClickListener(this);
                nine.setOnClickListener(this);
                zero.setOnClickListener(this);
                cancel.setOnClickListener(this);
                add.setOnClickListener(this);
                sub.setOnClickListener(this);
                mul.setOnClickListener(this);
                div.setOnClickListener(this);
                equal.setOnClickListener(this);
            } catch (Exception e) {
                logger.e("Setting onClickListeners failed", e);
                throw new RuntimeException(e);
            }

            logger.i("Configure history button");
            history = (Button) findViewById(R.id.history);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, History.class);
                    startActivity(intent);
                }
            });
        }
    public void operation() {
        if (optr.equals("+")) {
            logger.d("Operation '+'");
            try { op2 = Double.parseDouble(disp.getText().toString());
            disp.setText("");
            op1 = op1 + op2;
            disp.setText(Double.toString(op1)); }
            catch (Exception e) {
                op2 = 0.0;
            }
        } else if (optr.equals("-")) {
            logger.d("Operation '-'");
            try { op2 = Double.parseDouble(disp.getText().toString());
            disp.setText("");
            op1 = op1 - op2;
            disp.setText(Double.toString(op1)); }
            catch (Exception e) {
                op2 = 0.0;
            }
        } else if (optr.equals("*")) {
            logger.d("Operation '*'");
            try { op2 = Double.parseDouble(disp.getText().toString());
            disp.setText("");
            op1 = op1 * op2;
            disp.setText(Double.toString(op1)); }
            catch (Exception e) {
                op2 = 0.0;
            }
        } else if (optr.equals("/")) {
            logger.d("Operation '/'");
            try { op2 = Double.parseDouble(disp.getText().toString());
            disp.setText("");
            op1 = op1 / op2;
            disp.setText(Double.toString(op1)); }
            catch (Exception e) {
                op2 = 0.0;
            }
        }
    }
    @Override public void onClick(View arg0) {

        Editable str = disp.getText();
        if(equalsClicked) {
            str.clear();
            //op1 = 0.0;
            //op2 = 0.0;
            //optr = "";
        }

        switch (arg0.getId()) {
            case R.id.one:
                equalsClicked = false;
                calculation += "1";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(one.getText());
                disp.setText(str);
                break;
            case R.id.two:
                equalsClicked = false;
                calculation += "2";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(two.getText());
                disp.setText(str);
                break;
            case R.id.three:
                equalsClicked = false;
                calculation += "3";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(three.getText());
                disp.setText(str);
                break;
            case R.id.four:
                equalsClicked = false;
                calculation += "4";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(four.getText());
                disp.setText(str);
                break;
            case R.id.five:
                equalsClicked = false;
                calculation += "5";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(five.getText());
                disp.setText(str);
                break;
            case R.id.six:
                equalsClicked = false;
                calculation += "6";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(six.getText());
                disp.setText(str);
                break;
            case R.id.seven:
                equalsClicked = false;
                calculation += "7";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(seven.getText());
                disp.setText(str);
                break;
            case R.id.eight:
                equalsClicked = false;
                calculation += "8";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(eight.getText());
                disp.setText(str);
                break;
            case R.id.nine:
                equalsClicked = false;
                calculation += "9";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(nine.getText());
                disp.setText(str);
                break;
            case R.id.zero:
                equalsClicked = false;
                calculation += "0";
                wholeCalc.setText(calculation);
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(zero.getText());
                disp.setText(str);
                break;
            case R.id.cancel:
                equalsClicked = false;
                op1 = 0.0;
                op2 = 0.0;
                optr = "";
                disp.setText("");
                disp.setHint("0");
                calculation = "";
                wholeCalc.setText(calculation);
                logger.d("Cancel: data cleaned");
                break;
            case R.id.add:
                logger.d("Clicked '+'");
                equalsClicked = false;
                logger.d("Updating: calculation");
                if(!calculation.isEmpty()) {
                    if (!Character.isDigit(calculation.charAt(calculation.length() - 1))) {
                        calculation = calculation.substring(0, calculation.length() - 1);
                    }
                    calculation += "+";
                }
                else {
                    calculation += "+" + disp.getHint();
                }
                wholeCalc.setText(calculation);

                logger.d("Making calculations...");
                if (op1 == 0.0) {
                    try {
                        op1 = Double.parseDouble(disp.getText().toString());
                        disp.setHint(disp.getText());
                        disp.setText("");
                    }
                    catch (Exception e) {
                        calculation = "";
                        //logger.w("Could not parse disp value = %d", disp.getText());
                        break;
                    }
                } else if (op2 != 0.0) {
                    op2 = 0;
                    disp.setHint(disp.getText());
                    disp.setText("");
                    logger.d("Reset op2 and disp");
                } else {
                    operation();
                    try { op2 = Double.parseDouble(disp.getText().toString());
                    disp.setText("");
                    disp.setHint(Double.toString(op1)); }
                    catch (Exception e) {
                        logger.w("Could not process last operation");
                    }
                }

                optr = "+";
                break;
            case R.id.sub:
                logger.d("Clicked '-'");
                equalsClicked = false;
                logger.d("Updating: calculation");
                if(!calculation.isEmpty()) {
                    if (!Character.isDigit(calculation.charAt(calculation.length() - 1))) {
                        calculation = calculation.substring(0, calculation.length() - 1);
                    }
                    calculation += "-";
                }
                else {
                    calculation += "-" + disp.getHint();
                }
                wholeCalc.setText(calculation);

                logger.d("Making calculations...");
                if (op1 == 0.0) {
                    try {
                        op1 = Double.parseDouble(disp.getText().toString());
                        disp.setHint(disp.getText());
                        disp.setText("");
                    }
                    catch (Exception e) {
                        calculation = "";
                        //logger.w("Could not parse disp value = %d", disp.getText());
                        break;
                    }
                } else if (op2 != 0.0) {
                    op2 = 0;
                    disp.setHint(disp.getText());
                    disp.setText("");
                    logger.d("Reset op2 and disp");
                } else {
                    operation();
                    try { op2 = Double.parseDouble(disp.getText().toString());
                    disp.setText("");
                    disp.setHint(Double.toString(op1)); }
                    catch (Exception e) {
                        logger.w("Could not process last operation");
                    }
                }

                optr = "-";
                break;
            case R.id.mul:
                logger.d("Clicked '*'");
                equalsClicked = false;
                logger.d("Updating: calculation");
                if(!calculation.isEmpty()) {
                    if (!Character.isDigit(calculation.charAt(calculation.length() - 1))) {
                        calculation = calculation.substring(0, calculation.length() - 1);
                    }
                    calculation += "*";
                }
                else {
                    calculation += "*" + disp.getHint();
                }
                wholeCalc.setText(calculation);

                logger.d("Making calculations...");
                if (op1 == 0.0) {
                    try {
                        op1 = Double.parseDouble(disp.getText().toString());
                        disp.setHint(disp.getText());
                        disp.setText("");
                    }
                    catch (Exception e) {
                        calculation = "";
                        //logger.w("Could not parse disp value = %d", disp.getText());
                        break;
                    }
                } else if (op2 != 0.0) {
                    op2 = 0;
                    disp.setHint(disp.getText());
                    disp.setText("");
                    logger.d("Reset op2 and disp");
                } else {
                    operation();
                    try { op2 = Double.parseDouble(disp.getText().toString());
                    disp.setText("");
                    disp.setHint(Double.toString(op1)); }
                    catch (Exception e) {
                        logger.w("Could not process last operation");
                    }
                }

                optr = "*";
                break;
            case R.id.div:
                logger.d("Clicked '/'");
                equalsClicked = false;
                logger.d("Updating: calculation");
                if(!calculation.isEmpty()) {
                    if (!Character.isDigit(calculation.charAt(calculation.length() - 1))) {
                        calculation = calculation.substring(0, calculation.length() - 1);
                    }
                    calculation += "/";
                }
                else {
                    calculation += "/" + disp.getHint();
                }
                wholeCalc.setText(calculation);

                logger.d("Making calculations...");
                if (op1 == 0.0) {
                    try {
                        op1 = Double.parseDouble(disp.getText().toString());
                        disp.setHint(disp.getText());
                        disp.setText("");
                    }
                    catch (Exception e) {
                        calculation = "";
                        //logger.w("Could not parse disp value = %d", disp.getText());
                        break;
                    }
                } else if (op2 != 0.0) {
                    op2 = 0;
                    disp.setHint(disp.getText());
                    disp.setText("");
                    logger.d("Reset op2 and disp");
                } else {
                    operation();
                    try { op2 = Double.parseDouble(disp.getText().toString());
                    disp.setText("");
                    disp.setHint(Double.toString(op1)); }
                    catch (Exception e) {
                        logger.w("Could not process last operation");
                    }
                }
                optr = "/";

                break;
            case R.id.equal:
                logger.d("Clicked '='");
                if (optr != null) {
                    if (op2 != 0.0) {
                        logger.d("Updating: disp");
                        if (optr.equals("+")) {
                            disp.setText(""); /*op1 = op1 + op2;*/
                            disp.setHint(Double.toString(op1));
                        } else if (optr.equals("-")) {
                            disp.setText(""); /* op1 = op1 - op2;*/
                            disp.setHint(Double.toString(op1));
                        } else if (optr.equals("*")) {
                            disp.setText(""); /* op1 = op1 * op2;*/
                            disp.setHint(Double.toString(op1));
                        } else if (optr.equals("/")) {
                            disp.setText(""); /* op1 = op1 / op2;*/
                            disp.setHint(Double.toString(op1));
                        }
                    } else {
                        logger.d("Make operation");
                        operation();
                    }
                    if(!calculation.isEmpty()) {
                        calculation += "=";
                        calculation += disp.getText();
                        try {
                            logger.i("Update history file");
                            fos.write((calculation + "\n").getBytes());
                        } catch (Exception e) {
                            logger.e("Could not update history file", e);
                            throw new RuntimeException(e);
                        }
                        calculation = "";
                    }
                    wholeCalc.setText(calculation);
                }

                disp.setHint("0");
                equalsClicked = true;
                break;
        }
    }
}
