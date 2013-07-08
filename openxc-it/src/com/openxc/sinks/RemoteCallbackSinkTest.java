package com.openxc.sinks;

import com.openxc.remote.RawMeasurement;
import com.openxc.remote.VehicleServiceListener;

import android.test.AndroidTestCase;

import android.test.suitebuilder.annotation.SmallTest;

public class RemoteCallbackSinkTest extends AndroidTestCase {
    RemoteCallbackSink notifier;
    VehicleServiceListener listener;
    String measurementId = "the_measurement";
    String receivedId = null;

    @Override
    public void setUp() {
        notifier = new RemoteCallbackSink();
        listener = new VehicleServiceListener.Stub() {
            public void receive(RawMeasurement value) {
                receivedId = value.getName();
            }
        };
    }

    @SmallTest
    public void testRegister() {
        assertEquals(0, notifier.getListenerCount());
        notifier.register(listener);
        assertEquals(1, notifier.getListenerCount());
    }

    @SmallTest
    public void testUnregisterInvalid() {
        // this just shouldn't explode, it should ignore it...or should it?
        // failing silently is usually a bad thing
        assertEquals(0, notifier.getListenerCount());
        notifier.unregister(listener);
        assertEquals(0, notifier.getListenerCount());
    }

    @SmallTest
    public void testUnregisterValid() {
        notifier.register(listener);
        assertEquals(1, notifier.getListenerCount());
        notifier.unregister(listener);
        assertEquals(0, notifier.getListenerCount());
    }

    @SmallTest
    public void testReceiveCorrectId() throws DataSinkException {
        notifier.register(listener);
        assertNull(receivedId);
        notifier.receive(new RawMeasurement(measurementId, 1));
        try {
            Thread.sleep(50);
        } catch(InterruptedException e) {}
        assertTrue(notifier.containsMeasurement(measurementId));
        assertNotNull(receivedId);
        assertEquals(receivedId, measurementId);
    }
}
