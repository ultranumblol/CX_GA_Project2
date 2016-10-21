package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.security.PublicKey;
import java.util.Map;

import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.entity.ChatMsg;

/**
 * Created by wgz on 2016/9/8.
 */

public class ChatAdapter extends MyRecyclerArrayAdapter<ChatMsg.Re> {
    public static final int SEND_MSG =11;
    public static final int SEND_VIDEO =12;
    public static final int SEND_PIC =13;
    public static final int RECIEVE_MSG =21;
    public static final int RECIEVE_VIDEO =22;
    public static final int RECIEVE_PIC =23;
    public static final int DEFULT =0;



    public ChatAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case SEND_MSG:
                return new ChatSendMsgViewHolder(parent);
            case SEND_PIC:
                return new ChatSendPicViewHolder(parent);
            case SEND_VIDEO:
                return new ChatSendVideoViewHolder(parent);
            case RECIEVE_MSG:
                return new ChatRecieveMsgViewHolder(parent);
            case RECIEVE_VIDEO:
                return new ChatRecieveVideoViewHolder(parent);
            case RECIEVE_PIC:
                return new ChatRecievePicViewHolder(parent);




        }
        return null;
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position).getMark().equals(1)){
            return RECIEVE_MSG;
        }
        if (getItem(position).getMark().equals(0)){
            if (!getItem(position).getPic().equals("")&&!getItem(position).getPic().equals("null")){
                return SEND_PIC;
            }
            if (!getItem(position).getVideo().equals("")&&!getItem(position).getVideo().equals("null")){
                return SEND_VIDEO;
            }else
                return SEND_MSG;

        }


        return DEFULT;
    }
}
