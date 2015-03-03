package drizzt.sf.dao.support;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {

	public static boolean isEmpty(Object str) {
		if (str == null) {
			return true;
		}
		String trimStr = str.toString().trim();
		return trimStr.length() == 0;
	}

	public static boolean isEqual(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		} else {
			return o1.equals(o2);
		}
	}

	public static String killNull(Object str) {
		return killNull(str, "");
	}

	public static String killNull(Object str, String defaultStr) {
		if (isEmpty(str)) {
			return defaultStr;
		}
		return str.toString().trim();
	}

	/**
	 * 判断字符串str可否转化成数字。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		return str.length() > 0 && str.matches("\\d*\\.{0,1}\\d*") && !".".equals(str);
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		return str.length() > 0 && str.matches("\\d+");
	}

	public static String rename(String filename) {
		int pointPos = filename.lastIndexOf('.');
		int winSplitPos = filename.lastIndexOf('\\');
		int unixSplitPos = filename.lastIndexOf('/');
		if (pointPos < winSplitPos || pointPos < unixSplitPos || pointPos < 0) {
			// 没有后缀名
			return addIndex(filename);
		}
		// 有后缀名
		String prefix = filename.substring(0, pointPos);
		return addIndex(prefix) + filename.substring(pointPos);
	}

	private static String addIndex(String filename) {
		int _pos = filename.lastIndexOf('_');
		if (_pos >= 0) {
			String indexStr = filename.substring(_pos + 1);
			if (isNumber(indexStr)) {
				return filename.substring(0, _pos + 1) + (Integer.valueOf(indexStr) + 1);
			}
		}
		return filename + '_' + 1;
	}

	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List parserString(String str,String tag)
    {
        ArrayList list = new ArrayList();
        if(str == null || str.length()==0)
            return list;
        if (str.indexOf(tag) == -1)
        {
         list.add(str);
         return list;
        }
        
        StringTokenizer st = new StringTokenizer(str, tag, false);
        while (st.hasMoreElements())
        {
         list.add(st.nextToken());
        }  
        return list;
    }
	
	public static String toFileSize(Long fileSize) {
		if (fileSize == null) {
			return "未知";
		}
		final int K = 1024;
		final int M = 1048576;// 1024*1024
		if (fileSize < K) {
			return fileSize.toString() + 'B';
		} else if (fileSize < M) {
			return fileSize / K + "." + fileSize % K * 10 / K + 'K';
		}
		return fileSize / (M) + "." + fileSize % M * 10 / M + 'M';
	}

	public static boolean isVaildDirname(String dirname) {
		return dirname.matches("((\\w+/)|(\\w+\\\\))*\\w+");
	}
	 
	public static StringBuilder delLastChar(StringBuilder str, char ch) {
		if (str.length() < 1) {
			return str;
		}
		if (str.charAt(str.length() - 1) == ch) {
			str.deleteCharAt(str.length() - 1);
		}
		return str;
	}


	public static void main(String[] args) {
		System.out.println(String.format("foo %c", 70));
	}
}
