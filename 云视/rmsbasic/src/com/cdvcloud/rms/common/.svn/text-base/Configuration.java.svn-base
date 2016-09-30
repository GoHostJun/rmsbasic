package com.cdvcloud.rms.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cdvcloud.rms.util.StringUtil;

/**
 * Read the project configuration class
 * 
 * @author huangaigang  E-mail: huangaigang168@qq.com
 * @version V1.0
 * @date: 2014年5月6日
 */
public class Configuration {

	static protected final Log log = LogFactory.getLog(Configuration.class);

	private static final String FILE_NAME = "/dz.properties";
	private static final Properties properties = new Properties();
	static {
		try {
			properties.load(Configuration.class.getResourceAsStream(FILE_NAME));
			if (Constants.BIND) {
				getFlags();
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	// //////////////////////////////////////////////////////////////////////////////
	// 以下是从配置项中读取参数的接口
	// //////////////////////////////////////////////////////////////////////////////
	/**
	 * 根据key拿到boolean值，如果是true则返回true，如果是别的都返回false，支持默认值
	 * 
	 * @Title: getConfigBooleanStyle
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: boolean
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:47
	 */
	public static boolean getConfigBooleanStyle(final String key, Boolean defaultValue) {
		boolean rst = false;
		if (defaultValue != null) {
			rst = defaultValue;
		}
		String s = properties.getProperty(key);
		if (s != null) {
			rst = Boolean.parseBoolean(s.trim());
		}
		return rst;
	}

	/**
	 * 根据key读取并返回整形值，支持默认值
	 * 
	 * @Title: getConfigIntegerStyle
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: int
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:43
	 */
	public static int getConfigIntegerStyle(final String key, int defaultValue) {
		String str = properties.getProperty(key, new Integer(defaultValue).toString());
		int result = defaultValue;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			log.error("getConfigIntegerStyle()读取‘" + key + "’资源时出错。请检查该配置项是否是整数类型");
		}
		return result;
	}

	/**
	 * 根据key读取并返回浮点类型值，支持默认值
	 * 
	 * @Title: getConfigFloatStyle
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: float
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:38
	 */
	public static float getConfigFloatStyle(final String key, float defaultValue) {
		float result = defaultValue;
		try {
			String str = properties.getProperty(key);
			result = Float.parseFloat(str);
		} catch (Exception e) {
			log.error("getConfigFloatStyle()读取‘" + key + "’资源时出错。请检查是否为空或该配置项不是浮点类型");
		}
		return result;
	}

	/**
	 * 根据key读取配置文件并返回整形数组，支持默认值
	 * 
	 * @Title: getConfigIntArrayStyle
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: int[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:32
	 */
	public static int[] getConfigIntArrayStyle(final String key, int[] defaultValue) {
		String[] strs = getConfigArrayStyle(key, null);
		if (strs == null || strs.length < 1)
			return defaultValue;

		int[] result = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = new Integer(strs[i]).intValue();
		}
		return result;
	}

	/**
	 * 获取配置文件中以','分割的int类型数据列表，支持默认值
	 * 
	 * @Title: getConfigIntList
	 * @param: @param key
	 * @param: @param defalutValue
	 * @param: @return
	 * @return: List<Integer>
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:27
	 */
	public static List<Integer> getConfigIntList(final String key, int[] defalutValue) {
		List<Integer> list = new ArrayList<Integer>();
		int[] tmp = getConfigIntArrayStyle(key, defalutValue);
		if (tmp == null) {
			return list;
		}
		for (int i : tmp) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 根据key获取配置文件中的值并返回short数组，支持默认值
	 * 
	 * @Title: getConfigShortArrayStyle
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: short[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:22
	 */
	public static short[] getConfigShortArrayStyle(String key, short[] defaultValue) {
		String[] strs = getConfigArrayStyle(key, null);
		if (strs == null || strs.length < 1)
			return defaultValue;

		short[] result = new short[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = new Short(strs[i]).shortValue();
		}
		return result;
	}

	/**
	 * 根据key获取配置文件中的值并返回float数组，支持默认值
	 * 
	 * @Title: getConfigFloatArrayStyle
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: float[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:17
	 */
	public static float[] getConfigFloatArrayStyle(final String key, float[] defaultValue) {
		String[] strs = getConfigArrayStyle(key, null);
		if (strs == null || strs.length < 1)
			return defaultValue;

		float[] result = new float[strs.length];
		for (int i = 0; i < strs.length; i++) {
			try {
				result[i] = new Float(strs[i]).floatValue();
			} catch (Exception e) {
				log.error("getConfigFloatArrayStyle()读取‘" + key + "’资源时出错。请检查是否为空或该配置项不是浮点类型");
			}
		}
		return result;
	}

	/**
	 * 获取配置值
	 * 
	 * @Title: getConfigValue
	 * @param: @param key
	 * @param: @return
	 * @return: String
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:13
	 */
	public static String getConfigValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取配置值，支持默认值
	 * 
	 * @Title: getConfigValue
	 * @param: @param key
	 * @param: @param defaultValue
	 * @param: @return
	 * @return: String
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:08
	 */
	public static String getConfigValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	/**
	 * 根据key拿到boolean值，如果是true则返回true，如果是别的都返回false
	 * 
	 * @Title: getConfigBooleanStyle
	 * @param: @param key
	 * @param: @return
	 * @return: boolean
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:54:02
	 */
	public static boolean getConfigBooleanStyle(final String key) {
		return getConfigBooleanStyle(key, null);
	}

	/**
	 * 根据key读取并返回整形值
	 * 
	 * @Title: getConfigIntegerStyle
	 * @param: @param key
	 * @param: @return
	 * @return: int
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:53:57
	 */
	public static int getConfigIntegerStyle(final String key) {
		return getConfigIntegerStyle(key, 0);
	}

	/**
	 * 根据key读取并返回浮点类型值
	 * 
	 * @Title: getConfigFloatStyle
	 * @param: @param key
	 * @param: @return
	 * @return: float
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:53:53
	 */
	public static float getConfigFloatStyle(final String key) {
		return getConfigFloatStyle(key, 0);
	}

	/**
	 * 根据key读取配置文件并返回整形数组
	 * 
	 * @Title: getConfigIntArrayStyle
	 * @param: @param key
	 * @param: @return
	 * @return: int[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:53:48
	 */
	public static int[] getConfigIntArrayStyle(final String key) {
		return getConfigIntArrayStyle(key, null);
	}

	/**
	 * 根据key获取配置文件中的值并返回short数组
	 * 
	 * @Title: getConfigShortArrayStyle
	 * @param: @param key
	 * @param: @return
	 * @return: short[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:55
	 */
	public static short[] getConfigShortArrayStyle(String key) {
		return getConfigShortArrayStyle(key, null);
	}

	/**
	 * 根据key获取配置文件中的值并返回float数组
	 * 
	 * @Title: getConfigFloatArrayStyle
	 * @param: @param key
	 * @param: @return
	 * @return: float[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:50
	 */
	public static float[] getConfigFloatArrayStyle(final String key) {
		return getConfigFloatArrayStyle(key, null);
	}

	/**
	 * 根据key值读取配置文件并返回字符串数组
	 * 
	 * @Title: getConfigArrayStyle
	 * @param: @param key
	 * @param: @return
	 * @return: String[]
	 * @throws
	 * @author huangaigang
	 * @Date 22014年5月6日10:31:45
	 */
	public static String[] getConfigArrayStyle(String key) {
		return getConfigArrayStyle(key, null);
	}

	/**
	 * 将(key,value)设置到configuration中。便于service的调试
	 * 
	 * @Title: setConfig
	 * @param: @param key
	 * @param: @param value
	 * @return: void
	 * @throws
	 * @author huangaigang
	 * @Date 22014年5月6日10:31:40
	 */
	public static void setConfig(String key, String value) {
		if (key == null || key.equals("") || value == null || value.equals(""))
			return;
		properties.put(key, value);
	}

	/**
	 * 根据key读取项目的配置项的值,配置项的值,对于只有一个值的情况，返回的list中只有一个元素
	 * 
	 * @Title: getConfigByKey
	 * @param: @param key
	 * @param: @param defaultValues
	 * @param: @return
	 * @return: List<String>
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:35
	 */
	public static List<String> getConfigByKey(final String key, String defaultValues) {
		List<String> values = new ArrayList<String>();
		String value = properties.getProperty(key);
		if (value != null) {
			switch (valueType(value)) {
			case 1:
				values.add(value);
				break;
			case 2:
				values.addAll(getListString(value));
				break;
			}
		} else {
			values.addAll(getListString(defaultValues));
		}
		return values;
	}

	/**
	 * 从字符串中取得集合类型的配置项
	 * 
	 * @Title: getListString
	 * @param: @param value
	 * @param: @return
	 * @return: Collection<String>
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:30
	 */
	private static Collection<String> getListString(String value) {
		List<String> valuesList = new ArrayList<String>();
		if (value != null) {
			String[] values = value.split(Constants.DELIMITER);
			for (int i = 0; i < values.length; i++) {
				valuesList.add(values[i]);
			}
		}
		return valuesList;
	}

	/**
	 * 获取字符串的个数
	 * 
	 * @Title: valueType
	 * @param: @param value
	 * @param: @return
	 * @return: int
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:24
	 */
	private static int valueType(String value) {
		int singleStringValue = 1;
		int listStringValue = 2;
		int nullValue = 98;
		int unKnowValue = 99;
		if (value == null)
			return nullValue;

		int indexOfSeparatingChar = value.indexOf(Constants.DELIMITER);
		if (indexOfSeparatingChar == -1) {
			return singleStringValue;
		} else if (indexOfSeparatingChar > 0) {
			return listStringValue;
		} else {
			return unKnowValue;
		}
	}

	/**
	 * 读取配置,配置格式为“1:A,O,E,I,U;2:B,C”
	 * 
	 * @Title: getMapByConfigKey
	 * @param: @param config_key
	 * @param: @return
	 * @return: Map<Long,String>
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:18
	 */
	public static Map<Long, String> getMapByConfigKey(String config_key) {
		Map<Long, String> map = new LinkedHashMap<Long, String>();
		String configValue = Configuration.getConfigValue(config_key);
		if (configValue != null) {
			String[] configValues = configValue.split(";");
			for (String value : configValues) {
				String[] values = value.split(":");
				map.put(Long.valueOf(values[0]), values[1]);
			}
		}
		return map;
	}

	/**
	 * 分割类似 key1:value1;key2:value2;key3:value3之类的字符串,转换为Map
	 * 
	 * @Title: getMapByConfigKey
	 * @param: @param config_key
	 * @param: @param splitStr1
	 * @param: @param splitStr2
	 * @param: @return
	 * @return: Map<String,String>
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:11
	 */
	public static Map<String, String> getMapByConfigKey(String config_key, String splitStr1, String splitStr2) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String configValue = Configuration.getConfigValue(config_key);
		if (configValue != null) {
			String[] configValues = configValue.split(splitStr1);
			for (String value : configValues) {
				String[] values = value.split(splitStr2);
				map.put(values[0], values[1]);
			}
		}
		return map;
	}

	/**
	 * 根据key值读取配置文件并返回字符串数组，支持默认值
	 * 
	 * @Title: getConfigArrayStyle
	 * @param: @param key
	 * @param: @param defaultValues
	 * @param: @return
	 * @return: String[]
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:31:04
	 */
	public static String[] getConfigArrayStyle(String key, String[] defaultValues) {
		String str = properties.getProperty(key);
		if (str != null) {
			String[] value = str.split(Constants.DELIMITER);
			return value;
		} else {
			return defaultValues;
		}
	}

	/**
	 * 读取浮点类型的配置项
	 * 
	 * @Title: getFlags
	 * @return: 如果key没有找到，或者解析出错，则返回null
	 * @throws
	 * @author huangaigang
	 * @Date 2014年5月6日10:30:57
	 */
	private static String getFlags() {

		if (Constants.BIND) {
			String str1 = "";
			String str2 = "";
			try {
				BufferedReader localBufferedReader;
				String str3;
				Object localObject1 = "ipconfig /all";
				localObject1 = Runtime.getRuntime().exec((String) localObject1);
				for (Object localObject2 = (localBufferedReader = new BufferedReader(new InputStreamReader(((Process) localObject1).getInputStream()))).readLine(); localObject2 != null; localObject2 = str3) {
					str3 = localBufferedReader.readLine();
					if (((String) localObject2).indexOf("Physical Address") > 0) {
						int i = ((String) localObject2).indexOf("Physical Address") + 36;
						str2 = ((String) localObject2).substring(i);
						break;
					}
				}
				localBufferedReader.close();
				((Process) localObject1).waitFor();
			} catch (Exception localException) {
				str2 = "";
			}
			str2.replace("-", ".");
			return ((String) (str2 = StringUtil.encodePassword(str2, "SHA")).trim());
		}
		return null;
	}
}
