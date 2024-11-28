package vn.edu.stu.championsmanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.stu.championsmanager.R;
import vn.edu.stu.championsmanager.models.Role;

public class RoleAdapter extends ArrayAdapter<Role> {
    private final Activity context;
    private final int resource;
    private final List<Role> RoleList;

    public RoleAdapter(@NonNull Activity context, int resource, @NonNull List<Role> RoleList) {
        super(context, resource, RoleList);
        this.context = context;
        this.resource = resource;
        this.RoleList = RoleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.tvIdRole = convertView.findViewById(R.id.tvIdRole);
            holder.tvNameRole = convertView.findViewById(R.id.tvNameRole);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Role role = RoleList.get(position);

        holder.tvIdRole.setText(String.valueOf(role.getId()));
        holder.tvNameRole.setText(role.getName());

        return convertView;
    }


    private static class ViewHolder {
        TextView tvIdRole;
        TextView tvNameRole;
    }
}
