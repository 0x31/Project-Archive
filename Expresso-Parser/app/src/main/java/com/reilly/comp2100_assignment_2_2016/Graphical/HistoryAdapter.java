package com.parser.Graphical;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parser.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<String>{

    private final Activity context;
    public List<String> histories;
    public List<Bitmap> plots = new ArrayList<>();

    static int maxHistory = 20;

    ListView historyView;


    File historyFile;
    String historyString;


    /**
     * Generate the List of names for the adapter
     * @param notes
     * @return
     */

    public HistoryAdapter(Activity context,
                       File historyFile) {
        super(context, R.layout.history_graph, cut(loadHistory(historyFile)));

        histories = cut(loadHistory(historyFile));
        historyString = "";
        for(String history : histories) {
            historyString += history+"\n";
            plots.add(null);
        }
        this.context = context;
        this.historyFile = historyFile;
    }

    // Documentation taken from: http://developer.android.com/reference/android/widget/Adapter.html
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        String name = histories.get(position);


        View rowView;

        if(histories.get(position).replace(" ","").substring(0,2).equals("y=")) {
            rowView= inflater.inflate(R.layout.history_graph, null, true);

            String equation = name.substring(name.indexOf("=")+1, name.lastIndexOf("="));

            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            txtTitle.setText("y = "+equation);

            Plot plot = (Plot) rowView.findViewById(R.id.plot);
            Bitmap plotBMP = plots.get(position);
            if(plotBMP != null) {
                plot.addPlot(plotBMP);
            } else {

                Display display = context.getWindowManager().getDefaultDisplay();
                int width = display.getWidth() - 100;
                int height = width + 150;
                //int width = plot.getRootView().getWidth()-100;
                plotBMP = plot.plot(equation, width, height,
                        context);
                //}
                if (plotBMP != null) {
                    plots.set(position, plotBMP);
                    plot.addPlot(plotBMP);
                }
            }
        } else {
            rowView = inflater.inflate(R.layout.history_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            name = name.replace("x"," 0 ");
            txtTitle.setText(name);
        }

        return rowView;
    }


    static public List<String> loadHistory(File historyFile) {
        BufferedReader input;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(historyFile)));
            String line;
            List<String> histories = new ArrayList<>();
            //StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                histories.add(line);
            }
            //this.histories = histories;
            return histories;
            //return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //histories = new ArrayList<>();
        return new ArrayList<>();
        //return null;
    }

    public void addToHistory(String input, String output) {

        String toAdd = input+" = "+output;
        histories.add(0,toAdd);
        plots.add(0,null);
        this.insert(toAdd,0);
        if(histories.size()>maxHistory) {
            String hist = histories.get(histories.size()-1);
            plots.remove(histories.size()-1);
            histories.remove(histories.size()-1);
            this.remove(hist);
        }
        historyString=toAdd+"\n"+historyString;
        if(historyView!=null) {
            historyView.smoothScrollToPosition(0);
        }

        try {

            FileWriter fw = new FileWriter(historyFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(historyString);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setHistoryView(ListView view) {
        this.historyView = view;
    }

    public static List<String> cut(List<String> list) {
        // Only show top 20.
        return list.subList(0, Math.min(list.size(), maxHistory));
    }

}