package com.ylp.date.mgr.relation.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.ylp.date.mgr.relation.RelationType;
import com.ylp.date.mgr.relation.RelationTypeMgr;
import com.ylp.date.server.Server;
import com.ylp.date.util.DomXmlUtil;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
public class RelationTypeMgrImpl implements RelationTypeMgr {
	private Map<Integer, RelationType> types = new HashMap<Integer, RelationType>();

	@Override
	public void load() {
		InputStream stm = RelationTypeMgrImpl.class
				.getResourceAsStream("types.xml");
		try {
			Document dom = DomXmlUtil.getDocument(stm);
			Element root = dom.getRootElement();
			Iterator it = root.elementIterator();
			Element ele;
			while (it.hasNext()) {
				ele = (Element) it.next();
				RelationTypeImpl impl = new RelationTypeImpl();
				Integer typeId = Integer
						.valueOf(ele.attribute("id").getValue());
				impl.setType(typeId);
				impl.setCaption(ele.attributeValue("caption"));
				impl.setHomo(Boolean.valueOf(ele.attributeValue("homo")));
				impl.setRecognize(Integer.valueOf(ele
						.attributeValue("recognize")));
				impl.setBidirectional(Boolean.valueOf(ele
						.attributeValue("bidirectional")));
				types.put(typeId, impl);
			}
		} catch (DocumentException e) {
			Server.getInstance().handleException(e);
		} finally {
			try {
				stm.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public RelationType getType(int type) {
		return types.get(type);
	}

}
