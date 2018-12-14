package cn.freeexchange.common.base.usertype;

import java.io.Serializable;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public abstract class BaseUserType implements UserType,ParameterizedType {

	public BaseUserType() {}

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        } else {
            return x != null && y != null ? x.equals(y) : false;
        }
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object deepCopy(Object value) throws HibernateException {
        if(value instanceof HashMap) return ((HashMap) value).clone();
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
