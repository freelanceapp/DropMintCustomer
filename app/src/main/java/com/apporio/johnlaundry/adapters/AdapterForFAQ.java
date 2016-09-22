package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apporio.johnlaundry.R;

/**
 * Created by gaurav on 11/2/2015.
 */
public class AdapterForFAQ extends BaseAdapter {

    String [] Answer;
    String [] Question;
    Context ctc;

    LayoutInflater inflater;
    public AdapterForFAQ(Context context, String[] Question,String [] Answer) {

        this.ctc=context;
        this.Question=Question;
        this.Answer=Answer;
        inflater = LayoutInflater.from(this.ctc);
    }

    @Override
    public int getCount() {
        return Question.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class Holder{

        TextView tvQuestion,tvAnswer;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if(convertView== null) {
            convertView = inflater.inflate(R.layout.faqlistitems,null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else {
            holder = (Holder)convertView.getTag();
        }


        holder.tvQuestion = (TextView)convertView.findViewById(R.id.question);
        holder.tvQuestion.setText(Question[position]);

        holder.tvAnswer = (TextView)convertView.findViewById(R.id.answer);
        holder.tvAnswer.setText(Answer[position]);

        return convertView;
    }
}