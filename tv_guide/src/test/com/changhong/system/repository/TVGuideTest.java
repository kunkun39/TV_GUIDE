package com.changhong.system.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.changhong.client.web.controller.ClientBusiness;
import com.changhong.client.web.controller.ClientResourceController;
import com.changhong.common.utils.WebUtils;

/**
 * @author Eric Fang
 *
 * Apr 11, 2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class TVGuideTest extends TestCase{

	private static final String host = "http://localhost:8080/TM/client/resources.html";
    private static final String ANDROID = "android";
    private static final String VERSION = "1.0.0";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void test() {
		JSONObject request = new JSONObject();
		request.put(ClientResourceController.APP_TYPE, "android");
		request.put(ClientResourceController.APP_VERSION, "1.0.0");
		request.put(ClientResourceController.BUSINESS_TYPE, ClientBusiness.PROFRAM_INFO);

		JSONObject body = new JSONObject();
		body.put("id", 7);
		body.put("time", "2016-04-15");
		request.put(ClientResourceController.REQUEST_BODAY, body);

		PostMethod postMethod = new PostMethod(host);
		System.out.println(request.toJSONString());
		postMethod.addParameter("json", request.toJSONString());

		String result = WebUtils.httpPostRequest(postMethod);
		System.out.println(result);
    }
}
