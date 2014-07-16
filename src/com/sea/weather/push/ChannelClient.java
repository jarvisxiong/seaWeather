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
import com.sea.weather.date.model.PushMessagesVO;
import com.sea.weather.utils.SeaConstant;

public class ChannelClient {

	public void pushBroadcastMessage(String message) {

        // 1. 设置developer平台的ApiKey/SecretKey
        ChannelKeyPair pair = new ChannelKeyPair(SeaConstant.apiKey, SeaConstant.secretKey);

        // 2. 创建BaiduChannelClient对象实例
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. 若要了解交互细节，请注册YunLogHandler类
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
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
            // 处理客户端错误异常
            e.printStackTrace();
        } catch (ChannelServerException e) {
            // 处理服务端错误异常
            System.out.println(String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        }
    }
	
	public static void main(String[] args) {
		ChannelClient objChannelClient = new ChannelClient();
		PushMessagesVO objPushMessagesVO = new PushMessagesVO();
		objPushMessagesVO.setTitle("测试url");
		objPushMessagesVO.setDescription("test");
		objPushMessagesVO.setUrl("http://www.baidu.com/");
		JsonObject obj = new JsonObject();
		obj.addProperty("mykey", "http://www.baidu.com/");
		objPushMessagesVO.setCustom_content(obj.toString());
		Gson gson = new Gson();
		String josn = gson.toJson(objPushMessagesVO);
		objChannelClient.pushBroadcastMessage(josn);
	}
	
}
