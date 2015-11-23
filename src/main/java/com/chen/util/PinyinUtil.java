package com.chen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	private static Logger logger;
	
	static{
		logger = LoggerFactory.getLogger(PinyinUtil.class);
	}
	
	public static String getPinyin(String hanzi){
		if(hanzi == null){
			return null;
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        
        StringBuffer pinyin = new StringBuffer();
        for(int i = 0; i < hanzi.length(); i++){
        	char ch = hanzi.charAt(i);
        	try {
				String[] strings = PinyinHelper.toHanyuPinyinStringArray(ch, format);
				if(strings != null){
					pinyin.append(strings[0]);
				}else{
					pinyin.append(ch);
				}
        	} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.error("对于汉字++ {} ++的转码过程出现错误", hanzi);
				e.printStackTrace();
			}
        }
		return pinyin.toString();
	}
	
}
