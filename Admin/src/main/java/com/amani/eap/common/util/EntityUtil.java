package com.amani.eap.common.util;

import com.amani.eap.common.annotation.ValueInject;
import com.amani.eap.common.annotation.ValueSource;
import com.amani.eap.common.constant.BaseConstants;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 实体类相关工具类 
 * 解决问题： 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 * 
 */
public class EntityUtil {

	public static Map<String, String> getRequestHeader() {
		return new HashMap<String, String>() {{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			if (request != null) {
				put(BaseConstants.REQUEST_HEADER_HOST, request.getHeader(BaseConstants.REQUEST_HEADER_HOST));
				try {
					String username = request.getHeader(BaseConstants.REQUEST_HEADER_UNAME);
					put(BaseConstants.REQUEST_HEADER_UNAME, username == null ? username : URLDecoder.decode(username, null));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				put(BaseConstants.REQUEST_HEADER_UID, request.getHeader(BaseConstants.REQUEST_HEADER_UID));
			}
		}};
	}

	/**
	 * 快速将bean的crtUser、crtHost、crtTime、updUser、updHost、updTime附上相关值
	 * 
	 * @param entity 实体bean 
	 */
	public static <T> void setCreatAndUpdatInfo(T entity) {
		setCreateInfo(entity);
		setUpdatedInfo(entity);
	}

	/**
	 * 快速将bean的crtUser、crtHost、crtTime附上相关值
	 * 
	 * @param entity 实体bean
	 */
	public static <T> void setCreateInfo(T entity) {
		if (hasValueInject(entity)) {
			setEntityInfo(entity, false);
		} else {
			String[] fields = {"createName", "createUser", "createHost", "createTime"}; // 默认属性
			setDefaultValues(entity, fields, "createTime");
		}
	}

	/**
	 * 快速将bean的updUser、updHost、updTime附上相关值
	 * 
	 * @param entity 实体bean
	 */
	public static <T> void setUpdatedInfo(T entity) {
		if (hasValueInject(entity)) {
			setEntityInfo(entity, true);
		} else {
			String[] fields = {"updateName","updateUser","updateHost","updateTime"}; // 默认属性
			setDefaultValues(entity, fields, "updateTime");
		}
	}

	private static <T> void setDefaultValues(T entity, String[] fields, String accessibleField) {
		Field field = ReflectionUtil.getAccessibleField(entity, accessibleField);
		Object [] value = null;
		if(field != null && Date.class.equals(field.getType())){
			Map<String, String> values = getRequestHeader();
			value = new Object [] { values.get(BaseConstants.REQUEST_HEADER_UNAME),
					values.get(BaseConstants.REQUEST_HEADER_UID),
					values.get(BaseConstants.REQUEST_HEADER_HOST),
					new Date() };
		}

		/**
		 * 填充默认属性值
		 * 依据对象的属性数组和值数组对对象的属性进行赋值
		 */
		for(int i = 0; i < fields.length; i++) {
			String fl = fields[i];
			if (ReflectionUtil.hasField(entity, fl)) {
				ReflectionUtil.invokeSetter(entity, fl, value[i]);
			}
		}
	}

	/**
	 * 根据主键属性，判断主键是否值为空
	 * 
	 * @param entity
	 * @param field
	 * @return 主键为空，则返回false；主键有值，返回true
	 */
	public static <T> boolean isPKNotNull(T entity,String field){
		if(!ReflectionUtil.hasField(entity, field))
			return false;
		Object value = ReflectionUtil.getFieldValue(entity, field);
		return value!=null&&!"".equals(value);
	}

	/**
	 * 查看Entity及其父類是否含有@ValueInject
	 *
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public static <T> boolean hasValueInject(T entity) {
		Class clazz = entity.getClass();
		Class superClazz = clazz.getSuperclass();
		Class vi = ValueInject.class;

		return clazz.getAnnotation(vi) != null || superClazz.getAnnotation(vi) != null;
	}

	public static boolean hasValueInject(Class clazz) {
		return clazz.getAnnotation(ValueInject.class) != null;
	}


	/**
	 * <PRE>
	 * Updated by Guiwang, 2018/03/27
	 * 1.僅entity含有註解@ValueInject (僅檢查entity@ValueSource)
	 * 2.父類含有註解@ValueInject (父子都檢查@ValueSource)
	 * 3.父子皆含有註解@ValueInject (父子都檢查@ValueSource)
	 * </PRE>
	 *
	 * @param entity
	 * @param <T>
	 */
	public static <T> void setEntityInfo(T entity, boolean isUpdate) {
		Class clazz = entity.getClass();
		Class superClass = clazz.getSuperclass();

		setFieldValue(entity, clazz, isUpdate);
		if (hasValueInject(superClass)) setFieldValue(entity, superClass, isUpdate);
	}

	/**
	 * 根據對象的屬性註解@ValueSource設置屬性值
	 *
	 * @param entity
	 * @param clazz
	 * @param <T>
	 */
	private static <T> void setFieldValue(T entity, Class clazz, boolean isUpdate) {
		Field[] fields = clazz.getDeclaredFields();
		Date curDate = new Date();
		Map<String, String> values = getRequestHeader();

		for (Field fl:fields) {
			ValueSource vs = fl.getAnnotation(ValueSource.class);
			if (vs != null) {
				Object key = vs.value().getKey();
				if (vs.update() == isUpdate) {
					ReflectionUtil.invokeSetter(entity, fl.getName(), Date.class.equals(key)?curDate:values.get(key));
				}
			}
		}
	}

}
