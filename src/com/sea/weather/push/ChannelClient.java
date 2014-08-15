package com.sea.weather.push;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sea.weather.date.model.PushCustomContentVO;
import com.sea.weather.date.model.PushMessagesVO;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.SeaConstant;

public class ChannelClient {

	public static void pushBroadcastMessage(String message) {

        // 1. 设置developer平台的ApiKey/SecretKey
        ChannelKeyPair pair = new ChannelKeyPair(SeaConstant.apiKey, SeaConstant.secretKey);

        // 2. 创建BaiduChannelClient对象实例
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. 若要了解交互细节，请注册YunLogHandler类
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
            	Log.i("ChannelClient.pushBroadcastMessage push Message");
                //System.out.println(event.getMessage());
            }
        });

        try {

            // 4. 创建请求类对象
            PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
            //通知
            request.setMessageType(1);
            //安卓设备
            request.setDeviceType(3);
            request.setMessage(message);

            // 5. 调用pushMessage接口
            PushBroadcastMessageResponse response = channelClient
                    .pushBroadcastMessage(request);
            if (response.getSuccessAmount() == 1) {
                // TODO
            }

        } catch (ChannelClientException e) {
        	Log.e("ChannelClient.pushBroadcastMessage",e);
        } catch (ChannelServerException e) {
        	Log.e(String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()),e);
        }
    }
	
	public static void main(String[] args) {
		PushMessagesVO objPushMessagesVO = new PushMessagesVO();
		PushCustomContentVO objPushCustomContentVO = new PushCustomContentVO();
		Gson gson = new Gson();
		objPushMessagesVO.setTitle("台风预警");
		objPushMessagesVO.setDescription("中央台07月18日10时发布");
		
		//设置为内容，启动首页
		objPushCustomContentVO.setActKey(SeaConstant.pushTypeMeg);
		objPushCustomContentVO.setActValue(SeaConstant.pushMegAct_SI);
		objPushMessagesVO.setCustom_content(gson.toJson(objPushCustomContentVO));
		
		String josn = gson.toJson(objPushMessagesVO);
		ChannelClient.pushBroadcastMessage(josn);
	}
	
}
