package com.chinawiserv.fwk.core;

import java.util.Iterator;

/**
 * <li>文件名称: CWIterable.java</li>
 * <li>文件描述: 本类描述</li>
 * <li>版权所有: 版权所有(C)2005-2013</li>
 * <li>公司: 勤智数码</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>完成日期：2013-10-27</li>
 * <li>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</li>
 * @version 1.0
 * @author FWK Team
 */
public class CWIterable<T> implements Iterable<T> {
	
	private Iterator<T> iter;
	
	public CWIterable(Iterator<T> _iter) {
		
		iter = _iter;
		
	}
	
	public Iterator<T> iterator() {
		
		return new CWIterator<T>(iter);
	}

}
