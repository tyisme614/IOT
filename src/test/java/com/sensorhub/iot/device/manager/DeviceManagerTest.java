package com.sensorhub.iot.device.manager;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sensorhub.iot.device.domain.DeviceInfo;
import com.sensorhub.iot.user.domain.UserInfo;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class DeviceManagerTest
{

    private DeviceManager deviceManager;

    @Resource
    public void setDeviceManager(DeviceManager deviceManager)
    {
        this.deviceManager = deviceManager;
    }


    @Test
    @DatabaseSetup(value = "classpath:DB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testRegisterDeviceWithoutUserName() throws Exception
    {
        List<String> sensors = new ArrayList();
        sensors.add("sensor_hchc,ZF_sensor_hchc_01");
        DeviceInfo deviceInfo =  deviceManager.registerDeviceWithoutUserName("ZF_HCHC", "HCHC", sensors);
        DeviceInfo deviceInfo1 = deviceManager.findUniqueBy("uuid", "ZF_HCHC");
        assertEquals("ZF_HCHC", deviceInfo1.getUuid());
    }

    @Test
    @DatabaseSetup(value = "classpath:DB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testRegisterDeviceWithUserName() throws Exception
    {
        List<String> sensors = new ArrayList();
        sensors.add("sensor_hchc,ZF_sensor_hchc_01");
        DeviceInfo deviceInfo =  deviceManager.registerDeviceWithoutUserName("ZF_HCHC", "HCHC", sensors);

        List<String> uuids = new ArrayList<String>();
        uuids.add("ZF_HCHC");
        UserInfo userInfo = deviceManager.registerDeviceWithUserName("1",uuids);
        assertEquals("zhangfan", userInfo.getUsername());

    }

}