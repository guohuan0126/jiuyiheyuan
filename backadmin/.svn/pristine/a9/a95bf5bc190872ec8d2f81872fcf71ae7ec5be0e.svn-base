package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.duanrong.business.user.AuthType;
import com.duanrong.jsonservice.util.RegexInput;

/**
 * @Description: 字符串工具类
 * @Author: 林志明
 * @CreateDate: Nov 19, 2014
 */
public class MyStringUtils extends StringUtils {

	/**
	 * <p>
	 * 返回其中不为空的字符串，如果都不为空，则返回第一个。
	 * </p>
	 * 
	 * <pre>
	 * MyStringUtils.getNotBlankStr("abc", "def")	= "abc"
	 * MyStringUtils.getNotBlankStr("abc", null)	= "abc"
	 * MyStringUtils.getNotBlankStr(null, "def")	= "def"
	 * MyStringUtils.getNotBlankStr("abc", "abc")	= "abc"
	 * MyStringUtils.getNotBlankStr(null, null)	= null
	 * </pre>
	 * 
	 * @param str1
	 * @param str2
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
	 * <p>
	 * 判断是否不是数字字符串，并且不符合指定长度
	 * </p>
	 * 
	 * <pre>
	 * isNotNumericSpecifyLength("123", 3)	= false
	 * isNotNumericSpecifyLength("123", 0)	= true
	 * isNotNumericSpecifyLength("abc", 3)	= true
	 * isNotNumericSpecifyLength(null, 6)	= true
	 * isNotNumericSpecifyLength("123abc", 6) = true
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            字符串长度
	 */
	public static Boolean isNotNumericSpecifyLength(final String str,
			final int len) {
		return !isNumericSpecifyLength(str, len);
	}

	/**
	 * <p>
	 * 判断是否为数字字符串，并且符合指定长度
	 * </p>
	 * 
	 * <pre>
	 * isNumericSpecifyLength("123", 3)	= true
	 * isNumericSpecifyLength("123", 0)	= false
	 * isNumericSpecifyLength("abc", 3)	= false
	 * isNumericSpecifyLength(null, 6)	= false
	 * isNumericSpecifyLength("123abc", 6)	= false
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            字符串长度
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
		return replacePattern(str, "<[^>]+>", "");
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
	 * <p>
	 * 忽略大小写的情况下判断原始字符串是否与其他字符串中的任意一个相同
	 * </p>
	 * 
	 * <pre>
	 * equalsIgnoreCaseAnyString(null, null)   = true
	 * equalsIgnoreCaseAnyString("abc", "abc") = true
	 * equalsIgnoreCaseAnyString("abc", "ABC") = true
	 * equalsIgnoreCaseAnyString(null, "abc")  = false
	 * equalsIgnoreCaseAnyString("abc", null)  = false
	 * equalsIgnoreCaseAnyString(null, null, null) = true
	 * equalsIgnoreCaseAnyString("abc", "ABC", "AbC") = true
	 * </pre>
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 */
	public static boolean equalsIgnoreCaseAnyString(
			final CharSequence original, final CharSequence... others) {
		if (others == null) {
			return equalsIgnoreCase(original, null);
		}
		for (CharSequence charSequence : others) {
			if (equalsIgnoreCase(original, charSequence)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * 判断原始字符串是否与其他字符串中的任意一个相同
	 * </p>
	 * 
	 * <pre>
	 * equalsAnyString(null, null)   = true
	 * equalsAnyString("abc", "abc") = true
	 * equalsAnyString("abc", "ABC") = false
	 * equalsAnyString(null, "abc")  = false
	 * equalsAnyString("abc", null)  = false
	 * equalsAnyString(null, null, null) = true
	 * equalsAnyString("abc", "ABC", "AbC") = false
	 * </pre>
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 */
	public static boolean equalsAnyString(final CharSequence original,
			final CharSequence... others) {
		if (others == null) {
			return equalsIgnoreCase(original, null);
		}
		for (CharSequence charSequence : others) {
			if (equals(original, charSequence)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * 忽略大小写的情况下判断原始字符串是否与其他字符串中的任意一个都不相同
	 * </p>
	 * 
	 * <pre>
	 * notEqualsIgnoreCaseAnyString(null, null)   = false
	 * notEqualsIgnoreCaseAnyString("abc", "abc") = false
	 * notEqualsIgnoreCaseAnyString("abc", "ABC") = false
	 * notEqualsIgnoreCaseAnyString(null, "abc")  = true
	 * notEqualsIgnoreCaseAnyString("abc", null)  = true
	 * notEqualsIgnoreCaseAnyString(null, null, null) = false
	 * notEqualsIgnoreCaseAnyString("abc", "ABC", "AbC") = false
	 * </pre>
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 */
	public static boolean notEqualsIgnoreCaseAnyString(
			final CharSequence original, final CharSequence... others) {
		return !equalsIgnoreCaseAnyString(original, others);
	}

	/**
	 * <p>
	 * 判断原始字符串是否与其他字符串中的任意一个都不相同
	 * </p>
	 * 
	 * <pre>
	 * notEqualsAnyString(null, null)   = false
	 * notEqualsAnyString("abc", "abc") = false
	 * notEqualsAnyString("abc", "ABC") = true
	 * notEqualsAnyString(null, "abc")  = true
	 * notEqualsAnyString("abc", null)  = true
	 * notEqualsAnyString(null, null, null) = false
	 * notEqualsAnyString("abc", "ABC", "AbC") = true
	 * </pre>
	 * 
	 * @param original
	 *            原始字符串
	 * @param others
	 *            其他的字符串（一个或多个）
	 */
	public static boolean notEqualsAnyString(final CharSequence original,
			final CharSequence... others) {
		return !equalsAnyString(original, others);
	}

	/**
	 * <p>
	 * 字符串拼接
	 * </p>
	 * 
	 * <pre>
	 * appendIgnoreBlank("abc", "def")	= "abcdef"
	 * appendIgnoreBlank("abc", "abc")	= "abcabc"
	 * appendIgnoreBlank(null, null)	= "nullnull"
	 * appendIgnoreBlank("abc", "")	= "abc"
	 * appendIgnoreBlank("abc", null)	= "abcnull"
	 * appendIgnoreBlank("abc", " ")	= "abc "
	 * </pre>
	 * 
	 * @param css
	 *            需要拼接的字符串
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
	 * <p>
	 * 字符串拼接
	 * </p>
	 * 
	 * <pre>
	 * appendIgnoreBlank("abc", "def")	= "abcdef"
	 * appendIgnoreBlank("abc", "abc")	= "abcabc"
	 * appendIgnoreBlank(null, null)	= ""
	 * appendIgnoreBlank("abc", "")	= "abc"
	 * appendIgnoreBlank("abc", null)	= "abc"
	 * appendIgnoreBlank("abc", " ")	= "abc"
	 * </pre>
	 * 
	 * @param css
	 *            需要拼接的字符串
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
	 * <p>
	 * 字符串拼接
	 * </p>
	 * 
	 * <pre>
	 * appendIgnoreBlank("abc", "def")	= "abcdef"
	 * appendIgnoreBlank("abc", "abc")	= "abcabc"
	 * appendIgnoreBlank(null, null)	= ""
	 * appendIgnoreBlank("abc", "")	= "abc"
	 * appendIgnoreBlank("abc", null)	= "abc"
	 * appendIgnoreBlank("abc", " ")	= "abc "
	 * </pre>
	 * 
	 * @param css
	 *            需要拼接的字符串
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
	 * 验证码相关校验手机号格式与类型是否符合要求
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @param type
	 *            验证码类型
	 * @return
	 */
	public static boolean authAboutFormat(String mobileNumber, String type) {

		if (RegexInput.checkMobilePhone(mobileNumber)) {
			if (equals(type, AuthType.register.toString())
					|| equals(type, AuthType.findPwd.toString())) {
				return true;
			}
		}
		return false;
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
	 * 注册内容格式校验
	 * 
	 * @return
	 */
	public static boolean RegisterRegex(String mobileNumber, String email,
			String password) {
		if (RegexInput.checkMobilePhone(mobileNumber)
				&& RegexInput.checkEmail(email)
				&& RegexInput.checkPassword(password)) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * 判断两个字符串不相等
	 * </p>
	 * 
	 * <pre>
	 * MyStringUtils.notEquals("abc", "ABC")	= true
	 * MyStringUtils.notEquals("abc", "def")	= true
	 * MyStringUtils.notEquals("abc", null)	= true
	 * MyStringUtils.notEquals(null, null)		= true
	 * MyStringUtils.notEquals("abc", "abc")	= false
	 * </pre>
	 * 
	 * @param cs1
	 * @param cs2
	 */
	public static boolean notEquals(final CharSequence cs1,
			final CharSequence cs2) {
		return !equals(cs1, cs2);
	}

	/**
	 * <p>
	 * 判断所有字符串都不为空
	 * </p>
	 * 
	 * <pre>
	 * isNotAnyBlank("foo", "bar")     = true
	 * isNotAnyBlank(null)             = false
	 * isNotAnyBlank(null, "foo")      = false
	 * isNotAnyBlank(null, null)       = false
	 * isNotAnyBlank("", "bar")        = false
	 * isNotAnyBlank("bob", "")        = false
	 * isNotAnyBlank("  bob  ", null)  = false
	 * isNotAnyBlank(" ", "bar")       = false
	 * </pre>
	 * 
	 * @param css
	 *            the CharSequences to check, may be null or empty
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
	 * <p>
	 * 判断所有字符串都不为空
	 * </p>
	 * 
	 * <pre>
	 * isNotAnyEmpty(" ", "bar")       = true
	 * isNotAnyEmpty("foo", "bar")     = true
	 * isNotAnyEmpty(null)             = false
	 * isNotAnyEmpty(null, "foo")      = false
	 * isNotAnyEmpty("", "bar")        = false
	 * isNotAnyEmpty("bob", "")        = false
	 * isNotAnyEmpty("  bob  ", null)  = false
	 * </pre>
	 * 
	 * @param css
	 */
	public static boolean isNotAnyEmpty(final CharSequence... css) {
		return !isAnyEmpty(css);
	}
	/***

	* 验证中文名字

	* @param name

	* @return

	*/

  public static boolean isChineseName(String name) {
		Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
		Matcher matcher = pattern.matcher(name);
		if(matcher.find()){
		return true;
		}
		return false;
  }
  
  public static boolean isContains(String[] str, String str1){
	  for(String s : str){
		  if (s.equals(str1)) return true;
	  }
	  return false;
  }

}
