package example.com.familymap.adapter;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

import example.com.familymap.R;
import example.com.familymap.UI.MapActivity;
import example.com.familymap.UI.PersonActivity;
import model.Event;
import model.Person;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.ViewHolder> {
    private List<String> lines;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;
    private View v;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public LinearLayout mLayout;
        public ImageView mImageView;
        public ViewHolder(LinearLayout v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.textLine);
            mLayout = (LinearLayout)v.findViewById(R.id.lin);
            mImageView = (ImageView)v.findViewById(R.id.imageView66);
        }
    }

    public MySearchAdapter(List<String> lines, ArrayList<Person> persons, ArrayList<Event> events) {
        this.lines = lines;
        this.persons=persons;
        this.events = events;
    }

    @Override
    public MySearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
         v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos2 = position;
        holder.mTextView.setText(lines.get(position));
        Drawable genderIcon2;
        if(pos2<persons.size()){
            if(persons.get(pos2).getGender().equals("f")){
                genderIcon2 = new IconDrawable(v.getContext(), FontAwesomeIcons.fa_female).colorRes(R.color.colorAccent).sizeDp(40);
            }
            else{
                genderIcon2 = new IconDrawable(v.getContext(), FontAwesomeIcons.fa_male).colorRes(R.color.colorPrimary).sizeDp(40);
            }
        }
        else{

            genderIcon2 = new IconDrawable(v.getContext(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(40);
        }
        holder.mImageView.setImageDrawable(genderIcon2);

       holder.mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(pos2<persons.size()) {
                    Intent intent = new Intent(v.getContext(), PersonActivity.class);
                    Bundle b = new Bundle();
                    b.putString("personID", persons.get(pos2).getPersonID());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                    // System.out.println("You've clicked me"+lines.get(pos2)+"\n");
                }
                else{
                    Intent intent = new Intent(v.getContext(), MapActivity.class);
                    Bundle b = new Bundle();
                    b.putString("eventID", events.get(pos2-persons.size()).getEventID());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return lines.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
