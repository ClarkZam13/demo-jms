package com.example.demo.fixture;

import com.example.demo.dto.Transaccion;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Enumeration;

import static com.example.demo.fixture.DatosBasicos.ejecuciones;


public class MensajeVenta implements Message {

    @Override
    public String getJMSMessageID() throws JMSException {
        return null;
    }

    @Override
    public void setJMSMessageID(String s) throws JMSException {

    }

    @Override
    public long getJMSTimestamp() throws JMSException {
        return 0;
    }

    @Override
    public void setJMSTimestamp(long l) throws JMSException {

    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        return new byte[0];
    }

    @Override
    public void setJMSCorrelationIDAsBytes(byte[] bytes) throws JMSException {

    }

    @Override
    public void setJMSCorrelationID(String s) throws JMSException {

    }

    @Override
    public String getJMSCorrelationID() throws JMSException {
        return null;
    }

    @Override
    public Destination getJMSReplyTo() throws JMSException {
        return null;
    }

    @Override
    public void setJMSReplyTo(Destination destination) throws JMSException {

    }

    @Override
    public Destination getJMSDestination() throws JMSException {
        return null;
    }

    @Override
    public void setJMSDestination(Destination destination) throws JMSException {

    }

    @Override
    public int getJMSDeliveryMode() throws JMSException {
        return 0;
    }

    @Override
    public void setJMSDeliveryMode(int i) throws JMSException {

    }

    @Override
    public boolean getJMSRedelivered() throws JMSException {
        return false;
    }

    @Override
    public void setJMSRedelivered(boolean b) throws JMSException {

    }

    @Override
    public String getJMSType() throws JMSException {
        return null;
    }

    @Override
    public void setJMSType(String s) throws JMSException {

    }

    @Override
    public long getJMSExpiration() throws JMSException {
        return 0;
    }

    @Override
    public void setJMSExpiration(long l) throws JMSException {

    }

    @Override
    public long getJMSDeliveryTime() throws JMSException {
        return 0;
    }

    @Override
    public void setJMSDeliveryTime(long l) throws JMSException {

    }

    @Override
    public int getJMSPriority() throws JMSException {
        return 0;
    }

    @Override
    public void setJMSPriority(int i) throws JMSException {

    }

    @Override
    public void clearProperties() throws JMSException {

    }

    @Override
    public boolean propertyExists(String s) throws JMSException {
        return false;
    }

    @Override
    public boolean getBooleanProperty(String s) throws JMSException {
        return false;
    }

    @Override
    public byte getByteProperty(String s) throws JMSException {
        return 0;
    }

    @Override
    public short getShortProperty(String s) throws JMSException {
        return 0;
    }

    @Override
    public int getIntProperty(String s) throws JMSException {
        return 0;
    }

    @Override
    public long getLongProperty(String s) throws JMSException {
        return 0;
    }

    @Override
    public float getFloatProperty(String s) throws JMSException {
        return 0;
    }

    @Override
    public double getDoubleProperty(String s) throws JMSException {
        return 0;
    }

    @Override
    public String getStringProperty(String s) throws JMSException {
        return null;
    }

    @Override
    public Object getObjectProperty(String s) throws JMSException {
        return null;
    }

    @Override
    public Enumeration getPropertyNames() throws JMSException {
        return null;
    }

    @Override
    public void setBooleanProperty(String s, boolean b) throws JMSException {

    }

    @Override
    public void setByteProperty(String s, byte b) throws JMSException {

    }

    @Override
    public void setShortProperty(String s, short i) throws JMSException {

    }

    @Override
    public void setIntProperty(String s, int i) throws JMSException {

    }

    @Override
    public void setLongProperty(String s, long l) throws JMSException {

    }

    @Override
    public void setFloatProperty(String s, float v) throws JMSException {

    }

    @Override
    public void setDoubleProperty(String s, double v) throws JMSException {

    }

    @Override
    public void setStringProperty(String s, String s1) throws JMSException {

    }

    @Override
    public void setObjectProperty(String s, Object o) throws JMSException {

    }

    @Override
    public void acknowledge() throws JMSException {

    }

    @Override
    public void clearBody() throws JMSException {

    }

    @Override
    public <T> T getBody(Class<T> aClass) throws JMSException {
        Transaccion t = new Transaccion();
        try {

            Thread.sleep(1000*ejecuciones);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(ejecuciones++ < 10){
            t.setCodigo("200");
            t.setMensaje("Has hecho la venta");
            t.setId(Integer.toString(ejecuciones));
            return (T) t;
        }
        else if(ejecuciones < 15 && ejecuciones >= 10){
            t.setCodigo("500");
            t.setMensaje("Error");
            t.setId(Integer.toString(ejecuciones));
            return (T) t;
        } else {
            throw new JMSException("");
        }
    }

    @Override
    public boolean isBodyAssignableTo(Class aClass) throws JMSException {
        return false;
    }
}
