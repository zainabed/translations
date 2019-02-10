package org.zainabed.projects.translation.model.xml;

import org.zainabed.projects.translation.model.Translation;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "root")
@XmlType(name = "")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResxRoot {

    @XmlElement(name = "data")
    public List<ResxData> resxDatas;

	public List<ResxData> getResxDatas() {
		return resxDatas;
	}

	public void setResxDatas(List<ResxData> resxDatas) {
		this.resxDatas = resxDatas;
	}

    
}
