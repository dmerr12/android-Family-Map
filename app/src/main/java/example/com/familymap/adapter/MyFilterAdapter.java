package example.com.familymap.adapter;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import example.com.familymap.R;
import example.com.familymap.data.Settings;

public class MyFilterAdapter extends RecyclerView.Adapter<MyFilterAdapter.ViewHolder> {
    private List<String> lines;
    private View v;
    private Settings settings;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public LinearLayout mLayout;
        public Switch mSwitch;
        public ViewHolder(LinearLayout v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.textLine);
            mLayout = (LinearLayout)v.findViewById(R.id.lin);
            mSwitch = (Switch)v.findViewById(R.id.filter);

        }
    }

    public MyFilterAdapter(List<String> lines) {
        this.lines = lines;
    }


    @Override
    public MyFilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_filter_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos2 = position;
        settings = Settings.getInstance();
        holder.mTextView.setText(lines.get(position));
        holder.mSwitch.setChecked(settings.getOneFilter(pos2));
        final ViewHolder copy = holder;
        holder.mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setOneFilter(pos2,copy.mSwitch.isChecked());
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

    public void addLine(String line) {
        lines.add(line);
        notifyDataSetChanged();
    }

    public void removeLine(int lineNumber) {
        lines.remove(lineNumber);

    }

}
