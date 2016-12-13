package  com.duanrong.cps.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 字符串工具类
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public class MyStringUtils extends StringUtils {

	public static boolean contains(String str, String... conStrs) {
		for (String string : conStrs) {
			boolean contains = contains(str, string);
			if (contains) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 获得字符串数组的小写形式
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] toLowerCase(String[] strs) {

		if (strs.length > 0) {

			String[] arr = new String[strs.length];

			for (int i = 0; i < strs.length; i++) {
				String lowerCase = lowerCase(strs[i]);
				arr[i] = lowerCase;
			}

			return arr;
		}

		return null;
	}

	/**
	 * 返回其中不为空的字符串，如果都不为空，则返回第一个
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getNotBlankStr(final String str1, final String str2) {
		if (isNotAnyBlank(str1, str2)) {
			return str1;
		} else if (isNotBlank(str1)) {
			return str1;
		} else if (isNotBlank(str2)) {
			return str2;
		} else {
			return null;
		}
	}

	/**
	 * 判断是否不是数字字符串，并且不符合指定长度
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            字符串长度
	 * @return
	 */
	public static Boolean isNotNumericSpecifyLength(final String str,
			final int len) {
		return !isNumericSpecifyLength(str, len);
	}

	/**
	 * 判断是否为数字字符串，并且符合指定长度
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            字符串长度
	 * @return
	 */
	public static Boolean isNumericSpecifyLength(final String str, final int len) {
		if (isNotBlank(str) && isNumeric(str)) {
			if (length(str) == len) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 去除文本中的HTML标签
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String removeHtmlTags(final String str) {
		return replacePattern(str, "<[^>]+>", "HTMLJS");
	}

	/**
	 * 增强型规则替换
	 * 
	 * @param source
	 *            源字符串
	 * @param regex
	 *            正则
	 * @param replacement
	 *            替换以后的字符串
	 * @return
	 */
	public static String replacePattern(final String source,
			final String regex, final String replacement) {
		if (isBlank(source)) {
			return null;
		} else if (regex == null || replacement == null) {
			return source;
		} else {
			return StringUtils.replacePattern(source, regex, replacement);
		}
	}

	/**
	 * 格式化输出
	 * 
	 * @param objs
	 */
	public static void printFormat(final Object... objs) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Object obj : objs) {
			stringBuffer.append(obj);
			stringBuffer.append("\t");
		}

		System.out.println(stringBuffer);
	}

	/**
	 * 忽略大小写的情况下判断原始字符串是否与其他字符串中的任意一个相同
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 * @return
	 */
	public static boolean equalsIgnoreCaseAnyString(
			final CharSequence original, final CharSequence... others) {
		for (CharSequence charSequence : others) {
			if (equalsIgnoreCase(original, charSequence)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断原始字符串是否与其他字符串中的任意一个相同
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 * @return
	 */
	public static boolean equalsAnyString(final CharSequence original,
			final CharSequence... others) {
		for (CharSequence charSequence : others) {
			if (equals(original, charSequence)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 忽略大小写的情况下判断原始字符串是否与其他字符串中的任意一个都不相同
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 * @return
	 */
	public static boolean notEqualsIgnoreCaseAnyString(
			final CharSequence original, final CharSequence... others) {
		return !equalsIgnoreCaseAnyString(original, others);
	}

	/**
	 * 判断原始字符串是否与其他字符串中的任意一个都不相同
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 * @return
	 */
	public static boolean notEqualsAnyString(final CharSequence original,
			final CharSequence... others) {
		return !equalsAnyString(original, others);
	}

	/**
	 * 字符串拼接
	 * 
	 * @param css
	 *            需要拼接的字符串
	 * @return
	 */
	public static String append(final CharSequence... css) {
		StringBuffer stringBuffer = new StringBuffer();
		for (CharSequence charSequence : css) {
			stringBuffer.append(charSequence);
		}

		return stringBuffer.toString();
	}

	/**
	 * 字符串拼接
	 * 
	 * @param objs
	 *            需要拼接的对象
	 * @return
	 */
	public static String append(final Object... objs) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Object obj : objs) {
			stringBuffer.append(obj);
		}

		return stringBuffer.toString();
	}

	/**
	 * 字符串拼接， 忽略空值，具体参照isBlank方法
	 * 
	 * @param css
	 *            需要拼接的字符串
	 * @return
	 */
	public static String appendIgnoreBlank(final CharSequence... css) {
		StringBuffer stringBuffer = new StringBuffer();
		for (CharSequence charSequence : css) {
			if (isBlank(charSequence)) {
				continue;
			}
			stringBuffer.append(charSequence);
		}

		return stringBuffer.toString();
	}

	/**
	 * 字符串拼接， 忽略空值，具体参照isEmpty方法
	 * 
	 * @param css
	 *            需要拼接的字符串
	 * @return
	 */
	public static String appendIgnoreEmpty(final CharSequence... css) {
		StringBuffer stringBuffer = new StringBuffer();
		for (CharSequence charSequence : css) {
			if (isEmpty(charSequence)) {
				continue;
			}
			stringBuffer.append(charSequence);
		}

		return stringBuffer.toString();
	}

	/**
	 * 生成指定数量的随机字母组合
	 * 
	 * @param count
	 *            字母组合长度
	 * @return
	 */
	public static String multipleLetter(int count) {
		String str = "abcdefghijklmnopqrstuvwxyz";
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < count; i++) {
			stringBuffer.append(str.charAt((int) (Math.random() * 26)));
		}
		return stringBuffer.toString();
	}

	/**
	 * 判断两个字符串不相等
	 * 
	 * @param cs1
	 * @param cs2
	 * @return
	 */
	public static boolean notEquals(final CharSequence cs1,
			final CharSequence cs2) {
		return !equals(cs1, cs2);
	}

	/**
	 * 判断所有字符串都不为空
	 * 
	 * @param css
	 * @return
	 */
	public static boolean isNotAnyBlank(final CharSequence... css) {
		return !isAnyBlank(css);
	}

	/**
	 * 判断所有Object类型的字符串都不为空
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean isNotAnyBlank(final Object... objs) {
		CharSequence[] css = new CharSequence[objs.length];
		for (int i = 0; i < objs.length; i++) {
			Object object = objs[i];
			if (object instanceof CharSequence) {
				css[i] = (CharSequence) object;
			}
		}

		return !isAnyBlank(css);
	}

	/**
	 * 判断所有字符串都不为空
	 * 
	 * @param css
	 * @return
	 */
	public static boolean isNotAnyEmpty(final CharSequence... css) {
		return !isAnyEmpty(css);
	}
	
	public static String containPunctuation(String str){
		
		String[] arr = { ".EXE", ".MSI", ".VBS", ".JS", ".CMD", ".BAT",
				".SCR", ".REG", ".INI", ".INC", ".exe", ".msi", ".vbs", 
				".js", ".cmd", ".bat",".scr", ".reg", ".ini", ".inc" };
		
		if (contains(str, "<")) {
			str = MyStringUtils.replace(str, "<", "《");
			str = MyStringUtils.replace(str, ">", "》");
		}
		
		if (contains(str, arr)) {			
			for (String string : arr) {
				if (contains(str, string)) {
					str = MyStringUtils.replace(str, string, "可执行["+str+"]文件");
				}
			}
		}
		return str;
	}	
	
}
