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

        // 1. ����developerƽ̨��ApiKey/SecretKey
        ChannelKeyPair pair = new ChannelKeyPair(SeaConstant.apiKey, SeaConstant.secretKey);

        // 2. ����BaiduChannelClient����ʵ��
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. ��Ҫ�˽⽻��ϸ�ڣ���ע��YunLogHandler��
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
            	Log.i("ChannelClient.pushBroadcastMessage push Message");
                //System.out.println(event.getMessage());
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
		objPushMessagesVO.setTitle("̨��Ԥ��");
		objPushMessagesVO.setDescription("����̨07��18��10ʱ����");
		
		//����Ϊ���ݣ�������ҳ
		objPushCustomContentVO.setActKey(SeaConstant.pushTypeMeg);
		objPushCustomContentVO.setActValue(SeaConstant.pushMegAct_SI);
		objPushMessagesVO.setCustom_content(gson.toJson(objPushCustomContentVO));
		
		String josn = gson.toJson(objPushMessagesVO);
		ChannelClient.pushBroadcastMessage(josn);
	}
	
}
