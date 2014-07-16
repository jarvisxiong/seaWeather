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

        // 1. ����developerƽ̨��ApiKey/SecretKey
        ChannelKeyPair pair = new ChannelKeyPair(SeaConstant.apiKey, SeaConstant.secretKey);

        // 2. ����BaiduChannelClient����ʵ��
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. ��Ҫ�˽⽻��ϸ�ڣ���ע��YunLogHandler��
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {

            // 4. �������������
            PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
            //֪ͨ
            request.setMessageType(1);
            //��׿�豸
            request.setDeviceType(3);
            request.setMessage(message);

            // 5. ����pushMessage�ӿ�
            PushBroadcastMessageResponse response = channelClient
                    .pushBroadcastMessage(request);
            if (response.getSuccessAmount() == 1) {
                // TODO
            }

        } catch (ChannelClientException e) {
            // ����ͻ��˴����쳣
            e.printStackTrace();
        } catch (ChannelServerException e) {
            // �������˴����쳣
            System.out.println(String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        }
    }
	
	public static void main(String[] args) {
		ChannelClient objChannelClient = new ChannelClient();
		PushMessagesVO objPushMessagesVO = new PushMessagesVO();
		objPushMessagesVO.setTitle("����url");
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
