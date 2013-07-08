package com.openxc.util;

import java.util.Calendar;
import java.util.Date;

import com.google.common.base.Objects;
import com.openxc.units.Unit;

/**
 * AgingData is a container for a data value that keeps track of its age.
 *
 * This class keeps track of the birth time of a bit of data, i.e. time time
 * the AgingData object is instantiated.
 */
public class AgingData<TheUnit extends Unit> {
    TheUnit mValue;
    private Date mBirthdate;

    /**
     * Construct an instance of AgingData with the value of unit.
     *
     * @param value The data value for this bit of AgingData.
     */
    public AgingData(TheUnit value) {
        this(new Date(), value);
    }

    public AgingData(Date birthdate, TheUnit value) {
        mValue = value;
        mBirthdate = birthdate;
    }

    /**
     * Return the value this instance wraps.
     *
     * @return The wrapped value (an instance of TheUnit)
     */
    public TheUnit getValue() {
        return mValue;
    }

    /**
     * Retrieve the age of this piece of data.
     *
     * @return the age of the data in seconds.
     */
    public double getAge() {
        return (new Date()).getTime() - mBirthdate.getTime();
    }

    public double getTimestamp() {
        return mBirthdate.getTime();
    }

    public void setTimestamp(double timestamp) {
        if(timestamp != 0) {
            Calendar otherTime = Calendar.getInstance();
            otherTime.setTimeInMillis(Double.valueOf(timestamp * 1000).longValue());
            mBirthdate = otherTime.getTime();
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("value", mValue)
            .add("birthdate", mBirthdate)
            .toString();
    }
}
