package com.openxc;

import java.net.URI;

import android.content.Intent;
import android.os.RemoteException;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import com.openxc.measurements.AcceleratorPedalPosition;
import com.openxc.measurements.BrakePedalStatus;
import com.openxc.measurements.FuelConsumed;
import com.openxc.measurements.FuelLevel;
import com.openxc.measurements.HeadlampStatus;
import com.openxc.measurements.HighBeamStatus;
import com.openxc.measurements.Latitude;
import com.openxc.measurements.Longitude;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.SteeringWheelAngle;
import com.openxc.measurements.TorqueAtTransmission;
import com.openxc.measurements.TransmissionGearPosition;
import com.openxc.measurements.UnrecognizedMeasurementTypeException;
import com.openxc.measurements.VehicleButtonEvent;
import com.openxc.measurements.VehicleDoorStatus;
import com.openxc.measurements.VehicleSpeed;
import com.openxc.measurements.WindshieldWiperStatus;
import com.openxc.sources.trace.TraceVehicleDataSource;

public class MeasurementsTest extends ServiceTestCase<VehicleManager> {
    VehicleManager service;
    URI traceUri;
    TraceVehicleDataSource source;

    public MeasurementsTest() {
        super(VehicleManager.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        traceUri = TestUtils.copyToStorage(getContext(), R.raw.tracejson,
                "trace.json");

        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), VehicleManager.class);
        service = ((VehicleManager.VehicleBinder)
                bindService(startIntent)).getService();
        service.waitUntilBound();
        source = new TraceVehicleDataSource(getContext(), traceUri);
        service.addSource(source);
        TestUtils.pause(100);
    }

    @Override
    protected void tearDown() throws Exception {
        service.removeSource(source);
        super.tearDown();
    }

    private void checkReceivedMeasurement(Measurement measurement) {
        assertNotNull(measurement);
    }

    @MediumTest
    public void testGetSpeed() throws UnrecognizedMeasurementTypeException,
            NoValueException, RemoteException, InterruptedException {
        VehicleSpeed measurement = (VehicleSpeed)
                service.get(VehicleSpeed.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 42.0);
    }

    @MediumTest
    public void testGetSteeringWheelAngle()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        SteeringWheelAngle measurement = (SteeringWheelAngle)
                service.get(SteeringWheelAngle.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 94.1);
    }

    @MediumTest
    public void testGetTorqueAtTransmission()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        TorqueAtTransmission measurement = (TorqueAtTransmission)
                service.get(TorqueAtTransmission.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 232.1);
    }

    @MediumTest
    public void testGetOdometer()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        Odometer measurement = (Odometer) service.get(Odometer.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 124141.0);
    }

    @MediumTest
    public void testGetFuelLevel()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        FuelLevel measurement = (FuelLevel)
                service.get(FuelLevel.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 71.2);
    }

    @MediumTest
    public void testGetFuelConsumed()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        FuelConsumed measurement = (FuelConsumed)
                service.get(FuelConsumed.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 81.2);
    }

    @MediumTest
    public void testGetAcceleratorPedalPosition()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        AcceleratorPedalPosition measurement = (AcceleratorPedalPosition)
                service.get(AcceleratorPedalPosition.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 14.0);
    }

    @MediumTest
    public void testGetLatitude() throws UnrecognizedMeasurementTypeException,
            NoValueException, RemoteException, InterruptedException {
        Latitude measurement = (Latitude) service.get(Latitude.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 45.123);
    }

    @MediumTest
    public void testGetLongitude() throws UnrecognizedMeasurementTypeException,
            NoValueException, RemoteException, InterruptedException {
        Longitude measurement = (Longitude) service.get(Longitude.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 120.442);
    }

    @MediumTest
    public void testGetWindshieldWiperStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        WindshieldWiperStatus measurement = (WindshieldWiperStatus)
                service.get(WindshieldWiperStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), true);
    }

    @MediumTest
    public void testGetBrakePedalStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        BrakePedalStatus measurement = (BrakePedalStatus)
            service.get(BrakePedalStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), false);
    }

    @MediumTest
    public void testGetHeadlampStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        HeadlampStatus measurement = (HeadlampStatus)
            service.get(HeadlampStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), true);
    }

    @MediumTest
    public void testGetHighBeamStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        HighBeamStatus measurement = (HighBeamStatus)
            service.get(HighBeamStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), false);
    }

    @MediumTest
    public void testGetTransmissionGearPosition()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        TransmissionGearPosition measurement = (TransmissionGearPosition)
                service.get(TransmissionGearPosition.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().enumValue(),
                TransmissionGearPosition.GearPosition.FIRST);
    }

    @MediumTest
    public void testGetVehicleButtonEvent()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        VehicleButtonEvent event = (VehicleButtonEvent)
                service.get(VehicleButtonEvent.class);
        checkReceivedMeasurement(event);
        assertEquals(event.getValue().enumValue(),
                VehicleButtonEvent.ButtonId.OK);
        assertEquals(event.getEvent().enumValue(),
                VehicleButtonEvent.ButtonAction.PRESSED);
    }

    @MediumTest
    public void testGetVehicleDoorStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        VehicleDoorStatus event = (VehicleDoorStatus)
                service.get(VehicleDoorStatus.class);
        checkReceivedMeasurement(event);
        assertEquals(event.getValue().enumValue(),
                VehicleDoorStatus.DoorId.DRIVER);
        assertEquals(event.getEvent().booleanValue(), true);
    }
}

