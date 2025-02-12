package com.example.myfinances;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initCDButton();
        initLoansButton();
        initCheckingsButton();
        setForEditing(false);
    }
    int mode = 1;
    // "mode" is how the code will tell if the code what it'll do dependent on the button clicked
    // 1 is CD button, 2 is Loans, 3 is checkings

    protected void initCDButton () {
        RadioButton cdButton = findViewById(R.id.cdRadioB);

        cdButton.setOnClickListener(c -> {
            mode = 1;
            setForEditing(cdButton.isChecked());
        });
    }

    protected void initLoansButton () {
        RadioButton loansButton = findViewById(R.id.loanRadioB);

        loansButton.setOnClickListener(l -> {
            mode = 2;
            setForEditing(loansButton.isChecked());
        });
    }

    protected void initCheckingsButton () {
        RadioButton checkingsButton = findViewById(R.id.checksRadioB);

        checkingsButton.setOnClickListener(l -> {
            mode = 3;
            setForEditing(checkingsButton.isChecked());
        });
    }

    protected void setForEditing (boolean enabled) {
        EditText editAccNum = findViewById(R.id.accNumEdit);
        EditText editInitialBal = findViewById(R.id.inBalanceEdit);
        EditText editCurrentBal = findViewById(R.id.curBalanceEdit);
        EditText editPayment = findViewById(R.id.payAmEdit);
        EditText editIR = findViewById(R.id.irEdit);

        switch (mode){
            case 1:
                editAccNum.setEnabled(enabled);
                editInitialBal.setEnabled(enabled);
                editCurrentBal.setEnabled(enabled);
                editIR.setEnabled(enabled);
                editPayment.setEnabled(false);
                break;

            case 2:
                editAccNum.setEnabled(enabled);
                editInitialBal.setEnabled(enabled);
                editCurrentBal.setEnabled(enabled);
                editIR.setEnabled(enabled);
                editPayment.setEnabled(enabled);
                break;

            case 3:
                editAccNum.setEnabled(enabled);
                editCurrentBal.setEnabled(enabled);
                editInitialBal.setEnabled(false);
                editIR.setEnabled(false);
                editPayment.setEnabled(false);
                break;

            default:
                editAccNum.setEnabled(false);
                editInitialBal.setEnabled(false);
                editCurrentBal.setEnabled(false);
                editIR.setEnabled(false);
                editPayment.setEnabled(false);
                break;
        }
    }
}