package cn.hi028.android.highcommunity.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;
import com.lidroid.xutils.util.LogUtils;

import net.duohuo.dhroid.activity.BaseFragment;
import net.duohuo.dhroid.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_CreatMotionReaultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;

/**
 * @功能：自治大厅认证完成 创建提案Frag<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/11<br>
 */

public class AutoFrag_CreatMotion extends BaseFragment {
    public static final String Tag = "~CreatMotion：";
    public static final String FRAGMENTTAG = "AutoFrag_CreatMotion";
    public static final int TAG_MOTION_DETAIL = 2;
    int owner_id;
    String owner_idStr;
    @Bind(R.id.creatMotion_title)
    EditText mTitle;
    @Bind(R.id.creatMotion_content)
    EditText mContent;
    @Bind(R.id.creatMotion_commit)
    TextView mCommit;
Auto_CreatMotionReaultBean.CreatMotionReaultDataEntity mData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(Tag + "onCreateView");
        View view = inflater.inflate(R.layout.frag_auto_creat_motion, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
//        owner_id = bundle.getInt("owner_id", -1);
//        owner_idStr = owner_id + "";
        owner_idStr = HighCommunityApplication.mUserInfo.getOwner_id();
//        if (owner_id == -1) {
//        }
        initView();

        return view;
    }


    private void initView() {
        LogUtil.d(Tag + "initView");
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPHelper.AutoCreatMotion(mIbpi, HighCommunityApplication.mUserInfo.getId()+"", mTitle.getText().toString(), mContent.getText().toString());
            }
        });

    }
    BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            LogUtil.d(Tag + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);
        }
        @Override
        public void onSuccess(Object message) {
            if (message==null)return;
            HighCommunityUtils.GetInstantiation().ShowToast("提交成功", 0);
            mData= (Auto_CreatMotionReaultBean.CreatMotionReaultDataEntity) message;

            Intent mIntent_report = new Intent(getActivity(), AutonomousAct_Third.class);
            mIntent_report.putExtra("title", TAG_MOTION_DETAIL);
            mIntent_report.putExtra("motion_id", mData.getId()+"");
            startActivity(mIntent_report);
            getActivity().finish();

        }

        @Override
        public Object onResolve(String result) {
            LogUtil.d(Tag + " ~~~result" + result);

            return HTTPHelper.ResolveCreatMotionReaultData(result);
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {
        }

        @Override
        public void cancleAsyncTask() {

        }
    };


    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(Tag + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(Tag + "onResume");

        //		mLoadingView.startLoading();
//        registNetworkReceiver();
    }


    /****
     * 与网络状态相关
     */
    private BroadcastReceiver receiver;

    private void registNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(receiver, filter);
        }
    }

    private void unregistNetworkReceiver() {
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        ButterKnife.unbind(this);
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type = networkInfo.getType();
                    if (ConnectivityManager.TYPE_WIFI == type) {

                    } else if (ConnectivityManager.TYPE_MOBILE == type) {

                    } else if (ConnectivityManager.TYPE_ETHERNET == type) {

                    }
                    //					Toast.makeText(getActivity(), "有网络", 0).show();
                    LogUtils.d("有网络");
                    isNoNetwork = false;
                } else {
                    //没有网络
                    LogUtils.d("没有网络");
                    Toast.makeText(getActivity(), "没有网络", Toast.LENGTH_SHORT).show();
                    isNoNetwork = true;
                }
            }
        }
    }

    private boolean isNoNetwork;


}