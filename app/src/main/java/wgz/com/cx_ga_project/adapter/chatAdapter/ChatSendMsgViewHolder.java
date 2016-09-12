package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.ChatMsg;

/**
 * Created by qwerr on 2016/9/8.
 */
public class ChatSendMsgViewHolder extends BaseViewHolder<ChatMsg.Re> {
    private TextView chatcontent;
    private TextView timestamp;
    private ImageView userhead;


    public ChatSendMsgViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_sent_message);
        chatcontent = $(R.id.tv_chatcontent);
        timestamp = $(R.id.timestamp);
        userhead = $(R.id.iv_userhead);
    }


    @Override
    public void setData(ChatMsg.Re data) {
        super.setData(data);
        chatcontent.setText(data.getTxt());
        timestamp.setText(data.getSendtime());
    }
}
