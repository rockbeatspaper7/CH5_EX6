package com.example.myfinances;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editAccNum;
    EditText editInitialBal;
    EditText editCurrentBal;
    EditText editPayment;
    EditText editIR;

    // Declare account globally, but initialize later
    protected Accounts account;

    // Mode for determining which form to show
    int mode = 1;
    // "mode" is how the code will tell if the code what it'll do dependent on the button clicked
    // 1 is CD button, 2 is Loans, 3 is checkings

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

        account = new Accounts();

        editAccNum = findViewById(R.id.accNumEdit);
        editInitialBal = findViewById(R.id.inBalanceEdit);
        editCurrentBal = findViewById(R.id.curBalanceEdit);
        editPayment = findViewById(R.id.payAmEdit);
        editIR = findViewById(R.id.irEdit);

        initCDButton();
        initLoansButton();
        initCheckingsButton();
        setForEditing(false);
        initSaveButton();
        initClearButton();
    }


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

    protected void initSaveButton() {
        Button save = findViewById(R.id.saveButton);

        save.setOnClickListener( s -> {
            boolean wasSuccessful = false;
            AccountDataSource ds = new AccountDataSource(MainActivity.this);
            try {
                ds.open();
                switch (mode) {
                    case 1:
                        account.setAccNumber(editAccNum.getText().toString());
                        account.setInitialBalance(Double.parseDouble(editInitialBal.getText().toString()));
                        account.setCurrentBalance(Double.parseDouble(editCurrentBal.getText().toString()));
                        account.setInterestRate(Double.parseDouble(editIR.getText().toString()));

                        if (account.getId() == -1) {
                            wasSuccessful = ds.insertCDAccount(account);
                            if (wasSuccessful) {
                                int newId = ds.getLastID("CD_Account");
                                account.setId(newId);
                                Log.d("MainActivity", "Account inserted successfully with ID: " + newId);
                            } else {
                                Log.e("MainActivity", "Failed to insert account");
                            }
                        } else {
                            wasSuccessful = ds.updateCDAccount(account);
                            if (wasSuccessful) {
                                Log.d("MainActivity", "Account updated successfully with ID: " + account.getId());
                            } else {
                                Log.e("MainActivity", "Failed to update account");
                            }
                        }
                        break;
                    case 2:
                        account.setAccNumber(editAccNum.getText().toString());
                        account.setInitialBalance(Double.parseDouble(editInitialBal.getText().toString()));
                        account.setCurrentBalance(Double.parseDouble(editCurrentBal.getText().toString()));
                        account.setInterestRate(Double.parseDouble(editIR.getText().toString()));
                        account.setPaymentAmount(Double.parseDouble(editPayment.getText().toString()));

                        if (account.getId() == -1) {
                            wasSuccessful = ds.insertLoanAccount(account);
                            if (wasSuccessful) {
                                int newId = ds.getLastID("LOANS_Account");
                                account.setId(newId);
                                Log.d("MainActivity", "Account inserted successfully with ID: " + newId);
                            } else {
                                Log.e("MainActivity", "Failed to insert account");
                            }
                        } else {
                            wasSuccessful = ds.updateLoanAccount(account);
                            if (wasSuccessful) {
                                Log.d("MainActivity", "Account updated successfully with ID: " + account.getId());
                            } else {
                                Log.e("MainActivity", "Failed to update account");
                            }
                        }
                        break;

                    case 3:
                        account.setAccNumber(editAccNum.getText().toString());
                        account.setCurrentBalance(Double.parseDouble(editCurrentBal.getText().toString()));

                        if (account.getId() == -1) {
                            wasSuccessful = ds.insertCheckingAccount(account);
                            if (wasSuccessful) {
                                int newId = ds.getLastID("CHECKINGS_Account");
                                account.setId(newId);
                                Log.d("MainActivity", "Account inserted successfully with ID: " + newId);
                            } else {
                                Log.e("MainActivity", "Failed to insert account");
                            }
                        } else {
                            wasSuccessful = ds.updateCheckingAccount(account);
                            if (wasSuccessful) {
                                Log.d("MainActivity", "Account updated successfully with ID: " + account.getId());
                            } else {
                                Log.e("MainActivity", "Failed to update account");
                            }
                        }
                        break;
                }
                if (wasSuccessful) {
                    editAccNum.setText("");
                    editInitialBal.setText("");
                    editCurrentBal.setText("");
                    editIR.setText("");
                    editPayment.setText("");
                    Log.d("MainActivity", "Fields cleared!");
                }
            } catch (Exception e) {
                Log.e("MainActivity", "Database operation failed", e);
            }
            finally {
                ds.close();
            }

            if (!wasSuccessful) {
                Log.e("MainActivity", "Save operation was unsuccessful");
            }
        });
    }

    protected void initClearButton() {
        Button clear = findViewById(R.id.clearButton);

        clear.setOnClickListener( c -> {
                editAccNum.setText("");
                editInitialBal.setText("");
                editCurrentBal.setText("");
                editIR.setText("");
                editPayment.setText("");
        });
    }
}