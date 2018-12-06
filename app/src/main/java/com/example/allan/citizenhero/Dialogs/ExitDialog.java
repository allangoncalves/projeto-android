package com.example.allan.citizenhero.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class ExitDialog extends DialogFragment implements DialogInterface.OnClickListener{
    private ExitListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof ExitListener)){
            throw new RuntimeException("Deve ser um ExitListener");
        }

        listener = (ExitListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Deseja Sair?")
                .setPositiveButton("Sim",this)
                .setNegativeButton("Não",this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == DialogInterface.BUTTON_POSITIVE && listener != null){
            listener.onExit();
        }
    }

    public interface ExitListener{
        void onExit();
    }
}
