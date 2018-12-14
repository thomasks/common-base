package cn.freeexchange.common.base.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class CollectionUtils {
	
	public static boolean isOverlap(Collection<?> list1, Collection<?> list2) {
        if (list1 == list2) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        for (Object element : list1) {
            if (list2.contains(element)) {
                return true;
            }
        }

        return false;
    }
	
	
	public static Set<String> toLowerCase(Set<String> srcList) {
		if (srcList == null) {
			return null;
		}
	    
		Set<String> aimList = new HashSet<String>();
	    
		for (String string : srcList) {
			aimList.add(StringUtils.lowerCase(string));
	    }
	    return aimList;
	}

}
