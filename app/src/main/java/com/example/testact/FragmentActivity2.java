package com.example.testact;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentActivity2 extends Fragment {
    private List<Chat> chatList=new ArrayList<>();//存放Chat对象
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_fragment_activity2, container, false);
        //聊天对话框列表的适配
        ChatAdapter adapter=new ChatAdapter(view.getContext(),R.layout.item_chat,chatList);//实例化适配器
        final ListView listView=(ListView)view.findViewById(R.id.chat_listview);//获取ListView
        listView.setAdapter(adapter);//适配

        //为ListView添加上下文菜单
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(android.view.ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "标为未读");
                menu.add(0, 1, 0, "置顶聊天");
                menu.add(0,2,0,"删除聊天");

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                LinearLayout layout = (LinearLayout) listView.getChildAt(position);
                TextView textView = (TextView) layout.findViewById(R.id.chat_name);//获取ListView中显示姓名的TextView
                String s = textView.getText().toString();//获取TextView的值
                Intent intent = new Intent(view.getContext(), ChatView.class);//启动聊天界面
                intent.putExtra("username", s);//将TextView的值传递给聊天界面
                startActivity(intent);

            }
        });
        return view;

    }
    //初始化参数
    private void init(){
        Chat wzy=new Chat(R.drawable.wzy,"王震尧","快起来写代码了！","1.03");
        chatList.add(wzy);
        Chat zsw=new Chat(R.drawable.zsw,"赵世文","6月16号就要交软工项目啦！","1.27");
        chatList.add(zsw);
        Chat wyy=new Chat(R.drawable.wyy,"汪严逸","凌晨四点见","4.00");
        chatList.add(wyy);
        Chat wqj=new Chat(R.drawable.wqj ,"吴乔杰","我喜欢写代码","7.30");
        chatList.add(wqj);
        Chat dx=new Chat(R.drawable.dx, "丁鑫","提前感受996夜生活","8.30");
        chatList.add(dx);
        Chat xbt =new Chat(R.drawable.xbt ,"许柏涛","天哪，又报错了","8.52");
        chatList.add(xbt);
        Chat glm=new Chat(R.drawable.glm ,"关力铭","我一个月都没睡过觉了呜呜呜","8.52");
        chatList.add(glm);


    }
    @Override
    public void onStart() {
        Log.d("TAG","onStart");
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


}
