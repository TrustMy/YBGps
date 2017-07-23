package com.shengyu.ybgps.tools.sy;


public class ClassUtils {
//	public static Logger logger = LoggerFactory.getLogger(ClassUtils.class);

	public static Object getBean(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (Exception ex) {
//			logger.info("Can't find the class");
		}
		if (clazz != null) {
			try {
				return clazz.newInstance();
			} catch (Exception ex) {
//				logger.info("Initial class instance exception");
			}
		}
		return null;
	}
}
