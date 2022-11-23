package com.example.notes;

import android.content.Context;

import java.io.IOException;
import java.io.Serializable;



public interface Note extends Serializable {

    public String read(Context root) throws IOException;

    public void write(Context root, String string);

    public void rename(Context root, String newname);

    public void delete(Context root);

    public String preview(Context root);

    public String getName();

}
