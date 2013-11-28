package com.chinawiserv.fwk.event;

import com.chinawiserv.fwk.util.CWIDGenerator;

/**
 * <li>文件名称: CWEvent.java</li>
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
public abstract class CWEvent { 
    
	private long id = 0;
    private static CWIDGenerator idGen = new CWIDGenerator(); 
    
    public CWEvent( )
    {
    	id = idGen.nextID();  
    }
    
    public long getId()
    {
    	if( id == 0)
    		id = idGen.nextID(); 
    	return id;
    }
   
    public abstract String name();
    public abstract int value();
    
}
