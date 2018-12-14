package cn.freeexchange.common.base.usertype;

import static cn.freeexchange.common.base.utils.StringUtils.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;

public class StringMapUserType extends BaseUserType {

    @Override
    public void setParameterValues(Properties properties) {
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{StandardBasicTypes.STRING.sqlType()};
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor sessionImplementor, Object owner) throws HibernateException, SQLException {
        String mapStr = rs.getString(names[0]);
        if (StringUtils.isBlank(mapStr)) {
            return new LinkedHashMap<>();
        }
        return string2map(mapStr);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sessionImplementor) throws HibernateException, SQLException {
        String str = value != null ? map2string((Map) value) : null;
        st.setString(index, str);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) return null;
        return new HashMap<>((Map) value);
    }
}