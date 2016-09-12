package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.ChatMsg;

/**
 * Created by qwerr on 2016/9/8.
 */
public class ChatRecieveMsgViewHolder extends BaseViewHolder<ChatMsg.Re> {

    private TextView textcontent,time;

    public ChatRecieveMsgViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_received_message);
        textcontent = $(R.id.tv_chatcontent);
        time = $(R.id.timestamp);
    }

    @Override
    public void setData(ChatMsg.Re data) {
        textcontent.setText(data.getTxt());
        time.setText(data.getSendtime());
    }
}
